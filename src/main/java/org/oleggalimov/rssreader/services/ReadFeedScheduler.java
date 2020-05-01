package org.oleggalimov.rssreader.services;

import lombok.extern.slf4j.Slf4j;
import org.oleggalimov.rssreader.cg.CgRawDataConverter;
import org.oleggalimov.rssreader.cg.CgRawDataLoader;
import org.oleggalimov.rssreader.da.IFeedRecordsRepository;
import org.oleggalimov.rssreader.dto.request.ExtractingRule;
import org.oleggalimov.rssreader.tasks.LoadFeedTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
    void loadFeed() {
        log.info("Tasks: {}", feedMap.size());
        Map<String, Future<Boolean>> futureList = new HashMap<>();
        feedMap.forEach((url, extractingRule) -> {
                    LoadFeedTask task = new LoadFeedTask(cgRawDataLoader, cgRawDataConverter, extractingRule, recordsRepository, url);
                    Future<Boolean> future = executorService.submit(task);
                    futureList.put(url, future);
                }
        );
        futureList.forEach((url, booleanFuture) -> {
            try {
                booleanFuture.get(160, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                log.error("Error on task for url: {}", url);
            }
        });
        log.info("End of task: {}", feedMap.size());

    }
}
