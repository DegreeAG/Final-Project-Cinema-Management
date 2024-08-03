package main;

import entity.User;
import view.MainMenu;

public class Main {

    public static User LOGGED_IN_USER = null;

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.initializeData();
        mainMenu.menu();
    }
}
