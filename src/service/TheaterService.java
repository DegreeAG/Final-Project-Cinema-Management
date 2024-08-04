package service;

import constant.DateTimeConstant;
import constant.Status;
import entity.Movie;
import entity.Seat;
import entity.Theater;
import entity.User;
import util.FileUtil;
import util.InputUtil;

import java.net.SecureCacheResponse;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

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
        System.out.println("Mời bạn nhập tên phòng chiếu: ");
        String theaterName = new Scanner(System.in).nextLine();
        LocalDate createdDate;
        while (true) {
            System.out.println("Mời bạn nhập ngày khai trương phòng chiếu theo định dạng dd/MM/yyyy: ");
            try {
                createdDate = LocalDate.parse(new Scanner(System.in).nextLine(), DateTimeConstant.DATE_FORMATTER);
                break;
            } catch (DateTimeException e) {
                System.out.println("Định dạng không hợp lệ vui lòng nhập lại ");
            }
        }
        System.out.println("Phòng chiếu này có bao nhiêu ghế: ");
        int seatNumber = new Scanner(System.in).nextInt();
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < seatNumber; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(seatNumber);
            System.out.println("Nhập hàng ghế: ");
            seat.setRow(new Scanner(System.in).nextLine());
            System.out.println("Nhập số ghế: ");
            seat.setSeatNumber(new Scanner(System.in).nextInt());
        }
        Theater theater = new Theater(AUTO_ID++, theaterName, createdDate, Status.ACTIVE, seats);
        theaters.add(theater);
    }
    public void updateTheater () {
        while (true) {
            System.out.println("Mời bạn nhập ID của phòng chiếu : ");
            int theaterID;
            while (true) {
                try {
                    theaterID = new Scanner(System.in).nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                }
            }
            Theater theater = findTheaterById(theaterID);
            if (theater == null) {
                System.out.println("Thông tin không chính xác , vui lòng nhập lại : ");
                continue;
            }
            System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa : ");
            System.out.println("1. Tên phòng chiếu: ");
            System.out.println("2. Ngày khai trương phòng chiếu: ");
            System.out.println("3. Trạng thái phòng chiếu: ");
            System.out.println("4. Số ghế phòng chiếu: ");
            System.out.println("5. Thoát");
            int functionChoice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại",
                    1, 5);
            switch (functionChoice){
                case 1:
                    System.out.println("Mời bạn nhập tên mới cho phòng chiếu: ");
                    theater.setTheaterName(new Scanner(System.in).nextLine());
                    break;
                case 2:
                    while (true) {
                    System.out.println("Mời bạn nhập lại ngày khai trương phòng chiếu: ");
                    try {
                    theater.setCreatedDate(LocalDate.parse(new Scanner(System.in).nextLine(), DateTimeConstant.DATE_FORMATTER));
                        break;
                    } catch (DateTimeException e) {
                        System.out.println("Định dạng không hợp lệ, vui lòng nhập lại");
                    }
                }
                    break;
                case 3:
                    System.out.println("Mời bạn lựa chọn trạng thái mới của phòng chiếu: ");
                    System.out.println("1. HOẠT ĐỘNG");
                    System.out.println("2. KHÔNG HOẠT ĐỘNG");
                    System.out.println("3. BẢO TRÌ");
                    System.out.println("4. Thoát");
                    int statusChoice = InputUtil.chooseOption("Xin mời chọn chức năng",
                            "Chức năng là số dương từ 1 tới 4, vui lòng nhập lại",
                            1, 4);
                    switch (statusChoice) {
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
                case 4:
                    System.out.println("Mời bạn nhập số lượng ghế mới bên trong phòng chiếu: ");
                    int seatNumber = new Scanner(System.in).nextInt();
                    List<Seat> seats = new ArrayList<>();
                    for (int i = 0; i < seatNumber; i++) {
                        Seat seat = new Seat();
                        seat.setSeatNumber(seatNumber);
                        System.out.println("Nhập hàng ghế: ");
                        seat.setRow(new Scanner(System.in).nextLine());
                        System.out.println("Nhập số ghế: ");
                        seat.setSeatNumber(new Scanner(System.in).nextInt());
                    }
                    theater.setSeats(seats);
                case 5:
                    return;
            }
        }
    }


    private Theater findTheaterById(int theaterID) {
        for (Theater theater : theaters) {
            if (theater.getTheaterID() == theaterID) {
                return theater;
            }
        }
        return null;
    }

    public void printHeader() {
        System.out.printf("%-5s%-30s%-30s%-20s%-20s%-10s%-10s%n", "Id", "Name", "Created Date","Status", "Seat Number");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }

    public void printTheater(List<Theater> theaters1) {
        printHeader();
        for (Theater theater : theaters1) {
            showTheaterDetail(theater);
        }
    }

    private void showTheaterDetail(Theater theater) {
        System.out.printf("%-5s%-30s%-30s%-20s%-20s%-10s%-10s%n", theater.getTheaterID(), theater.getTheaterName(), theater.getCreatedDate(), theater.getStatus(), theater.getSeats());
    }

    public void showingTheaterList() {
        printHeader();
        for (Theater theater : theaters) {
            showTheaterDetail(theater);
        }
    }

    public void showingTheaterbyID() {
        System.out.println("Mời bạn nhập ID của rạp phim: ");
        int theaterID = new Scanner(System.in).nextInt();
        this.findTheaterById(theaterID);

    }

    public Theater getTheaterActive(int i) {
        for (Theater theater : theaters) {
            if (theater.getTheaterID() == i && theater.getStatus() == Status.ACTIVE) {
                    return theater;
                }
            }
        return null; //Trả về null nếu không tìm thấy phim hoặc Inactive
    }


    public void showingTheaterActive() {
        printHeader();
        List<Theater> theaters1 = new ArrayList<>();
        for (Theater theater : theaters) {
            if (theater.getStatus(Status.ACTIVE) == Status.ACTIVE) {
                theaters1.add(theater);
                System.out.println(theaters1);
            }
            return;
        }
    }

    public void showingTheaterInActive() {
        printHeader();
        List<Theater> theaters1 = new ArrayList<>();
        for (Theater theater : theaters) {
            if (theater.getStatus(Status.INACTIVE) == Status.INACTIVE) {
                theaters1.add(theater);
                System.out.println(theaters1);
            }
            return;
        }
    }

    public void showingTheaterMaintaining() {
        printHeader();
        List<Theater> theaters1 = new ArrayList<>();
        for (Theater theater : theaters) {
            if (theater.getStatus(Status.MAINTAINING) == Status.MAINTAINING) {
                theaters1.add(theater);
                System.out.println(theaters1);
            }
            return;
        }
    }



}



