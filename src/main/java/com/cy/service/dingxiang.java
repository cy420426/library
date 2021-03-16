package com.cy.service;

import com.cy.bean.result;
import com.cy.controller.seatController;
import com.cy.dao.UserDao;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import static com.cy.controller.seatController.*;

public class dingxiang implements Runnable {
    //1.创建连接池管理器
    PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager(60000,TimeUnit.MILLISECONDS);
    {
        pool.setMaxTotal(1000);
        pool.setDefaultMaxPerRoute(500);
    }
    CloseableHttpClient aDefault = HttpClients.custom().setConnectionManager(pool).disableAutomaticRetries().build();
    RequestConfig.Builder config = RequestConfig.custom().setSocketTimeout(70000).setConnectTimeout(70000)
            .setConnectionRequestTimeout(5000);
    private Integer ssid;
    private String login123;
    private String shijian;
    private long ge;
    private String info ="null";


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getGe() {
        return ge;
    }

    public void setGe(Date d) {
        this.ge = d.getTime();
    }

    public dingxiang( Integer ssid, String login123, String shijian,String info) {
        this.ssid = ssid;
        this.login123 = login123;
        this.shijian = shijian;
        this.info = info;
    }
    public Integer getSsid() {
        return ssid;
    }

    public String getLogin123() {
        return login123;
    }

    public String getShijian() {
        return shijian;
    }
    public void run () {
        try {
//           System.out.println("发送");
            HttpUriRequest login = RequestBuilder.post().setUri("http://210.44.64.139/touchscreen/seatOrderAction.php?action=payCardLogin").setConfig(config.build()).
                    setHeader("User-Agent", "").addParameter("cardno", login123).build();
            //public static int i= seatController.seatinfo.getSeatid();
            CloseableHttpResponse response = aDefault.execute(login);

            String html = EntityUtils.toString(response.getEntity(), "UTF-8");
            // if(response.getStatusLine().getStatusCode()==200)
            //判断响应码是否正常，调试正常所以现在可以不用再判断
            //     {

            if (html.contains("false")) {
                EntityUtils.consume(response.getEntity());
                aDefault.close();
                pool.shutdown();
                setInfo("error");
            }
            // if(response.getStatusLine().getStatusCode()==200)
            //判断响应码是否正常，调试正常所以现在可以不用再判断
            //     {
            EntityUtils.consume(response.getEntity());
            for (int s = 1; ; s++) {
                try {
//                    System.out.println("继续请求");
                    if (info.equals("true") ||info.equals("false")||info.equals("error"))
                        break;
                    HttpUriRequest addseat = RequestBuilder.post().setUri("http://210.44.64.139/touchscreen/seatOrderAction.php?action=addOrderSeat").setConfig(config.build()).
                            setHeader("User-Agent", "").
                            addParameter("seat_id", String.valueOf(ssid)).
                            addParameter("order_date", shijian).build();

                    CloseableHttpResponse add = aDefault.execute(addseat);
                    String seat = EntityUtils.toString(add.getEntity(), "UTF-8");
//                    System.out.println(login123+"号"+ssid+seat);
//                    System.out.println(ge);
                    EntityUtils.consume(add.getEntity());
                    if (seat.contains("true")) {
                       // System.out.println(threadLocalUtil.getResult().isSuccess());
                        aDefault.close();
                        pool.shutdown();
                        setInfo("true");
                        break;
                    }
                    if (seat.contains("被预约") || seat.contains("只能预约") || seat.contains("重复") || seat.contains("闭馆")) {
                        if (seat.contains("只能预约") && (System.currentTimeMillis() - ge < 3600000))//判断系统预约时间是否开启
                        {
                            TimeUnit.SECONDS.sleep(40);
                            continue;
                        } else {//否则结束
                            aDefault.close();
                            pool.shutdown();
                            setInfo("false");
                            break;
                        }


                    }

                    // }
                } catch (IOException e) {
                    continue;
                }
                Thread.sleep(50);
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("io异常");
            pool.shutdown();
            setInfo("false");
        }

    }

}