package com.example.weather;

import java.util.Random;

public class DataHandler {
    public int generateNumber(int min, int max) {
        Random rnd = new Random(System.currentTimeMillis());
        return (min + rnd.nextInt(max - min + 1));
    }
}