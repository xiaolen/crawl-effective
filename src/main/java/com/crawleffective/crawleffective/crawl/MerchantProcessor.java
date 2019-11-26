package com.crawleffective.crawleffective.crawl;


import com.crawleffective.crawleffective.model.Merchant;
import com.crawleffective.crawleffective.util.CookieUtils;
import com.crawleffective.crawleffective.util.ExcelUtil;
import com.crawleffective.crawleffective.util.WebClientUtil;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

/**
 * @Author: 秦小冷
 * 爬虫-处理类
 * Created by heliang on 6/27/2017.
 */
@Component
@SuppressWarnings("all")
public class MerchantProcessor {


    /**
     * 具体的登陆逻辑
     *
     * @param webClient
     * @param context
     * @return
     */
    public void doLogin(WebClient webClient) {

//        WebClient webClient = new WebClient();
        System.getProperties().setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();

        //将浏览器最大化
        webDriver.manage().window().maximize();
//        webDriver.get("https://login.1688.com/member/signin.htm");
        webDriver.get("https://login.1688.com/member/signin.htm?spm=b26110380.2178313.0.d3.783b427ano0dRw&Done=https://s.1688.com/company/company_search.htm?keywords=%C9%BD%CE%F7");
        try {
            Thread.sleep(20000);
//            webDriver.get(url);
//            Thread.sleep(2000);
            //这个cookie是selenium的
            Set<Cookie> cookies = webDriver.manage().getCookies();
            System.out.println(cookies);
            //而webclient需要的是htmlunit的cookie
            CookieUtils.copyCookies(webClient, cookies, "");
            Thread.sleep(1000);
//            webDriver.close();
            System.out.println(webClient.getCookieManager().getCookies() + "-------------------------");
            crawl01(webClient);
//            return webClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return null;
    }

    /**
     * 具体的登陆逻辑
     *
     * @param webClient
     * @param context
     * @return
     */
    public void doLogin0() {

        List list = new ArrayList();
        String page = null;
        Document document = null;
        Elements elementsByClass = null;
//        WebClient webClient = new WebClient();
        System.getProperties().setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        //将浏览器最大化
        webDriver.manage().window().maximize();
//        webDriver.get("https://login.1688.com/member/signin.htm");
        webDriver.get("https://login.1688.com/member/signin.htm?spm=b26110380.2178313.0.d3.783b427ano0dRw&Done=https://s.1688.com/company/company_search.htm?keywords=%C9%BD%CE%F7");
        try {
            Thread.sleep(18000);
            webDriver.get("https://s.1688.com/company/company_search.htm?keywords=%C9%BD%CE%F7&province=%C9%BD%CE%F7&n=y&filt=y");
            Thread.sleep(2000);
            String pageSource = webDriver.getPageSource();
            document = Jsoup.parseBodyFragment(pageSource);
            String totalpage = document.getElementsByClass("total-page").get(0).text();
            //截取页数
            String repickStr = Pattern.compile("[\u4e00-\u9fa5]").matcher(totalpage).replaceAll("");
            System.out.println(repickStr);
            for (int i = 1; i <= Integer.valueOf(repickStr); i++) {
                webDriver.get("https://s.1688.com/company/company_search.htm?keywords=%C9%BD%CE%F7&pageSize=30&offset=3&beginPage=" + i + "");
                Thread.sleep(500);
                pageSource = webDriver.getPageSource();
                document = Jsoup.parseBodyFragment(pageSource);
                elementsByClass = document.getElementsByClass("sm-company-list fd-clr");
                elementsByClass = elementsByClass.get(0).getElementsByClass("list-item-left");
                for (Element element : elementsByClass) {
                    //所有的商户
                    String href = element.getElementsByClass("list-item-title-text").get(0).attr("href");
                    webDriver.get(href);
                    Thread.sleep(8000);
                    pageSource = webDriver.getPageSource();
//                in = new File("G:\\sss.html");
//                document = Jsoup.parse(in, "UTF-8", "");
                    if (pageSource.contains("contactinfo-page")) {
                        System.out.println("=======================");
                        Merchant merchant = new Merchant();
                        document = Jsoup.parseBodyFragment(pageSource);
                        //所有的商户对应的联系人的链接
                        String attr = document.getElementsByClass("contactinfo-page").get(0).select("a").attr("href");
                        System.out.println(page);
//                in = new File("G:\\qqq.html");
//                document = Jsoup.parse(in, "UTF-8", "");
                        webDriver.get(attr);
                        pageSource = webDriver.getPageSource();
                        document = Jsoup.parseBodyFragment(pageSource);
                        // 经营模式
                        String text = document.getElementsByClass("biz-type-model").get(0).text();
                        System.out.println(text);

                        //所在地
                        element = document.getElementsByClass("item address fd-clr").get(0);
                        String disc = element.getElementsByClass("disc").get(0).text();
                        System.out.println(disc);
                        merchant.setDisc(disc);
                        //公司名称
                        String title = document.select("h4").get(0).text();
                        System.out.println(title);
                        merchant.setTitle(title);
                        //诚信年份
                        String span = document.getElementsByClass("tp-info").get(0).text();
                        merchant.setSpan(span);
                        //卖家
                        String membername = document.getElementsByClass("membername").get(0).text();
                        System.out.println(membername);
                        merchant.setMembername(membername);

                        Elements dl = document.getElementsByClass("contcat-desc").get(0).select("dl");

                        //电话
                        String phone = dl.get(0).select("dd").text();
                        merchant.setPhone(phone);
                        //移动电话
                        String mobilephone = document.getElementsByClass("m-mobilephone").get(0).text();
                        merchant.setMobilephone(mobilephone);
                        //地址
                        String address = document.getElementsByClass("address").get(0).text();
                        merchant.setAddress(address);
                        list.add(membername);
                    }
                }
            }
            ExcelUtil.writeData(list, "商户信息表", "商户信息表");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return null;
    }

    public void crawl(WebClient webClient) {
        Map map = new HashMap();
        List list = new ArrayList();
        map.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        map.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
        map.put("referer", "https://s.1688.com/company/company_search.htm?keywords=%C9%BD%CE%F7");
        String page = null;
        Document document = null;
        Elements elementsByClass = null;
        List<NameValuePair> reqParam = new ArrayList<>();
        page = WebClientUtil.getPage(webClient, "https://s.1688.com/company/company_search.htm?keywords=%C9%BD%CE%F7&province=%C9%BD%CE%F7&n=y&filt=y", map);
        System.out.println(page);

//        webClient = doLogin(webClient);
//        File in = new File("G:\\hhhh.html");
        try {
//            document = Jsoup.parse(in, "UTF-8", "");
            document = Jsoup.parseBodyFragment(page);
            String totalpage = document.getElementsByClass("total-page").get(0).text();
            //截取页数
            String repickStr = Pattern.compile("[\u4e00-\u9fa5]").matcher(totalpage).replaceAll("");

            for (int i = 1; i <= Integer.valueOf(repickStr); i++) {
                Thread.sleep(5000);
                String url = "https://s.1688.com/company/company_search.htm?";
                map.clear();
                map.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
                map.put("content-type", "application/x-www-form-urlencoded");
                map.put("origin", "https://s.1688.com");
                map.put("referer", "https://s.1688.com/company/company_search.htm?keywords=%C9%BD%CE%F7&province=%C9%BD%CE%F7&n=y&filt=y");
                map.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
                reqParam.add(new NameValuePair("keywords", "%C9%BD%CE%F7"));
                reqParam.add(new NameValuePair("province", "%C9%BD%CE%F7"));
                reqParam.add(new NameValuePair("n", "y"));
                reqParam.add(new NameValuePair("pageSize", "30"));
                reqParam.add(new NameValuePair("filt", "y"));
                reqParam.add(new NameValuePair("offset", "0"));
                reqParam.add(new NameValuePair("beginPage", String.valueOf(i)));
                page = WebClientUtil.postPage(webClient, url, map, reqParam);
                System.out.println(page);

                document = Jsoup.parseBodyFragment(page);
                elementsByClass = document.getElementsByClass("sm-company-list fd-clr");
                elementsByClass = elementsByClass.get(0).getElementsByClass("list-item-left");
                for (Element element : elementsByClass) {
                    //所有的商户
                    String href = element.getElementsByClass("list-item-title-text").get(0).attr("href");
                    map.clear();
                    map.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
                    map.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
                    map.put("referer", "https://s.1688.com/company/company_search.htm?keywords=%C9%BD%CE%F7&province=%C9%BD%CE%F7&n=y&pageSize=30&filt=y&offset=0&beginPage=" + i + "");
                    page = WebClientUtil.getPage(webClient, href, map);
                    System.out.println(page);
                    document = Jsoup.parseBodyFragment(page);
//                in = new File("G:\\sss.html");
//                document = Jsoup.parse(in, "UTF-8", "");
                    if (page.contains("contactinfo-page")) {
                        Merchant merchant = new Merchant();
                        //所有的商户对应的联系人的链接
                        String attr = document.getElementsByClass("contactinfo-page").get(0).select("a").attr("href");
                        map.clear();
                        map.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
                        map.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
                        map.put("referer", href);
                        page = WebClientUtil.getPage(webClient, attr, map);
                        System.out.println(page);
//                in = new File("G:\\qqq.html");
//                document = Jsoup.parse(in, "UTF-8", "");
                        document = Jsoup.parseBodyFragment(page);
                        // 经营模式
                        String text = document.getElementsByClass("biz-type-model").get(0).text();
                        System.out.println(text);

                        //所在地
                        element = document.getElementsByClass("item address fd-clr").get(0);
                        String disc = element.getElementsByClass("disc").get(0).text();
                        System.out.println(disc);
                        merchant.setDisc(disc);
                        //公司名称
                        String title = document.select("h4").get(0).text();
                        System.out.println(title);
                        merchant.setTitle(title);
                        //诚信年份
                        String span = document.getElementsByClass("tp-info").get(0).text();
                        merchant.setSpan(span);
                        //卖家
                        String membername = document.getElementsByClass("membername").get(0).text();
                        System.out.println(membername);
                        merchant.setMembername(membername);

                        Elements dl = document.getElementsByClass("contcat-desc").get(0).select("dl");

                        //电话
                        String phone = dl.get(0).select("dd").text();
                        merchant.setPhone(phone);
                        //移动电话
                        String mobilephone = document.getElementsByClass("m-mobilephone").get(0).text();
                        merchant.setMobilephone(mobilephone);
                        //地址
                        String address = document.getElementsByClass("address").get(0).text();
                        merchant.setAddress(address);
                        list.add(membername);
                    }
                }
            }
            ExcelUtil.writeData(list, "商户信息表", "商户信息表");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void crawl01(WebClient webClient) {
        Map map = new HashMap();
        List list = new ArrayList();
        map.put("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        map.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
        map.put("referer", "https://s.1688.com/company/company_search.htm");
        String page = null;
        Document document = null;
        Elements elementsByClass = null;
        Element element = null;
        List<NameValuePair> reqParam = new ArrayList<>();
        String filePath = "D:\\1028.xlsx";
        List list1 = ExcelUtil.ReadDataColumn(filePath, 1);

        for (int i = 0; i < list1.size(); i++) {

            page = WebClientUtil.getPage(webClient, (String) list1.get(i), map);
            System.out.println(page);
            document = Jsoup.parseBodyFragment(page);
                Merchant merchant = new Merchant();
                // 经营模式
                String text = document.getElementsByClass("biz-type-model").get(0).text();
                System.out.println(text);

                //所在地
                element = document.getElementsByClass("item address fd-clr").get(0);
                String disc = element.getElementsByClass("disc").get(0).text();
                System.out.println(disc);
                merchant.setDisc(disc);
                //公司名称
                String title = document.select("h4").get(0).text();
                System.out.println(title);
                merchant.setTitle(title);
                //诚信年份
                String span = document.getElementsByClass("tp-info").get(0).text();
                merchant.setSpan(span);
                //卖家
                String membername = document.getElementsByClass("membername").get(0).text();
                System.out.println(membername);
                merchant.setMembername(membername);

                Elements dl = document.getElementsByClass("contcat-desc").get(0).select("dl");

                //电话
                String phone = dl.get(0).select("dd").text();
                merchant.setPhone(phone);
                //移动电话
                String mobilephone = document.getElementsByClass("m-mobilephone").get(0).text();
                merchant.setMobilephone(mobilephone);
                //地址
                String address = document.getElementsByClass("address").get(0).text();
                merchant.setAddress(address);
                list.add(membername);
            }

        ExcelUtil.writeData(list, "商户信息表", "商户信息表");
    }
}
