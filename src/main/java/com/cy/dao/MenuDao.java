package com.cy.dao;

import com.cy.bean.mainmenu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao {
    List<mainmenu> getMenus();
}
