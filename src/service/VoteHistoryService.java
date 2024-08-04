package service;

import entity.Movie;
import entity.User;
import entity.VoteHistory;
import util.FileUtil;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class VoteHistoryService {

    private final FileUtil<VoteHistory> fileUtil = new FileUtil<>();
    private static final String VOTE_HISTORY_DATA_FILE = "voteHistories.json";
    private List<VoteHistory> voteHistories;

    private final UserService userService;
    private final MovieService movieService;


    public VoteHistoryService(UserService userService, MovieService movieService) {
        this.userService = userService;
        this.movieService = movieService;
    }

    public void setVoteHistories() {
        List<VoteHistory> voteHistoryList = fileUtil.readDataFromFile(VOTE_HISTORY_DATA_FILE, VoteHistory[].class);
        voteHistories = voteHistoryList != null ? voteHistoryList : new ArrayList<>();
    }

    public void saveVoteHistoriesData() {
        fileUtil.writeDataToFile(voteHistories, VOTE_HISTORY_DATA_FILE);
    }


    public void inputVote() {
        User user = userService.getLoggedInUser();
        System.out.println("Mời bạn nhập ID sách : ");
        Movie movie;
        while (true) {
            int movieId;
            while (true) {
                try {
                    movieId = new Scanner(System.in).nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                }
            }
            movie = movieService.findMovieById(movieId);
            if (movie == null) {
                System.out.println("thông tin ID không chính xác , vui lòng nhập lại ");
                continue;
            }
            break;
        }
        System.out.println("Mời bạn nhập số sao đánh giá cho phim này (trong khoảng từ 0 dến 5): ");
        double newVoteStar;
        while (true) {
            while (true) {
                try {
                    newVoteStar = new Scanner(System.in).nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Giá trị bạn vừa nhập không phải là một số tụ nhiên . Vui lòng nhập lại.");
                }
            }
            if (newVoteStar < 0 || newVoteStar > 5) {
                System.out.println("Số sao đánh giá trong khoảng từ 0 dến 5 , vui lòng nhập lại ");
                continue;
            }
            break;
        }
        System.out.println("Mời bạn ghi nhận xét về bộ phim: ");
        String comment = new Scanner(System.in).nextLine();
        VoteHistory voteHistory = new VoteHistory(user, movie, comment, newVoteStar);
        voteHistories.add(voteHistory);
        saveVoteHistoriesData();
    }


    public void findHistoryVoteByMovieId() {
        System.out.println("Mời bạn nhập ID phim : ");
        Movie movie;
        int movieId;
        while (true) {
            try {
                movieId = new Scanner(System.in).nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Giá trị bạn vừa nhập không phải là một số nguyên. Vui lòng nhập lại.");
                continue;
            }
            movie = movieService.findMovieById(movieId);
            if (movie == null) {
                System.out.println("thông tin ID không chính xác , vui lòng nhập lại ");
                continue;
            }
            break;
        }

        ArrayList<VoteHistory> votesFindHistory = new ArrayList<>();
        for (VoteHistory voteHistory : voteHistories) {
            if (voteHistory.getMovie().getAutoId() == movieId) {
                votesFindHistory.add(voteHistory);
            }
        }
        showVoteHistories(votesFindHistory);
    }

    private void showVoteHistories(ArrayList<VoteHistory> histories) {
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%n", "User", "Movie", "ratedContent", "Time", "voteStarHistory", "averageVoteStar");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        for (VoteHistory voteHistory : histories) {
            showVoteHistory(voteHistory);
        }
    }

    private void showVoteHistory(VoteHistory voteHistory) {
        double a = voteHistory.getMovie().getVoteStar() * voteHistory.getMovie().getVoteCount() + voteHistory.getVoteStarHistory();
        double b = voteHistory.getMovie().getVoteCount() + 1;
        double c = a / b;

        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%n", voteHistory.getUser().getName(), voteHistory.getMovie().getMovieName(),
                voteHistory.getContent(), voteHistory.getCreatedDate(), voteHistory.getVoteStarHistory(), c);
    }

    public void findHistoryVoteByMovieName() {
        System.out.println("Mời bạn nhập tên phim: ");
        String movieName = new Scanner(System.in).nextLine();
        ArrayList<VoteHistory> votesFindHistory = new ArrayList<>();
        for (VoteHistory voteHistory : voteHistories) {
            if (voteHistory.getMovie().getMovieName().toLowerCase().contains(movieName.toLowerCase())) {
                votesFindHistory.add(voteHistory);
            }
        }
        showVoteHistories(votesFindHistory);

    }
}

