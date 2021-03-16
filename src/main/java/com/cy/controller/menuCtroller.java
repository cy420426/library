package com.cy.controller;

import com.alibaba.fastjson.JSON;
import com.cy.bean.mainmenu;
import com.cy.dao.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class menuCtroller {
    @Autowired
    MenuDao menuDao;
    @RequestMapping("/menus")
    public String getAllmenus(){
        System.out.println("访问成功");
        HashMap<String, Object> data = new HashMap<>();
        int flag =404;
        List<mainmenu> menus = menuDao.getMenus();
        if(menus!=null)
        {
            data.put("menus",menus);
            data.put("flag",200);
        }
        else{
            data.put("flag",404);
        }
        String s = JSON.toJSONString(data);

        return s;
    }
}
