package service;

import constant.*;
import entity.*;
import main.Main;
import util.FileUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TicketService {

    private List<Ticket> tickets = new ArrayList<>();
    private List<Movie> movies;

    private final FileUtil<Ticket> fileUtil = new FileUtil<>();
    private static final String TICKET_DATA_FILE = "tickets.json";
    private final UserService userService;
    private final ShowTimeService showTimeService;
    private final SeatService seatService;
    private final MovieService movieService;
    private final TheaterService theaterService;
    private static final double WEEKEND_SURCHARGE = 2.0;
    private static final double HOLIDAY_SURCHARGE = 4.0;
    private static int AUTO_ID;

    public TicketService(UserService userService, ShowTimeService showTimeService, SeatService seatService, MovieService movieService, TheaterService theaterService) {
        this.userService = userService;
        this.showTimeService = showTimeService;
        this.seatService = seatService;
        this.movieService = movieService;
        this.theaterService = theaterService;
    }

    public void saveTicket(Ticket ticket) {
        tickets.add(ticket);
        saveTicketsData();
    }

    private void saveTicketsData() {
        fileUtil.writeDataToFile(tickets, TICKET_DATA_FILE);
    }


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


    public Seat bookSeat(Theater theater, String row, int seatNumber) {
        for (Seat seat : theater.getSeats()) {
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


    private void showTicketDetail(Ticket ticket) {
        System.out.printf("%-15s%-20s%-15s%-10s%-10s%-25s%-20s%-30s%n", ticket.getId(), ticket.getShowTime().getMovieTime(), ticket.getShowTime().getTheater().getTheaterName(), ticket.getSeat().getRow(), ticket.getSeat().getSeatNumber(), ticket.getPrice(), ticket.getUser().getName(), ticket.getCreatedDateTime());
    }

    public void printHeader() {
        System.out.printf("%-15s%-20s%-15s%-10s%-10s%-25s%-20s%-30s%n", "ticketID", "Giờ chiếu", "Phòng chiếu", "Hàng ghế", "Ghế ngồi", "Giá vé", "Người mua", "Ngày mua vé");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }


    public int getAvailableSeatsCount(Theater theater) {
        int count = 0;
        for (Seat seat : theater.getSeats()) { // Lấy danh sách ghế từ theater
            if (seat.getStatus() == Status.ACTIVE) { // Kiểm tra trạng thái ghế
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

    public List<Ticket> findByUserId(int userId) { // tìm tất cả các lượt mua vé  từ trước tới giờ của user theo id
        List<Ticket> orderedTicket = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getUser().getId() == userId) {
                orderedTicket.add(ticket);
            }
        }
        return orderedTicket;
    }

    public void showTicketsOrder(List<Ticket> tickets) {
        printHeader();
        for (Ticket ticket : tickets) {
            showTicketDetail(ticket);
        }
    }

    public void showTicketByMovie(Movie movie) {
        for (Ticket ticket : tickets) {
            if (ticket.getShowTime().getMovie() == movie) {
                printHeader();
                showTicketDetail(ticket);
            }
        }
    }


    public void orderedTicket(User user) {
        movieService.showingMovieList();
        showTimeService.showShowTimeByMovie();
        int userId = user.getId();
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
        ShowTime selectedShowTime = showTimeService.findShowTimeAvailableById(showtimeId);
        showTimeService.showShowTime(selectedShowTime);
        System.out.println("Mời bạn nhập số lượng vé muốn mua: ");
        int ticketNumer;
        while (true) {
            try {
                ticketNumer = new Scanner(System.in).nextInt();
                if (ticketNumer > getAvailableSeatsCount(selectedShowTime.getTheater())) {
                    System.out.println("Số lượng vé bạn mua không được vượt quá số ghế trống còn trong rạp, xin vui lòng thử lại hoặc chọn suất chiếu khác");
                    System.out.println("Số ghế còn lại: " + getAvailableSeatsCount(selectedShowTime.getTheater()));
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Định dạng không hợp lệ, vui lòng nhập lại");
            }

        }
        double userBalance = user.getBalance();
        Seat bookedSeat = null;
        SeatClass seatClass = null;
        Theater theater = selectedShowTime.getTheater();
        for (int i = 0; i < ticketNumer; i++) {
            System.out.println("Mời bạn nhập thông tin cho vé thứ" + (i + 1));
            System.out.println("Mời bạn nhập hàng ghế muốn đặt:");
            String row = new Scanner(System.in).nextLine();
            System.out.println("Mời bạn nhập số ghế ngồi: ");
            int seatNumber = new Scanner(System.in).nextInt();
            bookedSeat = bookSeat(theater, row, seatNumber);
            if (bookedSeat == null) {
                System.out.println("Ghế không khả dụng");
                return;
            }
            seatClass = bookedSeat.getSeatClass();

            double ticketPrice = calculateTicketPrice(selectedShowTime.getMovie().getMovieClass(), seatClass,
                    selectedShowTime.getFormatMovie(), selectedShowTime.getMovieTime());

            Ticket ticket = new Ticket(AUTO_ID++, bookedSeat, selectedShowTime, ticketPrice, Main.LOGGED_IN_USER, LocalDateTime.now());
            printHeader();
            showTicketDetail(ticket);
            saveTicket(ticket);
            if (ticket.getPrice() > userBalance) {
                System.out.println("Số dư tài khoản không đủ đê đặt vé.");
                break;
            }
            userBalance = user.getBalance() - ticket.getPrice();
            userService.updateUserBalance(userId, -ticketPrice);
        }
    }


    public List<Ticket> getTicketsByMovieId(int idMovie) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getShowTime().getMovie().getId() == idMovie) {
                result.add(ticket);
            }
        }
        return result;
    }

    public List<Ticket> getTicketsByDate(LocalDate date) {
        List<Ticket> result = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getCreatedDateTime().toLocalDate().equals(date)) {
                result.add(ticket);
            }
        }
        return result;
    }


    public void findNearestBuyTicketByUserId() {// tìm kiếm lượt mua gần nhất
        List<Ticket> tickets = findByUserId(Main.LOGGED_IN_USER.getId());
        long minDifference = Long.MAX_VALUE;
        Ticket nearestOrder = null;
        for (Ticket ticket : tickets) {
            if (ticket.getCreatedDateTime() == null) {
                continue;
            }
            long difference = ChronoUnit.DAYS.between(ticket.getCreatedDateTime(), LocalDate.now());
            if (difference >= 0 && difference < minDifference) {
                minDifference = difference;
                nearestOrder = ticket;
            }
        }
        showTicketOrderedNearest(nearestOrder);
    }

    private void showTicketOrderedNearest(Ticket nearestOrder) {
        showTicketsDetail();
    }

    public void setTicketHistories() {
        List<Ticket> ticketList = fileUtil.readDataFromFile(TICKET_DATA_FILE, Ticket[].class);
        tickets = ticketList != null ? ticketList : new ArrayList<>();
    }


}




