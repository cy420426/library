package com.cy.controller;

import com.alibaba.fastjson.JSON;
import com.cy.bean.Queryinfo;
import com.cy.bean.User;
import com.cy.bean.editinfo;
import com.cy.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
public class userController {

    @Autowired
    private UserDao userDao;
    @RequestMapping("user")
    public String getuserlist(Queryinfo q){
     //获取最大列表数和当前编号
        int counts = userDao.getusercounts("%" + q.getQuery() + "%");
     int pagestart =  (q.getPagenum()-1)*q.getPagesize();
        List<User> userlist = userDao.getalluser("%" + q.getQuery() + "%", pagestart, q.getPagesize());
        HashMap<String, Object> map = new HashMap<>();
        map.put("counts",counts);
        map.put("data",userlist);
        String s = JSON.toJSONString(map);
        System.out.println(map);
        return s;
    }
    @RequestMapping("userstate")

    public String updateuserstate(@RequestParam("id") Integer id,@RequestParam("state") Boolean state){
        int u = userDao.upedatestate(id, state);
        System.out.println(u);
        return u > 0 ? "success" : "error";
    }
    @RequestMapping("adduser")
    public String insertuser(@RequestBody User user){
//        user.setUsername();
//        user.getUserpassword();
//        user.setEmail();
        User getuserbyname = userDao.getuserbyname(user.getUsername());
        if(getuserbyname!=null)
            return "errorname";
        user.setState(false);
        int userflag = userDao.adduser(user);
   return userflag>0?"success":"error";
    }
    @RequestMapping("deleteuser")
    public String deleteuser(int id){
        int i =userDao.deluser(id);
        return i>0?"success":"error";
    }
    @RequestMapping("getupdate")
    public String getupdateuser(int id)
    {
        User u =userDao.getupdateUser(id);
        String s = JSON.toJSONString(u);
        return s;
    }
    @RequestMapping("edituser")
    public String edituser(@RequestBody User user)
    {
        int edituser = userDao.edituser(user);
        return edituser> 0 ?"success":"error";
    }
    @RequestMapping("updateinfo")
    public String update(@RequestBody editinfo e)
    {
        int i = userDao.updateinfo1(e);
        return i>0?"success":"error";
    }
}
