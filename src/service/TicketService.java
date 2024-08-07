package service;

import constant.*;
import entity.*;
import main.Main;
import util.FileUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

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
    private static final double WEEKEND_SURCHARGE = 2.0;
    private static final double HOLIDAY_SURCHARGE = 4.0;

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
        userService.printHeader();
        userService.showUserDetail(user);
    }


    public void orderedTicket(User user) {
        Seat seat = null;
        ShowTime showTime = null;
        Theater theater;
        int idShowTime;
        String rowName;
        int seatNumber;
        showTimeService.showShowTimeAvailableDetail();
        do {
            try {
                System.out.println("Mời bạn lựa chọn ID của suất chiếu: ");
                idShowTime = new Scanner(System.in).nextInt();
                showTime = showTimeService.findShowTimeAvailableById(idShowTime);
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên. Vui lòng nhập lại.");
            }
        } while (showTime == null);
        showSeatsAvailable();
        SeatClass seatClass = null;
        while (true) {
            System.out.println("Mời bạn nhập số lượng vé cần mua: ");
            int ticketNumber = new Scanner(System.in).nextInt();
            if (ticketNumber > getAvailableSeatsCount()) {
                System.out.println("Số lượng vé bạn mua không được vượt quá số ghế trống còn trong rạp, xin vui lòng thử lại hoặc chọn suất chiếu khác");
                System.out.println("Số ghế còn lại: " + getAvailableSeatsCount());
                break;
            } else {
                List<Seat> bookedSeats = new ArrayList<>();
                for (int i = 0; i < ticketNumber; i++) {
                    do {
                        System.out.println("Mời bạn nhập thông tin của vé" + (i + 1));
                        System.out.println("Mời bạn chọn hàng ghế: ");
                        rowName = new Scanner(System.in).nextLine();
                        System.out.println("Mời bạn chọn ghế: ");
                        seatNumber = new Scanner(System.in).nextInt();
                        seat = bookSeat(rowName, seatNumber);
                        if (seat == null) {
                            System.out.println("Ghế không tồn tại hoặc đã được đặt. Vui lòng chọn ghế khác.");
                        }
                    } while (seat != null);
                    bookedSeats.add(seat);
                }
                seats.addAll(bookedSeats);
                seatService.saveSeatData();
            }

            assert false;
            seatClass = seat.getSeatClass();

//            Ticket = Giá vé + giá phim + giá ghế + giá format + ngày cuối tuần hoặc ngày lễ (nếu có) + bỏng nước
            double price = 20000;
            double ticketPrice = calculateTicketPrice(showTime.getMovie().getMovieClass(), seatClass, showTime.getFormatMovie(), showTime.getMovieTime());
            double amount = ticketPrice; //+ snack
            Ticket ticket = new Ticket(seat, showTime, price, user, LocalDateTime.now(), ticketNumber, amount);
        }


    }

//    public void orderedTicket(User user) {
//        int showTimeID = inputShowTimeID();
//        ShowTime showTime = showTimeService.findShowTimeAvailableById(showTimeID);
//        Seat seat;
//        System.out.println("Nhập số vé bạn muốn mua: ");
//        int ticketNumber = new Scanner(System.in).nextInt();
//        chooseMultiOrSingleSeat(ticketNumber);
//        for (Seat seat : seats) {
//            SeatClass seatClass = seat.getSeatClass();
//        double ticketPrice = calculateTicketPrice(showTime.getMovie().getMovieClass(), seatClass, showTime.getFormatMovie(), showTime.getMovieTime()  );
//        Ticket ticket = Ticket(seat, showTime, ticketPrice, user, LocalDateTime.now(),, );
//        tickets.add(ticket);
//        saveTicketsData();
//        }
//
//    }
//
//    private Seat chooseMultiOrSingleSeat (int ticketNumber) {
//        Seat lastBookedSeat = null;
//        List<Seat> bookedSeats = new ArrayList<>();
//
//        for (int i = 0; i < ticketNumber; i++) {
//            Seat seat;
//            do {
//                String row = inputRow();
//                int seatNumber = inputSeatNumber();
//                seat = bookSeat(row, seatNumber);
//                if (seat == null) {
//                    System.out.println("Ghế không tồn tại hoặc đã được đặt. Vui lòng chọn ghế khác.");
//                }
//            } while (seat == null);
//            bookedSeats.add(seat);
//            lastBookedSeat = seat;
//        }
//
//        // Thêm các ghế đã đặt vào danh sách chính
//        seats.addAll(bookedSeats);
//
//        return lastBookedSeat;
//    }

    private double calculateTicketPrice(MovieClass movieClass,
                                        SeatClass seatClass,
                                        FormatMovie formatMovie,
                                        LocalDateTime dateTime) {

        double basePrice = 20000;

        return basePrice + basePrice * getMovieClass(movieClass) +
                basePrice * getSeatClass(seatClass) + basePrice * getShowFormatMultiplier(formatMovie) +
                basePrice * dateSurcharge(dateTime);
    }


    private double dateSurcharge(LocalDateTime showTimeDate) {
        double dateSurcharge = 0;
        DayOfWeek dayOfWeek = showTimeDate.getDayOfWeek();
        if (isWeekend(showTimeDate) && isHolidays(showTimeDate)) {
            dateSurcharge = Math.max(WEEKEND_SURCHARGE, HOLIDAY_SURCHARGE);
        } else if (isWeekend(showTimeDate)) {
            dateSurcharge = WEEKEND_SURCHARGE;
        } else if (isHolidays(showTimeDate)) {
            dateSurcharge = HOLIDAY_SURCHARGE;
        }
        return dateSurcharge;
    }

    private int inputSeatNumber() {
        int seatNumber = 0;
        do {
            try {
                System.out.println("Mời bạn lựa chọn ghế ngồi: ");
                seatNumber = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Lựa chọn phải là 1 số nguyên, vui lòng nhập lại");
            }
        } while (!isSeatNumberValid(seatNumber));
        return seatNumber;
    }

    private boolean isSeatNumberValid(int seatNumber) {
        return true;
    }

    private int inputShowTimeID() {
        int showID;
        do {
            showTimeService.showShowTimeAvailableDetail();
            System.out.println("Mời bạn lựa chọn ID suất chiếu: ");
            showID = new Scanner(System.in).nextInt();
        } while (!isShowIdValid(showID));
        return showID;
    }

    private String inputRow() {
        String row;
        do {
            showSeatsAvailable();
            System.out.println("Mời bạn lựa chọn hàng ghế ngồi: ");
            row = new Scanner(System.in).nextLine();
        } while (!isRowValid(row));
        return row;
    }

    private boolean isRowValid(String row) {
        return true;
    }


    private boolean isShowIdValid(int showID) {
        return showTimeService.findShowTimeAvailableById(showID) != null;
    }


    public double getShowFormatMultiplier(FormatMovie format) {
        return switch (format) {
            case TWO_DIMENSION -> 1.0;
            case THREE_DIMENSION -> 1.5;
            case FOUR_DIMENSION_X -> 1.8;
            case IMAX -> 2.3;
        };
    }

    public double getSeatClass(SeatClass seatClass) {
        return switch (seatClass) {
            case STANDARD -> 1.0;
            case VIP -> 1.8;
            case SWEETBOX -> 2.5;
        };
    }

    public double getMovieClass(MovieClass movieClass) {
        return switch (movieClass) {
            case MIDTIER -> 1.0;
            case HIGHTIER -> 1.4;
            case LUXYRYTIER -> 1.8;
        };
    }


    public boolean isWeekend(LocalDateTime date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.FRIDAY || day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public boolean isHolidays(LocalDateTime date) {
        List<LocalDate> holidays = Arrays.asList(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 4, 30),
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 9, 2),
                LocalDate.of(2024, 10, 20),
                LocalDate.of(2024, 10, 31),
                LocalDate.of(2024, 11, 20),
                LocalDate.of(2024, 12, 24),
                LocalDate.of(2024, 12, 31)
        );
        for (LocalDate holiday : holidays) {
            if (date.equals(holiday)) {
                return true;
            }
        }
        return false;
    }


    public Seat bookSeat(String row, int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getRow().equals(row) && seat.getSeatNumber() == seatNumber) {
                if (seat.getStatus() == Status.ACTIVE) {
                    seat.setStatus(Status.INACTIVE);
                    System.out.println("Đã đặt ghế: " + seat);
                    return seat;
                } else {
                    System.out.println("Ghế này đã được đặt rồi.");
                    return null;
                }
            }
        }
        System.out.println("Ghế không tồn tại.");
        return null;
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
