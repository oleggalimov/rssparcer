package org.oleggalimov.rssreader.cg;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Slf4j
@Service
public class CgRawDataLoader {
    public Document loadDataFromUrl(String url) {
        try {
            return Jsoup.parse(new URL(url), 60000);
        } catch (IOException e) {
            log.error("Error on loading data from remote source", e);
            e.printStackTrace();
            return null;
        }
    }
}
