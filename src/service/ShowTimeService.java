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
import java.util.*;

public class ShowTimeService {

    private static int AUTO_ID;
    private List<FormatMovie> formatMovies = new ArrayList<>();
    private List<ShowTime> showTimes = new ArrayList<>();
    private List<Movie> movies;
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
        LocalDateTime movieShowTime;
        while (true) {
        System.out.println("Mời bạn nhập ngày chiếu phim theo định dạng HH:mm:ss yyyy/MM/dd: ");
        String timeStart = new Scanner(System.in).nextLine();
            try {
                movieShowTime = LocalDateTime.parse(timeStart, DateTimeFormatter.ofPattern("HH:mm:ss yyyy/MM/dd"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Định dạng không hợp lệ, vui lòng nhập lại");
            }
        }
        movieService.showingMovieList();
        System.out.println("Mời bạn nhập id phim: ");
        int movieId;
        Movie movie;
        do {
            movieId = new Scanner(System.in).nextInt();
            movie = movieService.getMovieActive(movieId);
        } while (movie == null);
        theaterService.showingTheaterActive();
        System.out.println("Mời bạn nhập ID phòng chiếu (): ");
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
        FormatMovie formatMovie = null;
        do {
            System.out.println("Mời bạn chọn định dạng của phim chiếu");
            System.out.println("1. 2D");
            System.out.println("2. 3D");
            System.out.println("3. 4DX");
            System.out.println("4. IMAX");
            System.out.println("5. Thoát");
            int formatChoice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
            switch (formatChoice) {
                case 1:
                    formatMovie = FormatMovie.TWO_DIMENSION;
                    break;
                case 2:
                    formatMovie = FormatMovie.THREE_DIMENSION;
                    break;
                case 3:
                    formatMovie = FormatMovie.FOUR_DIMENSION_X;
                    break;
                case 4:
                    formatMovie = FormatMovie.IMAX;
                    break;
                case 5:
                    return;
            }
            if (movie.getFormatMovie().contains(formatMovie)){
                break;
            } else {
                System.out.println("Định dạng chiếu này không có cho phim đã chọn. Vui lòng chọn lại.");
            }
        } while (formatMovie == null);
            ShowTime showTime = new ShowTime(AUTO_ID++, movie, theater, formatMovie, movieShowTime);
            showTimes.add(showTime);
            showShowTime(showTime);
            saveShowTimeData();
    }


    private void saveShowTimeData() {
        fileUtil.writeDataToFile(showTimes, SHOWTIME_DATA_FILE);
    }
    

    private void showShowTime(ShowTime showTime) {
        printHeader();
        showShowTimeDetail(showTime);
    }

    private void showShowTimeDetail(ShowTime showTime) {
        System.out.printf("%-5s%-40s%-40s%-30s%-30s%n", showTime.getShowtimeId(), showTime.getMovie().getMovieName(),showTime.getFormatMovie() ,showTime.getTheater().getTheaterName(), showTime.getMovieTime());

    }

    private void printHeader() {
        System.out.printf("%-5s%-40s%-40s%-30s%-30s%n", "Id", "Movie", "FormatMovie" , "Theater", "MovieTime");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void updateInfo() {
        showShowTimeAvailableDetail();
        while (true) {
            System.out.println("Nhập ID của suất chiếu bạn muốn chỉnh sửa: ");
            int id = new Scanner(System.in).nextInt();
            ShowTime showTime = findShowTimeAvailableById(id);
            if (showTime == null) {
                System.out.println("Thông tin không chính xác , vui lòng nhập lại : ");
                continue;
            }
            System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa: ");
            System.out.println("1. Chỉnh sửa phim: ");
            System.out.println("2. Chỉnh sửa phòng chiếu");
            System.out.println("3. Chỉnh sửa thời gian chiếu phim: ");
            System.out.println("4. Chỉnh sửa định dạng phim: ");
            System.out.println("5. Thoát");
            int choice = InputUtil.chooseOption("Xin mời chọn chức năng: ",
                    "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
            switch (choice) {
                case 1:
                    System.out.println("Mời bạn nhập tên phim mới sau khi chỉnh sửa: ");
                    String name = new Scanner(System.in).nextLine();
                    showTime.getMovie().setMovieName(name);
                    break;
                case 2:
                    System.out.println("Mời bạn nhập phòng chiếu mới sau khi chỉnh sửa: ");
                    int theaterID;
                    while (true) {
                        try {
                            theaterID = new Scanner(System.in).nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.print("Lựa chọn phải là một số nguyên, vui lòng nhập lại: ");
                        }
                    }
                    showTime.getTheater().setTheaterID(theaterID);
                case 3:
                    System.out.println("Mời bạn nhập thời gian chiếu phim mới sau khi chỉnh sửa: ");
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
                    showTime.setMovieTime(movieShowTime);
                case 4:
                    FormatMovie formatMovie = null;
                    do {
                        System.out.println("Mời bạn chọn định dạng của phim chiếu");
                        System.out.println("1. 2D");
                        System.out.println("2. 3D");
                        System.out.println("3. 4DX");
                        System.out.println("4. IMAX");
                        System.out.println("5. Thoát");
                        int formatChoice = InputUtil.chooseOption("Xin mời chọn chức năng",
                                "Chức năng là số dương từ 1 tới 5, vui lòng nhập lại: ", 1, 5);
                        switch (formatChoice) {
                            case 1:
                                formatMovie = FormatMovie.TWO_DIMENSION;
                                break;
                            case 2:
                                formatMovie = FormatMovie.THREE_DIMENSION;
                                break;
                            case 3:
                                formatMovie = FormatMovie.FOUR_DIMENSION_X;
                                break;
                            case 4:
                                formatMovie = FormatMovie.IMAX;
                                break;
                            case 5:
                                return;
                        }
                        if (showTime.getMovie().getFormatMovie().contains(formatMovie)){
                            break;
                        } else {
                            System.out.println("Định dạng chiếu này không có cho phim đã chọn. Vui lòng chọn lại.");
                        }
                    } while (formatMovie == null);
                    showTime.setFormatMovie(formatMovie);
                case 5:
                    return;
            }
            showShowTime(showTime);
            saveShowTimeData();
        }
    }



    public ShowTime findShowTimeAvailableById(int id) {
        LocalTime now = LocalTime.now();
        for (ShowTime showTime : showTimes) {
            if (showTime.getShowtimeId() == id && showTime.getMovieTime().isAfter(ChronoLocalDateTime.from(now)) ) {
                return showTime;
            }
        }
        return null;
    }

    public void showShowTimeAvailableDetail() {
        List<ShowTime> showtimeAvailable = new ArrayList<>();
        for (ShowTime showTime : showTimes){
            if (!Objects.equals(showTime.getMovieTime(), LocalDateTime.now())) {
                showtimeAvailable.add(showTime);
            }
        }
        if (showtimeAvailable.isEmpty()) {
            System.out.println("Không có suất chiếu khả dụng");
        } else {
            System.out.println("------------------ CÁC SUẤT CHIẾU ------------------");
            printHeader();
            for (ShowTime showTime : showtimeAvailable) {
                showShowTimeDetail(showTime); //TODO CHECK LATER
            }
        }
    }

    public void showShowTimeByMovie() {
        System.out.println("Mời bạn nhập id của bộ phim: ");
        int movieId = new Scanner(System.in).nextInt();
        List<ShowTime> showtimeByMovieAvailable = new ArrayList<>();
        LocalTime now = LocalTime.now();
        printHeader();
        for (Movie movie : movies) {
            for (ShowTime showTime : showTimes) {
                if (movie.getId() == movieId &&
                        showTime.getMovieTime().isAfter(ChronoLocalDateTime.from(now))) {
                    showtimeByMovieAvailable.add(showTime);
                }
            }
            if (!showtimeByMovieAvailable.isEmpty()) {
                System.out.println("Suất chiếu khả dụng cho phim " + movie.getMovieName() + ":");
                System.out.println("------------------ CÁC SUẤT CHIẾU ------------------");
                printHeader();
                for (ShowTime showTime:showtimeByMovieAvailable) {
                    showShowTimeDetail(showTime);
                }
            }else{
                System.out.println("Không có suất chiếu khả dụng cho phim " + movie.getMovieName() + ".");
            }
        }
    }

    public void setShowTimes() {
        List<ShowTime> showTimeList = fileUtil.readDataFromFile(SHOWTIME_DATA_FILE, ShowTime[].class);
        showTimes = showTimeList != null ? showTimeList : new ArrayList<>();

    }

    public void findCurrentAutoId() {
        int maxId = -1;
        for (ShowTime showTime : showTimes) {
            if (showTime.getShowtimeId() > maxId) {
                maxId = showTime.getShowtimeId();
            }
        }
        AUTO_ID = maxId + 1;
    }
}

