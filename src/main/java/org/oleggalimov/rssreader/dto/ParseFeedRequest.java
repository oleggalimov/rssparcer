package org.oleggalimov.rssreader.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParseFeedRequest {
    String url;
    ExtractingRule extractingRule;
}
