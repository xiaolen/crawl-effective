package com.crawleffective.crawleffective.demo;

import com.alibaba.fastjson.JSONObject;
import com.crawleffective.crawleffective.util.JsonDataAllUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
public class Test {
    //获取代理ip,记得更换，我用的是蘑菇代理的，可以换成其他的网站的
    private final static String GET_IP_URL = "http://piping.mogumiao.com/proxy/api/get_ip_bs?appKey=xxxxx&count=10&format=1";
    public static void main(String[] args) throws InterruptedException {
        List<String> addrs = new LinkedList<String>();
        Map<String,Integer> addr_map = new HashMap<String,Integer>();
        Map<String,String> ipmap = new HashMap<String,String>();
        ExecutorService exe = Executors.newFixedThreadPool(10);
        for (int i=0 ;i<1;i++) {
            Document doc = null;
            try {
                doc = Jsoup.connect(GET_IP_URL).get();
            } catch (IOException e) {
                continue;
            }
            System.out.println(doc.text());
            JSONObject jsonObject = JsonDataAllUtil.getJsonObject(doc.text());
//            JSONObject jsonObject = JSONObject.fromObject(doc.text());
            List<Map<String,Object>> list = (List<Map<String,Object>>) jsonObject.get("msg");
            int count = list.size();
 
            for (Map<String,Object> map : list ) {
                String ip = (String)map.get("ip");
                String port = (String)map.get("port") ;
                ipmap.put(ip,"1");
                checkIp a = new checkIp(ip, new Integer(port),count);
                exe.execute(a);
            }
            exe.shutdown();
            Thread.sleep(1000);
        }
    }
}
