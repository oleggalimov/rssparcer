package org.oleggalimov.rssreader.fg;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.oleggalimov.rssreader.dto.RSSChannel;
import org.oleggalimov.rssreader.dto.RSSRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RSSDocumentParser implements IHTMLDocumentParser {
    @Override
    public List<RSSRecord> parse(Document document) {
        //ищем описание канала
        Elements channel = document.getElementsByTag("channel");
        if (channel==null || channel.size()==0) {
            return null; //<channel> не найден либо не имеет элементов внутри
        }
        Elements children = channel.get(0).children();
        if (children==null || children.size()==0) {
            return null; //в <channel> вложен пустой элемент
        }
        RSSChannel channelObject=new RSSChannel();
        List<RSSRecord> rssRecords = new ArrayList<>();
        children.forEach(element -> {
            String tagName = element.tagName();
            switch (tagName) {
                case "title": {
                    channelObject.setChannelTitle(element.text());
                    break;
                }
                case "description":{
                    channelObject.setChannelDescription(element.text());
                    break;
                }
                case "link":{
                    channelObject.setChannelLink(element.text());
                    break;
                }
                case "item": {
                    rssRecords.add(
                            RSSRecord.builder()
                                    .id(nodeToString(element.getElementsByTag("guid")))
                                    .title(nodeToString(element.getElementsByTag("title")))
                                    .link(nodeToString(element.getElementsByTag("link")))
                                    .pubDate(nodeToString(element.getElementsByTag("pubDate")))
                                    .guid(nodeToString(element.getElementsByTag("guid")))
                                    .description(nodeToString(element.getElementsByTag("description")))
                                    .build()
                    );
                    break;
                }
            }

        });
        if (!rssRecords.isEmpty()) {
            rssRecords.forEach(rssRecord -> rssRecord.setChannel(channelObject));
        };
        return rssRecords;
    };
    private String nodeToString(Elements element) {
        return element == null ? "No data" : element.text();
    }
}
