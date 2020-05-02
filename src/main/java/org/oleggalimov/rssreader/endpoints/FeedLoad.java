package org.oleggalimov.rssreader.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.oleggalimov.rssreader.cg.CgRawDataConverter;
import org.oleggalimov.rssreader.da.IFeedRecordsRepository;
import org.oleggalimov.rssreader.dto.request.ExtractingRule;
import org.oleggalimov.rssreader.dto.request.AddFeedRequest;
import org.oleggalimov.rssreader.services.ReadFeedScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
public class FeedLoad {
    CgRawDataConverter cgRawDataConverter;
    IFeedRecordsRepository recordsRepository;
    ConcurrentHashMap<String, ExtractingRule> feedMap;
    ReadFeedScheduler readFeedScheduler;

    @Autowired
    public void setCgRawDataConverter(CgRawDataConverter cgRawDataConverter) {
        this.cgRawDataConverter = cgRawDataConverter;
    }

    @Autowired
    public void setRecordsRepository(org.oleggalimov.rssreader.da.IFeedRecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    @Autowired
    public void setFeedMap(ConcurrentHashMap<String, ExtractingRule> feedMap) {
        this.feedMap = feedMap;
    }

    @Autowired
    public void setReadFeedScheduler(ReadFeedScheduler readFeedScheduler) {
        this.readFeedScheduler = readFeedScheduler;
    }



    @PostMapping("api/feed/load")
    public String loadRSS(@RequestBody AddFeedRequest request) throws IOException {
        log.debug(request.toString());
        if (request.getUrl()==null || request.getExtractingRule()==null) {
            return "Bad request: url or rule is null.";
        }
        try {
            feedMap.put(request.getUrl(), request.getExtractingRule());
            readFeedScheduler.loadFeed();
            return "Success. Feed will updated in 3 minutes";
        } catch (Exception ex) {
            log.error("Failed to put data to feed map", ex);
            return "Fail. See logs for error.";
        }
    }

}
