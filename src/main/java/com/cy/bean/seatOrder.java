package com.cy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Data
public class seatOrder {
    private Integer id;
    private String username;
    private Integer seatid;
    private Date createdate;
    private Integer state;
    private Date dingshi;
    private String fangjian;

}
