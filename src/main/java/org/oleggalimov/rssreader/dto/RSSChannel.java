package org.oleggalimov.rssreader.dto;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class RSSChannel {
    String channelTitle;
    String channelLink;
    String channelDescription;
}
