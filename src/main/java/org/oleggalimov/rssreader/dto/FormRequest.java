package org.oleggalimov.rssreader.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.oleggalimov.rssreader.enumerations.RSSType;
@Getter
@Setter
@ToString
public class FormRequest {
    String url;
    RSSType type;
}
