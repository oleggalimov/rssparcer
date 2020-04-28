package org.oleggalimov.rssreader.cg;

import org.jsoup.nodes.Document;
import org.oleggalimov.rssreader.dto.RSSRecord;
import org.oleggalimov.rssreader.enumerations.RSSType;
import org.oleggalimov.rssreader.fg.IDocumentParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawDataConverter {
    IDocumentParser htmlDocumentParser;

    @Autowired
    public void setHtmlDocumentParser(IDocumentParser htmlDocumentParser) {
        this.htmlDocumentParser = htmlDocumentParser;
    }

    public List<RSSRecord> getData(RSSType type, Object data) {
        switch (type) {
            case HTML:
                return htmlDocumentParser.parse((Document) data);
            default:
                return null;
        }
    }
}
