package org.oleggalimov.rssreader.fg;

import org.jsoup.nodes.Document;
import org.oleggalimov.rssreader.dto.FeedRecord;

import java.util.List;

public interface IRSSParser {
    List<FeedRecord> parse(Document document);
}
