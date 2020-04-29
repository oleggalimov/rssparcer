package org.oleggalimov.rssreader.cg;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.oleggalimov.rssreader.dto.ExtractingRule;
import org.oleggalimov.rssreader.dto.RSSChannel;
import org.oleggalimov.rssreader.dto.RSSRecord;
import org.oleggalimov.rssreader.dto.RuleElement;
import org.oleggalimov.rssreader.enumerations.SearchMethod;
import org.oleggalimov.rssreader.fg.RBCNewsFeedParser;
import org.oleggalimov.rssreader.fg.RSSDocumentParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.oleggalimov.rssreader.enumerations.SearchMethod.ATTR_VALUE;
import static org.oleggalimov.rssreader.enumerations.SearchMethod.VALUE;

@Slf4j
@Service
public class RawDataConverter {
    RSSDocumentParser rssDocumentParser;
    RBCNewsFeedParser rbcNewsFeedParser;

    @Autowired
    public void setRssDocumentParser(RSSDocumentParser rssDocumentParser) {
        this.rssDocumentParser = rssDocumentParser;
    }

    @Autowired
    public void setRbcNewsFeedParser(RBCNewsFeedParser rbcNewsFeedParser) {
        this.rbcNewsFeedParser = rbcNewsFeedParser;
    }

    public List<RSSRecord> getData(ExtractingRule rule, Document data) {
        log.debug("Extracting rule: {}", rule);
        if (rule.getRss()) {
            return rssDocumentParser.parse(data);
        }
        if (rule.getContainer() == null) {
            log.error("Container rule is null,  extractingRule is : {}", rule);
            return null;
        }
        Elements rawFeedRecords = getElements(data, rule.getContainer());
        if (rawFeedRecords == null || rawFeedRecords.stream() == null) {
            log.error("No rawFeedRecords");
            return null;
        }
        RSSChannel channel = rule.getChannel();
        return rawFeedRecords.stream()
                .map(element -> {
                    return RSSRecord.builder()
                            .channel(channel)
                            .title(extractElementValue(data, rule.getTitle()))
                            .link(extractElementValue(data, rule.getLink()))
                            .pubDate(extractElementValue(data, rule.getLink()))
                            .guid(extractElementValue(data, rule.getGuid()))
                            .description(extractElementValue(data, rule.getLink()))
                            .build();

                })
                .collect(Collectors.toList());

    }

    private Elements getElements(Document data, RuleElement rule) {
        Elements result = null;
        SearchMethod searchMethod = rule.getSearchMethod();
        if (searchMethod == ATTR_VALUE) {
            result = data.getElementsByAttributeValueMatching(rule.getAttrName(), rule.getPattern());
        } else if (searchMethod == VALUE) {
            result = data.getElementsMatchingOwnText(rule.getPattern());
        }
        return result;
    }

    private String extractElementValue(Document data, RuleElement rule) {
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
