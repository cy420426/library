package com.cy.dao;

import com.cy.bean.result;
import com.cy.bean.seatOrder;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface seatorderDao {
    void createorder(seatOrder s);
    seatOrder queryorder(Integer seatid,String fangjian);
    seatOrder queryorderbyuser(String username);
    int deleteorder(String username);
    int updateorder(String username,int state);
    int countorder();

    result selectResult(String username);
    int insertResult(result r);
    int updateResult(String username, int r, Date date);

}
