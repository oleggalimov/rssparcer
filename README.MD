#RSS READER
##WHAT IS THIS? 
Feed reader is a simple application for parsing different news feeds. Supporting formats are 2.x (stable) and different HTML-pages (unstable). HTML pages should contains tree-structure news records, f.ex.: record_node -> child_nodes ->sub_child_nodes. record_node is a container for news record, which contains child_nodes with minimal information  (link, title, guid  etc., more in org.oleggalimov.rssreader.dto.FeedRecord.class) in plane text or in sub_childe_nodes. 
##HOW TO USE?
1. Clone this project from branch master
2. Open cmd or linux-terminal in the  project directory.
3. Execute mvn clean install
4. Change directory to rssparcer/target
5. Execute java -jar rssreader-<current_version>.jar, where you need to replace <current_version> to actual version of artifact.
##HOW IT WORKS?
It's based on sources and rules. The source is the URL to RSS 2.xx feed, e.g. https://feeds.feedburner.com/Baeldung 
or https://rss.nytimes.com/services/xml/rss/nyt/World.xml. The rule is the set of instructions for parsing data from source. 
In case of RSS 2.xx you don't need to define the rule: because of stable format there is default value:
``` 
{
    "rss":true
}
```

 Just add source and this default rule  on main page in section "ADD YOUR FEED" to fetch and parse data. 
  <br>
  <br>
  Less stable case is for HTML pages. You also need to provide the source and the rule. For HTML rss-flag must be set to false. Then you need to add container, title, link, pubDate, guid and channel sections, e.g.:
```
{
  "rss":false,
  "container":{
    "searchMethod":"ATTR_VALUE",
    "attrName":"class",
    "pattern":"item "
  },
  "title":{
    "searchMethod":"ATTR_VALUE",
    "attrName":"class",
    "pattern":"titles"
  },
  "link":{
    "searchMethod":"ATTR_VALUE",
    "attrName":"href",
    "pattern":"/news",
    "nestedAttr":"href"

  },
  "pubDate":{
    "searchMethod":"VALUE",
    "pattern":"[0-9]{2}:[0-9]{2}"
  },
  "guid":{
    "searchMethod":"ATTR_VALUE",
    "attrName":"href",
    "pattern":"/news",
    "nestedAttr":"href"
  },
  "channel":{
    "channelTitle":"lenta.ru",
    "channelLink":"https://lenta.ru/parts/news/",
    "channelDescription":"Новости: Lenta.ru"
  }
}
```
In this example defined rules for parsing elements of news feed:
- container - root container for news feed, contains all records which should be added to database;
- title - string for title of record;
- link - link of record;
- pubdate - date of publication news record;
- guid - identifier for record. It is a required element. Only string values allowed. This value must be unique and very precisely defined, because it's an id for saving record in database. 
- channel- static predefined element, contains common information about source.
<br>
<br>
Every of this parts represents a search rule for particular element:
- searchMethod - the method for search element in a part of DOM - tree: ATTR_VALUE - search on value of HTML-attribute (e.g. class, href etc.),  VALUE - search on text value of DOM-element;
- attrName - name of viewed attribute if search method is set on ATTR_VALUE;
- pattern - REGEX pattern, which will be applied to viewed element;
- nestedAttr - the name of nested attribute of HTML-element, which contains value for extraction (e.g. href if there you need to extract a link to news record).
<br>
<br>
There is rules-example.yml in resource folder which contains example URL and link to parsing rule for testing. Simply copy the url and text from the corresponding file to the form in the "ADD YOUR FEED" section and view the result.  
<br>
<br>
The RSS reader will try every 180 seconds to fetch data from source and find elements according to rules. First of all it will try identify the list of records ("container"), then read every element in this list and fetch fields for record ("title", "link" etc.). 