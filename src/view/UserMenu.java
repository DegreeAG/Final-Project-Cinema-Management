package view;

import entity.User;
import main.Main;
import service.*;
import util.InputUtil;

import java.util.Scanner;

public class UserMenu {
    private final UserService userService;
    private final MovieService movieService;
    private final VoteHistoryService voteHistoryService;
    private final TicketService ticketService;
    private final MovieCategoryService movieCategoryService;

    public UserMenu(UserService userService, MovieService movieService, VoteHistoryService voteHistoryService, TicketService ticketService, MovieCategoryService movieCategoryService) {
        this.userService = userService;
        this.movieService = movieService;
        this.voteHistoryService = voteHistoryService;
        this.ticketService = ticketService;
        this.movieCategoryService = movieCategoryService;
    }

    public void menu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("1. Quản lý thông tin tài khoản: ");
            System.out.println("2. Danh sách phim mới");
            System.out.println("3. Tìm kiếm phim");
            System.out.println("4. Đặt vé xem phim");
            System.out.println("5. Review phim");
            System.out.println("6. Đổi vé xem phim");
            System.out.println("7. Xem lịch sử đặt vé");
            System.out.println("8. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 8, vui lòng nhập lại: ", 1, 8);
            switch (choice) {
                case 1:
                    showAccountManagementMenu();
                    break;
                case 2:
                    movieService.showMoviesIfActive();
                    break;
                case 3:
                    showSearchMovieMenu();
                case 4:
                    User user = Main.LOGGED_IN_USER;
                    ticketService.orderedTicket(user);
                    break;
                case 5:
                    showMovieVotingMenu();
                    break;
                case 6:
                    changeTicketMenu();
                    break;
                case 7:
                    showTicketTransactionHistoryMenu();
                    break;
                case 8:
                    return;
            }
        }
    }

    private void showAccountManagementMenu() {
        while (true) {
            System.out.println("------------ Quản lý thông tin tài khoản ------------");
            System.out.println("1. Cập nhật thông tin tài khoản");
            System.out.println("2. Nạp tiền");
            System.out.println("3. Xem lịch sử giao dịch");
            System.out.println("4. Xem số dư tài khoản");
            System.out.println("5. Thoát");
            int featureChoice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 7, vui lòng nhập lại: ", 1, 5);
            switch (featureChoice) {
                case 1:
                    userService.updateUserInformation(Main.LOGGED_IN_USER.getId());
                    break;
                case 2:
                    User user = Main.LOGGED_IN_USER;
                    ticketService.deposit(user);
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    userService.showBalance();
                    break;
            }
        }
    }

    private void showTicketTransactionHistoryMenu() {

    }

    private void changeTicketMenu() {
    }

    private void transactionTicketMenu() {
    }

    private void showMovieVotingMenu() {
        while (true) {
            System.out.println("------------ Quản lý lượt đánh giá phim ------------");
            System.out.println("1. Thực hiện đánh giá phim"); // vote
            System.out.println("2. Xem lượt đánh giá phim theo ID phim ");
            System.out.println("3. Xem lượt đánh giá phim theo tên phim ");
            System.out.println("4. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 4, vui lòng nhập lại: ", 1, 4);
            switch (choice) {
                case 1:
                    voteHistoryService.inputVote();
                    break;
                case 2:
                    voteHistoryService.findHistoryVoteByMovieId();
                    break;
                case 3:
                    voteHistoryService.findHistoryVoteByMovieName();
                    break;
                case 4:
                    return;
            }
        }
    }

    private void showSearchMovieMenu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("------------------ TÌM KIẾM PHIM ------------------");
            System.out.println("1. Tìm kiếm phim theo tên: ");
            System.out.println("2. Tìm kiếm phim theo tên thể loại: ");
            System.out.println("3. Tìm kiếm phim theo lượt vote từ 4-5 sao: ");
            System.out.println("4 Thoát: ");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 4, vui lòng nhập lại: ", 1, 4);
            switch (choice) {
                case 1:
                    movieService.search();
                    break;
                case 2:
                    movieCategoryService.showCategories();
                    System.out.println("Mời bạn nhập id của thể loại phim mà bạn muốn tìm: ");
                    int idCategory = new Scanner(System.in).nextInt();
                    movieService.findMoviesByCategoryId(idCategory);
                    break;
                case 3:
                    movieService.findMoviesByVotedStar();
                    break;
                case 4:
                    return;
            }
        }
    }
}
