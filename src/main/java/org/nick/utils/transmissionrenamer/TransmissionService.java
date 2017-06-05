package org.nick.utils.transmissionrenamer;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import lombok.extern.slf4j.Slf4j;
import org.nick.utils.transmissionrenamer.domain.transmission.Response;
import org.nick.utils.transmissionrenamer.domain.transmission.Torrent;
import org.nick.utils.transmissionrenamer.parsers.RutrackerPage;
import org.nick.utils.transmissionrenamer.parsers.TrackerPage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.util.Optional;

@Slf4j
@Service
public class TransmissionService {

    private static final String TOKEN_HEADER_NAME = "X-Transmission-Session-Id";

    private final RestTemplate restTemplate;
    private final String rpcUrl;
    private final String storagePath;
    private String token;

    TransmissionService(@Value("${conf.rpc.url:http://192.168.22.245:9091/transmission/rpc}") String rpcUrl,
                        @Value("${conf.storage.path:cifs://192.168.22.245/}") String storagePath) {
        this.restTemplate = new RestTemplate();
        this.rpcUrl = rpcUrl;
        this.storagePath = storagePath;
    }

    private String getCsrfToken() {
        if (token != null) return token;

        try {
            throw new RuntimeException("Token not found " + restTemplate.getForObject(rpcUrl, String.class));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                token = e.getResponseHeaders().get(TOKEN_HEADER_NAME).get(0);
                return token;
            } else {
                throw e;
            }
        }
    }

    private Optional<TrackerPage> getSite(final Torrent torrent) {
        Assert.notNull(torrent, "Torrent is null");
        Assert.notNull(torrent.getComment(), "Torrent comment is null");

        if (torrent.getComment().toLowerCase().contains("http://rutracker.org/")) {
            return Optional.of(new RutrackerPage(torrent.getComment()));
        /*} else if (torrent.getComment().toLowerCase().contains("http://nnm-club.me/")) {
            page = new NNMPage(torrent.getComment());*/
        } else {
            log.debug("Unknown tracker " + torrent.getComment());
            return Optional.empty();
        }
    }

    private Optional<SmbFile> getStoragePath(final String downloadDir, final String fileName) {
        Assert.isTrue(!StringUtils.isEmpty(downloadDir), "Download dir is null");
        Assert.isTrue(!StringUtils.isEmpty(fileName), "Name is null");

        try {
            return Optional.of(new SmbFile(downloadDir.replace("/var/lib/transmission-daemon/downloads/", storagePath) + "/" + fileName));
        } catch (MalformedURLException e) {
            return Optional.empty();
        }
    }

    public void rename() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(TOKEN_HEADER_NAME, getCsrfToken());

        final Response response = restTemplate.exchange(rpcUrl, HttpMethod.POST, new HttpEntity<>("{\"method\":\"torrent-get\",\"arguments\":{\"fields\":[\"id\",\"title\",\"errorString\",\"error\",\"status\",\"comment\",\"downloadDir\",\"files\"]}}", httpHeaders), Response.class).getBody();


        if (response.getResult().equals("success")) {
            response.getArguments().getTorrents().forEach(torrent -> {
                if (torrent.getStatus() == 0L) {
                    getSite(torrent).ifPresent(torrentPage -> {
                        if (torrentPage.isMovie()) {
                            processMovie(torrent, torrentPage);
                        }
                    });
                }
            });
        }
    }

    private void processMovie(Torrent torrent, TrackerPage torrentPage) {
        final String name = processName(torrentPage);

        torrent.getFiles().forEach(file -> {
            getStoragePath(torrent.getDownloadDir(), file.getName()).ifPresent(storagePath -> {
                try {
                    if (storagePath.exists()) {
                        log.info(torrentPage.getUrl() + " " + storagePath.getPath());
                        log.warn(name.length() + " " + name);
                    }
                } catch (SmbException e) {
                    log.error("File error", e);
                }
            });
        });
    }

    private String processName(TrackerPage torrentPage) {
        if (torrentPage.getTitle().length() > 188) {
            final Optional<TrackerPage.ParsedTitle> parsedTitleOptional = torrentPage.parseTitle();
            if (parsedTitleOptional.isPresent()) {
                return removeNotPathCharacters(parsedTitleOptional.get().getName() + " (" + parsedTitleOptional.get().getDirector() + ") [" + parsedTitleOptional.get().getYear() + ", " + parsedTitleOptional.get().getCountry() + "]");
            } else {
                throw new RuntimeException("Title is not parsed");
            }
        } else {
            return removeNotPathCharacters(torrentPage.getTitle());
        }
    }

    private String removeNotPathCharacters(final String string) {
        return string.replaceAll("/", "-").replaceAll("[^- \\[\\]()а-яА-Яa-zA-Z0-9.-]", "");
    }
}
