package com.umelab.selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {

    public static void main(String args[]) {
        // 今津
        // 長濱
        // 彦根
        // 南小松
        // 大津
        String[] url = {"https://www.jma.go.jp/bosai/amedas/#amdno=60051&area_type=offices&area_code=250000&format=table1h&elems=53610",
                        "https://www.jma.go.jp/bosai/amedas/#amdno=60061&area_type=offices&area_code=250000&format=table1h&elems=53410",
                        "https://www.jma.go.jp/bosai/amedas/#amdno=60131&area_type=offices&area_code=250000&format=table1h&elems=53614",
                        "https://www.jma.go.jp/bosai/amedas/#amdno=60116&area_type=offices&area_code=250000&format=table1h&elems=53410",
                        "https://www.jma.go.jp/bosai/amedas/#amdno=60216&area_type=offices&area_code=250000&format=table1h&elems=53610"
                        };
        int siteID[] = {1, 2, 3, 4, 5, 6};
        for (int i = 0; i < url.length; i++) {
            BiwaWeatherCrowler crowler = new BiwaWeatherCrowler(url[i]);
            BiwaWeatherModel model = new BiwaWeatherModel();
            crowler.setModel(model);
            crowler.getConnection();
            try {
                BiwaWeatherDbInserter db = new BiwaWeatherDbInserter(model);
                db.initConnection();
                db.insertData(siteID[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
