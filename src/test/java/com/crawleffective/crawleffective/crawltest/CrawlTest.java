package com.crawleffective.crawleffective.crawltest;

import com.crawleffective.crawleffective.crawl.CrawlerApp;
import com.crawleffective.crawleffective.crawl.MerchantProcessor;
import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.Test;

/**
 * @Author: 秦小冷
 * @Date: 2019/10/22 17:42
 * @Version 1.0
 */
public class CrawlTest {

    @Test
    public void crawlTest() {
        WebClient webClient = new WebClient();
        MerchantProcessor hbcProcessor = new MerchantProcessor();
//        hbcProcessor.crawl(webClient);
        hbcProcessor.doLogin(webClient);
//        hbcProcessor.doLogin0();
//        ExcelUtil.writeData(new ArrayList<>(),"4444","77777");
    }

    @Test
    public void crawlTestXiMaLaYa() {
        WebClient webClient = new WebClient();
        CrawlerApp crawlerApp = new CrawlerApp();
        crawlerApp.crawlerXiMaLaYa();
    }
}
