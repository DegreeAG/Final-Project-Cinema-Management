package service;

import constant.DateTimeConstant;
import constant.FormatMovie;
import entity.*;
import util.FileUtil;
import util.InputUtil;

import javax.swing.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ShowTimeService {

    private static int AUTO_ID;
    private List<ShowTime> showTimes = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();
    private final FileUtil<ShowTime> fileUtil = new FileUtil<>();
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
                    showTime.setFormatMovie(FormatMovie.TWO_DIMENSION);
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
            break;
        }
            ShowTime showTime = new ShowTime(AUTO_ID++ ,movie, theater, movieShowTime);
            showTimes.add(showTime);
            showShowTime(showTime);
            saveShowTimeData();
    }

    private void saveShowTimeData() {
        fileUtil.writeDataToFile(showTimes, SHOWTIME_DATA_FILE);
    }

    private FormatMovie setFormat(int Number) {
        ShowTime showTime = new
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
                    showTime.setFormatMovie(FormatMovie.TWO_DIMENSION);
                    break;
                case 2:
                    showTime.setFormatMovie(FormatMovie.THREE_DIMENSION);
                    break;
                case 3:
                    showTime.setFormatMovie(FormatMovie.FOUR_DIMENSION_X);
                    break;
                case 4:
                    showTime.setFormatMovie(FormatMovie.IMAX);
                    break;
                case 5:
                    break;
            }
            break;
        }
    }

    private void showShowTime(ShowTime showTime) {
        printHeader();
        showShowTimeDetail(showTime);
    }

    private void showShowTimeDetail(ShowTime showTime) {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n", showTime.getShowtimeId(), showTime.getMovie(), showTime.getTheater(), showTime.getMovieTime());

    }

    private void printHeader() {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n", "Id", "Movie", "Theater", "MovieTime");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void updateInfo() {
        showShowTimeAvailableDetail();
        System.out.println("Nhập ID của suất chiếu bạn muốn chỉnh sửa: ");
        int id = new Scanner(System.in).nextInt();
        ShowTime showTime = findShowTimeAvailableById(id);
        System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa: ");
        System.out.println("1. Chỉnh sửa phim: ");
        System.out.println("2. Chỉnh sửa phòng chiếu");
        System.out.println("3. Chỉnh sửa thời lượng phim: ");
        System.out.println("4. Thoát");
       int choice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
               "Chức năng là số dương từ 1 tới 4, vui lòng nhập lại: ", 1,4);
        switch (choice) {
            case 1:
                break;
        }
    }

    private ShowTime findShowTimeAvailableById(int id) {
        LocalTime now = LocalTime.now();
        for (ShowTime showTime : showTimes) {
            if (showTime.getShowtimeId() == id && showTime.getMovieTime().isAfter(ChronoLocalDateTime.from(now)) ) {
                return showTime;
            }
        }
        return null;
    }

    private void showShowTimeAvailableDetail() {
        List<ShowTime> showtimeAvailable = new ArrayList<>();
        LocalTime now = LocalTime.now();
        printHeader();
        for (ShowTime showTime : showTimes){
            if (showTime.getMovieTime().isAfter(ChronoLocalDateTime.from(now))) {
                showtimeAvailable.add(showTime);
            }
        }
        if (showtimeAvailable.isEmpty()) {
            System.out.println("Không có suất chiếu khả dụng để chỉnh sửa");
        } else {
            System.out.println("------------------ CÁC SUẤT CHIẾU ------------------");
            printHeader();
            for (ShowTime showTime : showtimeAvailable) {
                showShowTimeDetail(showTime); //TODO CHECK LATER
            }
        }
    }

    public void showShowTimeByMovie() {
        System.out.println("Mời bạn nhập tên bộ phim: ");
        String name = new Scanner(System.in).nextLine();
        List<ShowTime> showtimeByMovieNameAvailable = new ArrayList<>();
        LocalTime now = LocalTime.now();
        printHeader();
        for (Movie movie : movies) {
            for (ShowTime showTime : showTimes) {
                if (movie.getMovieName().toLowerCase().contains(name.toLowerCase()) &&
                        showTime.getMovieTime().isAfter(ChronoLocalDateTime.from(now))) {
                    showtimeByMovieNameAvailable.add(showTime);
                }
            }
            if (!showtimeByMovieNameAvailable.isEmpty()) {
                System.out.println("Suất chiếu khả dụng cho phim " + movie.getMovieName() + ":");
                System.out.println("------------------ CÁC SUẤT CHIẾU ------------------");
                printHeader();
                for (ShowTime showTime:showtimeByMovieNameAvailable) {
                    showShowTimeDetail(showTime);
                }
            }else{
                System.out.println("Không có suất chiếu khả dụng cho phim " + movie.getMovieName() + ".");
            }
        }
    }
}

