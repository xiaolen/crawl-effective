package com.crawleffective.crawleffective.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @Author: qyy
 * @Date: 2019/2/13 10:45
 */
@Slf4j
public class JsonDataAllUtil {
    /**
     * 获取json数据
     * 先将为数组格式的字符串转换成jsonArray集合
     * 遍历jsonArray集合获取到每个JSONObject对象
     * 在根据传过的key获取指定的value集合并返回
     * @return
     */
    public static List<String> getDataJsonArray(String jsonObject, String key){
        List<String> list = new  ArrayList<>();
        try {
            if(jsonObject != null && key != null){
                JSONArray jsonArray = (JSONArray) JSONArray.parse(jsonObject);
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = (JSONObject) jsonArray.get(i);
                    String dataValue = getData(json, key);
                    list.add(dataValue);
                }
                return list;
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }


    /**
     * 获取json数据
     * @return
     * 传入一个JsonObject对象和对象中的一个key值
     * 返回key对应的value的字符串值
     */
    public static String getData(JSONObject jsonObject, String key){
        try {
            if(jsonObject != null && key != null){
                String dataValue = jsonObject.get(key).toString();
                return dataValue;
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }

    /**
     * 获取json数据
     * @return
     * 传入一个JsonObject对象和对象中的一个key值
     * 返回key对应的jsonObject对象
     */
    public static JSONObject getDataJson(JSONObject jsonObject, String key){
        try {
            if (jsonObject != null && key != null){
                String dataValue = jsonObject.get(key).toString();
                return getJsonObject(dataValue);
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }
    /**
     * 将字符串转换成json对象
     * @param data
     * @return
     */
    public static JSONObject getJsonObject(String data){
        try {
            if(data != null){
                JSONObject jsonObject = JSONObject.parseObject(data);
                return jsonObject;
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }

    /**
     * 将字符串转换成json对象,
     * 并且根据key获取其中的value字符串数据数据
     * @param data
     * @return
     */
    public static String getJsonObjectAndString(String data,String key){
        try {
            if(data != null){
                JSONObject jsonObject = JSONObject.parseObject(data);
                String data1 = getData(jsonObject, key);
                return data1;
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }
    /**
     * 传入一个jsonObject（是key为字符串value是数组格式）和一个key
     * 将字符串转换成JSONArray集合并返回,并且获取其中的数据
     * @param list
     * @return
     */
    public static JSONArray getJsonArray(JSONObject jsonObject,String list){
        try {
            if(list != null){
                JSONArray jsonArray = (JSONArray) jsonObject.get(list);
//                String data1 = getData(jsonObject, key);
                return jsonArray;
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }
    /**
     * 传入一个jsonObject
     * 转换为String通过序列化
     * @param totalData
     * @return
     */
    public static String getJsonString(JSONObject totalData){
        try {
            if(totalData != null){
                return JSON.toJSONStringWithDateFormat(totalData,"yyyy-MM-dd HH:mm:ss", SerializerFeature.DisableCircularReferenceDetect);
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }

    /**
     * 递归解析数据
     * @param objJson
     */
    public static void  analysisJson(Object objJson){
        //如果obj为json数组
        if(objJson instanceof JSONArray){
            JSONArray objArray = (JSONArray)objJson;
            for (int i = 0; i < objArray.size(); i++) {
                analysisJson(objArray.get(i));
            }
        }
        //如果为json对象
        else if(objJson instanceof JSONObject){
            JSONObject jsonObject = (JSONObject)objJson;
            Iterator it = jsonObject.keySet().iterator();
            while(it.hasNext()){
                String key = it.next().toString();
                System.out.println("{ \n");
                System.out.println("\""+key);
                Object object = jsonObject.get(key);
                //如果得到的是数组
                if(object instanceof JSONArray){
                    JSONArray objArray = (JSONArray)object;
                    analysisJson(objArray);
                }
                //如果key中是一个json对象
                else if(object instanceof JSONObject){
                    analysisJson((JSONObject)object);
                }
                //如果key中是其他
                else{
                    System.out.println("\""+key+"\":\""+object.toString()+"\"");
                }
            }
        }
    }

    /**
     * 传入一个JSONArray格式的字符串转换成JsonArray对象
     *
     * @param data
     * @return
     */
    public static JSONArray getJsonArray(String data) {
        try {
            if (data != null) {
                JSONArray jsonArray = JSONArray.parseArray(data);
                return jsonArray;
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }

    /**
     * 传入一个map转换成JSONObject对象
     *
     * @param map
     * @return
     */
    public static JSONObject getJSONObject(Map map) {
        try {
            if (map != null) {
                JSONObject json = new JSONObject(map);
                return json;
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }

    /**
     * 传入一个JSONObject转换成map对象
     * @param jsonObject
     * @return
     */
    public static Map<String, Object> getMap(JSONObject jsonObject) {
        try {
            if (jsonObject != null) {
                Map<String, Object> map = (Map<String, Object>)jsonObject;
                return map;
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }

    /**
     **** 此方法不建议使用---JSONObject有自己的方法可以转换（练习封装）
     * 传入一个JSONObject转换成一个对象
     * @param jsonObject
     * @param clazz
     * @return
     */
    public static <T> T getClass(JSONObject jsonObject,Class<T> clazz) {
        try {
            if (jsonObject != null) {
                T t = JSONObject.parseObject(jsonObject.toString(), clazz);
                return t;
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }

    /**
     * 传入一个实体类转换成JSONObject对象
     * @param clazz
     * @return
     */
    public static JSONObject getJSONObject(Object clazz) {
        try {
            if (clazz != null) {
//                String jsonString = JSON.toJSONString(clazz);
                JSONObject jsonObject = getJsonObject(JSON.toJSONString(clazz));
                return jsonObject;
            }
        } catch (Exception ex) {
            log.error("bean转化为json失败", ex);
        }
        return null;
    }

}
