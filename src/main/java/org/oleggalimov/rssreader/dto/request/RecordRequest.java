package org.oleggalimov.rssreader.dto.request;

import lombok.Data;
import org.oleggalimov.rssreader.dto.FeedChannel;

@Data
public class RecordRequest {

    String guid;
    FeedChannel channel;
    String title;
    String link;
    String pubDate;
    String description;
    Long loadEpochTimeStamp;

}
