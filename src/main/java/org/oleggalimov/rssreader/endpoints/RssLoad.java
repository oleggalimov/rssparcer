package org.oleggalimov.rssreader.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.oleggalimov.rssreader.cg.FgRawDataConverter;
import org.oleggalimov.rssreader.da.IFeedRecordsRepository;
import org.oleggalimov.rssreader.dto.ParseFeedRequest;
import org.oleggalimov.rssreader.dto.FeedRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@RestController
@Slf4j
public class RssLoad {
    FgRawDataConverter fgRawDataConverter;
    IFeedRecordsRepository recordsRepository;

    @Autowired
    public void setFgRawDataConverter(FgRawDataConverter fgRawDataConverter) {
        this.fgRawDataConverter = fgRawDataConverter;
    }

    @Autowired
    public void setRecordsRepository(org.oleggalimov.rssreader.da.IFeedRecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    @PostMapping("api/rss/load")
    public boolean loadRSS(@RequestBody ParseFeedRequest request) throws IOException {
        log.debug(request.toString());
        if (request.getUrl()==null || request.getExtractingRule()==null) {
            return false;
        }
        Document document = Jsoup.parse(new URL(request.getUrl()), 60000);
        List<FeedRecord> data = fgRawDataConverter.convert(request.getExtractingRule(), document);
        if (data==null) {
            return false;
        }
        try {
            recordsRepository.saveAll(data);
        } catch (Exception ex) {
            log.error("Saving data error",ex.getCause());
            return false;
        }
        return true;
    }

}
