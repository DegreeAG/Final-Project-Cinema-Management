package service;

import constant.DateTimeConstant;
import constant.FormatMovie;
import entity.Movie;
import entity.Seat;
import entity.ShowTime;
import entity.Theater;
import util.FileUtil;
import util.InputUtil;

import javax.swing.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ShowTimeService {

    private List<ShowTime> showTimes = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();
    private final FileUtil<Seat> fileUtil = new FileUtil<>();
    private static final String SHOWTIME_DATA_FILE = "showtimes.json";
    private final MovieService movieService;
    private final UserService userService;
    private final TheaterService theaterService;

    public ShowTimeService(MovieService movieService, UserService userService, TheaterService theaterService) {
        this.movieService = movieService;
        this.userService = userService;
        this.theaterService = theaterService;
    }


    public void inputInfo() {
        System.out.println("Mời bạn nhập ngày chiếu phim theo định dạng HH:mm:ss yyyy/MM/dd: ");
        String timeStart = new Scanner(System.in).nextLine();
        LocalDateTime movieShowTime;
        while (true) {
            try {
                movieShowTime = LocalDateTime.parse(timeStart, DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng không hợp lệ, vui lòng nhập lại");
            }
        }
        System.out.println("Mời bạn nhập tên phim: ");
        String movieName = new Scanner(System.in).nextLine();
        Movie movie = movieService.getMovieActive(movieName);
        System.out.println("Mời bạn nhập ID phòng chiếu (nhập 'exit' để thoát): ");
        int theaterID;
        while (true) {
            try {
                theaterID = new Scanner(System.in).nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Lựa chọn phải là một số nguyên, vui lòng nhập lại: ");
            }
        }
        Theater theater = theaterService.getTheaterActive(theaterID);
        while (true) {
            System.out.println("Mời bạn lựa chọn định dạng của phim chiếu");
            System.out.println("1. 2D");
            System.out.println("2. 3D");
            System.out.println("3. 4DX");
            System.out.println("4. IMAX");
            System.out.println("5. Thoát");
            int formatChoice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
            switch (formatChoice) {
                case 1:
                    movie.setFormat(FormatMovie.TWO_DIMENSION);
                    break;
                case 2:
                    movie.setFormat(FormatMovie.THREE_DIMENSION);
                    break;
                case 3:
                    movie.setFormat(FormatMovie.FOUR_DIMENSION_X);
                    break;
                case 4:
                    movie.setFormat(FormatMovie.IMAX);
                    break;
                case 5:
                    return;
            }
        }
            ShowTime showTime = new ShowTime(movie, theater, movieShowTime);
            showTimes.add(showTime);
            return showTime;
    }

    public void updateInfo() {
    }
}

