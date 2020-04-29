package org.oleggalimov.rssreader.endpoints;

import org.oleggalimov.rssreader.da.IRSSRecordsRepository;
import org.oleggalimov.rssreader.dto.RSSRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RssList {
    private IRSSRecordsRepository repository;

    @Autowired
    public void setRepository(IRSSRecordsRepository repository) {
        this.repository = repository;
    }
    @GetMapping("api/rss/list/")
    public List<RSSRecord> getAllRssRecords() {
        return this.repository.findAll();
    }
}
