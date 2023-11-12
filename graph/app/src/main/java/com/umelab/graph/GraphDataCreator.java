package com.umelab.graph;

import java.util.Calendar;
import java.util.Stack;
import java.util.Iterator;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
        // createGraphHeader();
        // createGraphContext();
        createGraph(1);
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
    private String createGraphHeader() {
        // create graph header
        int currentHour = getCurrentHour();
        int startHour = currentHour + 1;
        String header = getHeaderTimeLine(startHour, currentHour);
        return header;
    }

    /**
     * グラフのデータを作成する
     */
    private String createGraphContext() throws SQLException {
        // create graph data
        initConnection();
        String data = extractTemperatureData(0, 1);
        return data;
    }

    public void createGraph(int siteID){
        String filePathCurrentData = "/home/umeda/bassyan_public/biwako-data/adogawa-current.csv";
        String filePathPastData    = "/home/umeda/bassyan_public/biwako-data/adogawa-yesterday.csv";

        String header = createGraphHeader();
        //current graph data
        String currentData = extractTemperatureData(0, siteID);
        createFile(filePathCurrentData, header, currentData);
    }

    private void createFile(String filePath, String header, String data) {
        String context = header + "\n";
        context += data;
        File file = new File(filePath);
        try {
            FileWriter filewriter = new FileWriter(file);
            filewriter.write(context);
            filewriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * 温度データを取得する
     * @return 温度データ
     */
    private String extractTemperatureData(int extractType, int siteId) {
        String sql = "select temperature from Temperature where siteID = " + String.valueOf(siteId) + " order by year desc, month desc, day desc, hour desc";        
        String tempData = ""; 
        Stack stack = new Stack();

        try {
            Statement stmt1 = conn.createStatement();
            ResultSet rs = stmt1.executeQuery(sql);
            
            
            int cnt = 0;

            if (extractType == 1) {
                rs.absolute(24);
            }
            while (rs.next()) {
                //tempData += rs.getString("temperature") + " ";
                String data = rs.getString("temperature");
                stack.push(data);
                
                cnt++;
                if (cnt == 24) {
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Iterator it = stack.iterator();

        while (it.hasNext()) {
            tempData += it.next() + " ";
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
