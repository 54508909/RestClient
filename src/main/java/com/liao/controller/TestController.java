package com.liao.controller;


import com.liao.utils.RedisUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequestMapping("/test")
@RestController
public class TestController {

    private Lock lock = new ReentrantLock();

    @Autowired
    private RedisUtils redisUtils;

    @Autowired private HttpServletRequest req;


    @RequestMapping("/ts")
    public void test3(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lock.lock();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println(Thread.currentThread().getName()+"-开始时间："+format.format(new Date()));
                Integer i= (int)Math.floor(Math.random() * 99 + 1);
                String redisKey = "teamAppoint:"+i+":"+i;
                System.out.println("redisKey = " + redisKey);
                try {
                    if (redisUtils.hasKey(redisKey)){
                        System.out.println("您的速度太快，请稍后重试！");
                    }else {
                        redisUtils.set(redisKey,"1",3);
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }finally {
                    System.out.println(Thread.currentThread().getName()+"-结束时间："+format.format(new Date()));
                    lock.unlock();
                }
                System.out.println("random = " + 13123);
                System.out.println(11111);

            }
        };

        for (int i = 0; i < 100; i++) {
            executorService.submit(runnable);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("asd-开始时间："+format.format(new Date()));
            show();
            System.out.println("asd-结束时间："+format.format(new Date()));
        }
        executorService.shutdown();

    }


    @RequestMapping("/ts2")
    public void show(){

        System.out.println("req.getServletContext().getRealPath(\"\").replace(\"\\\\\", \"/\") = " + req.getServletContext().getRealPath("").replace("\\", "/"));
//        for (int i = 0; i < 100; i++) {
//            show2();
//        }
    }
    public void show2(){
        System.out.println("asdf123zxcv451");
    }


    public static void main(String[] args) throws DocumentException {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.accumulate("1", new String[]{"https://www.qq.com", "https://www.qq.com"});
//        System.out.println("jsonObject = " + jsonObject.toString());


//        String xml = "<xml><AppId><![CDATA[wx08c924ae79fc1704]]></AppId>\n" +
//                "<CreateTime>1676336985</CreateTime>\n" +
//                "<InfoType><![CDATA[notify_third_fasteregister]]></InfoType><status>-1</status>\n" +
//                "<msg><![CDATA[check legal persona and enterprise fail]]></msg>\n" +
//                "<info>\n" +
//                "<name><![CDATA[厦门三寸雪科技有限公司]]></name>\n" +
//                "<code><![CDATA[91350211MA35A9T43N]]></code>\n" +
//                "<code_type>1</code_type>\n" +
//                "<legal_persona_wechat><![CDATA[a1610138362]]></legal_persona_wechat>\n" +
//                "<legal_persona_name><![CDATA[廖鸿利]]></legal_persona_name>\n" +
//                "<component_phone><![CDATA[]]></component_phone>\n" +
//                "</info>\n" +
//                "</xml>";
//        Document document = DocumentHelper.parseText(xml);
//        System.out.println("component_verify_ticket推送消息：\n"+xml);
//        Element rootElt = document.getRootElement();
//        String type = rootElt.elementText("InfoType");
//        String appid = rootElt.elementText("AppId");
//        System.out.println("appid = " + appid);
//
//        //快速注册小程序
//        String status = rootElt.elementText("status");
//        Element info = rootElt.element("info");
//        System.out.println("info = " + info);
//        String name = info.elementText("name");
//        String code = info.elementText("code");
//        String legalPersonaWechat = info.elementText("legal_persona_wechat");
//        String legalPersonaName = info.elementText("legal_persona_name");
//        System.out.println("name = " + name);
//        System.out.println("code = " + code);

//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Calendar tomorrow = Calendar.getInstance();
//        tomorrow.setTime(new Date());
//        tomorrow.add(Calendar.DAY_OF_MONTH,1);
//        System.out.println("tomorrow = " + format.format(tomorrow.getTime()));
//        Calendar now = Calendar.getInstance();
//        now.setTime(new Date());
//        System.out.println("now = " + format.format(now.getTime()));
//        System.out.println("tomorrow.get(Calendar.YEAR) = " + tomorrow.get(Calendar.YEAR));
//        System.out.println("now.get(Calendar.YEAR) = " + now.get(Calendar.YEAR));
//        System.out.println("tomorrow.get(Calendar.DAY_OF_YEAR) = " + tomorrow.get(Calendar.DAY_OF_YEAR));
//        System.out.println("now.get(Calendar.DAY_OF_YEAR) = " + now.get(Calendar.DAY_OF_YEAR));

//        Element rootElement = document.getRootElement();
//        rootElement.element("")
//        for (Element element : elements) {
//            System.out.println("element.elementText(\"name\") = " + element.elementText("name"));
//        }

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        System.out.println("list = " + list);
        list.remove("4");
        System.out.println("list = " + list);
    }

}
