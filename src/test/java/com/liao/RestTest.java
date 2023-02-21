package com.liao;

import com.liao.pojo.Hotel;
import com.liao.pojo.HotelDoc;
import com.liao.service.HotelService;
import com.liao.utils.RedisUtils;
import net.sf.json.JSON;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static com.liao.restClientEnum.createIndexTemplate;

//@RunWith(SpringRunner.class)
@SpringBootTest(/*classes = Application.class*/)
public class RestTest {

    private Lock lock = new ReentrantLock();

//    @Autowired
//    private RedisUtils redisUtils;


    @Autowired
    DataSource dataSource;

    @Autowired
    private HotelService hotelService;

    private RestHighLevelClient client;

//    private Lock lock = new ReentrantLock();

    @Test
    public void test(){
//        System.out.println("dataSource = " + dataSource.getClass());
        System.out.println("client = " + client);
    }

    @Test
    public void createIndex() throws IOException {
        //创建Request对象
        CreateIndexRequest request1 = new CreateIndexRequest("hotel");
        //请求的参数
        request1.source(createIndexTemplate, XContentType.JSON);
        //3.发起请求
        client.indices().create(request1, RequestOptions.DEFAULT);
    }


    @Test
    public void delIndex() throws IOException {
        //创建Request对象
        DeleteIndexRequest request1 = new DeleteIndexRequest("hotel");
        //发起请求
        client.indices().delete(request1, RequestOptions.DEFAULT);
    }

    @Test
    public void existIndex() throws IOException {
        GetIndexRequest request1 = new GetIndexRequest("hotel");
        boolean exists = client.indices().exists(request1, RequestOptions.DEFAULT);
        System.out.println(exists?"存在":"不存在");
    }

    @Test
    public void getHotel() throws IOException {
        Hotel hotel = hotelService.getHotel(1);
        HotelDoc hotelDoc = new HotelDoc(hotel);

        IndexRequest request = new IndexRequest("hotel").id(hotelDoc.getId().toString());
        request.source(JSONObject.fromObject(hotelDoc).toString(),XContentType.JSON);
        System.out.println("client = " + client);
        try {
            client.index(request,RequestOptions.DEFAULT);
        } catch (Exception e) {
            if (!(e.getMessage().contains("200"))){
                throw e;
            }
        }

        System.out.println("hotel = " + hotel);
    }


    @Test
    public void getHotelDoc() throws IOException {

        GetRequest request = new GetRequest("hotel","1");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        String json = response.getSourceAsString();
        System.out.println("json = " + json);
        HotelDoc o = (HotelDoc) JSONObject.toBean(JSONObject.fromObject(json), HotelDoc.class);
//        Object o = JSONObject.toBean(JSONObject.fromObject(json), HotelDoc.class);
        System.out.println("HotelDoc = " + o);
    }

    @Test
    public void updateHotelDoc() throws IOException {

        UpdateRequest request = new UpdateRequest("hotel", "1");
        request.doc(
                "address","破军",
                "brand","三寸雪"
        );

        try {
            client.update(request,RequestOptions.DEFAULT);
        } catch (Exception e) {
            if (!(e.getMessage().contains("200"))){
                throw e;
            }
        }
    }

    @Test
    public void delHotelDoc() throws IOException {
        DeleteRequest request = new DeleteRequest("hotel", "1");
        try {
            client.delete(request,RequestOptions.DEFAULT);
        } catch (Exception e) {
            if (!(e.getMessage().contains("200"))){
                throw e;
            }
        }
    }

    @Test
    public void batchIndexHotelDoc() throws IOException {
        List<Hotel> list = hotelService.getHotelList();
        BulkRequest request = new BulkRequest();

        for (Hotel hotel : list) {
            HotelDoc hotelDoc = new HotelDoc(hotel);
            request.add(new IndexRequest("hotel").id(hotelDoc.getId().toString()).source(JSONObject.fromObject(hotelDoc).toString(),XContentType.JSON));
        }

        try {
            client.bulk(request,RequestOptions.DEFAULT);
        } catch (Exception e) {
            if (!(e.getMessage().contains("200"))){
                throw e;
            }
        }
    }



    @BeforeEach
    void setUp(){
        this.client = new RestHighLevelClient(RestClient.builder(HttpHost.create("http://localhost:9200")));
    }

    @AfterEach
    void down() throws IOException {
        this.client.close();
    }

    @Test
    public void test2(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                out();
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                out();
            }
        };
        Thread thread = new Thread(runnable, "A");
        Thread thread1 = new Thread(runnable2, "B");
        thread.start();
        thread1.start();
        try {
            thread1.join();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public void out(){
        for (int i = 5; i > 0; i--) {
            lock.lock();
            try {
                System.out.println(i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }


//    @Test
//    public void test3(){
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                lock.lock();
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                System.out.println(Thread.currentThread().getName()+"-开始时间："+format.format(new Date()));
//                Random random = new Random();
//                Integer i= (int)Math.floor(Math.random() * 99 + 1);
//                String redisKey = "teamAppoint:"+i+":"+i;
//                try {
//                    if (redisUtils.hasKey(redisKey)){
//                        System.out.println("您的速度太快，请稍后重试！");
//                    }
//                    redisUtils.set(redisKey,"1",3);
//                }finally {
//                    System.out.println(Thread.currentThread().getName()+"-结束时间："+format.format(new Date()));
//                    lock.unlock();
//                }
//                System.out.println("random = " + random);
//            }
//        };
//
//        for (int i = 0; i < 100; i++) {
//            executorService.submit(runnable);
//        }
//        executorService.shutdown();
//
//    }
}
