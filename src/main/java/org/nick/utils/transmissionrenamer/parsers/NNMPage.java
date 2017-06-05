package org.nick.utils.transmissionrenamer.parsers;

import org.htmlcleaner.TagNode;

import java.util.List;

//public class NNMPage extends TrackerPage {
public class NNMPage {
    public NNMPage(String url) {
//        super(url);
    }

    //    @Override
    public String parseName(final TagNode tagNode) {
        List<? extends TagNode> titles = tagNode.getElementListByName("title", true);
        if (titles == null || titles.size() == 0) {
            return null;
        } else {
            String title = titles.get(0).getText().toString();
            int i = title.indexOf("torrent ::");
            if (i != -1) {
                title = title.substring(0, i).trim();
            }

            return title.replaceAll("&quot;", "");
        }
    }

    //    @Override
    public String parsePostImg(final TagNode tagNode) {
        List<? extends TagNode> imgs = tagNode.getElementListByAttValue("class", "postImg postImgAligned img-right", true, true);
        return imgs == null || imgs.size() == 0 ? null : "http://nnm-club.me/" + imgs.get(0).getAttributeByName("title");
    }


    //    @Override
    public String parseSignature() {
        return "http://nnm-club.me/";
    }

//    @Override
    /*public String findCategory(final TagNode tagNode) {
        List<? extends TagNode> categories = tagNode.getElementListByAttValue("href", getUrl(), true, true);
        return categories == null || categories.size() == 0 ? null : categories.get(0).getText().toString();
    }*/
}
