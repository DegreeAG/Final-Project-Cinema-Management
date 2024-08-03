package service;

import entity.Seat;
import util.FileUtil;

public class SeatService {

    private final FileUtil<Seat> fileUtil = new FileUtil<>();
    private static final String SEAT_DATA_FILE = "seats.json";
}
