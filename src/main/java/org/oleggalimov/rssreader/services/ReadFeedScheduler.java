package org.oleggalimov.rssreader.services;

import lombok.extern.slf4j.Slf4j;
import org.oleggalimov.rssreader.cg.CgRawDataConverter;
import org.oleggalimov.rssreader.cg.CgRawDataLoader;
import org.oleggalimov.rssreader.da.IFeedRecordsRepository;
import org.oleggalimov.rssreader.dto.FeedRecord;
import org.oleggalimov.rssreader.dto.request.ExtractingRule;
import org.oleggalimov.rssreader.tasks.LoadFeedTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


@Slf4j
@Service
@EnableScheduling
public class ReadFeedScheduler {
    ConcurrentHashMap<String, ExtractingRule> feedMap;
    CgRawDataLoader cgRawDataLoader;
    CgRawDataConverter cgRawDataConverter;
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    IFeedRecordsRepository recordsRepository;

    @Autowired
    public void setFeedMap(ConcurrentHashMap<String, ExtractingRule> feedMap) {
        this.feedMap = feedMap;
    }

    @Autowired
    public void setCgRawDataLoader(CgRawDataLoader cgRawDataLoader) {
        this.cgRawDataLoader = cgRawDataLoader;
    }

    @Autowired
    public void setCgRawDataConverter(CgRawDataConverter cgRawDataConverter) {
        this.cgRawDataConverter = cgRawDataConverter;
    }

    @Autowired
    public void setRecordsRepository(IFeedRecordsRepository recordsRepository) {
        this.recordsRepository = recordsRepository;
    }

    @Scheduled(fixedRate = 180000)
    public void loadFeed() {
        log.info("Tasks: {}", feedMap.size());
        Map<String,  Future<List<FeedRecord>>> futureList = new HashMap<>();
        feedMap.forEach((url, extractingRule) -> {
                    LoadFeedTask task = new LoadFeedTask(cgRawDataLoader, cgRawDataConverter, extractingRule, url);
                    Future<List<FeedRecord>> future = executorService.submit(task);
                    futureList.put(url, future);
                }
        );
        futureList.forEach((url, future) -> {
            try {
                List<FeedRecord> feedRecords = future.get(160, TimeUnit.SECONDS);
                saveData(feedRecords);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                log.error("Error on task for url: {}", url);
            }
        });
        log.info("End of task: {}", feedMap.size());
    }
//    @Transactional
    private void saveData(List<FeedRecord> records) {
        log.info("Saving {} records", records.size());
        recordsRepository.saveAll(records);
    }
}
