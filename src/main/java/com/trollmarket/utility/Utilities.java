package com.trollmarket.utility;

public class Utilities {

    public static String capitalizeFirstChar(String theWord){

        return theWord.substring(0, 1).toUpperCase() + theWord.substring(1, theWord.length()).toLowerCase();
    }
}
