package com.umelab.graph;

public class App {
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            GraphDataCreator graphDataCreator = new GraphDataCreator();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
