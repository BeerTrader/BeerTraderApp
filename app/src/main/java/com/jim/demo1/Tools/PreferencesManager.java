package com.jim.demo1.Tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.jim.demo1.Favorites.Favs;
import com.jim.demo1.Post.Beer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//public class PersistentData {
//public static ArrayList<Beer> beerInventory = new ArrayList<>();
//public static ArrayList<Favs> favsInventory = new ArrayList<>();
//public static String authorization;
//public static String latitude;
//public static String longitude;
//}

/**
 * Created by jasekurasz on 5/30/15.
 */
public class PreferencesManager {
    private SharedPreferences settings;

    public static String USER = "USER";
    public static String CHANNELS = "CHANNELS";
    public static String AUTHORIZATION = "AUTHORIZATION";
    public static String LATITUDE = "LATITUDE";
    public static String LONGITUDE = "LONGITUDE";
    public static String INVENTORY = "INVENTORY";
    public static String FAVORITES = "FAVORITES";

    private static PreferencesManager preferencesManagerManager;

    private PreferencesManager(SharedPreferences sp) {
        settings = sp;
    }

    public static PreferencesManager getInstance(Context ctx) {
        if (preferencesManagerManager == null) {
            preferencesManagerManager = new PreferencesManager(PreferenceManager.getDefaultSharedPreferences(ctx));
        }
        return preferencesManagerManager;
    }

    public String loadAuthorization(){
        return settings.getString(AUTHORIZATION,null);
    }

    public void saveAuthorization(String auth){
        SharedPreferences.Editor e = settings.edit();
        e.putString(AUTHORIZATION,auth);
        e.commit();
    }

    public String loadLongitude(){
        return settings.getString(LONGITUDE,null);
    }

    public void saveLongitude(String lon){
        SharedPreferences.Editor e = settings.edit();
        e.putString(LONGITUDE,lon);
        e.commit();
    }

    public String loadLatitude(){
        return settings.getString(LATITUDE,null);
    }

    public void saveLatitude(String lat){
        SharedPreferences.Editor e = settings.edit();
        e.putString(LATITUDE,lat);
        e.commit();
    }

    public String loadUser(){
        return settings.getString(USER,null);
    }

    public void saveUser(String novaVersao){
        SharedPreferences.Editor e = settings.edit();
        e.putString(USER,novaVersao);
        e.commit();
    }

    public ArrayList<String> loadChannels(){
        String channels = settings.getString(CHANNELS, null);
        if(channels == null)
        {
            return new ArrayList<String>();
        }

        String[] parts = channels.split(",");

        ArrayList<String> list = new ArrayList<String>();

        for (String channel : parts) {
            list.add(channel);
        }
        return list;
    }

    public void saveChannels(ArrayList<String> channels){
        SharedPreferences.Editor e = settings.edit();
        StringBuilder channelsString = new StringBuilder();

        for (String channel : channels) {
            channelsString.append(channel + ",");
        }

        e.putString(CHANNELS,channelsString.toString());
        e.commit();
    }

    //For Saving your Inventory
    private void saveInventory(ArrayList<Beer> beerList) {
        SharedPreferences.Editor e = settings.edit();
        Gson gson = new Gson();
        String jsonBeers = gson.toJson(beerList);
        e.putString(INVENTORY, jsonBeers);
        e.commit();
    }

    public void addInventory(Beer beer) {
        ArrayList<Beer> inventory = getInventory();
        if (inventory == null)
            inventory = new ArrayList<Beer>();
        inventory.add(beer);
        saveInventory(inventory);
    }

    public void removeInventory(Beer beer) {
        ArrayList<Beer> inventory = getInventory();
        if (inventory != null) {
            inventory.remove(beer);
            saveInventory(inventory);
        }
    }

    public ArrayList<Beer> getInventory() {
        List<Beer> beerList;

        if (settings.contains(INVENTORY)) {
            String jsonInventory = settings.getString(INVENTORY, null);
            Gson gson = new Gson();
            Beer[] inventoryItems = gson.fromJson(jsonInventory, Beer[].class);
            beerList = Arrays.asList(inventoryItems);
            beerList = new ArrayList<>(beerList);
        } else{
            Beer[] emptyInventory = new Beer[0];
            beerList = Arrays.asList(emptyInventory);
            beerList = new ArrayList<>(beerList);
            return (ArrayList<Beer>) beerList;
        }

        return (ArrayList<Beer>) beerList;
    }

    //For Saving your Favorites
    private void saveFavorites(ArrayList<Favs> favList) {
        SharedPreferences.Editor e = settings.edit();
        Gson gson = new Gson();
        String jsonFavs = gson.toJson(favList);
        e.putString(FAVORITES, jsonFavs);
        e.commit();
    }

    public void addFavorites(Favs fav) {
        ArrayList<Favs> favorites = getFavorites();
        if (favorites == null)
            favorites = new ArrayList<Favs>();
        favorites.add(fav);
        saveFavorites(favorites);
    }

    public void removeFavorites(Favs fav) {
        ArrayList<Favs> favorites = getFavorites();
        if (favorites != null) {
            favorites.remove(fav);
            saveFavorites(favorites);
        }
    }

    public ArrayList<Favs> getFavorites() {
        List<Favs> favsList;

        if (settings.contains(FAVORITES)) {
            String jsonFavs = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Favs[] favItems = gson.fromJson(jsonFavs, Favs[].class);
            favsList = Arrays.asList(favItems);
            favsList = new ArrayList<>(favsList);
        } else{
            Favs[] emptyFavorites = new Favs[0];
            favsList = Arrays.asList(emptyFavorites);
            favsList = new ArrayList<>(favsList);
            return (ArrayList<Favs>) favsList;
        }
        return (ArrayList<Favs>) favsList;
    }

}
