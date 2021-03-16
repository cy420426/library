package com.cy.dao;

import com.cy.bean.User;
import com.cy.bean.editinfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface UserDao {
    User getByMessage(@Param("username") String username, @Param("userpassword") String userpassword);
    List<User> getalluser(@Param("username") String username,@Param("pageStart") int pageStart,@Param("pageSize") int pageSize);
    int getusercounts(@Param("username") String username);
    int upedatestate(Integer id,Boolean state);
    int adduser(User user);
    int deluser(int id);
    User getupdateUser(int id);
    int edituser(User user);
    User getuserbyname(String username);
    int updateinfo1(editinfo e);



}
