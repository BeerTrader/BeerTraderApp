package com.jim.demo1;

/**
 * Created by Jim on 4/7/2015.
 */
public class Beer {
    String beer_name;
    String brewery;
    String beer_style;
    String imgUrl;


    public Beer(String beer_name, String brewery, String beer_style, String imgUrl){
        this.beer_name = beer_name;
        this.brewery = brewery;
        this.beer_style = beer_style;
        this.imgUrl = imgUrl;
    }



    public String getBeer_name() {
        return beer_name;
    }

    public void setBeer_name(String beer_name) {

        this.beer_name = beer_name.replace("\"", "");
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {

        this.imgUrl = imgUrl.replace("\\", "").replace("\"", "");
    }

    public String getBeer_style() {
        return beer_style;
    }

    public void setBeer_style(String beer_style) {
        this.beer_style = beer_style;
    }
}

