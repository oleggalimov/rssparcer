package org.oleggalimov.rssreader.endpoints;

import org.oleggalimov.rssreader.da.IFeedRecordsRepository;
import org.oleggalimov.rssreader.dto.FeedRecord;
import org.oleggalimov.rssreader.dto.request.RecordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FindRecords {
    IFeedRecordsRepository recordsRepository;

    @Autowired
    public void setRecordsRepository(IFeedRecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }
    @PostMapping ("api/feed/find")
    public List<FeedRecord> findRecordsByTitle(@RequestBody RecordRequest recordRequest) {
        return recordsRepository.findAllByTitleContains(recordRequest.getTitle());
    }
}
