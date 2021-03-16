package com.cy.dao;

import com.cy.bean.seat;
import org.springframework.stereotype.Repository;

@Repository
public interface seatDao {
    seat query101a(int seatid);
    seat query101b(int seatid);
    seat query107a(int seatid);
    seat query107b(int seatid);
    seat querythird(int seatid);
    seat querysecond(int seatid);
    seat queryfourth(int seatid);
    seat queryfifth(int seatid);

    seat query101abyid(int id);
    seat query101bbyid(int id);
    seat query107abyid(int id);
    seat query107bbyid(int id);
    seat querythirdbyid(int id);
    seat querysecondbyid(int id);
    seat queryfourthbyid(int id);
    seat queryfifthbyid(int id);

}
