package com.example.myrestaurant.Model.DAO;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class LocalCartDataSource implements CartDataSource {
    private CartDAO mCartDAO;

    public LocalCartDataSource(CartDAO mCartDAO) {
        this.mCartDAO = mCartDAO;
    }

    @Override
    public Flowable<List<CartItem>> getAllCart(String fbid, int restaurantId) {
        return mCartDAO.getAllCart(fbid,restaurantId);
    }

    @Override
    public Single<Integer> countItemInCart(String fbid, int restaurantId) {
        return mCartDAO.countItemInCart(fbid,restaurantId);
    }

    @Override
    public Single<Long> sumPrice(String fbid, int restaurantId) {
        return mCartDAO.sumPrice(fbid,restaurantId);
    }

    @Override
    public Single<CartItem> getItemInCart(int foodId, String fbid, int restaurantId) {
        return mCartDAO.getItemInCart(foodId,fbid,restaurantId);
    }

    @Override
    public Completable insertOrReplaceAll(CartItem... cartItems) {
        return mCartDAO.insertOrReplaceAll(cartItems);
    }

    @Override
    public Single<Integer> updateCart(CartItem cart) {
        return mCartDAO.updateCart(cart);
    }

    @Override
    public Single<Integer> deleteCart(CartItem cart) {
        return mCartDAO.deleteCart(cart);
    }

    @Override
    public Single<Integer> cleanCart(String fbid, int restaurantId) {
        return mCartDAO.cleanCart(fbid,restaurantId);
    }
}
