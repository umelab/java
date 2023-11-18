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

    /**
     * コンストラクタ
     * @throws SQLException
     */
    public GraphDataCreator(int id) throws SQLException {
        getCurrentHour();
        initConnection();
        createGraph(id);
    }

    /**
     * DBとの接続を初期化する
     * @throws SQLException
     */
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
     * グラフを作成する
     */
    public void createGraph(int siteID){
        String current = "-current.csv";
        String past = "-yesterday.csv";
        String place = "";
        String filePathCurrentData = "/home/umeda/bassyan_public/biwako-data/";//adogawa-current.csv";
        String filePathPastData    = "/home/umeda/bassyan_public/biwako-data/";//adogawa-yesterday.csv";

        switch(siteID) {
            case 1:
                place = "adogawa";
                break;
            case 2:
                place = "biwako-ohashi";
                break;
            case 3:
                place = "ogoto";
                break;
            case 4:
                place = "mihogasaki";
                break;
            case 5:
                place = "karasaki";
                break;
            case 6:
                place = "setagawa";
                break;
        }

        filePathCurrentData += place + current;
        filePathPastData += place + past;
        System.out.println("------ filepath -------");
        System.out.println(filePathCurrentData);
        System.out.println("-----------------------");

        String header = createGraphHeader();

        //current graph data
        String currentData = extractTemperatureData(0, siteID);
        System.out.println("------------------");
        System.out.println(currentData);
        createFile(filePathCurrentData, header, currentData);

        //past graph data
        String pastData = extractTemperatureData(1, siteID);
        System.out.println("------------------");
        System.out.println(pastData);
        createFile(filePathPastData, header, pastData);
    }


    /**
     * グラフ表示用のファイルを作成する
     * @param filePath グラフ表示用のファイルパス
     * @param header グラフのヘッダー
     * @param data グラフのデータ
     */
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
        String sql = "select day, hour, temperature from Temperature where siteID = " + String.valueOf(siteId) + " order by year desc, month desc, day desc, hour desc";        
        String tempData = ""; 
        Stack<String> stack = new Stack<>();

        try {
            Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt1.executeQuery(sql);
                    
            int cnt = 0;

            // 0: current, 1: past
            // 過去データを取得する場合は24レコード目から取得する
            if (extractType == 1) {
                rs.absolute(24);
            }
            // データを取得する
            while (rs.next()) {
                String data = rs.getString("temperature");
                String day = rs.getString("day");
                String hour = rs.getString("hour");
                System.out.println(day + " " + hour + ":00 " + data);
                stack.push(data);
                
                cnt++;
                if (cnt == 24) {
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int size = stack.size();
        for (int i = 0; i < size; i++) {
            tempData += stack.pop() + ",";
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
            header += (startHour + increment) + ":00,";
            increment++;
        }
        return header;
    }

}
