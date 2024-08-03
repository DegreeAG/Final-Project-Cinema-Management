package service;

import constant.DateTimeConstant;
import constant.Status;
import entity.Seat;
import entity.Theater;
import util.FileUtil;
import util.InputUtil;

import java.net.SecureCacheResponse;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class TheaterService {

    private final FileUtil<Theater> fileUtil = new FileUtil<>();
    private static final String THEATER_DATA_FILE = "theaters.json";
    private static int AUTO_ID;
    private List<Theater> theaters;
    private List<Seat> seats;





    public void setTheaters() {
        List<Theater> theatersList = fileUtil.readDataFromFile(THEATER_DATA_FILE, Theater[].class);
        theaters = theatersList != null ? theatersList : new ArrayList<>();
    }


    public void createTheater() {
        Theater theater = new Theater();
        System.out.println("Mời bạn nhập tên phòng chiếu: ");
        theater.setTheaterName(new Scanner(System.in).nextLine());
        while (true) {
            System.out.println("Mời bạn nhập ngày khai trương phòng chiếu theo định dạng dd/MM/yyyy: ");
            try {
                theater.setCreatedDate(LocalDate.parse(new Scanner(System.in).nextLine()), DateTimeConstant.DATE_FORMATTER);
                break;
            }catch (DateTimeException e) {
                System.out.println("Định dạng không hợp lệ vui lòng nhập lại ");
            }
        }
        System.out.println("Lựa chọn tình trạng của phòng chiếu: ");
        System.out.println("1. Hoạt động");
        System.out.println("2. Ngưng hoạt động");
        System.out.println("3. Bảo trì");
        System.out.println("4. Thoát");
        int choice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                "Chức năng là số dương từ 1 tới 4, vui lòng nhập lại: ", 1, 4);
        switch (choice) {
            case 1:
                theater.setStatus(Status.ACTIVE);
                break;
            case 2:
                theater.setStatus(Status.INACTIVE);
                break;
            case 3:
                theater.setStatus(Status.MAINTAINING);
                break;
            case 4:
                return;
        }
        System.out.println("Phòng chiếu này có bao nhiêu ghế: ");
        int seatNumber = new Scanner(System.in).nextInt();
        for (int i = 0; i < seatNumber; i++) {

                
            }

        }
    }

