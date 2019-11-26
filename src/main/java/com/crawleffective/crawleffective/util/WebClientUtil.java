package com.crawleffective.crawleffective.util;

import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Author: 小冷
 * @Date: 2019/6/25 11:36
 */
@SuppressWarnings("all")
public class WebClientUtil {


//    private static WebClient webClient = new WebClient();
/**
 * 代理ip池的使用
 * 1.首先从网上爬取一些代理ip
 * 2.检测代理ip:发送请求将返回状态为200的代理ip保存到一代理池中
 * 3.使用代理:在使用,htmlunit,jsoup,selenium,httpclient等发送请求库时设置代理ip和代理端口号
 * 4.正常爬取发送请求
 */

    /**
     * 爬取工具类GET请求
     *
     * @param pUrl   :请求地址
     * @param heders :请求头参数
     * @return
     */
    public static String getPage(WebClient webClient,String pUrl, Map<String, String> heders) {
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        Page page = null;
        URL url = null;
        String pageInfo = null;
        WebRequest webRequest = null;

        try {
            url = new URL(pUrl);
            webRequest = new WebRequest(url, HttpMethod.GET);
            //统一请求编码
            webRequest.setCharset(StandardCharsets.UTF_8);
            //遍历请求头
            if (heders == null || heders.equals("")) {
                webRequest.setAdditionalHeader("", "");
            }else{
                for (Map.Entry<String, String> entry : heders.entrySet()) {
                    webRequest.setAdditionalHeader(entry.getKey(), entry.getValue());
                }
            }
            page = webClient.getPage(webRequest);
            //将响应页面转换成字符串，并返回
            pageInfo = page.getWebResponse().getContentAsString();
            return pageInfo;
        } catch (IOException e) {
            return "{\"code\":\"" + e.getMessage() + "\",\"mage\":\"爬取异常!\"}";
        }
    }

    /**
     * 爬取工具类时刻用代理GET请求
     *
     * @param pUrl   :请求地址
     * @param heders :请求头参数
     * @param proxyIp: 代理ip
     * @param proxyPort: 代理ip端口号
     * @return
     */
    public static String getPage(WebClient webClient,String pUrl, Map<String, String> heders,String proxyIp,String proxyPort) {
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        Page page = null;
        URL url = null;
        String pageInfo = null;
        WebRequest webRequest = null;

        try {
            url = new URL(pUrl);
            webRequest = new WebRequest(url, HttpMethod.GET);
            //统一请求编码
            webRequest.setCharset(StandardCharsets.UTF_8);
            //设置代理ip
            webRequest.setProxyHost(proxyIp);
            //设置代理ip对应的端口号
            webRequest.setProxyPort(Integer.valueOf(proxyPort));
            //遍历请求头
            if (heders == null || heders.equals("")) {
                webRequest.setAdditionalHeader("", "");
            }else{
                for (Map.Entry<String, String> entry : heders.entrySet()) {
                    webRequest.setAdditionalHeader(entry.getKey(), entry.getValue());
                }
            }
            page = webClient.getPage(webRequest);
            //将响应页面转换成字符串，并返回
            pageInfo = page.getWebResponse().getContentAsString();
            return pageInfo;
        } catch (IOException e) {
            return "{\"code\":\"" + e.getMessage() + "\",\"mage\":\"爬取异常!\"}";
        }
    }

    /**
     * 爬取工具类POST请求
     *
     * @param pUrl     :请求地址
     * @param reqParam :请求参数
     * @param heders   :请求头
     * @param reqParam   :请求表单参数
     * @param proxyIp: 代理ip
     * @param proxyPort: 代理ip端口号
     * @return
     */
    public static String postPage(WebClient webClient,String pUrl, Map<String, String> heders, List<NameValuePair> reqParam,String proxyIp,String proxyPort) {
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        Page page = null;
        URL url = null;
        String pageInfo = null;
        WebRequest webRequest = null;

        try {
            url = new URL(pUrl);
            webRequest = new WebRequest(url, HttpMethod.POST);
            //统一请求编码
            webRequest.setCharset(StandardCharsets.UTF_8);
            //设置代理ip
            webRequest.setProxyHost(proxyIp);
            //设置代理ip对应的端口号
            webRequest.setProxyPort(Integer.valueOf(proxyPort));
            //遍历请求头
            if (heders == null || heders.equals("")) {
                    webRequest.setAdditionalHeader("", "");
            }else{
                for (Map.Entry<String, String> entry : heders.entrySet()) {
                    webRequest.setAdditionalHeader(entry.getKey(), entry.getValue());
                }
                //post请求参数
                webRequest.setRequestParameters(reqParam);
            }
            page = webClient.getPage(webRequest);
            //将响应页面转换成字符串，并返回
            pageInfo = page.getWebResponse().getContentAsString();
            return pageInfo;
        } catch (IOException e) {
            return "{\"code\":\"" + e.getMessage() + "\",\"mage\":\"爬取异常!\"}";
        }
    }

    /**
     * 爬取工具类POST请求
     *
     * @param pUrl     :请求地址
     * @param reqParam :请求参数
     * @param heders   :请求头
     * @param reqParam   :请求表单参数
     * @return
     */
    public static String postPage(WebClient webClient,String pUrl, Map<String, String> heders, List<NameValuePair> reqParam) {
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        Page page = null;
        URL url = null;
        String pageInfo = null;
        WebRequest webRequest = null;

        try {
            url = new URL(pUrl);
            webRequest = new WebRequest(url, HttpMethod.POST);
            //统一请求编码
            webRequest.setCharset(StandardCharsets.UTF_8);
            //遍历请求头
            if (heders == null || heders.equals("")) {
                    webRequest.setAdditionalHeader("", "");
            }else{
                for (Map.Entry<String, String> entry : heders.entrySet()) {
                    webRequest.setAdditionalHeader(entry.getKey(), entry.getValue());
                }
                //post请求参数
                webRequest.setRequestParameters(reqParam);
            }
            page = webClient.getPage(webRequest);
            //将响应页面转换成字符串，并返回
            pageInfo = page.getWebResponse().getContentAsString();
            return pageInfo;
        } catch (IOException e) {
            return "{\"code\":\"" + e.getMessage() + "\",\"mage\":\"爬取异常!\"}";
        }
    }
}
