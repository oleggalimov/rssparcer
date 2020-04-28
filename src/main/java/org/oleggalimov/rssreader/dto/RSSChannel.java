package org.oleggalimov.rssreader.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Getter
@Setter
@ToString
@Embeddable
public class RSSChannel {
    String channelTitle;
    String channelLink;
    String channelDescription;
}
