package org.oleggalimov.rssreader.fg;

import lombok.extern.slf4j.Slf4j;
import org.oleggalimov.rssreader.dto.RSSRecord;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;

@Service
@Slf4j
public class XMLConverter implements IXMLConverter {
    @Override
    public RSSRecord parseXmlString(String xml) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new InputSource(new  StringReader(xml)));
            NodeList childNodes = document.getChildNodes();
            log.debug(childNodes.toString());

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
