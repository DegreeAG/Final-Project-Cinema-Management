package view;

import service.MovieService;
import service.UserService;
import service.VoteHistoryService;
import util.InputUtil;

import java.util.Scanner;

public class UserMenu {
    private final UserService userService;
    private final MovieService movieService;
    private final VoteHistoryService voteHistoryService;

    public UserMenu(UserService userService, MovieService movieService, VoteHistoryService voteHistoryService) {
        this.userService = userService;
        this.movieService = movieService;
        this.voteHistoryService = voteHistoryService;
    }

    public void menu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("1. Danh sách phim mới");
            System.out.println("2. Tìm kiếm phim");
            System.out.println("3. Đặt vé xem phim");
            System.out.println("4. Review phim");
            System.out.println("5. Đổi vé xem phim");
            System.out.println("6. Xem lịch sử đặt vé");
            System.out.println("7. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 7, vui lòng nhập lại: ", 1, 7);
            switch (choice) {
                case 1:
                    movieService.showMoviesIfActive();
                    break;
                case 2:
                    showSearchMovieMenu();
                    break;
                case 3:
                    transactionTicketMenu();
                    break;
                case 4:
                    showBookVotingMenu();
                    break;
                case 5:
                    changeTicketMenu();
                    break;
                case 6:
                    showTicketTransactionHistoryMenu();
                    break;
                case 7:
                    return;
            }
        }
    }

    private void showTicketTransactionHistoryMenu() {
    }

    private void changeTicketMenu() {
    }

    private void transactionTicketMenu() {
    }

    private void showBookVotingMenu() {
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
            System.out.println("1. Tìm kiếm phim theo ID: ");
            System.out.println("2. Tìm kiếm phim theo tên: ");
            System.out.println("3. Tìm kiếm phim theo tên thể loại: ");
            System.out.println("4. Tìm kiếm phim theo lượt vote từ 4-5 sao: ");
            System.out.println("5 Thoát: ");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
            switch (choice) {
                case 1:
                    System.out.println("Mời bạn nhập ID của phim: ");
                    int movieID = new Scanner(System.in).nextInt();
                    movieService.findMovieById(movieID);
                    break;
                case 2:
                    movieService.search();
                    break;
                case 3:
                    movieService.findMoviesByCategoryName();
                    break;
                case 4:
                    movieService.findMoviesByVotedStar();
                    break;
                case 5:
                    return;
            }
        }
    }
}
