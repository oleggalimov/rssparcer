package org.oleggalimov.rssreader.endpoints;

import org.oleggalimov.rssreader.da.IFeedRecordsRepository;
import org.oleggalimov.rssreader.dto.request.RecordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DeleteRecords {
    IFeedRecordsRepository IFeedRecordsRepository;

    @Autowired
    public void setIRssRecordsRepository(IFeedRecordsRepository IFeedRecordsRepository) {
        this.IFeedRecordsRepository = IFeedRecordsRepository;
    }

    @GetMapping ("api/feed/delete")
    public boolean dropAllRecords() {
        IFeedRecordsRepository.deleteAll();
        return true;
    }

    @PostMapping("api/feed/delete/")
    public boolean deleteRecord(@RequestBody RecordRequest recordRequest) {
        IFeedRecordsRepository.deleteById(recordRequest.getGuid());
        return true;
    }
}
