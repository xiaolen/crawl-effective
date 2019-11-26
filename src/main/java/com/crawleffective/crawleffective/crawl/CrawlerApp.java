package com.crawleffective.crawleffective.crawl;

import com.alibaba.fastjson.JSONObject;
import com.crawleffective.crawleffective.util.JsonDataAllUtil;
import com.gargoylesoftware.htmlunit.WebClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 秦小冷
 * @Date: 2019/10/29 14:12
 * @Version 1.0
 */
public class CrawlerApp {


    public void crawlerXiMaLaYa() {
        WebClient webClient = new WebClient();
        Map head = new HashMap();
        String page = null;
        Document document = null;
//        String url = "https://www.ximalaya.com/search/album/商业/https:/www.ximalaya.com/revision/search/main/?core=album&kw=%25e5%2595%2586%25e4%25b8%259a&page=1&spellchecker=true&rows=20&condition=relation&device=iPhone&fq=&paidFilter=false";
//
//        head.put("Accept", "*/*");
//        head.put("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
//        head.put("Host", "www.ximalaya.com");
//        head.put("Referer", "https://www.ximalaya.com/search/album/%E5%95%86%E4%B8%9A");
//        head.put("Sec-Fetch-Mode", "cors");
//        head.put("Sec-Fetch-Site", "same-origin");
//        head.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
//        page = WebClientUtil.getPage(webClient, url, head);
//        System.out.println(page);

        File file = new File("G:\\ggg.html");
        try {
            Document parse = Jsoup.parse(file, "UTF-8", "");
            //获取数据json串
            String[] split = parse.toString().split("window.__INITIAL_STATE__ = ");
            String award = split[1].split("<script>window.basename")[0].split(";</script>")[0];

            //最外层
            JSONObject jsonObjectAward = JsonDataAllUtil.getJsonObject(award);
            JSONObject store = JsonDataAllUtil.getDataJson(jsonObjectAward, "store");

            JSONObject albums = JsonDataAllUtil.getDataJson(store, "albums");
            System.out.println(albums);
//        for (Object o : albums) {
//            String url1 = JsonDataAllUtil.getData((JSONObject) o, "url");
//            System.out.println(url1);
//        }
//        String s = HttpClientUtil.doGet(url);
//        System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
