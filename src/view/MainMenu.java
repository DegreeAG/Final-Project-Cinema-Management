package view;

import constant.UserRole;
import entity.User;
import main.Main;
import service.MovieService;
import service.UserService;
import util.InputUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu {

        private final UserService userService = new UserService();
        private final MovieService movieService = new MovieService();


        private final UserMenu userMenu = new UserMenu(userService);
        private final AdminMenu adminMenu = new AdminMenu(userService, movieService);



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

}
