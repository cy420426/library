package com.cy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Accessors(chain = true)
@Repository
public class seatQuery {
    private String fangjian;
    private Date shijian;
    private Integer seatid;
    private String logininfo;
    private Date dingshi;
    private Boolean suiji;

}
