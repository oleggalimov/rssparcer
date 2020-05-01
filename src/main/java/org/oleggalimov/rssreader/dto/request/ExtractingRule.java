package org.oleggalimov.rssreader.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.oleggalimov.rssreader.dto.FeedChannel;

@Data
@NoArgsConstructor
public class ExtractingRule {
    Boolean rss;
    RuleElement container;
    RuleElement title;
    RuleElement link;
    RuleElement pubDate;
    RuleElement guid;
    RuleElement description;
    FeedChannel channel;
}
