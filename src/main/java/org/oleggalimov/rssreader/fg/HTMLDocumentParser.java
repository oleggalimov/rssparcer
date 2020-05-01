package org.oleggalimov.rssreader.fg;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.oleggalimov.rssreader.dto.request.ExtractingRule;
import org.oleggalimov.rssreader.dto.FeedChannel;
import org.oleggalimov.rssreader.dto.FeedRecord;
import org.oleggalimov.rssreader.dto.request.RuleElement;
import org.oleggalimov.rssreader.enumerations.SearchMethod;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.oleggalimov.rssreader.enumerations.SearchMethod.ATTR_VALUE;
import static org.oleggalimov.rssreader.enumerations.SearchMethod.VALUE;

@Slf4j
@Service
public class HTMLDocumentParser implements IHTMLDocumentParser {

    @Override
    public List<FeedRecord> parse(ExtractingRule rule, Document data) {
        if (rule.getContainer() == null) {
            log.error("Container rule is null,  extractingRule is : {}", rule);
            return null;
        }
        Elements rawFeedRecords = getElements(data, rule.getContainer());
        if (rawFeedRecords == null || rawFeedRecords.stream() == null) {
            log.error("No rawFeedRecords");
            return null;
        }
        FeedChannel channel = rule.getChannel();
        return rawFeedRecords.stream()
                .map(element -> FeedRecord.builder()
                        .channel(channel)
                        .title(extractElementValue(element, rule.getTitle()))
                        .link(extractElementValue(element, rule.getLink()))
                        .pubDate(extractElementValue(element, rule.getPubDate()))
                        .guid(extractElementValue(element, rule.getGuid()))
                        .description(extractElementValue(element, rule.getDescription()))
                        .loadEpochTimeStamp(Instant.now().toEpochMilli())
                        .build())
                .collect(Collectors.toList());

    }

    private Elements getElements(Element data, RuleElement rule) {
        if (rule == null) {
            return null;
        }
        Elements result = null;
        SearchMethod searchMethod = rule.getSearchMethod();
        if (searchMethod == ATTR_VALUE) {
            result = data.getElementsByAttributeValueMatching(rule.getAttrName(), rule.getPattern());
        } else if (searchMethod == VALUE) {
            result = data.getElementsMatchingOwnText(rule.getPattern());
        }
        return result;
    }

    private String extractElementValue(Element data, RuleElement rule) {
        Elements elements = getElements(data, rule);
        if (elements == null) {
            return null;
        }
        String nestedAttr = rule.getNestedAttr();
        if (nestedAttr != null) {
            return elements.attr(nestedAttr);
        } else {
            return elements.text();
        }
    }
}
