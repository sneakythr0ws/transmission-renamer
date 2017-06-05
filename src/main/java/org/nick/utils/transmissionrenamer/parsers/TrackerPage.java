package org.nick.utils.transmissionrenamer.parsers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;

@Slf4j
@Data
public abstract class TrackerPage {
    private String url;

    private String title;
    private String postImg;
    private List<String> category;

    public TrackerPage(final String url) {
        this.url = url;

        final CleanerProperties props = new CleanerProperties();
        props.setTransResCharsToNCR(true);
        props.setTransSpecialEntitiesToNCR(true);
        props.setTranslateSpecialEntities(true);

        try (final InputStream is = new URL(url).openStream()) {
            final TagNode tagNode = new HtmlCleaner(props).clean(is, "Windows-1251");

            this.title = findTitle(tagNode);
            this.postImg = findPosterImg(tagNode);
            this.category = findCategories(tagNode);
        } catch (IOException e) {
            throw new RuntimeException("Didn't parsed " + url);
        }
    }

    public abstract String findTitle(TagNode tagNode);

    public abstract String findPosterImg(TagNode tagNode);

    public abstract String getSignature();

    public abstract List<String> findCategories(TagNode tagNode);

    public abstract Optional<ParsedTitle> parseTitle();

    public abstract boolean isMovie();

    @Data
    @AllArgsConstructor
    public static class ParsedTitle {
        private String name;
        private String director;
        private Short year;
        private String country;
    }
}
