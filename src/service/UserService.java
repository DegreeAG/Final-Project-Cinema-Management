package service;

import constant.Regex;
import constant.Status;
import constant.UserRole;
import entity.User;
import main.Main;
import util.FileUtil;
import util.InputUtil;

import java.util.*;


public class UserService {

    private List<User> users;
    private List<String> lockedUser = new ArrayList<>();
    private static final HashSet<String> lockUserByEmails = new HashSet<>();
    private static final String USER_DATA_FILE = "users.json";
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "admin";
    private final FileUtil<User> fileUtil = new FileUtil<>();
    private static int AUTO_ID;


    private static final int MAX_LOGIN_TIMES = 5;

    public User login() {
        int loginCount = 1;
        User user = null;
        do {
            System.out.println("Nhập email (nhập 'exit' để thoát): ");
            String email = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(email)) {
                return null;
            }
            if (!email.matches(Regex.EMAIL_REGEX)) {
                System.out.println("Email không hợp lệ, vui lòng nhập lại đúng định dạng mail: ");
                continue;
            }
            System.out.println("Nhập mật khẩu (nhập 'exit' để thoát): ");
            String password = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(password)) {
                return null;
            }
            if (!password.matches(Regex.PASSWORD_REGEX)) {
                System.out.println("Password không hợp lệ, vui lòng nhập lại đúng định dạng password: ");
            }

            user = findUserByEmailAndPassword(email, password);
            if (user != null) {
                if (user.getStatus() == Status.INACTIVE) {
                    System.out.println("Tài khoản của quý khách hàng đã bị khóa.");
                    user = null;
                } else {
                    break;
                }
            }
            loginCount++;
            if (loginCount == MAX_LOGIN_TIMES) {
                System.out.println("Bạn đã vượt quá số lần đăng nhập tối đa (5 lần), vui lòng thử lại sau");
                break;
            }
            System.out.println("Thông tin đăng nhập không chính xác, vui lòng thử lại: ");
        } while (true);
        return user;
    }

    public User findUserById(int idUser) {
        for (User user : users) {
            if (user.getId() == idUser) {
                return user;
            }
        }
        return null;
    }

    public User register() {
        String email;
        String password;
        String phone;
        String name;
        String address;
        while (true) {
            System.out.println("Nhập email (nhập 'exit' để thoát): ");
            email = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(email)) {
                return null;
            }
            if (!email.matches(Regex.EMAIL_REGEX)) {
                System.out.println("Email không hợp lệ, vui lòng nhập lại đúng định dạng mail: ");
                continue;
            }
            User existedUser = findUserByEmail(email);
            if (existedUser != null) {
                System.out.println("Email đã tồn tại trong hệ thống, vui lòng nhập lại: ");
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("Nhập mật khẩu (nhập 'exit' để thoát): ");
            password = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(password)) {
                return null;
            }
            if (!password.matches(Regex.PASSWORD_REGEX)) {
                System.out.println("Password không đúng định dạng vui lòng nhập lại ");
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("Mời bạn nhập SĐT (đầu 0 và có 9 so tiep theo): ");
            phone = new Scanner(System.in).nextLine();
            if (!phone.matches(Regex.VN_PHONE_REGEX)) {
                System.out.println("Số điện thoại không đúng định dạng , vui lòng nhập lại ");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Mời bạn nhập tên: ");
            name = new Scanner(System.in).nextLine();
            if (!name.matches(".*\\d.*") && !name.isEmpty()) {
                break;
            } else {
                System.out.println("Tên không hợp lệ. Vui lòng nhập lại.");
            }
        }
        System.out.println("Mời bạn nhập địa chỉ : ");
        address = new Scanner(System.in).nextLine();
        double balance = 0;
        User user = new User(AUTO_ID++, email, password, phone, UserRole.USER, address, balance, name, Status.ACTIVE);
        users.add(user);
        saveUserData();
        return user;
    }

    private User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    private User findUserByEmailAndPassword(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public User createUserCommonInfo() {
        // TODO - nhập các thông tin cần tạo cho 1 user
        //  (chú ý, cần chọn quyền của user vì đây là admin tạo user nên admin hoan toàn có thể chọn user họ tạo
        //  là một admin hay là 1 user bình thường)
        String email;
        String password;
        String phone;
        String name;
        String address;
        while (true) {
            System.out.println("Nhập email (nhập 'exit' để thoát): ");
            email = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(email)) {
                return null;
            }
            if (!email.matches(Regex.EMAIL_REGEX)) {
                System.out.println("Email không hợp lệ, vui lòng nhập lại đúng định dạng mail: ");
                continue;
            }
            User existedUser = findUserByEmail(email);
            if (existedUser != null) {
                System.out.println("Email đã tồn tại trong hệ thống, vui lòng nhập lại: ");
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("Nhập mật khẩu (nhập 'exit' để thoát): ");
            password = new Scanner(System.in).nextLine();
            if (InputUtil.exitInput(password)) {
                return null;
            }
            if (!password.matches(Regex.PASSWORD_REGEX)) {
                System.out.println("Password không đúng định dạng vui lòng nhập lại ");
                continue;
            }
            break;
        }
        while (true) {
            System.out.println("Mời bạn nhập SĐT (đầu 0 và có 9 so tiep theo): ");
            phone = new Scanner(System.in).nextLine();
            if (!phone.matches(Regex.VN_PHONE_REGEX)) {
                System.out.println("Số điện thoại không đúng định dạng , vui lòng nhập lại ");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Mời bạn nhập tên: ");
            name = new Scanner(System.in).nextLine();
            if (!name.matches(".*\\d.*") && !name.isEmpty()) {
                break;
            } else {
                System.out.println("Tên không hợp lệ. Vui lòng nhập lại.");
            }
        }
        UserRole userRole = null;
        System.out.println("Mời bạn lựa chọn chức năng của người dùng: ");
        System.out.println("1. Admin");
        System.out.println("2. Khách hàng");
        int choice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                "Chức năng là số dương từ 1 tới 2, vui lòng nhập lại: ", 1, 2);
        userRole = switch (choice) {
            case 1 -> UserRole.ADMIN;
            case 2 -> UserRole.USER;
            default -> userRole;
        };
        System.out.println("Mời bạn nhập địa chỉ : ");
        address = new Scanner(System.in).nextLine();
        double balance = 0;
        User user = new User(AUTO_ID++, email, password, userRole, phone, address, balance, name, Status.ACTIVE);
        users.add(user);
        saveUserData();
        return user;
    }


    public void findUserByMail() {
        System.out.println("Mời bạn nhập email của User : ");
        String email = new Scanner(System.in).nextLine();
        for (User user : users) {
            if (user.getEmail() != null && user.getEmail().toLowerCase().contains(email.toLowerCase())) {
                System.out.println(user);
            }
        }
//                showUser(user);
    }


    public void showUsers(List<User> users1) {
        printHeader();
        for (User user : users1) {
            showUserDetail(user);
        }
    }

    public void printHeader() {
        System.out.printf("%-5s%-30s%-30s%-20s%-20s%-10s%-10s%-10s%n", "Id", "Name", "Email", "Phone", "Address", "Role", "Balance","Status");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }

    public void showUserDetail(User user) {
        System.out.printf("%-5s%-30s%-30s%-20s%-20s%-10s%-10s%-10s%n", user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getRole(), user.getBalance(),user.getStatus());
    }

    public void updateUserInformation(int idUserUpdate) {
        User user = findUserById(idUserUpdate);
        System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa: ");
        System.out.println("1. Email");
        System.out.println("2. Password");
        System.out.println("3. Tên");
        System.out.println("4. Số điện thoại");
        System.out.println("5. Địa chỉ");
        System.out.println("6. Thoát");
        int featureChoice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1, 6);
        switch (featureChoice) {
            case 1:
                String newEmail;
                while (true) {
                    System.out.println("Mời bạn nhập email mới: ");
                    newEmail = new Scanner(System.in).nextLine();
                    if (!newEmail.matches(Regex.EMAIL_REGEX)) {
                        System.out.println("Email không đúng định dạng vui lòng nhập lại");
                        continue;
                    }
                    boolean coTrungEmailKhong = false;
                    for (User user1 : users) {
                        if (newEmail.equalsIgnoreCase(user1.getEmail()) && user1.getId() != user.getId()) {
                            System.out.println("Email đã tồn tại vui lòng nhập lại");
                            coTrungEmailKhong = true;
                            break;
                        }
                    }
                    if (coTrungEmailKhong == false) {
                        break;
                    }
                }
                user.setEmail(newEmail);
                break;
            case 2:
                String newPassword;
                while (true) {
                    System.out.println("Mới bạn nhập password (8 -> 16 ký tự cả chữ thường, chữ hoa và cả số)");
                    newPassword = new Scanner(System.in).nextLine();
                    if (!newPassword.matches(Regex.PASSWORD_REGEX)) {
                        System.out.println("Password không đúng định dạng vui lòng nhập lại ");
                        continue;
                    }
                    break;
                }
                user.setPassword(newPassword);
                break;
            case 3:
                System.out.println("Mời bạn nhập tên mới: ");
                String newName = new Scanner(System.in).nextLine();
                user.setName(newName);
                break;
            case 4:
                String newPhone;
                while (true) {
                    System.out.println("Mời bạn nhập SĐT (đầu 0 và có 9 so tiep theo): ");
                    newPhone = new Scanner(System.in).nextLine();
                    if (!newPhone.matches(Regex.VN_PHONE_REGEX)) {
                        System.out.println("Số điện thoại không đúng định dạng , vui lòng nhập lại ");
                        continue;
                    }
                    break;
                }
                user.setPhone(newPhone);
                break;
            case 5:
                System.out.println("Mời bạn nhập địa chỉ mới : ");
                String newAddress = new Scanner(System.in).nextLine();
                user.setAddress(newAddress);
                break;
            case 6:
                return;
        }
        showUser(user);
        saveUserData();// FILE - khi có thay đổi về list user, can luu vao file
    }

    public void showUser(User user) {
        printHeader();
        showUserDetail(user);
    }

    public void saveUserData() {
        fileUtil.writeDataToFile(users, USER_DATA_FILE);
    }

    public void setUsers() {

        List<User> userList = fileUtil.readDataFromFile(USER_DATA_FILE, User[].class);
        users = userList != null ? userList : new ArrayList<>();
    }

    public void createDefaultAdminUser() {
        if (users == null || users.isEmpty()) {
            createAdmin();
            return;
        }
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(ADMIN_EMAIL)
                    && user.getPassword().equalsIgnoreCase(ADMIN_PASSWORD)) {
                return;
            }
        }
        createAdmin();
    }

    private void createAdmin() {
        User user = new User(ADMIN_EMAIL, ADMIN_PASSWORD, UserRole.ADMIN);
        user.setId(0);
        users.add(user);
        saveUserData();
    }

    public void findCurrentAutoId() {
        int maxId = -1;
        for (User user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        AUTO_ID = maxId + 1;
    }

    public User getLoggedInUser() {
        for (User userTemp : users) {
            if (userTemp.getId() == Main.LOGGED_IN_USER.getId()) {
                return userTemp;
            }
        }
        return null;
    }

//    public void updateUserInformationByAdmin() {
//        System.out.println("Mời bạn nhập email tài khoản cần chỉnh sửa thông tin: ");
//        String email = new Scanner(System.in).nextLine();
//        User user = findUserByEmail(email);
//        System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa: ");
//        System.out.println("1. Email");
//        System.out.println("2. Password");
//        System.out.println("3. Tên");
//        System.out.println("4. Số điện thoại");
//        System.out.println("5. Địa chỉ");
//        System.out.println("6. Thoát");
//        int featureChoice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
//                "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1,6);
//        switch (featureChoice) {
//            case 1:
//                String newEmail;
//                while (true) {
//                    System.out.println("Mời bạn nhập email mới: ");
//                    newEmail = new Scanner(System.in).nextLine();
//                    if (!newEmail.matches(Regex.EMAIL_REGEX)) {
//                        System.out.println("Email không đúng định dạng vui lòng nhập lại");
//                        continue;
//                    }
//                    boolean coTrungEmailKhong = false;
//                    for (User user1 : users) {
//                        if (newEmail.equalsIgnoreCase(user1.getEmail()) && user1.getId() != user.getId()) {
//                            System.out.println("Email đã tồn tại vui lòng nhập lại");
//                            coTrungEmailKhong = true;
//                            break;
//                        }
//                    }
//                    if (coTrungEmailKhong == false) {
//                        break;
//                    }
//                }
//                user.setEmail(newEmail);
//                break;
//            case 2:
//                String newPassword;
//                while (true) {
//                    System.out.println("Mới bạn nhập password (8 -> 16 ký tự cả chữ thường, chữ hoa và cả số)");
//                    newPassword = new Scanner(System.in).nextLine();
//                    if (!newPassword.matches(Regex.PASSWORD_REGEX)) {
//                        System.out.println("Password không đúng định dạng vui lòng nhập lại ");
//                        continue;
//                    }
//                    break;
//                }
//                user.setPassword(newPassword);
//                break;
//            case 3:
//                System.out.println("Mời bạn nhập tên mới: ");
//                String newName = new Scanner(System.in).nextLine();
//                user.setName(newName);
//                break;
//            case 4:
//                String newPhone;
//                while (true) {
//                    System.out.println("Mời bạn nhập SĐT (đầu 0 và có 9 so tiep theo): ");
//                    newPhone = new Scanner(System.in).nextLine();
//                    if (!newPhone.matches(Regex.VN_PHONE_REGEX)) {
//                        System.out.println("Số điện thoại không đúng định dạng , vui lòng nhập lại ");
//                        continue;
//                    }
//                    break;
//                }
//                user.setPhone(newPhone);
//                break;
//            case 5:
//                System.out.println("Mời bạn nhập địa chỉ mới : ");
//                String newAddress = new Scanner(System.in).nextLine();
//                user.setAddress(newAddress);
//                break;
//            case 6:
//                return;
//        }
//        showUser(user);
//        saveUserData();
//
//    }

    public void showBalance() {
        User user = getLoggedInUser();
        System.out.println("Số dư tài khoản của khách hàng là " + user.getBalance());
    }

    public void transactionHistory() {
    }

    public void lockedUserById(int idUserLock) {

        for (User user:users) {
            if (user.getId() == idUserLock) {
                user.setStatus(Status.INACTIVE);
                System.out.println("User có ID trên đã được khóa");
                printHeader();
                showUserDetail(user);
                saveUserData();
                break;
            }
        }
    }

    public void unlockedUserById(int idUserLock) {
        for (User user:users) {
            if (user.getId() == idUserLock) {
                user.setStatus(Status.ACTIVE);
                System.out.println("User có ID trên đã được mở khóa");
                saveUserData();
                printHeader();
                showUserDetail(user);
                break;
            }
        }
    }

}


            



