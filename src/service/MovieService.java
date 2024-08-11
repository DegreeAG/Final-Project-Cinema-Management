package service;

import constant.DateTimeConstant;
import constant.FormatMovie;
import constant.MovieClass;
import constant.Status;
import entity.Movie;
import entity.MovieCategory;
import util.FileUtil;
import util.InputUtil;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class MovieService {

    private final FileUtil<Movie> fileUtil = new FileUtil<>();
    private static final String FORMAT_DATA_FILE = "formats.json";
    private List<FormatMovie> formatMovies = new ArrayList<>();
    private final FileUtil<FormatMovie> fileUtil2 = new FileUtil<>();
    private List<Movie> movies = new ArrayList<>();
    private final MovieCategoryService movieCategoryService;
    private static final String MOVIE_DATA_FILE = "movies.json";
    private static int AUTO_ID;


    public MovieService(MovieCategoryService movieCategoryService) {
        this.movieCategoryService = movieCategoryService;
    }


    public void search() {
        System.out.println("Mời bạn nhập tên của phim : ");
        String name = new Scanner(System.in).nextLine();
        List<Movie> movies1 = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getMovieName().toLowerCase().contains(name.toLowerCase())) {
                movies1.add(movie);
            }else {
                System.out.println("Hệ thống không có phim bạn vừa nhập");
            }
        }
        showMovies(movies1);
    }


    public Movie findMovieByName(String string) {
        for (Movie movie : movies) {
            if (movie.getMovieName().toLowerCase().contains(string.toLowerCase())) {
                return movie;
            }
        }
        return null; //Trả về null nếu không tìm thấy phim
    }

    public Movie getMovieActive(int id) {
        for (Movie movie : movies) {
            if (movie.getId()==id && movie.getStatus() == Status.ACTIVE) {
                return movie;
            }
        }
        System.out.println("Không có tên phim mà bạn vừa lựa chọn, xin vui lòng chọn lại theo trên bảng.");
        return null; //Trả về null nếu không tìm thấy phim hoặc Inactive
    }

    public Movie findMovieById(int movieID) {
        for (Movie movie : movies) {
            if (movie.getId() == movieID) {
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
            System.out.println("2. Diễn viên");
            System.out.println("3. Thể loại");
            System.out.println("4. Ngày công chiếu");
            System.out.println("5. Thời lượng bộ phim");
            System.out.println("6. Ngôn ngữ phụ đề");
            System.out.println("7. Trạng thái phim");
            System.out.println("8. Định dạng phim");
            System.out.println("9. Thoát");
            int functionChoice = InputUtil.chooseOption("Xin mời chọn chức năng",
                    "Chức năng là số dương từ 1 tới 9, vui lòng nhập lại", 1, 9);
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
                    int newMovieTime = 0;
                    System.out.println("Mời bạn nhập thời lượng chiếu phim mới: ");
                    try {
                        newMovieTime = new Scanner(System.in).nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                    }
                    movie.setMovieTime(newMovieTime);
                    break;
                case 6:
                    System.out.println("Mời bạn nhập ngôn ngữ phụ đề mới cho phim: ");
                    String newLanguage = new Scanner(System.in).next();
                    movie.setLanguage(newLanguage);
                    break;
                case 7:
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
                case 8:
                    List<FormatMovie> selectedFormats = new ArrayList<>();
                    System.out.println("Nhập (các) định dạng chiếu của phim: ");
                    System.out.println("1. 2D");
                    System.out.println("2. 3D");
                    System.out.println("3. 4DX");
                    System.out.println("4. IMAX");
                    System.out.println("5. Thoát");
                    List<Integer> chosenFormats = new ArrayList<>();
                    while (true) {
                        String format = new Scanner(System.in).next();
                        String[] formatChoices = format.split(",");
                        boolean validChoice = true;
                        for (String choice : formatChoices) {
                            try {
                                int formatClassChoice = Integer.parseInt(choice);
                                if (formatClassChoice < 1 || formatClassChoice > 4) {
                                    System.out.println("Giá trị '" + choice + "' không phải là một lựa chọn hợp lệ. Vui lòng nhập lại.");
                                    validChoice = false;
                                    break;
                                }
                                chosenFormats.add(formatClassChoice);
                            } catch (NumberFormatException e) {
                                System.out.println("Giá trị '" + choice + "' không phải là một số nguyên hợp lệ. Vui lòng nhập lại.");
                                validChoice = false;
                                break;
                            }
                        }
                        if (validChoice) {
                            break;
                        }
                    }
                    for (Integer choice : chosenFormats) {
                        FormatMovie formatMovie;
                        switch (choice) {
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
                                System.out.println("Thoát.");
                                return;
                            default:
                                System.out.println("Lựa chọn không hợp lệ: " + choice + ". Vui lòng chọn lại từ 1 đến 5.");
                                continue;
                        }
                        movie.setFormatMovie(selectedFormats);
                    }
                    break;
                case 9:
                    return;
            }
            showMovie(movie);
            saveMovieData();
            break;
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
        System.out.println("Mời bạn nhập thời lượng phim chiếu (số phút): ");
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
            try {
                statusChoice = new Scanner(System.in).nextInt();
                if (statusChoice >= 1 && statusChoice <= 3) {
                    break;
                }
                System.out.print("Lựa chọn không hợp lệ, vui lòng chọn lại số nguyên từ 1 tới 3: ");

            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên . Vui lòng nhập lại.");
            }
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
        System.out.println("Mời bạn lựa chọn hạng phim: ");
        System.out.println("1. Phim phổ thông");
        System.out.println("2. Phim chất lượng cao");
        System.out.println("3. Phim bom tấn");
        System.out.println("4. Thoát");
        int movieClassChoice;
        while (true) {
            try {

                movieClassChoice = new Scanner(System.in).nextInt();
                if (movieClassChoice >= 1 && movieClassChoice <= 4) {
                    break;
                }
                System.out.print("Lựa chọn không hợp lệ, vui lòng chọn lại số nguyên từ 1 tới 4: ");
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số tự nhiên . Vui lòng nhập lại.");
            }
        }
        MovieClass movieClass = null;
        switch (movieClassChoice) {
            case 1:
                movieClass = MovieClass.MIDTIER;
                break;
            case 2:
                movieClass = MovieClass.HIGHTIER;
                break;
            case 3:
                movieClass = MovieClass.LUXYRYTIER;
                break;
            case 4:
                return;
        }
        List<FormatMovie> selectedFormats = new ArrayList<>();
        System.out.println("Nhập (các) định dạng chiếu của phim: ");
        System.out.println("1. 2D");
        System.out.println("2. 3D");
        System.out.println("3. 4DX");
        System.out.println("4. IMAX");
        System.out.println("5. Thoát");
        Set<Integer> chosenFormats = new HashSet<>();
        while (true) {
            String format = new Scanner(System.in).next();
            String[] formatChoices = format.split(",");
            boolean validChoice = true;
            for (String choice : formatChoices) {
                try {
                    int formatClassChoice = Integer.parseInt(choice);
                    if (formatClassChoice < 1 || formatClassChoice > 4) {
                        System.out.println("Giá trị '" + choice + "' không phải là một lựa chọn hợp lệ. Vui lòng nhập lại.");
                        validChoice = false;
                        break;
                    }
                    chosenFormats.add(formatClassChoice);
                } catch (NumberFormatException e) {
                    System.out.println("Giá trị '" + choice + "' không phải là một số nguyên hợp lệ. Vui lòng nhập lại.");
                    validChoice = false;
                    break;
                }
            }
            if (validChoice) {
                break;
            }
        }
        for (Integer choice : chosenFormats) {
            FormatMovie formatMovie;
            switch (choice) {
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
                    System.out.println("Thoát.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ: " + choice + ". Vui lòng chọn lại từ 1 đến 5.");
                    continue;
            }
            selectedFormats.add(formatMovie);


        }
        Movie movie = new Movie(AUTO_ID++, name, actor, category, publishedYear, movieTime, language, movieStatus, movieClass, selectedFormats);
        movies.add(movie);
        showMovie(movie);
        saveMovieData();
    }


    public void setMovies() {
        List<Movie> moviesList = fileUtil.readDataFromFile(MOVIE_DATA_FILE, Movie[].class);
        movies = moviesList != null ? moviesList : new ArrayList<>();
    }

    private void saveFormatData() {
        fileUtil2.writeDataToFile(formatMovies, FORMAT_DATA_FILE);
    }

    public List<Movie> getMovies () {
        return movies;
    }

    public void saveMovieData() {
        fileUtil.writeDataToFile(movies, MOVIE_DATA_FILE);
    }

    public void showMovie(Movie movie) {
        printHeader();
        showMovieDetail(movie);
    }

    public void showMovies(List<Movie> movies1) {
        printHeader();
        for (Movie movie : movies1) {
            showMovieDetail(movie);
        }
    }


    private void showMovieDetail(Movie movie) {
        System.out.printf("%-5s%-40s%-40s%-30s%-25s%-15s%-20s%-60s%n", movie.getId(), movie.getMovieName(), movie.getActors(), movie.getCategory().getNameCategory()
                , movie.getPublishedYear(), movie.getVoteStar(), movie.getLanguage(), movie.getFormatMovie());
    }

    public void printHeader() {
        System.out.printf("%-5s%-40s%-40s%-30s%-25s%-15s%-20s%-60s%n", "Id", "Name", "Actor", "CateGory", "PublishedYear", "VoteStar", "Language", "Formats");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void showingMovieList() {
        printHeader();
        for (Movie movie : movies) {
            if (movie.getStatus(Status.ACTIVE) == Status.ACTIVE) {
                showMovieDetail(movie);
                continue;
            }
            break;
        }
    }

    public Status getMovieActive(Status status) {
        return Status.ACTIVE;
    }

    public Status getMovieInactive(Status status) {
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

    public void showMoviesIfActive() {
        printHeader();
        List<Movie> movies1 = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getStatus(Status.ACTIVE) == Status.ACTIVE) {
                movies1.add(movie);
                System.out.println(movies1);
            }
            return;
        }
    }


    public void findMoviesByCategoryId() {
        movieCategoryService.showCategories();
        System.out.println("Mời bạn nhập id của thể loại phim mà bạn muốn tìm: ");
        int idCategory;
        while (true) {
            try {
                idCategory = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                continue;
            }

            List<Movie> movies1 = new ArrayList<>();
            for (Movie movie : movies) {
                if (movie.getCategory().getIdCategory() == idCategory) {
                    movies1.add(movie);
                }
            }
            showMovies(movies1);
            break;
        }
    }

    public void findMoviesByVotedStar() {
        ArrayList<Movie> movies1 = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getVoteStar() >= 4 && movie.getVoteStar() <= 5) {
                movies1.add(movie);
            }
        }
        showMovies(movies1);
    }

}







