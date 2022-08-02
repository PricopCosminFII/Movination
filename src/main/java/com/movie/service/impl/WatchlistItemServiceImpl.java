package com.movie.service.impl;

import com.movie.constants.MessageConstants;
import com.movie.exception.InvalidData;
import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.model.Movie;
import com.movie.model.User;
import com.movie.model.Watchlist;
import com.movie.model.WatchlistItem;
import com.movie.repository.MovieDAO;
import com.movie.repository.UserDAO;
import com.movie.repository.WatchlistDAO;
import com.movie.repository.WatchlistItemDAO;
import com.movie.service.WatchlistItemService;
import lombok.Setter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Setter
public class WatchlistItemServiceImpl implements WatchlistItemService {
    private MovieDAO movieDAO;
    private WatchlistDAO watchlistDAO;
    private WatchlistItemDAO watchlistItemDAO;
    private UserDAO userDAO;

    private List<WatchlistItem> getCopyOfItemsWithAddedItem(List<WatchlistItem> watchlistItems, WatchlistItem addedWatchlistItem) {
        List<WatchlistItem> copyOfWatchlistItems = watchlistItems;
        if (copyOfWatchlistItems == null) copyOfWatchlistItems = new ArrayList<>();
        copyOfWatchlistItems.add(addedWatchlistItem);
        return copyOfWatchlistItems;
    }

    private List<WatchlistItem> getCopyOfItemsWithRemovedItem(List<WatchlistItem> watchlistItems, WatchlistItem removedWatchlistItem) {
        List<WatchlistItem> copyOfWatchlistItems = watchlistItems;
        if (copyOfWatchlistItems == null) copyOfWatchlistItems = new ArrayList<>();
        copyOfWatchlistItems.remove(removedWatchlistItem);
        return copyOfWatchlistItems;
    }

    @Override
    public List<WatchlistItem> getItemsFromWatchlistOfUser(String email) throws ObjectNotFound {
        User user = userDAO.findUserByEmail(email);
        if (user == null) throw new ObjectNotFound(MessageConstants.USER_NOT_FOUND);
        Watchlist watchlist = user.getWatchlist();
        if (watchlist != null)
            return watchlist.getWatchlistItems();
        return null;

    }

    @Override
    public void addMovieToWatchlistOfUser(String email, Long idMovie) throws ObjectNotFound, ObjectAlreadyExists {
        User user = userDAO.findUserByEmail(email);
        if (user == null) throw new ObjectNotFound(MessageConstants.USER_NOT_FOUND);
        Watchlist watchlist = user.getWatchlist();
        Movie movie = movieDAO.findMovieById(idMovie);
        if (movie == null) throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND);
        if (watchlist == null) {
            watchlist = createWatchlistForUser(user);
        }
        if (getItemWithMovieFromWatchlist(watchlist, movie) != null)
            throw new ObjectAlreadyExists(MessageConstants.MOVIE_EXISTS_IN_WATCHLIST);
        WatchlistItem watchlistItem = createItem(watchlist, movie);
        List<WatchlistItem> watchlistItems = getCopyOfItemsWithAddedItem(movie.getWatchlistItems(), watchlistItem);
        movie.setWatchlistItems(watchlistItems);
        movieDAO.save(movie);
        watchlistItems = getCopyOfItemsWithAddedItem(watchlist.getWatchlistItems(), watchlistItem);
        watchlist.setWatchlistItems(watchlistItems);
        watchlistDAO.save(watchlist);
    }

    private Watchlist createWatchlistForUser(User user) {
        Watchlist watchlist;
        watchlist = new Watchlist();
        watchlist.setUser(user);
        user.setWatchlist(watchlist);
        watchlistDAO.save(watchlist);
        userDAO.save(user);
        return watchlist;
    }

    @Override
    public void addRatingToMovieFromWatchlistOfUser(Double rating, Long idMovie, String email) throws InvalidData, ObjectNotFound {
        if (rating < 1 || rating > 5) throw new InvalidData(MessageConstants.INVALID_RATING);
        User user = userDAO.findUserByEmail(email);
        if (user == null) throw new ObjectNotFound(MessageConstants.USER_NOT_FOUND);
        Watchlist watchlist = user.getWatchlist();
        if (watchlist == null) throw new ObjectNotFound(MessageConstants.WATCHLIST_NOT_FOUND);
        Movie movie = movieDAO.findMovieById(idMovie);
        if (movie == null) throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND);
        WatchlistItem watchlistItem = getItemWithMovieFromWatchlist(watchlist, movie);
        if (watchlistItem == null) throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND_IN_WATCHLIST);
        Double ratingUser = watchlistItem.getUserRating();
        if (!isHavingRatingsFromOthers(watchlist, movie)) movie.setRating(getFormattedDouble(rating));
        else {
            ArrayList<Double> utilsForAverage = getUtilsForAverage(movie);
            Double sum = utilsForAverage.get(0);
            Double numberOfRatings = utilsForAverage.get(1);
            if (ratingUser == null) movie.setRating(getFormattedDouble((sum + rating) / (numberOfRatings + 1.0)));
            else {
                movie.setRating(getFormattedDouble((sum - ratingUser + rating) / numberOfRatings));
            }
        }
        watchlistItem.setUserRating(rating);
        movieDAO.save(movie);
        watchlistItemDAO.save(watchlistItem);
    }

    @Override
    public void removeMovieFromWatchlistOfUser(String email, Long idMovie) throws ObjectNotFound {
        User user = userDAO.findUserByEmail(email);
        if (user == null) throw new ObjectNotFound(MessageConstants.USER_NOT_FOUND);
        Watchlist watchlist = user.getWatchlist();
        Movie movie = movieDAO.findMovieById(idMovie);
        if (movie == null) throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND);
        WatchlistItem watchlistItem = getItemWithMovieFromWatchlist(watchlist, movie);
        if (watchlistItem == null) throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND_IN_WATCHLIST);
        if (!isHavingRatingsFromOthers(watchlist, movie)) movie.setRating(null);
        else if (watchlistItem.getUserRating() != null) {
            ArrayList<Double> utilsForAverage = getUtilsForAverage(movie);
            Double sum = utilsForAverage.get(0);
            Double numberOfRatings = utilsForAverage.get(1);
            movie.setRating(getFormattedDouble((sum - watchlistItem.getUserRating()) / (numberOfRatings - 1.0)));
        }
        movie.setWatchlistItems(getCopyOfItemsWithRemovedItem(movie.getWatchlistItems(), watchlistItem));
        movieDAO.save(movie);
        watchlist.setWatchlistItems(getCopyOfItemsWithRemovedItem(watchlist.getWatchlistItems(), watchlistItem));
        watchlistDAO.save(watchlist);
        watchlistItemDAO.delete(watchlistItem);
        removeWatchlistEmpty(user, watchlist);

    }

    private void removeWatchlistEmpty(User user, Watchlist watchlist) {
        List<WatchlistItem> itemsFromWatchlist = user.getWatchlist().getWatchlistItems();
        if (itemsFromWatchlist == null || itemsFromWatchlist.size() < 1) {
            user.setWatchlist(null);
            watchlist.setUser(null);
            watchlistDAO.delete(watchlist);
        }
    }


    private boolean isHavingRatingsFromOthers(Watchlist watchlist, Movie movie) {
        List<Watchlist> allWatchlist = watchlistDAO.findAll();
        if (!allWatchlist.isEmpty()) {
            for (Watchlist oneWatchlist : allWatchlist)
                if (!oneWatchlist.equals(watchlist)) {
                    if (isRatedInWatchlist(movie, oneWatchlist)) return true;
                }
        }
        return false;
    }

    private Double getFormattedDouble(Double number) {
        DecimalFormat formatter = new DecimalFormat("0.00");
        formatter.setRoundingMode(RoundingMode.DOWN);
        return Double.parseDouble(formatter.format(number));
    }

    private ArrayList<Double> getUtilsForAverage(Movie movie) {
        List<Watchlist> allWatchlist = watchlistDAO.findAll();
        Double sum = 0.0;
        Double numberOfRatings = 0.0;
        ArrayList<Double> utilsForAverage = new ArrayList<>();
        if (!allWatchlist.isEmpty()) {
            for (Watchlist watchlist : allWatchlist) {
                List<WatchlistItem> itemsOfWatchlist = watchlist.getWatchlistItems();
                if (itemsOfWatchlist != null && !itemsOfWatchlist.isEmpty()) {
                    for (WatchlistItem watchlistItem : itemsOfWatchlist)
                        if (watchlistItem.getMovie().equals(movie) && (watchlistItem.getUserRating() != null)) {
                            sum += watchlistItem.getUserRating();
                            numberOfRatings++;
                        }
                }
            }
        }
        utilsForAverage.add(sum);
        utilsForAverage.add(numberOfRatings);
        return utilsForAverage;
    }

    private boolean isRatedInWatchlist(Movie movie, Watchlist watchlist) {
        List<WatchlistItem> itemsOfWatchlist = watchlist.getWatchlistItems();
        if (itemsOfWatchlist != null && !itemsOfWatchlist.isEmpty()) {
            for (WatchlistItem watchlistItem : itemsOfWatchlist)
                if (watchlistItem.getMovie().equals(movie) && (watchlistItem.getUserRating() != null)) return true;
        }
        return false;
    }

    private WatchlistItem createItem(Watchlist watchlist, Movie movie) {
        WatchlistItem watchlistItem = new WatchlistItem();
        watchlistItem.setWatchlist(watchlist);
        watchlistItem.setMovie(movie);
        watchlistItemDAO.save(watchlistItem);
        return watchlistItem;
    }

    private WatchlistItem getItemWithMovieFromWatchlist(Watchlist watchlist, Movie movie) {
        if (watchlist != null) {
            List<WatchlistItem> itemsOfWatchlist = watchlist.getWatchlistItems();
            if (itemsOfWatchlist != null && !itemsOfWatchlist.isEmpty()) {
                for (WatchlistItem watchlistItem : itemsOfWatchlist)
                    if (watchlistItem.getMovie().equals(movie)) return watchlistItem;
            }
        }
        return null;
    }

    @Override
    public boolean isInWatchlistOfUser(String email, Long idMovie) throws ObjectNotFound {
        User user = userDAO.findUserByEmail(email);
        if (user == null) throw new ObjectNotFound(MessageConstants.USER_NOT_FOUND);
        Watchlist watchlist = user.getWatchlist();
        Movie movie = movieDAO.findMovieById(idMovie);
        if (movie == null) throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND);
        if (watchlist == null || watchlist.getWatchlistItems().isEmpty())
            return false;
        else return getItemWithMovieFromWatchlist(watchlist, movie) != null;

    }
}
