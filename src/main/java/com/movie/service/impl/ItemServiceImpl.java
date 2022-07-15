package com.movie.service.impl;

import com.movie.constants.MessageConstants;
import com.movie.exception.InvalidData;
import com.movie.exception.ObjectAlreadyExists;
import com.movie.exception.ObjectNotFound;
import com.movie.model.Item;
import com.movie.model.Movie;
import com.movie.model.Watchlist;
import com.movie.repository.ItemDAO;
import com.movie.repository.MovieDAO;
import com.movie.repository.WatchlistDAO;
import com.movie.service.ItemService;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
public class ItemServiceImpl implements ItemService {
    private MovieDAO movieDAO;
    private WatchlistDAO watchlistDAO;
    private ItemDAO itemDAO;

    private List<Item> getCopyOfItemsWithAddedItem(List<Item> items, Item addedItem) {
        List<Item> copyOfItems = items;
        if (copyOfItems == null) copyOfItems = new ArrayList<>();
        copyOfItems.add(addedItem);
        return copyOfItems;
    }

    private List<Item> getCopyOfItemsWithRemovedItem(List<Item> items, Item removedItem) {
        List<Item> copyOfItems = items;
        if (copyOfItems == null) copyOfItems = new ArrayList<>();
        copyOfItems.remove(removedItem);
        return copyOfItems;
    }

    @Override
    public List<Item> getItemsByIdWatchlist(Long idWatchlist) throws ObjectNotFound {
        Watchlist watchlist = watchlistDAO.findWatchlistById(idWatchlist);
        if (watchlist == null) throw new ObjectNotFound(MessageConstants.WATCHLIST_NOT_FOUND);
        return watchlist.getItems();
    }

    @Override
    public void addMovieToWatchlist(Long idWatchlist, Long idMovie) throws ObjectNotFound, ObjectAlreadyExists {
        Watchlist watchlist = watchlistDAO.findWatchlistById(idWatchlist);
        Movie movie = movieDAO.findMovieById(idMovie);
        if (watchlist == null) throw new ObjectNotFound(MessageConstants.WATCHLIST_NOT_FOUND);
        if (movie == null) throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND);
        if (getItemWithMovieFromWatchlist(watchlist, movie) != null)
            throw new ObjectAlreadyExists(MessageConstants.MOVIE_EXISTS_IN_WATCHLIST);
        Item item = createItem(watchlist, movie);
        List<Item> items = getCopyOfItemsWithAddedItem(movie.getItems(), item);
        movie.setItems(items);
        movieDAO.save(movie);
        items = getCopyOfItemsWithAddedItem(watchlist.getItems(), item);
        watchlist.setItems(items);
        watchlistDAO.save(watchlist);
    }

    @Override
    public void addRatingToMovieFromWatchlist(Double rating, Long idMovie, Long idWatchlist) throws InvalidData, ObjectNotFound {
        if (rating < 1 || rating > 5) throw new InvalidData(MessageConstants.INVALID_RATING);
        Watchlist watchlist = watchlistDAO.findWatchlistById(idWatchlist);
        Movie movie = movieDAO.findMovieById(idMovie);
        if (watchlist == null) throw new ObjectNotFound(MessageConstants.WATCHLIST_NOT_FOUND);
        if (movie == null) throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND);
        Item item = getItemWithMovieFromWatchlist(watchlist, movie);
        if (item == null) throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND_IN_WATCHLIST);
        Double ratingMovie = movie.getRating();
        Double ratingUser = item.getUserRating();
        if (!isHavingRatingsFromOthers(watchlist, movie)) movie.setRating(rating);
        else {
            if (ratingUser == null) movie.setRating((ratingMovie + rating) / 2.0);
            else {
                movie.setRating((2.0 * ratingMovie - ratingUser + rating) / 2.0);
            }
        }
        item.setUserRating(rating);
        movieDAO.save(movie);
        itemDAO.save(item);
    }

    @Override
    public void removeMovieFromWatchlist(Long idWatchlist, Long idMovie) throws ObjectNotFound {
        Watchlist watchlist = watchlistDAO.findWatchlistById(idWatchlist);
        Movie movie = movieDAO.findMovieById(idMovie);
        if (watchlist == null) throw new ObjectNotFound(MessageConstants.WATCHLIST_NOT_FOUND);
        if (movie == null) throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND);
        Item item = getItemWithMovieFromWatchlist(watchlist, movie);
        if (item == null) throw new ObjectNotFound(MessageConstants.MOVIE_NOT_FOUND_IN_WATCHLIST);
        if (!isHavingRatingsFromOthers(watchlist, movie)) movie.setRating(null);
        else if (item.getUserRating() != null) movie.setRating(2.0 * movie.getRating() - item.getUserRating());

        movie.setItems(getCopyOfItemsWithRemovedItem(movie.getItems(), item));
        movieDAO.save(movie);
        watchlist.setItems(getCopyOfItemsWithRemovedItem(watchlist.getItems(), item));
        watchlistDAO.save(watchlist);
        itemDAO.delete(item);

    }


    private boolean isHavingRatingsFromOthers(Watchlist watchlist, Movie movie) {
        List<Watchlist> allWatchlist = watchlistDAO.findAll();
        if (allWatchlist != null && !allWatchlist.isEmpty()) {
            for (Watchlist oneWatchlist : allWatchlist)
                if (!oneWatchlist.equals(watchlist)) {
                    if (isRatedInWatchlist(movie, oneWatchlist)) return true;
                }
        }
        return false;
    }

    private boolean isRatedInWatchlist(Movie movie, Watchlist watchlist) {
        List<Item> itemsOfWatchlist = watchlist.getItems();
        if (itemsOfWatchlist != null && !itemsOfWatchlist.isEmpty()) {
            for (Item item : itemsOfWatchlist)
                if (item.getMovie().equals(movie) && (item.getUserRating() != null)) return true;
        }
        return false;
    }

    private Item createItem(Watchlist watchlist, Movie movie) {
        Item item = new Item();
        item.setWatchlist(watchlist);
        item.setMovie(movie);
        itemDAO.save(item);
        return item;
    }

    private Item getItemWithMovieFromWatchlist(Watchlist watchlist, Movie movie) {
        List<Item> itemsOfWatchlist = watchlist.getItems();
        if (itemsOfWatchlist != null) {
            for (Item item : itemsOfWatchlist)
                if (item.getMovie().equals(movie)) return item;
        }
        return null;
    }
}
