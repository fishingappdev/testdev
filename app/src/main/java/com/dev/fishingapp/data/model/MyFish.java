package com.dev.fishingapp.data.model;

/**
 * Created by user on 4/21/2016.
 */
public class MyFish {

    private String fishType;
    private String whereCaught;
    private String weight;
    private String length;
    private String date;

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public String getWhereCaught() {
        return whereCaught;
    }

    public void setWhereCaught(String whereCaught) {
        this.whereCaught = whereCaught;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public MyFish(String fishtype,String wherecaught,String weight,String length,String date){
        this.fishType=fishtype;
        this.whereCaught=wherecaught;
        this.weight=weight;
        this.length=length;
        this.date=date;

    }
}
