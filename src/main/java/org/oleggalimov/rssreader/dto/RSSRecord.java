package org.oleggalimov.rssreader.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "rss_records")
@Getter
@Setter
@Builder
public class RSSRecord {
    @Id
    String id;
    //channel elements
    @Column
    String channelTitle;
    @Column
    String channelLink;
    @Column
    String channelDescription;

    //item elements
    @Column
    String itemTitle;
    @Column
    String itemLink;
    @Column
    String itemPubDate;
    @Column
    String itemGuid;
    @Column
    String itemDescription;

}
