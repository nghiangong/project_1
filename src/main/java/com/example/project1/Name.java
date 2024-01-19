package com.example.project1;

import java.util.Stack;

public class Name {
    public static int index = 0;
    public static String getRandomName() {

        return (char)(index++ + 65) + "0";
    }
}
