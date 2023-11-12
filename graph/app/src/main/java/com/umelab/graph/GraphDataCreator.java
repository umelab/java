package com.umelab.graph;

import java.util.Calendar;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * GraphDataCreator
 * 
 * This class is used to create graph data.
 */
public class GraphDataCreator {

    private Connection conn = null;

    public GraphDataCreator() throws SQLException {
        getCurrentHour();
        createGraphHeader();
        createGraphData();
    }

    private void initConnection() throws SQLException {
        conn = DriverManager.getConnection(
		    "jdbc:mysql://localhost:3306/biwako1",
		    "root",
		    "umeda389@"
	       );
	    conn.setAutoCommit(false);
    }

    /**
     * 現在の時刻を24時間形式で取得する
     * @return 現在の時刻
     */
    private int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }

    /**
     * グラフのヘッダーを作成する
     */
    private void createGraphHeader() {
        // create graph header
        int currentHour = getCurrentHour();
        int startHour = currentHour + 1;
        String header = getHeaderTimeLine(startHour, currentHour);
        System.out.println(header);
    }

    /**
     * グラフのデータを作成する
     */
    private void createGraphData() throws SQLException {
        // create graph data
        initConnection();
        String data = extractTemperatureData();
        System.out.println("----------");
        System.out.println(data);
        System.out.println("----------");
    }

    /**
     * 温度データを取得する
     * @return 温度データ
     */
    private String extractTemperatureData() {
        String sql = "select temperature from Temperature order by year desc, month desc, day desc, hour desc";        
        String tempData = ""; 
        
        try {
            Statement stmt1 = conn.createStatement();
            ResultSet rs = stmt1.executeQuery(sql);
            int cnt = 0;
            
            while (rs.next()) {
                tempData += rs.getString("temperature") + " ";
                cnt++;
                if (cnt == 24) {
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempData;
    }

    /**
     * グラフヘッダーの時間を作成する
     * @param startHour
     * @param currentHour
     * @return グラフのヘッダー
     */
    private String getHeaderTimeLine(int startHour, int currentHour) {
        int hours = 24;
        String header = "";
        int increment = 0;
        for (int i = 0; i < hours; i++) {
            if (startHour + i >= 24) {
                startHour = 0;
                increment = 0;
            }
            header += (startHour + increment) + ":00 ";
            increment++;
        }
        return header;
    }

}
