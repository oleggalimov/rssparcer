package org.oleggalimov.rssreader.endpoints;

import org.oleggalimov.rssreader.da.IFeedRecordsRepository;
import org.oleggalimov.rssreader.dto.FeedRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FeedList {
    private IFeedRecordsRepository repository;

    @Autowired
    public void setRepository(IFeedRecordsRepository repository) {
        this.repository = repository;
    }
    @GetMapping("api/feed/list/")
    public List<FeedRecord> getAllRssRecords() {
        return this.repository.findAll();
    }
}
