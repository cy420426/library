package com.cy.controller;

import com.alibaba.fastjson.JSON;
import com.cy.bean.User;
import com.cy.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    UserDao userdao;

    @RequestMapping("login")
    public  String login(@RequestBody User user){
        String biao= "error";
        User byMessage = userdao.getByMessage(user.getUsername(), user.getUserpassword());
//        System.out.println("user:"+byMessage);
        HashMap<String, Object> map = new HashMap<>();
        if(byMessage!=null)
        {
            biao ="ok";

        }
        map.put("biao",biao);
        map.put("user",byMessage);
        String s = JSON.toJSONString(map);
        return s;
    }
}
