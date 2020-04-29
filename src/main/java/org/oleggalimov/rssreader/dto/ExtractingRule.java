package org.oleggalimov.rssreader.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExtractingRule {
    Boolean rss;
    RuleElement container;
    RuleElement title;
    RuleElement link;
    RuleElement pubDate;
    RuleElement guid;
    RSSChannel channel;
}
