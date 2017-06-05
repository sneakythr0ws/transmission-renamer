package org.nick.utils.transmissionrenamer.parsers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Slf4j
@Data
public abstract class TrackerPage {
    private String url;

    private Title title;
    private String postImg;
    private String signature;
    private String category;

    public TrackerPage(final String url) {
        this.url = url;

        final CleanerProperties props = new CleanerProperties();
        props.setTransResCharsToNCR(true);
        props.setTransSpecialEntitiesToNCR(true);
        props.setTranslateSpecialEntities(true);

        try (final InputStream is = new URL(url).openStream()) {
            final TagNode tagNode = new HtmlCleaner(props).clean(is, "Windows-1251");

            this.title = parseTitle(tagNode);
            this.postImg = parsePosterImg(tagNode);
            this.signature = parseSignature();
            this.category = parseCategory(tagNode);
        } catch (IOException e) {
            throw new RuntimeException("Didn't parsed " + url);
        }
    }

    public abstract Title parseTitle(TagNode tagNode);

    public abstract String parsePosterImg(TagNode tagNode);

    public abstract String parseSignature();

    public abstract String parseCategory(TagNode tagNode);


    @Data
    @AllArgsConstructor
    public static class Title {
        private String title;
        private String name;
        private String director;
        private Short year;
    }
}
