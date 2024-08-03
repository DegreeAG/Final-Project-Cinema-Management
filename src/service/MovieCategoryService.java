package service;

import entity.Movie;
import entity.MovieCategory;
import util.FileUtil;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MovieCategoryService {

    private final FileUtil<MovieCategory> fileUtil = new FileUtil<>();

    private static final String MOVIE_CATEGORY_DATA_FILE = "movieCategories.json";
    private static int AUTO_ID;
    private List<MovieCategory> movieCategories;

    public void setMovieCategories() {
        List<MovieCategory> bookCategoriesList = fileUtil.readDataFromFile(MOVIE_CATEGORY_DATA_FILE, MovieCategory[].class);
        movieCategories = bookCategoriesList != null ? bookCategoriesList : new ArrayList<>();
    }

    public void saveMovieCategoryData() {
        fileUtil.writeDataToFile(movieCategories, MOVIE_CATEGORY_DATA_FILE);
    }

    public void printHeader() {
        System.out.printf("%-5s%-30s%n", "Id", "Name");
        System.out.println("----------------------------------------");
    }

    public void showCategories() {
        printHeader();
        for (MovieCategory category : movieCategories) {
            showCategoryDetail(category);
        }
    }

    private void showCategoryDetail(MovieCategory category) {
        System.out.printf("%-5s%-30s%n", category.getIdCategory(), category.getNameCategory());

    }

    public MovieCategory findCategoryById(int idCategory) {
        for (MovieCategory movieCategory : movieCategories) {
            if (movieCategory.getIdCategory() == idCategory) {
                return movieCategory;
            }
        }
        return null;
    }

    public void create() {
        MovieCategory movieCategory = new MovieCategory(AUTO_ID++);
        System.out.println("Mời bạn nhập tên cho thể loại : ");
        movieCategory.setNameCategory(new Scanner(System.in).nextLine());
        movieCategories.add(movieCategory);
        System.out.println(movieCategory);
        saveMovieCategoryData();
    }

    public void updateCategoryById() {
        while (true) {
            System.out.println("Mời bạn nhập ID của thể loại phim muốn cập nhật: ");
            int idCategory;
            try {
                idCategory = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                continue;
            }
            MovieCategory movieCategory = findCategoryById(idCategory);
            if (movieCategory == null) {
                System.out.println("Id vừa nhập không tồn tại trong hệ thống, vui lòng nhập lại:  ");
                continue;
            }
            System.out.println("Mời bạn nhập tên thể loại mới : ");
            movieCategory.setNameCategory(new Scanner(System.in).nextLine());
            showCategory(movieCategory);
            saveMovieCategoryData(); // Lưu dữ liệu file
            break;
        }
    }

    private void showCategory(MovieCategory movieCategory) {
        printHeader();
        showCategoryDetail(movieCategory);
    }

    public void deleteCategoryById(int idCategory) {
        MovieCategory movieCategory = findCategoryById(idCategory);
        movieCategories.remove(movieCategory);
        saveMovieCategoryData();// Lưu file
        showCategories();
    }
}


