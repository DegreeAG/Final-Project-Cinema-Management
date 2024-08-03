package service;

import constant.DateTimeConstant;
import constant.Status;
import entity.Movie;
import entity.MovieCategory;
import util.FileUtil;
import util.InputUtil;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MovieService {
    // TODO - khai báo List<Movie>

    private List<Movie> movies = new ArrayList<>();
    private final MovieCategoryService movieCategoryService;
    private static final String MOVIE_DATA_FILE = "movies.json";
    private final FileUtil<Movie> fileUtil = new FileUtil<>();
    private static int AUTO_ID;


    public MovieService(MovieCategoryService movieCategoryService) {
        this.movieCategoryService = movieCategoryService;
    }


    public void search() {
        // TODO - implement search
    }

    public Movie findMovieById(int movieID) {
        for (Movie movie : movies) {
            if (movie.getAutoId() == movieID) {
                return movie;
            }
        }
        return null;
    }


    public void updateMovie() {
        while (true) {
            System.out.println("Mời bạn nhập ID của phim : ");
            int movieID;
            while (true) {
                try {
                    movieID = new Scanner(System.in).nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                }
            }
            Movie movie = findMovieById(movieID);
            if (movie == null) {
                System.out.println("Thông tin không chính xác , vui lòng nhập lại : ");
                continue;
            }
            System.out.println("Mời bạn chọn phần thông tin muốn chỉnh sửa : ");
            System.out.println("1. Tên phim");
            System.out.println("2. Đạo diễn");
            System.out.println("3. Thể loại");
            System.out.println("4. Ngày công chiếu");
            System.out.println("5. Định dạng bộ phim");
            System.out.println("6. Thời lượng bộ phim");
            System.out.println("7. Đánh giá phim");
            System.out.println("8. Ngôn ngữ phụ đề");
            System.out.println("9. Trạng thái phim");
            System.out.println("10. Thoát");
            int functionChoice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 10, vui lòng nhập lại", 1, 10);
            switch (functionChoice) {
                case 1:
                    System.out.println("Mời bạn nhập tên phim mới: ");
                    String newMovieName = new Scanner(System.in).next();
                    movie.setMovieName(newMovieName);
                    break;
                case 2:
                    System.out.println("Mời bạn nhập tên diễn viên mới: ");
                    String newActor = new Scanner(System.in).next();
                    movie.setActors(newActor);
                    break;
                case 3:
                    while (true) {
                        movieCategoryService.showCategories();
                        System.out.println("Mời bạn nhập id của thể loại mới: ");
                        int idCategory;
                        try {
                            idCategory = new Scanner(System.in).nextInt();
                        } catch (InputMismatchException e) {
                            System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                            continue;
                        }
                        MovieCategory movieCategory = movieCategoryService.findCategoryById(idCategory);
                        if (movieCategory == null) {
                            System.out.println("Thông tin nhập không chính xác");
                            continue;
                        }
                        movie.setCategory(movieCategory);
                        break;
                    }
                    break;
                case 4:
                    LocalDate newPublishedYear;
                    while (true) {
                        System.out.println("Mời bạn nhập ngày công chiếu mới theo định dạng dd/MM/yyyy: ");
                        try {
                            newPublishedYear = LocalDate.parse(new Scanner(System.in).nextLine(), DateTimeConstant.DATE_FORMATTER);
                            break;
                        } catch (DateTimeException e) {
                            System.out.println("Định dạng không hợp lệ vui lòng nhập lại ");
                        }
                    }
                    movie.setPublishedYear(newPublishedYear);
                    break;
                case 5:
                    System.out.println("Mời bạn nhập định dạng phim mới: ");
                    String newFormat = new Scanner(System.in).next();
                    movie.setFormat(newFormat);
                    break;
                case 6:
                    int newMovieTime = 0;
                    System.out.println("Mời bạn nhập thời lượng chiếu phim mới: ");
                    try {
                        newMovieTime = new Scanner(System.in).nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                    }
                    movie.setMovieTime(newMovieTime);
                    break;
                case 7:
                    System.out.println("Mời bạn nhập đánh giá phim mới: ");
                    double newVoteStar = 0d;
                    try {
                        newVoteStar = new Scanner(System.in).nextDouble();
                    } catch (InputMismatchException e) {
                        System.out.println("Giá trị bạn vừa nhập không phải là một số thực. Vui lòng nhập lại.");
                        movie.setVoteStar(newVoteStar);
                    }
                    break;
                case 8:
                    System.out.println("Mời bạn nhập ngôn ngữ phụ đề mới cho phim: ");
                    String newLanguage = new Scanner(System.in).next();
                    movie.setLanguage(newLanguage);
                    break;
                case 9:
                    System.out.println("Mời bạn cập nhật trạng thái mới của phim:");
                    System.out.println("1. Phim đang công chiếu");
                    System.out.println("2. Phim không công chiếu");
                    System.out.println("3. Thoát");
                    int statusChoice;
                    while (true) {
                        statusChoice = new Scanner(System.in).nextInt();
                        if (statusChoice >= 1 && statusChoice <= 3) {
                            break;
                        }
                        System.out.print("Lựa chọn không hợp lệ, vui lòng chọn lại số nguyên từ 1 tới 3: ");
                    }
                    switch (statusChoice) {
                        case 1:
                            movie.getStatus(Status.ACTIVE);
                            break;
                        case 2:
                            movie.getStatus(Status.INACTIVE);
                            break;
                        case 3:
                            return;
                    }
                    break;
                case 10:
                    return;
            }
        }
    }

    public void inputMovie() {
        System.out.println("Mời bạn nhập tên phim : ");
        String name = new Scanner(System.in).nextLine();
        System.out.println("Mời bạn nhập tên diễn viên : ");
        String actor = new Scanner(System.in).nextLine();
        MovieCategory category;
        movieCategoryService.showCategories();
        while (true) {
            System.out.println("Mời bạn nhập id của thể loại mà bạn muốn gán cho phim: ");
            int idCategory;
            try {
                idCategory = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                continue;
            }
            category = movieCategoryService.findCategoryById(idCategory);
            if (category == null) {
                System.out.println("Thông tin không chính xác vui lòng nhập lại ");
                continue;
            }
            break;
        }
        LocalDate publishedYear;
        while (true) {
            System.out.println("Mời bạn nhập ngày công chiếu theo định dạng dd/MM/yyyy: ");
            try {
                publishedYear = LocalDate.parse(new Scanner(System.in).nextLine(), DateTimeConstant.DATE_FORMATTER);
                break;
            } catch (DateTimeException e) {
                System.out.println("Định dạng không hợp lệ vui lòng nhập lại ");
            }
        }
        String format;
        while (true) {
        System.out.println("Mời bạn nhập định dạng phim chiếu :");
            try {
                format = new Scanner(System.in).next();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một miêu tả định dạng của phim. Vui lòng nhập lại bằng chữ.");
            }
        }
        System.out.println("Mời bạn nhập thời lượng phim chiếu: ");
        int movieTime;
        while (true) {
            try {
                movieTime = new Scanner(System.in).nextInt();
                if (movieTime < 0) {
                    System.out.println("Thời lượng phim chiếu phải là số dương , vui lòng nhập lại ");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên . Vui lòng nhập lại.");
            }
        }
        System.out.println("Mới bạn nhập đánh giá của phim: ");
        double voteStar;
        while (true) {
            try {
                voteStar = new Scanner(System.in).nextDouble();
                if (voteStar <= 0) {
                    System.out.println("Số lượt sao đánh giá phim phải là số dương , vui lòng nhập lại ");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số thực. Vui lòng nhập lại.");
            }
        }
        System.out.println("Mời bạn nhập ngôn ngữ phụ đề của phim: ");
        String language;
        while (true) {
            try {
                language = new Scanner(System.in).next();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vư nhập không phải là một miêu tả ngôn ngữ phụ đề của phim, vui lòng nhập lại bằng chữ");
            }
        }
        System.out.println("Mời bạn lựa chọn trạng thái của bộ phim: ");
        System.out.println("1. Phim đang công chiếu");
        System.out.println("2. Phim không công chiếu");
        System.out.println("3. Thoát");
        int statusChoice;
        while (true) {
            statusChoice = new Scanner(System.in).nextInt();
            if (statusChoice >= 1 && statusChoice <= 3) {
                break;
            }
            System.out.print("Lựa chọn không hợp lệ, vui lòng chọn lại số nguyên từ 1 tới 3: ");
        }
        Status movieStatus = null;
        switch (statusChoice) {
            case 1:
                movieStatus = Status.ACTIVE;
            break;
            case 2:
                movieStatus = Status.INACTIVE;
                break;
            case 3:
                return;
        }
        Movie movie = new Movie(AUTO_ID++, name, actor, category, publishedYear, format, movieTime, voteStar, language, movieStatus);
        movies.add(movie);
        showMovie(movie);
        saveMovieData();
    }

    public void setMovies() {
        List<Movie> moviesList = fileUtil.readDataFromFile(MOVIE_DATA_FILE, Movie[].class);
        movies = moviesList != null ? moviesList : new ArrayList<>();
    }


    private void saveMovieData() {
        fileUtil.writeDataToFile(movies, MOVIE_DATA_FILE);
    }

    private void showMovie(Movie movie) {
            printHeader();
            showMovieDetail(movie);
    }


    private void showMovieDetail(Movie movie) {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n",movie.getAutoId(),movie.getMovieName(),movie.getActors(),movie.getCategory().getNameCategory()
                ,movie.getPublishedYear(), movie.getVoteStar(),movie.getLanguage());
    }

    public void printHeader(){
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-20s%-10s%-30s%-20s%-10s%-10s%n", "Id", "Name", "Actor", "CateGory","PublishedYear","VoteStar","Language");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void showingMovieList() {
        printHeader();
        for (Movie movie : movies){
            if (movie.equals(movie.getStatus(Status.ACTIVE))) {
                showMovieDetail(movie);
            }
            break;
        }
    }

    public Status getMovieActive (Status status) {
        return Status.ACTIVE;
    }
    public Status getMovieInactive (Status status) {
        return Status.INACTIVE;
    }


    public void findCurrentAutoId() {
        int maxId = -1;
        for (Movie movie : movies) {
            if (movie.getAutoId() > maxId) {
                maxId = movie.getAutoId();
            }
        }
        AUTO_ID = maxId + 1;
    }


    public List<Movie> findMoviesByCategoryId(int idCategory) {
        List<Movie> movies1 = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getCategory().getIdCategory() == idCategory) {
                movies1.add(movie);
            }
        }
        return movies1;
    }
}





