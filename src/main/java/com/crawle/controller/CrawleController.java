package com.crawle.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * @author zhongzhiqiang
 * @date 2018/8/17  14:59
 */
public class CrawleController {

    public void getGoods(String url) throws  Exception{
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("ul[class=gl-warp clearfix]").select("li[class=gl-item]");

        Map<String, Integer> map = new TreeMap<>();

        for(Element element : elements) {
            String goodsId = element.attr("data-sku");
            String goodsName = element.select("div[class=p-name p-name-type-2]").select("em").text();
            String goodsPrice = element.select("div[class=p-price]").select("strong").select("i").text();
            map.put(goodsName, (int)Double.parseDouble(goodsPrice));
        }

        List<Map.Entry<String, Integer>> entryList = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(entryList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o1.getValue() < o2.getValue()) ? -1 : 1;
            }
        });

        Iterator<Map.Entry<String, Integer>> entryIterator = entryList.iterator();
        Map.Entry<String, Integer> entymap = null;
        while (entryIterator.hasNext()){
            entymap = entryIterator.next();
            System.out.println(entymap.getValue() + "\t" + entymap.getKey());
        }
    }

    public static void main(String[] args) throws  Exception{
        CrawleController crawleController = new CrawleController();
        crawleController.getGoods("https://search.jd.com/Search?keyword=%E8%BF%B7%E4%BD%A0%E6%8A%95%E5%BD%B1&enc=utf-8&wq=%E8%BF%B7%E4%BD%A0%E6%8A%95%E5%BD%B1&pvid=d398cd9f9705415d81a26b959df2b449");
    }
}
