package org.oleggalimov.rssreader.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "rss_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "channelTitle",
        "channelLink",
        "channelDescription",
        "itemTitle",
        "itemLink",
        "itemPubDate",
        "itemGuid",
        "itemDescription"
})
public class FeedRecord {
    @Id
    String guid;

    @Embedded
    RSSChannel channel;

    //item elements
    @Column
    String title;
    @Column
    String link;
    @Column
    String pubDate;

    @Column
    String description;

    @Column
    Long loadEpochTimeStamp;

}
