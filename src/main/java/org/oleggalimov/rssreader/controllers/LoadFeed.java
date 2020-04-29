package org.oleggalimov.rssreader.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoadFeed {
    @GetMapping("load")
    String getLoadRssPage() {
        return "loadfeed";
    }
}
