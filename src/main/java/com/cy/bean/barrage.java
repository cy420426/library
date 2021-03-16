package com.cy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Repository;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Data
@Repository
public class barrage {
    private int id;
    private String msg;
}
