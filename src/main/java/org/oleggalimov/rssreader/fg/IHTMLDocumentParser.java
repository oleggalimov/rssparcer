package org.oleggalimov.rssreader.fg;

import org.jsoup.nodes.Document;
import org.oleggalimov.rssreader.dto.request.ExtractingRule;
import org.oleggalimov.rssreader.dto.FeedRecord;

import java.util.List;

public interface IHTMLDocumentParser {
    List<FeedRecord> parse (ExtractingRule rule, Document data);
}
