package com.crawleffective.crawleffective.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class checkIp implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(checkIp.class);
    private static int suc=0;
    private static int total=0;
    private static int fail=0;
 
    private String ip ;
    private int port;
    private int count;
    public checkIp(String ip, int port,int count) {
        super();
        this.ip = ip;
        this.port = port;
        this.count = count;
    }
 
    @Override
    public void run() {
        Random r = new Random();
        String[] ua = {"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36 OPR/37.0.2178.32",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586",
                "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko",
                "Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)",
                "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0)",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.3 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.277.400 QQBrowser/9.4.7658.400",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 UBrowser/5.6.12150.8 Safari/537.36",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7",
                "Mozilla/5.0 (Windows NT 6.1; W…) Gecko/20100101 Firefox/60.0"};
        int i = r.nextInt(14);
        logger.info("检测中------ {}:{}",ip,port );
        Map<String,String> map = new HashMap<String,String>();
        map.put("waybillNo","DD1838768852");
        try {
            total ++ ;
            long a = System.currentTimeMillis();
            //爬取的目标网站，url记得换下。。。！！！
            Document doc = Jsoup.connect("http://trace.yto.net.cn:8022/TraceSimple.aspx")
                    .timeout(5000)
                    .proxy(ip,port)
                    .data(map)
                    .ignoreContentType(true)
                    .userAgent(ua[i])
                    .header("referer","http://trace.yto.net.cn:8022/gw/index/index.html")//这个来源记得换..
                    .post();
            System.out.println(ip+":"+port+"访问时间:"+(System.currentTimeMillis() -a) + "   访问结果: "+doc.text());
            suc ++ ;
        } catch (IOException e) {
            e.printStackTrace();
            fail ++ ;
        }finally {
            if (total == count ) {
                System.out.println("总次数："+total);
                System.out.println("成功次数："+suc);
                System.out.println("失败次数："+fail);
            }
        }
    }
 
}
