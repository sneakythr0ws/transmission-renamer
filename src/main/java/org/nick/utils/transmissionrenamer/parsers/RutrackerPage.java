package org.nick.utils.transmissionrenamer.parsers;

import lombok.extern.slf4j.Slf4j;
import org.htmlcleaner.TagNode;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by Владимир on 29.03.2015.
 */
@Slf4j
public class RutrackerPage extends TrackerPage {
    RutrackerPage() {
    }

    public RutrackerPage(String url) {
        super(url);
    }

    @Override
    public String findTitle(final TagNode tagNode) {
        String title = tagNode.getElementListByAttValue("href", getUrl(), true, true).get(0).getText().toString();

        return (title.contains("]")) ? title.substring(0, title.indexOf("]") + 1) : title;
    }

    @Override
    public Optional<ParsedTitle> parseTitle() {
        final Matcher matcher = Pattern.compile("^(.*) (\\(.*\\)) (\\[.*\\])$").matcher(getTitle());
        if (matcher.matches()) {
            return Optional.of(new ParsedTitle(
                    matcher.group(1),
                    matcher.group(2).replaceAll("[(|)]", ""),
                    Short.parseShort(matcher.group(3).substring(1, 5)),
                    matcher.group(3).split(", ")[1]));
        } else {
            log.error("Doesn't match " + getTitle() + " " + getUrl());
            return Optional.empty();
        }
    }

    @Override
    public boolean isMovie() {
        Assert.notNull(getCategories(), "Category not found");

        return getCategories().get(1).equals("Кино, Видео и ТВ") && Arrays.asList("Зарубежное кино", "Наше кино", "HD Video", "Мультфильмы").contains(getCategories().get(2));
    }

    @Override
    public String findPosterImg(final TagNode tagNode) {
        List<? extends TagNode> imgs = tagNode.getElementListByAttValue("class", "postImg postImgAligned img-right", true, true);
        return imgs == null || imgs.size() == 0 ? null : imgs.get(0).getAttributeByName("title");
    }

    @Override
    public String getSignature() {
        return "http://rutracker.org/";
    }

    @Override
    public List<String> findCategories(final TagNode tagNode) {
        return tagNode.getElementListByAttValue("class", "nav w100 pad_2", true, true).get(0)
                .getElementListByName("a", false).stream().map(TagNode::getText).map(CharSequence::toString).collect(Collectors.toList());
    }
}
