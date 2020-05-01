package org.oleggalimov.rssreader.fg;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.oleggalimov.rssreader.dto.FeedRecord;
import org.oleggalimov.rssreader.dto.FeedChannel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RSSParser implements IRSSParser {
    @Override
    public List<FeedRecord> parse(Document document) {
        //ищем описание канала
        Elements channel = document.getElementsByTag("channel");
        if (channel == null || channel.size() == 0) {
            return null; //<channel> не найден либо не имеет элементов внутри
        }
        Elements children = channel.get(0).children();
        if (children == null || children.size() == 0) {
            return null; //в <channel> вложен пустой элемент
        }
        FeedChannel channelObject = new FeedChannel();
        List<FeedRecord> feedRecords = new ArrayList<>();
        children.forEach(element -> {
            String tagName = element.tagName();
            switch (tagName) {
                case "title": {
                    channelObject.setChannelTitle(element.text());
                    break;
                }
                case "description": {
                    channelObject.setChannelDescription(element.text());
                    break;
                }
                case "link": {
                    channelObject.setChannelLink(element.text());
                    break;
                }
                case "item": {
                    feedRecords.add(
                            FeedRecord.builder()
                                    .guid(nodeToString(element.getElementsByTag("guid")))
                                    .title(nodeToString(element.getElementsByTag("title")))
                                    .link(nodeToString(element.getElementsByTag("link")))
                                    .pubDate(nodeToString(element.getElementsByTag("pubDate")))
                                    .description(nodeToString(element.getElementsByTag("description")))
                                    .build()
                    );
                    break;
                }
            }

        });
        if (!feedRecords.isEmpty()) {
            feedRecords.forEach(rssRecord -> rssRecord.setChannel(channelObject));
        }
        return feedRecords;
    }

    private String nodeToString(Elements element) {
        return element == null ? "No data" : element.text();
    }
}
