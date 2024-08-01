package view;

import main.Main;
import service.MovieService;
import service.UserService;
import util.InputUtil;

public class AdminMenu {
    private final UserService userService;
    private final MovieService movieService;

    public AdminMenu(UserService userService, MovieService movieService) {
        this.userService = userService;
        this.movieService = movieService;
    }

    public void menu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("1. Quản lý danh sách người dùng");
            System.out.println("2. Quản lý phim");
            System.out.println("3. Quản lý phòng chiếu");
            System.out.println("4. Quản lý lịch chiếu");
            System.out.println("5. Quản lý vé chiếu");
            System.out.println("6. Quản lý mã giảm giá và voucher");
            System.out.println("7. Thống kê doanh thu");
            System.out.println("8. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                    "Chức năng là số dương từ 1 tới 8, vui lòng nhập lại: ", 1, 8);
            switch (choice) {
                case 1:
                    userManagementMenu();
                    break;
                case 2:
                    movieMenu();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    return;
            }
        }
    }

    private void movieMenu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("------------------ QUẢN LÝ DANH SÁCH PHIM ------------------");
            System.out.println("1. Tìm kiếm phim theo tên");
            System.out.println("2. Thêm mới phim");
            System.out.println("3. Cập nhật thông tin phim");
            System.out.println("5. Danh sách các phim đang chiếu");
            System.out.println("6. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1, 6);
            switch (choice) {
                case 1:
                    movieService.search();
                    break;
                case 2:
                    movieService.createMovie();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    return;
            }
        }
    }

    private void userManagementMenu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("------------------ QUẢN LÝ DANH SÁCH NGƯỜI DÙNG ------------------");
            System.out.println("1. Tìm kiếm người dùng theo tên:");
            System.out.println("2. Tạo mới tài khoản người dùng");
            System.out.println("3. Cập nhật thông tin người dùng");
            System.out.println("4. Khóa hoạt động người dùng");
            System.out.println("5. Lịch sử đặt vé của người dùng");
            System.out.println("6. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 3, vui lòng nhập lại: ", 1, 3);
            switch (choice) {
                case 1:
                    userService.findUserByName();
                    break;
                case 2:
                    userService.createUserCommonInfo();
                    break;
                case 3:
                    userService.updateUserInformation(Main.LOGGED_IN_USER.getId());
                    break;
                case 4:
                    userService.lockUser();
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    return;
            }
        }
    }
}
