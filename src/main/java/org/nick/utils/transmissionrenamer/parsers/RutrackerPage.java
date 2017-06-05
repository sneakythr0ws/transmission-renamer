package org.nick.utils.transmissionrenamer.parsers;

import lombok.extern.slf4j.Slf4j;
import org.htmlcleaner.TagNode;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Владимир on 29.03.2015.
 */
@Slf4j
public class RutrackerPage extends TrackerPage {
    public RutrackerPage(String url) {
        super(url);
    }

    @Override
    public Title parseTitle(final TagNode tagNode) {
        final String title = tagNode.getElementListByAttValue("href", getUrl(), true, true).get(0).getText().toString()
                .replaceAll("/", "-").replaceAll("[^- \\[\\]()а-яА-Яa-zA-Z0-9.-]", "");

        final Matcher matcher = Pattern.compile("^(.*) (\\(.*\\)) (\\[.*\\]).*$").matcher(title);
        if (matcher.matches()) {
            return new Title(title, matcher.group(1), matcher.group(2).replaceAll("[(|)]", ""), Short.parseShort(matcher.group(3).substring(1, 5)));
        } else {
            log.error("Doesn't match " + title + " " + getUrl());
            return null;
        }
    }

    @Override
    public String parsePosterImg(final TagNode tagNode) {
        List<? extends TagNode> imgs = tagNode.getElementListByAttValue("class", "postImg postImgAligned img-right", true, true);
        return imgs == null || imgs.size() == 0 ? null : imgs.get(0).getAttributeByName("title");
    }

    @Override
    public String parseSignature() {
        return "http://rutracker.org/";
    }

    @Override
    public String parseCategory(final TagNode tagNode) {
        return tagNode.getElementListByAttValue("class", "nav w100 pad_2", true, true).get(0)
                .getElementListByName("a", false).get(1).getText().toString();
    }
}
