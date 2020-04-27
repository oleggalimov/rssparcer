package org.oleggalimov.rssreader.endpoints;

import org.oleggalimov.rssreader.da.RSSRecordsRepository;
import org.oleggalimov.rssreader.dto.RSSRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RssList {
    private RSSRecordsRepository repository;

    @Autowired
    public void setRepository(RSSRecordsRepository repository) {
        this.repository = repository;
    }
    @GetMapping("rss/list/all")
    public List<RSSRecord> getAllRssRecords() {
        return this.repository.findAll();
    }
}
