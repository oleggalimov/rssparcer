package org.oleggalimov.rssreader.endpoints;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.oleggalimov.rssreader.cg.RawDataConverter;
import org.oleggalimov.rssreader.da.RSSRecordsRepository;
import org.oleggalimov.rssreader.dto.RSSChannel;
import org.oleggalimov.rssreader.dto.RSSRecord;
import org.oleggalimov.rssreader.enumerations.RSSType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class RssLoad {
    RawDataConverter dataConverter;
    RSSRecordsRepository recordsRepository;

    @Autowired
    public void setDataConverter(RawDataConverter dataConverter) {
        this.dataConverter = dataConverter;
    }
    @Autowired
    public void setRecordsRepository(RSSRecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    @GetMapping("rss/load")

    public boolean loadRSS() throws IOException {
        Document document = Jsoup.parse(new URL("https://news.yandex.ru/science.rss"), 60000);
        List<RSSRecord> data = dataConverter.getData(RSSType.HTML, document);
        recordsRepository.saveAll(data);
        return true;
    }

}
