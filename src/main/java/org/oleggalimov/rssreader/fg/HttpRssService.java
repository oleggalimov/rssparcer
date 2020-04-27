package org.oleggalimov.rssreader.fg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Service
public class HttpRssService implements IHttpService {

    @Override
    public String getPage(String url) {

        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);
        log.debug("Got response: {}", forObject);
        return Objects.requireNonNull(forObject);
    }
}
