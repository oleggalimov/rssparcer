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

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
public class LoadFeedTask implements Callable<Boolean> {
    private CgRawDataLoader loader;
    private CgRawDataConverter converter;
    private ExtractingRule rule;
    private IFeedRecordsRepository recordsRepository;
    private String url;

    @Override
    public Boolean call() {
        log.debug("Starting scheduled task");
        try{
            log.debug("Loading data from {}", url);
            Document document = loader.loadDataFromUrl(url);
            if (document == null) {
                log.error("Empty data from raw document");
                return Boolean.FALSE;
            }
            log.debug("Converting raw data");
            List<FeedRecord> feedRecords = converter.convert(rule, document);
            if (feedRecords==null || feedRecords.isEmpty()) {
                log.error("No data from document");
                return Boolean.FALSE;
            }
            log.debug("Saving {} records", feedRecords.size());
            recordsRepository.saveAll(feedRecords);
            return Boolean.TRUE;
        } catch (Exception ex) {
            log.error("Exception in task for URL {}", url, ex);
            return Boolean.FALSE;
        }
    }
}
