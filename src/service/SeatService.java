package service;

import entity.Seat;
import entity.ShowTime;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class SeatService {

    private final FileUtil<Seat> fileUtil = new FileUtil<>();
    private static final String SEAT_DATA_FILE = "seats.json";
    private List<Seat> seats = new ArrayList<>();

    public void saveSeatData() {
        fileUtil.writeDataToFile(seats, SEAT_DATA_FILE);
    }
}
