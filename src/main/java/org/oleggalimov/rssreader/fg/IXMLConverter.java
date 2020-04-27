package org.oleggalimov.rssreader.fg;

import org.oleggalimov.rssreader.dto.RSSRecord;

public interface IXMLConverter {
    RSSRecord parseXmlString(String xml);
}
