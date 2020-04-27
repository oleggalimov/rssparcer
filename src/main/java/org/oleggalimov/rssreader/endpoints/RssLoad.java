package org.oleggalimov.rssreader.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.oleggalimov.rssreader.dto.RSSRecord;
import org.oleggalimov.rssreader.fg.IHttpService;
import org.oleggalimov.rssreader.fg.IXMLConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class RssLoad {
    IHttpService service;
    IXMLConverter converter;

    @Autowired
    public void setService(IHttpService service) {
        this.service = service;
    }
    @Autowired
    public void setConverter(IXMLConverter converter) {
        this.converter = converter;
    }

    @GetMapping("rss/load")
    public String loadRSS() {
        String page = service.getPage("http://feeds.feedburner.com/Baeldung");
        RSSRecord rssRecord = converter.parseXmlString(page);
        return page;
    }
}
