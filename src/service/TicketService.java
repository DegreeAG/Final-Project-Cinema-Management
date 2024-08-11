package service;

import constant.*;
import entity.*;
import util.FileUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class TicketService {
    private List<User> users = new ArrayList<>();
    private List<ShowTime> showTimes = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private List<Theater> theaters = new ArrayList<>();
    private List<Seat> seats = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();
    private final FileUtil<Ticket> fileUtil = new FileUtil<>();
    private static final String TICKET_DATA_FILE = "tickets.json";
    private final UserService userService;
    private final ShowTimeService showTimeService;
    private final SeatService seatService;
    private final MovieService movieService;
    private final TheaterService theaterService;
    private final TransactionService transactionService;
    private static final double WEEKEND_SURCHARGE = 2.0;
    private static final double HOLIDAY_SURCHARGE = 4.0;
    private static int AUTO_ID;

    public TicketService(UserService userService, ShowTimeService showTimeService, SeatService seatService, MovieService movieService, TheaterService theaterService, TransactionService transactionService) {
        this.userService = userService;
        this.showTimeService = showTimeService;
        this.seatService = seatService;
        this.movieService = movieService;
        this.theaterService = theaterService;
        this.transactionService = transactionService;
    }

    public void saveTicket(Ticket ticket) {
        tickets.add(ticket);
        saveTicketsData();
    }

    private void saveTicketsData() {
        fileUtil.writeDataToFile(tickets, TICKET_DATA_FILE);
    }


//    public void orderedTicket(User user) {
//        ShowTime selectedShowTime = null;
//        movieService.showingMovieList();
//        showTimeService.showShowTimeByMovie();
//        System.out.println("Mời bạn nhập id của suất chiếu để đặt vé: ");
//        int showTimeId;
//        showTimeId = new Scanner(System.in).nextInt();
//        for (ShowTime showTime : showTimes) {
//            if (showTime.getShowtimeId() == showTimeId) {
//                selectedShowTime = showTime;
//                break;
//            }
//        }
//        if (selectedShowTime == null) {
//            System.out.println("Không tìm thấy suất chiếu với ID đã nhập. Vui lòng thử lại.");
//            return;
//        }
//        showSeatsAvailable();
//        System.out.println("Mời bạn lựa chọn ghế ngồi: ");
//        System.out.println("Mời bạn nhập hàng ghế: ");
//        String row = new Scanner(System.in).nextLine();
//        System.out.println("Mời bạn nhập số ghế ngồi: ");
//        int seatNumber = new Scanner(System.in).nextInt();
//        Seat bookedSeat = bookSeat(row, seatNumber);
//        SeatClass seatClass = bookedSeat.getSeatClass();
//        double ticketPrice = calculateTicketPrice(selectedShowTime.getMovie().getMovieClass(), seatClass,
//                selectedShowTime.getFormatMovie(), selectedShowTime.getMovieTime());
//        Ticket ticket = new Ticket(AUTO_ID++, bookedSeat, selectedShowTime, ticketPrice, user, LocalDateTime.now());
//        tickets.add(ticket);
//        showTicket(ticket);
//        saveTicketsData();
//    }


    private void showTicket(Ticket ticket) {
        printHeader();
        showTicketDetail(ticket);
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
        List<Seat> seatsAvailable = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.getStatus() == Status.ACTIVE) {
                seatsAvailable.add(seat);
                System.out.println(seatsAvailable);
            }
        }
    }

    private void showTicketDetail(Ticket ticket) {
        System.out.printf("%-5s%-30s%-15s%-20s%-25s%-20s%-30s%n", ticket.getId(), ticket.getShowTime().getMovieTime(), ticket.getShowTime().getTheater(), ticket.getSeat(), ticket.getPrice(), ticket.getUser(), ticket.getCreatedDateTime());
    }

    public void printHeader() {
        System.out.printf("%-5s%-30s%-15s%-20s%-25s%-20s%-30s%n", "ticketID", "Giờ chiếu", "Phòng chiếu", "Ghế ngồi", "Giá vé", "Người mua", "Ngày mua vé");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }


    public int getAvailableSeatsCount(Theater theater) {
        int count = 0;
        for (Seat seat : seats) {
            if (seat.getStatus(Status.ACTIVE) == Status.ACTIVE) {
                count++;
            }
        }
        return count;
    }

    public void showTicketsDetail() {
        printHeader();
        for (Ticket ticket : tickets) {
            showTicketDetail(ticket);
        }
    }

    public List<Ticket> findUserByName(User loggedInUser) {
        List<Ticket> tickets1 = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getUser().getName().toLowerCase().contains(loggedInUser.getName().toLowerCase())) {
                tickets.add(ticket);
                tickets1.addAll(tickets);
            }
        }
        return tickets1;
    }

    public void showTicketsOrder(List<Ticket> tickets) {
        printHeader();
        for (Ticket ticket : tickets) {
            showTicketDetail(ticket);
        }
    }


    public void orderedTicket(User user) {
        movieService.showingMovieList();
        showTimeService.showShowTimeByMovie();

        System.out.println("Mời bạn nhập ID suất chiếu muốn chọn lựa: ");
        int showtimeId;
        while (true) {
            try {
                showtimeId = new Scanner(System.in).nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Định dạng không hợp lệ, vui lòng nhập lại");
            }
        }
        ShowTime showTime = showTimeService.findShowTimeAvailableById(showtimeId);
        showTimeService.showShowTime(showTime);
        System.out.println("Mời bạn nhập số lượng vé muốn mua: ");
        int ticketNumer;
        while (true) {
            try {
                ticketNumer = new Scanner(System.in).nextInt();
        if (ticketNumer > getAvailableSeatsCount(showTime.getTheater())) {
            System.out.println("Số lượng vé bạn mua không được vượt quá số ghế trống còn trong rạp, xin vui lòng thử lại hoặc chọn suất chiếu khác");
            System.out.println("Số ghế còn lại: " + getAvailableSeatsCount(showTime.getTheater()));
            return;
        }
        break;
            } catch (InputMismatchException e) {
                System.out.println("Định dạng không hợp lệ, vui lòng nhập lại");
            }

        }
        showSeatsAvailable();
        for (int i = 0; i < ticketNumer; i++) {
            System.out.println("Mời bạn nhập thông tin cho vé thứ" + (i + 1));
            System.out.println("Mời bạn nhập hàng ghế muốn đặt:");
            String row = new Scanner(System.in).nextLine();
            System.out.println("Mời bạn nhập số ghế ngồi: ");
            int seatNumber = new Scanner(System.in).nextInt();
            Seat bookedSeat = bookSeat(row, seatNumber);
        }

    }
}

