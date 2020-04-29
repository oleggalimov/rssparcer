package org.oleggalimov.rssreader.da;

import org.oleggalimov.rssreader.dto.RSSRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRSSRecordsRepository extends JpaRepository <RSSRecord, String> {
}