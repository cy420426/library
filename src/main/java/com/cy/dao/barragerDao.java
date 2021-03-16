package com.cy.dao;

import com.cy.bean.barrage;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public  interface barragerDao {
    List<barrage> findall();
    int insertbarrage(barrage msg);
}
