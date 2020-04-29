package org.oleggalimov.rssreader.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.oleggalimov.rssreader.cg.RawDataConverter;
import org.oleggalimov.rssreader.da.IRSSRecordsRepository;
import org.oleggalimov.rssreader.dto.ParseFeedRequest;
import org.oleggalimov.rssreader.dto.RSSRecord;
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
    RawDataConverter dataConverter;
    IRSSRecordsRepository recordsRepository;

    @Autowired
    public void setDataConverter(RawDataConverter dataConverter) {
        this.dataConverter = dataConverter;
    }
    @Autowired
    public void setRecordsRepository(IRSSRecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    @PostMapping("api/rss/load")
    public boolean loadRSS(@RequestBody ParseFeedRequest request) throws IOException {
        log.debug(request.toString());
        if (request.getUrl()==null || request.getExtractingRule()==null) {
            return false;
        }
        Document document = Jsoup.parse(new URL(request.getUrl()), 60000);
        List<RSSRecord> data = dataConverter.getData(request.getExtractingRule(), document);
        if (data==null) {
            return false;
        }
        recordsRepository.saveAll(data);
        return true;
    }

}
