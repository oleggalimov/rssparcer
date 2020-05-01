package org.oleggalimov.rssreader.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.oleggalimov.rssreader.dto.request.ExtractingRule;

@Getter
@Setter
@ToString
public class AddFeedRequest {
    String url;
    ExtractingRule extractingRule;
}
