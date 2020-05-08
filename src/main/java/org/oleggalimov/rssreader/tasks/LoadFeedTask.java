package org.oleggalimov.rssreader.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.oleggalimov.rssreader.cg.CgRawDataConverter;
import org.oleggalimov.rssreader.cg.CgRawDataLoader;
import org.oleggalimov.rssreader.da.IFeedRecordsRepository;
import org.oleggalimov.rssreader.dto.request.ExtractingRule;
import org.oleggalimov.rssreader.dto.FeedRecord;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class LoadFeedTask implements Callable< List <FeedRecord>> {
    private CgRawDataLoader loader;
    private CgRawDataConverter converter;
    private ExtractingRule rule;
    private String url;

    @Override
    public List <FeedRecord> call() {
        log.debug("Starting scheduled task");
        try{
            log.debug("Loading data from {}", url);
            Document document = loader.loadDataFromUrl(url);
            if (document == null) {
                log.error("Empty data from raw document");
                return Collections.emptyList();
            }
            log.debug("Converting raw data");
            List<FeedRecord> feedRecords = converter.convert(rule, document);
            if (feedRecords==null || feedRecords.isEmpty()) {
                log.error("No data from document");
                return Collections.emptyList();
            }
           return feedRecords;
        } catch (Exception ex) {
            log.error("Exception in task for URL {}", url, ex);
            return Collections.emptyList();
        }
    }
}
