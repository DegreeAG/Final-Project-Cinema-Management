package entity;

import constant.Status;

import java.time.LocalDate;

public class Theater {

    private int theaterID;
    private String theaterName;
    private LocalDate createdDate;
    private Status status;
    private Seat[] seat;

}
