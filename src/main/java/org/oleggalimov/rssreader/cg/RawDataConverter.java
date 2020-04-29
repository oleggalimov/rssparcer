package org.oleggalimov.rssreader.cg;

import org.jsoup.nodes.Document;
import org.oleggalimov.rssreader.dto.RSSRecord;
import org.oleggalimov.rssreader.enumerations.RSSType;
import org.oleggalimov.rssreader.fg.IHTMLDocumentParser;
import org.oleggalimov.rssreader.fg.RBCNewsFeedParser;
import org.oleggalimov.rssreader.fg.RSSDocumentParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawDataConverter {
    RSSDocumentParser rssDocumentParser;
    RBCNewsFeedParser rbcNewsFeedParser;

    @Autowired
    public void setRssDocumentParser(RSSDocumentParser rssDocumentParser) {
        this.rssDocumentParser = rssDocumentParser;
    }
    @Autowired
    public void setRbcNewsFeedParser(RBCNewsFeedParser rbcNewsFeedParser) {
        this.rbcNewsFeedParser = rbcNewsFeedParser;
    }

    public List<RSSRecord> getData(RSSType type, Document data) {
        switch (type) {
            case RSS:
                return rssDocumentParser.parse(data);
            case RBC_NEWS:
                return rbcNewsFeedParser.parse(data);
            default:
                return null;
        }
    }
}
