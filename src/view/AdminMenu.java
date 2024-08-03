package view;

import entity.Movie;
import main.Main;
import service.TheaterService;
import service.MovieCategoryService;
import service.MovieService;
import service.UserService;
import util.InputUtil;

import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final UserService userService;
    private final MovieService movieService;
    private final MovieCategoryService movieCategoryService;
    private final TheaterService theaterService;

    public AdminMenu(UserService userService, MovieService movieService, MovieCategoryService movieCategoryService, TheaterService cinemaService) {
        this.userService = userService;
        this.movieService = movieService;
        this.movieCategoryService = movieCategoryService;
        this.theaterService = cinemaService;
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
                    cinemaMenu();
                    break;
                case 4:
                    showtimeMenu();
                    break;
                case 5:
                    ticketPriceMenu();
                    break;
                case 6:
                    couponAndVoucherMenu();
                    break;
                case 7:
                    revenueStatisticsMenu();
                    break;
                case 8:
                    return;
            }
        }
    }

    private void showtimeMenu() {

    }

    private void ticketPriceMenu() {

    }

    private void couponAndVoucherMenu() {

    }

    private void revenueStatisticsMenu() {

    }

    private void cinemaMenu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("------------------ QUẢN LÝ PHÒNG CHIẾU PHIM ------------------");
            System.out.println("1. Thêm phòng chiếu mới");
            System.out.println("2. Câp nhật thông tin phòng chiếu");
            System.out.println("3. Cập nhật thông tin ghế ngồi trong phòng chiếu");
            System.out.println("4. In thông tin phòng chiếu");
            System.out.println("5. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
            switch (choice) {
                case 1:
                    theaterService.createTheater();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    return;
            }
        }
    }

    private void movieMenu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("------------------ QUẢN LÝ DANH SÁCH PHIM ------------------");
            System.out.println("1. Tìm kiếm phim theo tên");
            System.out.println("2. Quản lý danh mục phim");
            System.out.println("3. Thêm mới phim");
            System.out.println("4. Cập nhật thông tin phim");
            System.out.println("5. Danh sách các phim đang chiếu");
            System.out.println("6. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 6);
            switch (choice) {
                case 1:
                    movieService.search();
                    break;
                case 2:
                    showCategoryManagementMenu();
                    break;
                case 3:
                    movieService.inputMovie();
                    break;
                case 4:
                    movieService.updateMovie();
                    break;
                case 5:
                    movieService.showingMovieList();
                    break;
                case 6:
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
                    "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1, 6);
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
                    userService.lockUserByEmail();
                    break;
                case 5:
                    userService.transactionHistory();
                    break;
                case 6:
                    return;

            }
        }
    }
    public void showCategoryManagementMenu() {
        while (true){
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("------------------ QUẢN LÝ DANH MỤC (THỂ LOẠI) PHIM ------------------");
            System.out.println("1. Thêm danh mục mới");
            System.out.println("2. Cập nhật thông tin danh mục");
            System.out.println("3. Xóa danh mục");
            System.out.println("4. Xem các thể loại phim đang có  ");
            System.out.println("5. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    " Chức năng là số dương từ 1 tới 5, vui lòng nhập lai: ",
                    1,5);
            switch (choice) {
                case 1:
                    movieCategoryService.create();
                    break;
                case 2:
                    movieCategoryService.updateCategoryById();
                    break;
                case 3:
                    System.out.println("Mời bạn nhập ID của thể loại cần xóa: ");
                    int idCategory = new Scanner(System.in).nextInt();
                    List<Movie> movies = movieService.findMoviesByCategoryId(idCategory);
                    if (movies == null || movies.isEmpty()) {
                        movieCategoryService.deleteCategoryById(idCategory);
                        break;
                    }
                    System.out.println("Không được xóa thể loại này do đã gán vào phim");
                    break;
                case 4:
                        movieCategoryService.showCategories();
                    break;
                case 5:
                    return;
            }
        }
    }
}
