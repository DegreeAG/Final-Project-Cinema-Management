package view;

import service.UserService;
import util.InputUtil;

public class UserMenu {
    private final UserService userService;

    public UserMenu(UserService userService) {
        this.userService = userService;
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
                    break;
                case 2:
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
                    return;
            }
        }
    }
}
