package com.cy.controller;

import com.alibaba.fastjson.JSON;
import com.cy.bean.barrage;
import com.cy.dao.barragerDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@CrossOrigin
public class barrageController {
    @Autowired
    private barragerDao dao;
    @RequestMapping("insertbar")
    public String insertBarrage(@RequestBody barrage msg){
        int insertbarrage = dao.insertbarrage(msg);
        if(insertbarrage==1)
            return "插入成功";
        return "插入失败";
    }
    @RequestMapping("getallBarrage")
    public String getallBarrager(){
        List<barrage> findall = dao.findall();
        Map<String,Object> m = new HashMap<>();
        m.put("data",findall);
        String d = JSON.toJSONString(m);
        return d;
    }
}
