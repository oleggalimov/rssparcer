package org.oleggalimov.rssreader.cg;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.oleggalimov.rssreader.dto.ExtractingRule;
import org.oleggalimov.rssreader.dto.FeedRecord;
import org.oleggalimov.rssreader.fg.HTMLDocumentParser;
import org.oleggalimov.rssreader.fg.IHTMLDocumentParser;
import org.oleggalimov.rssreader.fg.IRSSParser;
import org.oleggalimov.rssreader.fg.RSSParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CgRawDataConverter {
    IHTMLDocumentParser ihtmlDocumentParser;
    IRSSParser rssDocumentParser;

    @Autowired
    public void setHTMLDocumentParser(HTMLDocumentParser HTMLDocumentParser) {
        this.ihtmlDocumentParser = HTMLDocumentParser;
    }

    @Autowired
    public void setRssDocumentParser(RSSParser rssDocumentParser) {
        this.rssDocumentParser = rssDocumentParser;
    }

    public List<FeedRecord> convert(ExtractingRule rule, Document data) {
        log.debug("Extracting rule: {}", rule);
        if (rule.getRss()) {
            return rssDocumentParser.parse(data);
        } else {
            return ihtmlDocumentParser.parse(rule, data);
        }
    }
}
