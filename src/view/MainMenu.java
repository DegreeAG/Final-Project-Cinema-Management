package view;

import constant.UserRole;
import entity.User;
import main.Main;
import service.*;
import util.InputUtil;

import java.util.ArrayList;
import java.util.List;

public class MainMenu {

    private final UserService userService = new UserService();
    private final MovieCategoryService movieCategoryService =new MovieCategoryService();
    private final MovieService movieService = new MovieService(movieCategoryService);
    private final List<User> users = new ArrayList<>();
    private final TheaterService theaterService = new TheaterService();
    private final VoteHistoryService voteHistoryService = new VoteHistoryService(userService, movieService);
    private final ShowTimeService showTimeService= new ShowTimeService(movieService, userService, theaterService );

    private final UserMenu userMenu = new UserMenu(userService, movieService, voteHistoryService);
    private final AdminMenu adminMenu = new AdminMenu(userService, movieService, movieCategoryService, theaterService, showTimeService );


    public void menu() {


        while (true) {
            System.out.println("==================================================================");
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng ký");
            System.out.println("3. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 3, vui lòng nhập lại: ", 1, 3);
            switch (choice) {
                case 1:
                    User loggedInUser = userService.login();
                    if (loggedInUser == null) {
                        System.out.println("Đăng nhập thất bại!!!");
                        break;
                    }
                    Main.LOGGED_IN_USER = loggedInUser;
                    if (loggedInUser.getRole().equals(UserRole.USER)) {
                        userMenu.menu();
                        break;
                    }
                    adminMenu.menu();
                    break;
                case 2:
                    User registeredUser = userService.register();
                    if (registeredUser == null) {
                        System.out.println("Dừng đăng ký tài khoản!!!");
                        break;
                    }
                    System.out.println("Đăng ký tài khoản thành công, vui lòng đăng nhập để tiếp tục sử dụng phần mềm.");
                    break;
                case 3:
                    return;
            }
        }
    }

    public void initializeData() {
        userService.setUsers();
        userService.createDefaultAdminUser(); // haàm này sẽ tự động tạo admin user nếu chua có, neu co rồi thì không tạo nữa

        userService.findCurrentAutoId();
        // ví du
        movieService.setMovies();
        movieService.findCurrentAutoId();

        voteHistoryService.setVoteHistories();

//        transactionService.setTransactionHistories();

//        movieCategoryService.setBookCategories();
//        movieCategoryService.findCurrentAutoId();
//
//        bookBorrowService.setBookBorrows();
//        bookBorrowService.findCurrentAutoId();

    }


}
