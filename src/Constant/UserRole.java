package Constant;

public class UserRole {
    ADMIN("Admin"),
    USER("User");

    private final String roleName;

    public String getRoleName() {
        return roleName;
    }

    UserRole(String roleName) {
        this.roleName = roleName;
    }
}
