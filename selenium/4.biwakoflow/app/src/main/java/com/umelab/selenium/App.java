package com.umelab.selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {

    public static void main(String args[]) {
        String url = "https://www.kkr.mlit.go.jp/biwako/index.php";
        BiwaFlowLevelCrowler crowler = new BiwaFlowLevelCrowler(url);
        BiwaFlowLevelModel model = new BiwaFlowLevelModel();
        try {
            crowler.setModel(model);
            crowler.getConnection();
            BiwaFlowLevelDbInserter db = new BiwaFlowLevelDbInserter(model);
            db.initConnection();
            db.insertData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
