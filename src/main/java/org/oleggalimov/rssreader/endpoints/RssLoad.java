package org.oleggalimov.rssreader.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.oleggalimov.rssreader.cg.CgRawDataConverter;
import org.oleggalimov.rssreader.da.IFeedRecordsRepository;
import org.oleggalimov.rssreader.dto.ExtractingRule;
import org.oleggalimov.rssreader.dto.ParseFeedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@Slf4j
public class RssLoad {
    CgRawDataConverter cgRawDataConverter;
    IFeedRecordsRepository recordsRepository;
    ConcurrentHashMap<String, ExtractingRule> feedMap;

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

    @PostMapping("api/rss/load")
    public boolean loadRSS(@RequestBody ParseFeedRequest request) throws IOException {
        log.debug(request.toString());
        if (request.getUrl()==null || request.getExtractingRule()==null) {
            return false;
        }
        feedMap.put(request.getUrl(), request.getExtractingRule());
        return true;
    }

}
