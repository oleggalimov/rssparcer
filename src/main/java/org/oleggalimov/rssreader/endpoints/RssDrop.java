package org.oleggalimov.rssreader.endpoints;

import org.oleggalimov.rssreader.da.IFeedRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RssDrop {
    IFeedRecordsRepository IFeedRecordsRepository;

    @Autowired
    public void setIRssRecordsRepository(IFeedRecordsRepository IFeedRecordsRepository) {
        this.IFeedRecordsRepository = IFeedRecordsRepository;
    }

    @GetMapping ("api/rss/delete")
    public boolean dropAllCollection() {
        IFeedRecordsRepository.deleteAll();
        return true;
    }
}
