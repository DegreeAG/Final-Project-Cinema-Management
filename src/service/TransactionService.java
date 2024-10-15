package service;

import constant.TransactionType;
import entity.*;
import util.FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TransactionService {

    private final FileUtil<Transaction> fileUtil = new FileUtil<>();
    private static final String TRANSACTION_DATA_FILE = "transactions.json";
    private List<Transaction> transactionHistories;
    private final MovieService movieService;
    private final TicketService ticketService;
    private final UserService userService;


    public TransactionService(MovieService movieService, TicketService ticketService, UserService userService) {
        this.movieService = movieService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    public void setTransactionHistories() {
        List<Transaction> transactionList = fileUtil.readDataFromFile(TRANSACTION_DATA_FILE, Transaction[].class);
        transactionHistories = transactionList != null ? transactionList : new ArrayList<>();
    }

    public void saveTransactionHistoriesData() {
        fileUtil.writeDataToFile(transactionHistories, TRANSACTION_DATA_FILE);
    }

    public void saveTransaction(Transaction transaction) {
        transactionHistories.add(transaction);
        saveTransactionHistoriesData(); // Lưu File
    }

    public ArrayList<Transaction> showTransactionHistories() {
        User user = userService.getLoggedInUser();
        ArrayList<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : transactionHistories) {
            if (transaction.getUser().getId() == user.getId()) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public void showTransaction(Transaction transaction) {
        System.out.printf("%-50s%-20s%-15s%-25s%-30s%n", transaction.getUser().getEmail(), transaction.getCreatedDate(),
                transaction.getAmount(), transaction.getTransactionType(), transaction.getTransactionContent());
    }

    public void showTransactions(List<Transaction> transactions1) {
        System.out.printf("%-50s%-20s%-15s%-25s%-30s%n", "User", "createdDate", "amount", "transactionType", "transactionContent");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        for (Transaction transaction : transactions1) {
            showTransaction(transaction);
        }
    }


    public void updateBalance(User user, double money, TransactionType transactionType) {
        String message = switch (transactionType) {
            case DEPOSIT -> "Nạp tiền vào tài khoản";

            case WITHDRAW -> "Rút tiền khỏi tài khoản";
        };
        user.setBalance(user.getBalance() + money);
        userService.saveUserData();
        Transaction transaction = new Transaction(user, LocalDate.now(), money, transactionType, message);
        transactionHistories.add(transaction);
        saveTransactionHistoriesData();
    }

    public void deposit(User user) {
        double money;
        while (true) {
            try {
                System.out.println("Mời bạn nhập số tiền muốn nạp : ");
                money = new Scanner(System.in).nextDouble();
                if (money < 0) {
                    System.out.println("số tiền nạp vào bắt buộc phải > 0 , vui lòng nhập lại ");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên . Vui lòng nhập lại.");
            }
        }
        System.out.println("Bạn đã nạp tiền thành công!!");
        System.out.println("Số dư tài khoản của bạn là: " + (user.getBalance() + money) + "VND");
        updateBalance(user, money, TransactionType.DEPOSIT);
    }

    public void withDraw() {
        User user;
        int idUser;
        while (true) {
            try {
                System.out.println("Mời bạn nhập ID của User muốn rút tiền ");
                idUser = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                continue;
            }
            user = userService.findUserById(idUser);
            if (user == null) {
                System.out.print("Thông tin không chính xác , vui lòng nhập lại : ");
                continue;
            }
            break;
        }

        if (user.getBalance() <= 50000) {
            System.out.println("Số dư trong tài khoản không đủ 50k, muốn rút tiền thì số dư phải lớn hơn 50k!");
            return;
        }
        System.out.println("Số dư tài khoản hiện tại của khách hàng " + user.getName() + " là " + user.getBalance());
        System.out.println("LƯU Ý: Số dư trong tài khoản sau khi rút tiền phải lớn hơn hoặc bằng 50000");
        System.out.println("Mời bạn nhập số tiền muốn rút : ");
        double a;
        double money;
        while (true) {
            try {
                money = new Scanner(System.in).nextDouble();
                if (money <= 0) {
                    System.out.println("Số tiền rút phải là số dương , vui lòng nhập lại");
                    continue;
                }
                a = user.getBalance() - money;
                if (a < 50000) {
                    System.out.println("Số dư trong tài khoản sau khi rút tiền phải lớn hơn hoặc bằng 50000 , vui lòng nhập lại");
                    continue;
                }

                break;
            } catch (InputMismatchException ex) {
                System.out.println("Yêu cầu nhập vào 1 số tự nhiên ");
            }
        }
        System.out.println("Bạn đã rút tiền thành công với số tiền là: " + money + "VND");
        System.out.println("Số tiền còn lại trong tài khoản là: " + (user.getBalance() - money)  +"VND");
        updateBalance(user, -money, TransactionType.WITHDRAW);
    }

    public void revenueStatisticsbyMovie() {
        movieService.showAllMovieList();
        Movie movie;
        System.out.println("Mời bạn nhập id bộ phim muốn thống kê doanh thu: ");
        int idMovie;
        while (true) {
            try {
                idMovie = new Scanner(System.in).nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
            }
        }
        movie = movieService.findMovieById(idMovie);
        if (movie == null) {
            System.out.println("Thông tin không chính xác , vui lòng nhập lại : ");
        }
        List<Ticket> soldTickets = ticketService.getTicketsByMovieId(idMovie); // Lấy danh sách vé đã bán theo ID bộ phim

        if (soldTickets == null || soldTickets.isEmpty()) {
            System.out.println("Không có vé nào được bán cho bộ phim này.");
            return;
        }
        double totalRevenue = 0;
        for (Ticket ticket : soldTickets) {
            double ticketPrice = ticket.getPrice();
            totalRevenue += ticketPrice;
        }
        System.out.println("Tổng doanh thu của bộ phim là: " + totalRevenue + " VND");
    }

    public void revenueStatisticsByDate() {
        System.out.println("Mời bạn nhập ngày muốn thống kê doanh thu (yyyy/MM/dd): ");
        String dateInput = new Scanner(System.in).next();
        LocalDate date;
        while (true) {
            try {
                date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng không hợp lệ, vui lòng nhập lại");
            }
        }
        List<Ticket> soldTickets = ticketService.getTicketsByDate(date);
        if (soldTickets == null || soldTickets.isEmpty()) {
            System.out.println("Không có vé nào được bán trong ngày này.");
            return;
        }
        double totalRevenue = 0;
        for (Ticket ticket : soldTickets) {
            totalRevenue += ticket.getPrice();
        }
        System.out.println("Tổng doanh thu của ngày " + dateInput + "là: " + totalRevenue + "VND" );

    }
}


