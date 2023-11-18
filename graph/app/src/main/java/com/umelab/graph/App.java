package com.umelab.graph;

public class App {
    
    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            for(int i = 1; i <= 6; i++) {
                GraphDataCreator graphDataCreator = new GraphDataCreator(i);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
