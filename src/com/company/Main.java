package com.company;

import Classes.*;


public class Main {

    public static void main(String[] args) {
        Menuu menu=new Menuu();
        //generation of all the application elements
        menu.generation();
        //start transfers
        menu.startTransfers();
        //start writing to the file
        menu.startWritingInfoToFile();
    }
}
