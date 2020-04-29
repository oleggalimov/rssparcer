package org.oleggalimov.rssreader.endpoints;

import org.oleggalimov.rssreader.da.IRSSRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RssDrop {
    IRSSRecordsRepository irssRecordsRepository;

    @Autowired
    public void setIRssRecordsRepository(IRSSRecordsRepository irssRecordsRepository) {
        this.irssRecordsRepository = irssRecordsRepository;
    }

    @GetMapping ("api/rss/delete")
    public boolean dropAllCollection() {
        irssRecordsRepository.deleteAll();
        return true;
    }
}
