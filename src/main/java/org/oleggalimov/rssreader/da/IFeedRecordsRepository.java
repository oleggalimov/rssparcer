package org.oleggalimov.rssreader.da;

import org.oleggalimov.rssreader.dto.FeedRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFeedRecordsRepository extends JpaRepository <FeedRecord, String> {

    List<FeedRecord> findAllByTitleContains(String substring);
}
