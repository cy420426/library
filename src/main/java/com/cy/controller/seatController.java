package com.cy.controller;

import com.alibaba.fastjson.JSON;
import com.cy.bean.*;
import com.cy.dao.UserDao;
import com.cy.dao.seatDao;
import com.cy.dao.seatorderDao;
import com.cy.service.dingxiang;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

import static com.cy.service.dingxiang.*;


@Controller
@CrossOrigin
public class seatController {
    @Autowired
    private seatorderDao seatorderDao;
    @Autowired
    private seatDao seatDao;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserDao userdao;

    public  String logininfo1;
    public  String email1;
    public  seat seatinfo;
    public  String shijian1;
    public Date dingshi1;
    int zuohao22;
    public  String fangjian1;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public seatController() {
    }
    @RequestMapping("query")
    @ResponseBody
    public  String queryseat(@RequestBody seatQuery q){
        User getuserbyname = userdao.getuserbyname(q.getLogininfo());
        q.setLogininfo(getuserbyname.getRole());
        email1=getuserbyname.getEmail();
        logininfo1=q.getLogininfo();
        shijian1 =sdf.format(q.getShijian());
        zuohao22 =q.getSeatid();
        dingshi1=q.getDingshi();
        fangjian1=q.getFangjian();
        if(q.getLogininfo()!=null) {
            if (fangjian1.equals("1楼101A")) {
                seatinfo = seatDao.query101a(q.getSeatid());
                return seatinfo != null ? JSON.toJSONString(seatinfo) : "error";

            }
            if (fangjian1.equals("1楼101B")) {
                seatinfo = seatDao.query101b(q.getSeatid());
                return seatinfo != null ? JSON.toJSONString(seatinfo) : "error";

            }
            if (fangjian1.equals("1楼107(1-160)")) {
                seatinfo = seatDao.query107a(q.getSeatid());
                return seatinfo != null ? JSON.toJSONString(seatinfo) : "error";

            }
            if (fangjian1.equals("1楼107(161-420)")) {
                seatinfo = seatDao.query107b(q.getSeatid());
                return seatinfo != null ? JSON.toJSONString(seatinfo) : "error";

            }
            if (fangjian1.equals("二楼南厅自修区")) {
                seatinfo = seatDao.querysecond(q.getSeatid());
                return seatinfo != null ? JSON.toJSONString(seatinfo) : "error";

            }
            if (fangjian1.equals("三楼北厅自修区")) {
                seatinfo = seatDao.querythird(q.getSeatid());
                return seatinfo != null ? JSON.toJSONString(seatinfo) : "error";

            }
            if (fangjian1.equals("四楼北厅自修区")) {
                seatinfo = seatDao.queryfourth(q.getSeatid());
                return seatinfo != null ? JSON.toJSONString(seatinfo) : "error";

            }
            if (fangjian1.equals("五楼北厅自修区")) {
                seatinfo = seatDao.queryfifth(q.getSeatid());
                return seatinfo != null ? JSON.toJSONString(seatinfo) : "error";
            }
        }
        return "error";
    }

    @RequestMapping("queryorder")
    @ResponseBody
    public synchronized String queryorder(@RequestBody seatQuery q) throws ParseException {
        if(logininfo1!=null)//判断登录信息，未登录返回信息
        { seatOrder queryorder1= seatorderDao.queryorder(q.getSeatid(),q.getFangjian());
            seatOrder querybyuser =seatorderDao.queryorderbyuser(logininfo1);
            if(seatinfo!=null) {
                if (queryorder1 != null||querybyuser!=null)//登录之后判断用户是否存在订单，存在阻止用户继续操作
                {
                    if(querybyuser!=null){
                        if(querybyuser.getUsername().equals(logininfo1))
                            return "重复提交";
                    }
                    return "订单已存在";
                }
                else {//订单不存在，创建订单
                    seatOrder s = new seatOrder();
                    if (q.getDingshi()!=null){
                        String f = new SimpleDateFormat("HH:mm:ss").format(q.getDingshi());
                        Date r = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(shijian1+" "+f);
                        s.setDingshi(r);
                    }
                    s.setUsername(logininfo1);
                    s.setSeatid(q.getSeatid());
                    s.setCreatedate(new Date());
                    s.setFangjian(q.getFangjian());
                    s.setState(0);
                    seatorderDao.createorder(s);
                    return JSON.toJSONString(s);
                }

            }
            else return "座位错误";
        }else
            return "当前未登录";
    }
    @RequestMapping("seatyewu")
    @ResponseBody
    public  String seatyewu() throws InterruptedException, ExecutionException {
        String log1 = logininfo1;
        Integer ssid = seatinfo.getSeatid();
        String email=email1;
        int zuohao =zuohao22;
        String fangjian = fangjian1;
        String info = "null";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        Date d = new Date();
        if((d.getHours()>=0&&d.getHours()<5)||(d.getHours()>=22&&d.getHours()<=23))
        {
            simpleMailMessage.setFrom("1481083690@qq.com");
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("时间有误");
            simpleMailMessage.setText("当前为闭馆时间，请在图书馆系统正常开放范围内提交立即约座请求！");
            javaMailSender.send(simpleMailMessage);
            seatorderDao.deleteorder(log1);
            return "时间有误";
        }

//    ExecutorService sd = Executors.newFixedThreadPool(3);
        ExecutorService sd = Executors.newFixedThreadPool(3);
        if (log1==null)
            return "当前未登录";//
        // 判断当前用户是否是登录状态
        dingxiang dingxiang = new dingxiang( ssid, log1, shijian1,info);
        dingxiang.setGe(d);
        for (int s = 1;; s++) {
            if ( dingxiang.getInfo().equals("true")||dingxiang.getInfo().equals("error")||dingxiang.getInfo().equals("false")) {
                sd.shutdownNow();
                break;
            }
            sd.submit( dingxiang);

            Thread.sleep(1000);
        }

//    dingxiang.isClose() && !dingxiang.isErr() && dingxiang.isLogin1() == false
        if (dingxiang.getInfo().equals("error")) {

            int is = seatorderDao.deleteorder(log1);
            if(is!=1)
            {
                System.out.println("删除失败");
            }
            simpleMailMessage.setFrom("1481083690@qq.com");
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("信息有误");
            simpleMailMessage.setText("您提供的登录信息有误！");
            javaMailSender.send(simpleMailMessage);
            return "登录有误";
        }
        else if (dingxiang.getInfo().equals("true")) {
            int is = seatorderDao.deleteorder(log1);
            if(is!=1)
            {
                System.out.println("删除失败");
            }
            result result = seatorderDao.selectResult(logininfo1);
            if(result!=null)
            {
                seatorderDao.updateResult(logininfo1,1,new Date());
            }
            else{
                result r = new result();
                r.setUsername(logininfo1);
                r.setResult(1);
                r.setDate(new Date());
                seatorderDao.insertResult(r);
            }
            simpleMailMessage.setFrom("1481083690@qq.com");
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("抢座成功");
            simpleMailMessage.setText("抢座成功!您的座号为：" + fangjian + "," + zuohao + "号座位，感谢您的使用");
            javaMailSender.send(simpleMailMessage);
            return "预约成功";
//                dingxiang.isErr() && !dingxiang.isClose()
        } else if (dingxiang.getInfo().equals("false")) {
            int is = seatorderDao.deleteorder(log1);
            if(is!=1)
            {
                System.out.println("删除失败");
            }
            result result = seatorderDao.selectResult(logininfo1);
            if(result!=null)
            {
                seatorderDao.updateResult(logininfo1,0,new Date());
            }
            else{
                result r = new result();
                r.setUsername(logininfo1);
                r.setResult(0);
                r.setDate(new Date());
                seatorderDao.insertResult(r);
            }
            simpleMailMessage.setFrom("1481083690@qq.com");
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("抢座失败");
            simpleMailMessage.setText("抢座失败!非常抱歉");
            javaMailSender.send(simpleMailMessage);
            return "预约失败";
        }  else
        {

            int is = seatorderDao.deleteorder(logininfo1);
            if(is!=1)
            {
                System.out.println("删除失败");
            }
            result result = seatorderDao.selectResult(logininfo1);
            if(result!=null)
            {
                seatorderDao.updateResult(logininfo1,0,new Date());
            }
            else{
                result r = new result();
                r.setUsername(logininfo1);
                r.setResult(0);
                r.setDate(new Date());
                seatorderDao.insertResult(r);
            }
            simpleMailMessage.setFrom("1481083690@qq.com");
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("图书馆服务器数据返回错误");
            simpleMailMessage.setText("无法确定是否成功，图书馆服务器数据返回错误！");
            javaMailSender.send(simpleMailMessage);
            return "error";
        }

    }
    public  String seatdingshiyewu(String fangjian1 ,Integer ssid,String logininfo1,String shijian,String email,int zuohao22,String info) throws InterruptedException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        ExecutorService sd = Executors.newFixedThreadPool(2);
        if (logininfo1==null)
            return "当前未登录";//
        // 判断当前用户是否是登录状态
        dingxiang dingxiang = new dingxiang( ssid, logininfo1, shijian,info);
        dingxiang.setGe(new Date());
        for (int s = 1;; s++) {
//            dingxiang.isClose()||dingxiang.isErr()|| dingxiang.isLogin1()
           // System.out.println(threadLocalUtil.getResult().isSuccess());
            if (dingxiang.getInfo().equals("true")||dingxiang.getInfo().equals("error")||dingxiang.getInfo().equals("false")) {
                sd.shutdownNow();
                break;
            }
            sd.submit(dingxiang);
            Thread.sleep(1000);
        }
            if (dingxiang.getInfo().equals("error")) {
                int is = seatorderDao.deleteorder(logininfo1);
                if(is!=1)
                {
                    System.out.println("删除失败");
                }
                simpleMailMessage.setFrom("1481083690@qq.com");
                simpleMailMessage.setTo(email);
                simpleMailMessage.setSubject("信息有误");
                simpleMailMessage.setText("您提供的登录信息有误！");
                javaMailSender.send(simpleMailMessage);
                return "登录有误";
                //dingxiang.isClose() && !dingxiang.isErr()
            } else if (dingxiang.getInfo().equals("true")) {

                int is = seatorderDao.deleteorder(logininfo1);
                if(is!=1)
                {
                    System.out.println("删除失败");
                }
                result result = seatorderDao.selectResult(logininfo1);
                if(result!=null)
                {
                    seatorderDao.updateResult(logininfo1,1,new Date());
                }
                else{
                    result r = new result();
                    r.setUsername(logininfo1);
                    r.setResult(1);
                    r.setDate(new Date());
                    seatorderDao.insertResult(r);
                }
                simpleMailMessage.setFrom("1481083690@qq.com");
                simpleMailMessage.setTo(email);
                simpleMailMessage.setSubject("抢座成功");
                simpleMailMessage.setText("抢座成功!您的座号为：" + fangjian1 + "," + zuohao22 + "号座位，感谢您的使用");
                javaMailSender.send(simpleMailMessage);
                return "预约成功";
            } else if (dingxiang.getInfo().equals("false")) {

                int is = seatorderDao.deleteorder(logininfo1);
                if(is!=1)
                {
                    System.out.println("删除失败");
                }
                result result = seatorderDao.selectResult(logininfo1);

                if(result!=null)
                {
                    seatorderDao.updateResult(logininfo1,0,new Date());
                }
                else{
                    result r = new result();
                    r.setUsername(logininfo1);
                    r.setResult(0);
                    r.setDate(new Date());
                    seatorderDao.insertResult(r);
                }
                simpleMailMessage.setFrom("1481083690@qq.com");
                simpleMailMessage.setTo(email);
                simpleMailMessage.setSubject("抢座失败");
                simpleMailMessage.setText("抢座失败!非常抱歉");
                javaMailSender.send(simpleMailMessage);
                return "预约失败";
            } else {
                int is = seatorderDao.deleteorder(logininfo1);
                if(is!=1)
                {
                    System.out.println("删除失败");
                }
                result result = seatorderDao.selectResult(logininfo1);
                if(result!=null)
                {
                    seatorderDao.updateResult(logininfo1,0,new Date());
                }
                else{
                    result r = new result();
                    r.setUsername(logininfo1);
                    r.setResult(0);
                    r.setDate(new Date());
                    seatorderDao.insertResult(r);
                }
                simpleMailMessage.setFrom("1481083690@qq.com");
                simpleMailMessage.setTo(email);
                simpleMailMessage.setSubject("图书馆服务器数据返回错误");
                simpleMailMessage.setText("无法确定是否成功，图书馆服务器数据返回错误！");
                javaMailSender.send(simpleMailMessage);
                return "error";
            }

        }
    @RequestMapping("seatdingshi")
    @ResponseBody
    public String dingshiseat() throws ParseException {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        String log2 = logininfo1;
        String shijian = shijian1;
        Integer i = seatinfo.getSeatid();
        String email=email1;
        int zuohao =zuohao22;
        String fangjian =fangjian1;
        String info = "null";

        Runnable runnable = new Runnable() {
            @SneakyThrows
            @Override
            public void run () {
                seatdingshiyewu(fangjian, i, log2, shijian, email, zuohao,info);
                service.shutdownNow();
            }

        };
        String s = new SimpleDateFormat("HH:mm:ss").format(dingshi1);
        Date r = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(shijian+" "+s);

        if(r.getTime()<System.currentTimeMillis()) {
            seatorderDao.deleteorder(log2);
            return "时间有误";

        }
        service.schedule(runnable, r.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        return "定时开启";
    }

    @RequestMapping("count")
    @CrossOrigin
    @ResponseBody
    public String getCount(){
        int countorder = seatorderDao.countorder();
        return String.valueOf(countorder);
    }
    @RequestMapping(path = "/result",method = RequestMethod.POST)
    @ResponseBody
    public String getResult(@RequestBody result resultform)
    {
        if(resultform!=null) {
            User getuserbyname = userdao.getuserbyname(resultform.getUsername());
            result result = seatorderDao.selectResult(getuserbyname.getRole());
            if(result==null)
            {
                return "null";
            }
            return JSON.toJSONString(result);
        }
        else
            return "error";
    }

}
