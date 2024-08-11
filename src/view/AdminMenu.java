package view;

import entity.Movie;
import entity.Theater;
import entity.Ticket;
import entity.User;
import main.Main;
import service.*;
import util.InputUtil;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private final UserService userService;
    private final MovieService movieService;
    private final MovieCategoryService movieCategoryService;
    private final TheaterService theaterService;
    private final ShowTimeService showTimeService;
    private final TicketService ticketService;

    public AdminMenu(UserService userService, MovieService movieService, MovieCategoryService movieCategoryService, TheaterService cinemaService, ShowTimeService showTimeService, TicketService ticketService) {
        this.userService = userService;
        this.movieService = movieService;
        this.movieCategoryService = movieCategoryService;
        this.theaterService = cinemaService;
        this.showTimeService = showTimeService;
        this.ticketService = ticketService;
    }

    public void menu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("1. Quản lý danh sách người dùng");
            System.out.println("2. Quản lý phim");
            System.out.println("3. Quản lý phòng chiếu");
            System.out.println("4. Quản lý lịch chiếu");
            System.out.println("5. In thông tin vé chiếu đã đặt");
            System.out.println("6. Thống kê doanh thu");
            System.out.println("7. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                    "Chức năng là số dương từ 1 tới 7 vui lòng nhập lại: ", 1, 7);
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
                    ticketService.showTicketsDetail();
                    break;
                case 6:
                    break;
                case 7:
                    return;
            }
        }
    }

    private void showtimeMenu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("------------------ QUẢN LÝ GIỜ CHIẾU PHIM ------------------");
            System.out.println("1. Thêm lịch chiếu mới");
            System.out.println("2. Câp nhật, sửa dổi thông tin lịch chiếu");
            System.out.println("3. In danh sách suất chiếu: ");
            System.out.println("4. Thoát: ");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                    "Chức năng là số dương từ 1 tới 4, vui lòng nhập lại: ", 1, 4);
            switch (choice) {
                case 1:
                    showTimeService.inputInfo();
                    break;
                case 2:
                    showTimeService.updateInfo();
                    break;
                case 3:
                    showTimeService.showShowTimeAvailableDetail();
                    break;
                case 4:
                    return;

            }
        }
    }



    private void cinemaMenu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("------------------ QUẢN LÝ PHÒNG CHIẾU PHIM ------------------");
            System.out.println("1. Thêm phòng chiếu mới");
            System.out.println("2. Câp nhật thông tin phòng chiếu");
            System.out.println("3. In thông tin phòng chiếu theo ID: ");
            System.out.println("4. In thông tin các phòng chiếu:");
            System.out.println("5. Thoát:");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
            switch (choice) {
                case 1:
                    theaterService.createTheater();
                    break;
                case 2:
                    theaterService.updateTheater();
                    break;
                case 3:
                    theaterService.showTheaterById();
                    break;
                case 4:
                    theaterService.showTheaters();
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
            System.out.println("2. Tìm kiếm phim theo danh mục phim");
            System.out.println("3. Quản lý danh mục phim");
            System.out.println("4. Thêm mới phim");
            System.out.println("5. Cập nhật thông tin phim");
            System.out.println("6. Danh sách các phim đang chiếu");
            System.out.println("7. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 7, vui lòng nhập lại: ", 1, 7);
            switch (choice) {
                case 1:
                    movieService.search();
                    break;
                case 2:
                    movieService.findMoviesByCategoryId();
                    break;
                case 3:
                    showCategoryManagementMenu();
                    break;
                case 4:
                    movieService.inputMovie();
                    break;
                case 5:
                    movieService.updateMovie();
                    break;
                case 6:
                    movieService.showingMovieList();
                    break;
                case 7:
                    return;
            }
        }
    }


    private void userManagementMenu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("------------------ QUẢN LÝ DANH SÁCH NGƯỜI DÙNG ------------------");
            System.out.println("1. Tìm kiếm người dùng theo email:");
            System.out.println("2. Tạo mới tài khoản người dùng");
            System.out.println("3. Cập nhật thông tin người dùng");
            System.out.println("4. Quản lý hoạt động người dùng");
            System.out.println("5. Lịch sử đặt vé của người dùng");
            System.out.println("6. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1, 6);
            switch (choice) {
                case 1:
                    userService.findUserByMail();
                    break;
                case 2:
                    userService.createUserCommonInfo();
                    break;
                case 3:
                    int idUserUpdate;
                    while (true) {
                        try {
                            System.out.println("Mời bạn nhập ID của User muốn update ");
                            idUserUpdate = new Scanner(System.in).nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
                        break;
                    }
                    userService.updateUserInformation(idUserUpdate);
                    break;
                case 4:
                    statusUserManagementMenu();

                    break;
                case 5:
                    userService.transactionHistory();
                    break;
                case 6:
                    return;

            }
        }
    }

    private void statusUserManagementMenu() {
        User user;
        int idUserLock;
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("------------------ QUẢN LÝ TRẠNG THÁI NGƯỜI DÙNG ------------------");
            System.out.println("1. Khóa trạng thái hoạt động của người dùng");
            System.out.println("2. Mở khóa trạng thái hoạt động của người dùng");
            System.out.println("3. Thoát");
            int functionChoice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    " Chức năng là số dương từ 1 tới 3, vui lòng nhập lai: ",
                    1, 3);
            switch (functionChoice) {
                case 1:
                    while (true) {
                        try {
                            System.out.println("Mời bạn nhập ID của User muốn khóa ");
                            idUserLock = new Scanner(System.in).nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
                        user = userService.findUserById(idUserLock);
                        if (user == null) {
                            System.out.print("Thông tin không chính xác , vui lòng nhập lại : ");
                            continue;
                        }
                        break;
                    }
                    userService.lockedUserById(idUserLock);
                    break;
                case 2:
                    while (true) {
                        try {
                            System.out.println("Mời bạn nhập ID của User muốn khóa ");
                            idUserLock = new Scanner(System.in).nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
                        user = userService.findUserById(idUserLock);
                        if (user == null) {
                            System.out.print("Thông tin không chính xác , vui lòng nhập lại : ");
                            continue;
                        }
                        break;
                    }
                    userService.unlockedUserById(idUserLock);
                    break;
                case 3:
                    return;
            }
        }
    }

    public void showCategoryManagementMenu() {
        while (true) {
            System.out.println("------- PHẦN MỀM QUẢN LÝ VÀ MUA BÁN VÉ XEM PHIM CHIẾU RẠP --------");
            System.out.println("------------------ QUẢN LÝ DANH MỤC (THỂ LOẠI) PHIM ------------------");
            System.out.println("1. Thêm danh mục mới");
            System.out.println("2. Cập nhật thông tin danh mục");
            System.out.println("3. Xóa danh mục");
            System.out.println("4. Xem các thể loại phim đang có  ");
            System.out.println("5. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    " Chức năng là số dương từ 1 tới 5, vui lòng nhập lai: ",
                    1, 5);
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
