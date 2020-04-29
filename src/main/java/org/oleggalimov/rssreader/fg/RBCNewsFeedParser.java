package org.oleggalimov.rssreader.fg;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.oleggalimov.rssreader.dto.RSSRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RBCNewsFeedParser implements IHTMLDocumentParser{
    @Override
    public List<RSSRecord> parse(Document document) {
        Elements itemListElement = document.getElementsByAttributeValueMatching("class", "item__wrap l-col-center");
        itemListElement.forEach(element -> {

            /*
            element.getElementsMatchingOwnText("[0-9]:").text()
            element.getElementsByAttributeValueMatching("class", "item__link").attr("href").toString()
            element.getElementsByAttributeValueMatching("class", "item__title rm-cm-item-text").text()
             */


            System.out.print("link: "+element.getElementsByClass("item__link").attr("href"));
            System.out.print(" title "+element.text());
            System.out.print(" time: "+element.getElementsMatchingOwnText("[0-9]{2}:[0-9]{2}").text());
            System.out.println();
        });
        return null;
    }
}
