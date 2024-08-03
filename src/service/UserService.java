package service;

import constant.Regex;
import constant.UserRole;
import entity.User;
import util.FileUtil;
import util.InputUtil;

import java.util.*;


public class UserService {

    private List<User> users;
    private final List<String> lockedUserMail = new ArrayList<>();
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
            for (int i = 0; i < users.size(); i++) {
                ;//TODO code this


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
                break;
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
        User user = createUserCommonInfo();
        user.setRole(UserRole.USER);
        users.add(user);
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
            if (!name.matches(".*\\d.*") && !name.isEmpty()) { // Kiểm tra nếu tên không chứa ký tự số và không rỗng
                break;
            } else {
                System.out.println("Tên không hợp lệ. Vui lòng nhập lại.");
            }
        }
        System.out.println("Mời bạn nhập địa chỉ : ");
        address = new Scanner(System.in).nextLine();

        User user = new User(email, password, UserRole.USER, phone, address);
        users.add(user);
        return user;

    }


    public void findUserByName() {
        System.out.println("Mời bạn nhập tên của User : ");
        String name = new Scanner(System.in).nextLine();
        List<User> users1 = new ArrayList<>();

        for (User user : users) {
            if (user.getName() != null && user.getName().toLowerCase().contains(name.toLowerCase())) {
                users1.add(user);
            }
        }
        showUsers(users1);
    }

    public void showUsers(List<User> users1) {
        printHeader();
        for (User user : users1) {
            showUserDetail(user);
        }
    }

    public void printHeader() {
        System.out.printf("%-5s%-30s%-30s%-20s%-20s%-10s%-10s%n", "Id", "Name", "Email", "Phone", "Address", "Role", "Balance");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
    }

    public void showUserDetail(User user) {
        System.out.printf("%-5s%-30s%-30s%-20s%-20s%-10s%-10s%n", user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getAddress(), user.getRole(), user.getBalance());
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
                "Chức năng là số dương từ 1 tới 6, vui lòng nhập lại: ", 1,6);
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

    public void lockUserByEmail() {
        System.out.println("Nhập email của tài khoản cần khóa: ");
        String emailLock = new Scanner(System.in).nextLine();
        for (User user : users) {
            if (user.getEmail().equals(emailLock)) {
                if (!lockUserByEmails.contains(emailLock)) {
                    lockUserByEmails.add(emailLock);
                    System.out.println("User có mail là " +emailLock + " đã được khóa.");
                    saveUserData();
                } else {
                    System.out.println("Email vừa nhập đã bị khóa");
                }
                return;
            }
        }
        System.out.println("Người dùng không tồn tại!");
    }

    public boolean isUserLocked(String mailUser){
        return lockedUserMail.contains(mailUser);
    }

    public void transactionHistory() {
//TODO Code lịch sử mua vé của khách hàng
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
}


            



