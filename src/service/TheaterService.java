package service;

import constant.DateTimeConstant;
import constant.SeatClass;
import constant.Status;
import entity.Movie;
import entity.Seat;
import entity.Theater;
import util.FileUtil;
import util.InputUtil;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class TheaterService {

    private final FileUtil<Theater> fileUtil = new FileUtil<>();
    private static final String THEATER_DATA_FILE = "theaters.json";
    private final FileUtil<Seat> fileUtil2 = new FileUtil<>();
    private static final String SEAT_DATA_FILE = "seats.json";
    private static int AUTO_ID;
    private static int AUTO_ID2;
    private List<Theater> theaters = new ArrayList<>();
    private List<Seat> seats = new ArrayList<>();


    private void saveSeatData() {
        fileUtil2.writeDataToFile(seats, SEAT_DATA_FILE);
    }

    public void saveTheaterData() {
        fileUtil.writeDataToFile(theaters, THEATER_DATA_FILE);
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
        SeatClass seatClass;
        Seat seat = new Seat();
        List<Seat> seatsInTheater = new ArrayList<>();
        System.out.print("Nhập số lượng hàng ghế: ");
        int totalRows = new Scanner(System.in).nextInt();
        for (int i = 0; i < totalRows; i++) {
            System.out.println("Nhập tên hàng ghế thứ " + (i + 1) + ":");
            String rowName = new Scanner(System.in).nextLine();
            System.out.println("Nhập số lượng ghế của hàng này: ");
            int seatQuantity = new Scanner(System.in).nextInt();
            for (int j = 0; j < seatQuantity; j++) {
                System.out.println("Đánh số cho ghế thứ " + (j+1) + ":");
                int seatNumber = new Scanner(System.in).nextInt();
                if (i < 2) {
                    seatClass = SeatClass.STANDARD;
                } else if (i < totalRows - 1) {
                    seatClass = SeatClass.VIP;
                } else {
                    seatClass = SeatClass.SWEETBOX;
                }
                seat = new Seat(AUTO_ID2++, rowName, seatNumber, Status.ACTIVE, seatClass);
            seatsInTheater.add(seat);
            }
        }
        Theater theater = new Theater(AUTO_ID++, theaterName, createdDate, Status.ACTIVE, seatsInTheater);
        theaters.add(theater);
        showTheater(theater);
        saveTheaterData();
        seats.addAll(seatsInTheater);
        saveSeatData();
    }


    public void updateTheater() {
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
            System.out.println("4. Thay đổi trạng thái ghế ngồi trong phòng chiếu: ");
            System.out.println("5. Thoát");
            int functionChoice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại",
                    1, 5);
            switch (functionChoice) {
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
                    break;
                case 4:
                    System.out.println("Mời bạn nhập hàng ghế cần chỉnh sửa:");
                    String rowName = new Scanner(System.in).nextLine();
                    findRowName(rowName);
                    System.out.println("Mời bạn chọn tác vụ chỉnh sửa: ");
                    System.out.println("1. ACTIVE seat");
                    System.out.println("2. MAINTAINING seat");
                    int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                            "Chức năng là số dương từ 1 tới 2, vui lòng nhập lại",
                            1, 2);
                    switch (choice) {
                        case 1:
                            System.out.println(" Nhập các ghế về trạng thái có thể sử dụng (ACTIVE)" +
                                    " theo định dạng các số cách nhau bởi dấu phẩy, ví dụ: 1, 8, 12,...");
                            String inactiveSeatStr = new Scanner(System.in).nextLine();
                            List<String> inactiveSeats = Arrays.asList(inactiveSeatStr.split(","));
                            for (int j = 0; j < seats.size(); j++) {
                                for (Seat value : seats) {
                                    if (value.getRow().equals(rowName) &&
                                            value.getId() == Integer.parseInt(inactiveSeats.get(j))) {
                                        value.setStatus(Status.ACTIVE);
                                    }
                                }
                            }
                            theater.setSeats(seats);
                        case 2:
                            System.out.println(" Nhập các ghế về trạng thái bảo trì (MAINTAINING)" +
                                    " theo định dạng các số cách nhau bởi dấu phẩy, ví dụ: 1, 8, 12,...");
                            String maintainSeatStr = new Scanner(System.in).next();
                            List<String> maintainSeats = Arrays.asList(maintainSeatStr.split(","));
                            for (int i = 0; i < seats.size(); i++) {
                                for (Seat value : seats) {
                                    if (value.getRow().equals(rowName) &&
                                            value.getId() == Integer.parseInt(maintainSeats.get(i))) {
                                        value.setStatus(Status.MAINTAINING);
                                    }
                                }
                            }
                            theater.setSeats(seats);
                    }
                    break;
                case 5:
                    return;
            }
            saveTheaterData();
            showTheater(theater);
            break;
        }
    }

    private void findRowName(String rowName) {
        for (Seat seat : seats) {
            if (Objects.equals(seat.getRow(), rowName)) {
                return;
            }
        }
    }


    public Theater findTheaterById(int theaterID) {
        for (Theater theater : theaters) {
            if (theater.getTheaterID() == theaterID) {
                return theater;
            }
        }
        return null;
    }


    public void showTheater(Theater theater) {
        printHeader();
        showTheaterDetail(theater);
    }

    public void printHeader() {
        System.out.printf("%-5s%-15s%-30s%-25s%n", "Id", "Name", "Created Date", "Status");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }


    private void showTheaterDetail(Theater theater) {
        System.out.printf("%-5s%-15s%-30s%-25s%n", theater.getTheaterID(), theater.getTheaterName(), theater.getCreatedDate(), theater.getStatus());
    }

    private void showTheatersDetails(List<Theater> theaters) {
        printHeader();
        for (Theater theater:theaters) {
            showTheaterDetail(theater);
        }
    }


    public void showingTheaterList() {
        printHeader();
        for (Theater theater : theaters) {
            showTheaterDetail(theater);
        }
    }

    public void showingTheaterByID() {
        System.out.println("Mời bạn nhập ID của rạp phim: ");
        int theaterID = new Scanner(System.in).nextInt();
        for (Theater theater : theaters) {
            if (theater.getTheaterID() == theaterID) {
                System.out.println("------- THÔNG TIN PHÒNG CHIẾU --------");
            }
            printHeader();
            showTheaterDetail(theater);
        }
    }

    public Theater getTheaterActive(int id) {
        for (Theater theater : theaters) {
            if (theater.getTheaterID() == id && theater.getStatus() == Status.ACTIVE) {
                return theater;
            }
        }
        return null; //Trả về null nếu không tìm thấy phim hoặc Inactive
    }


    public void showTheaterById() {
        System.out.println("Mời bạn nhập ID của phòng chiếu: ");
        int theaterID = new Scanner(System.in).nextInt();
        for (Theater theater : theaters) {
            if (theater.getTheaterID() == theaterID) {
                printHeader();
                showTheaterDetail(theater);
            }
        }
    }

    public void showingTheaterActive() {
        List<Theater> theaters1 = new ArrayList<>();
        for (Theater theater : theaters) {
            if (theater.getStatus() == Status.ACTIVE) {
                theaters1.add(theater);
            }
        }
                showTheatersDetails(theaters1);
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

    public void showTheaters() {
        printHeader();
        for (Theater theater : theaters) {
            showTheaterDetail(theater);
        }
    }


    public void setTheater() {
        List<Theater> theaterList = fileUtil.readDataFromFile(THEATER_DATA_FILE, Theater[].class);
        theaters = theaterList != null ? theaterList : new ArrayList<>();

    }

    public void findCurrenAutoId() {
        int maxId = -1;
        for (Theater theater : theaters) {
            if (theater.getTheaterID() > maxId) {
                maxId = theater.getTheaterID();
            }
        }
        AUTO_ID = maxId + 1;
    }


}



