package com.jim.demo1.Tools;

import com.jim.demo1.Favorites.Favs;
import com.jim.demo1.Post.Beer;

import java.util.ArrayList;

/**
 * Created by jasekurasz on 4/21/15.
 */
public class PersistentData {
    public static ArrayList<Beer> beerInventory = new ArrayList<>();
    public static ArrayList<Favs> favsInventory = new ArrayList<>();
    public static String authorization;
    public static String latitude;
    public static String longitude;
}
