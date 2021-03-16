package com.cy.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
@Data
public class seat {
    private Integer id;
    private Integer seatid;
    private Integer realid;

}
