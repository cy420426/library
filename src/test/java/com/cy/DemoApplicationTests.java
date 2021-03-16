package com.cy;


import com.cy.bean.result;
import com.cy.dao.seatorderDao;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private JavaMailSender javaMailSender1;
    @Autowired
    private seatorderDao seatorderDao;
    //    @Autowired
//    private DiscussDao discussDao;
//    @Autowired
//    private discussUserdao discussUserdao;
    int count = 0;

    @Test
    void contextLoads() throws InterruptedException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom("1481083690@qq.com");
////        1293954899@qq.com
////        443312666@qq.com
////        906413966@qq.com
////        346810351@qq.com
////        2289919112@qq.com
////        641579089@qq.com
        simpleMailMessage.setTo("2521462110@qq.com");
        simpleMailMessage.setSubject("项目重新部署，请重新提交订单");
        simpleMailMessage.setText("项目重新部署，请重新提交订单");
        javaMailSender1.send(simpleMailMessage);



//        for (int i = 0; i <100 ; i++) {
//            executorService.submit(r);
//        }
//
//Thread.sleep(3000000);
    }

//    @Test
//    public void selecttest(){
//        List<Discuss> discusses = discussDao.selectDiscuss(0, 0, 10);
//        for(Discuss s:discusses)
//        {
//            System.out.println(s);
//        }
//        int i = discussDao.selectDiscussCount(0);
//        System.out.println(i);
//    }
//
//    @Test
//    public void usertest(){
//        discussUser discussUser = discussUserdao.selectByid(101);
//        System.out.println(discussUser);
//
//    }
    @Test
    public void test(){

        result result = seatorderDao.selectResult("0270156299");
        if(result!=null)
        {
            seatorderDao.updateResult("0270156299",1,new Date());
        }
        else{
            result r = new result();
            r.setUsername("0270156299");
            r.setResult(0);
            r.setDate(new Date());
            seatorderDao.insertResult(r);
        }


    }

}
