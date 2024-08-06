package service;

import constant.Status;
import constant.TransactionType;
import entity.*;
import util.FileUtil;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TicketService {
    private List<User> users;
    private List<ShowTime> showTimes;
    private List<Ticket> tickets;
    private List<Theater> theaters;
    private List<Seat> seats;
    private final FileUtil<Ticket> fileUtil = new FileUtil<>();
    private static final String TICKET_DATA_FILE = "tickets.json";
    private final UserService userService;
    private final ShowTimeService showTimeService;
    private final SeatService seatService;

    public TicketService(UserService userService, ShowTimeService showTimeService, SeatService seatService) {
        this.userService = userService;
        this.showTimeService = showTimeService;
        this.seatService = seatService;
    }

    public void saveTicket(Ticket ticket) {
        tickets.add(ticket);
        saveTicketsData();
    }

    private void saveTicketsData() {
        fileUtil.writeDataToFile(tickets, TICKET_DATA_FILE);
    }


    public void deposit(User user) {
        double price;
        while (true) {
            try {
                System.out.println("Mời bạn nhập số tiền muốn nạp : ");
                price = new Scanner(System.in).nextDouble();
                if (price < 0) {
                    System.out.println("số tiền nạp vào bắt buộc phải > 0 , vui lòng nhập lại ");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên . Vui lòng nhập lại.");
            }
        }

        updateBalance(user, price, TransactionType.DEPOSIT);
    }

    private void updateBalance(User user, double price, TransactionType transactionType) {
        String message = switch (transactionType) {
            case DEPOSIT -> "Nạp tiền vào tài khoản";

            case PUNISH -> "Tiền phạt mượn sách";

            case WITHDRAW -> "Rút tiền khỏi tài khoản";
        };
        user.setBalance(user.getBalance() + price);
        userService.saveUserData();
        Ticket ticket = new Ticket(user, price, )
        userService.printHeader();
        userService.showUserDetail(user);
    }


    public void orderedTicket(User user) {
        showTimeService.showShowTimeAvailableDetail();
        try {
            System.out.println("Mời bạn lựa chọn ID của suất chiếu: ");
            int idShowTime = new Scanner(System.in).nextInt();
            showTimeService.findShowTimeAvailableById(idShowTime);
        } catch (InputMismatchException e) {
            System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên . Vui lòng nhập lại.");
        }
        showSeatsAvailable();
        while (true) {
            System.out.println("Mời bạn nhập số lượng vé cần mua: ");
            int ticketNumber = new Scanner(System.in).nextInt();
            if (ticketNumber > getAvailableSeatsCount()) {
                System.out.println("Số lượng vé bạn mua không được vượt quá số ghế trống còn trong rạp, xin vui lòng thử lại hoặc chọn suất chiếu khác");
                System.out.println("Số ghế còn lại: " + getAvailableSeatsCount());
                break;
            } else {
                for (int i = 0; i < ticketNumber; i++) {
                    System.out.println("Mời bạn nhập thông tin của vé" + (i + 1));
                    System.out.println("Mời bạn chọn hàng ghế: ");
                    String rowName = new Scanner(System.in).nextLine();
                    System.out.println("Mời bạn chọn ghế: ");
                    int seatNumber = new Scanner(System.in).nextInt();
                    bookSeat(rowName, seatNumber);
                    //Đoạn này thêm combo nếu đủ time
                }
            }
        }


    }
    public void bookSeat(String row, int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getRow().equals(row) && seat.getSeatNumber() == seatNumber) {
                if (seat.getStatus() == Status.ACTIVE) {
                    seat.setStatus(Status.INACTIVE);
                    System.out.println("Đã đặt ghế: " + seat);
                } else {
                    System.out.println("Ghế này đã được đặt rồi.");
                }
                return;
            }
        }
        System.out.println("Ghế không tồn tại.");
    }

    public void showSeatsAvailable() {
        printHeader();
        List<Seat> seats1 = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.getStatus(Status.ACTIVE) == Status.ACTIVE) {
                seats1.add(seat);
                printHeader();
                showSeatDetail(seat);
            }
            break;
        }
    }

    private void showSeatDetail(Seat seat) {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n", seat.getRow(), seat.getSeatNumber());
    }

    public void printHeader() {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n", "Hàng ghế", "Số ghế");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public int getAvailableSeatsCount() {
        int count = 0;
        for (Seat seat : seats) {
            if (seat.getStatus(Status.ACTIVE) == Status.ACTIVE) {
                count++;
            }
        }
        return count;
    }

}
