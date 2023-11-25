package com.umelab.selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BiwaWeatherDbInserter {
    private Connection conn = null;
    private BiwaWeatherModel model;
    final String sql = "insert into Weather (year, month, day, hour, placeID, temperature, rainfall, winddirection, windspeed, humidity) values (?,?,?,?,?,?,?,?,?,?)";

    public BiwaWeatherDbInserter(BiwaWeatherModel model){
	this.model = model;
    }

    public void initConnection() throws SQLException {
        conn = DriverManager.getConnection(
		"jdbc:mysql://localhost:3306/biwako1",
		"root",
		"umeda389@"
	       );
	conn.setAutoCommit(false);
    }

   private String getCurrentTime(){
       Calendar cal = Calendar.getInstance();
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       String currentTime = sdf.format(cal.getTime());
       return currentTime;
   }

   public void insertData(int id) throws Exception {
        String currentTime = getCurrentTime();
        System.out.println("currentTime:" + currentTime);
		String ymd[] = currentTime.split("/");
		int year = Integer.parseInt(ymd[0]);
		int month = Integer.parseInt(ymd[1]);
		String tmp[] = ymd[2].split(" ");
		int day = Integer.parseInt(tmp[0]);
		String tmpTime[] = tmp[1].split(":");
		int hour = Integer.parseInt(tmpTime[0]);

		System.out.println("year: " + year);
		System.out.println("month: " + month);
		System.out.println("date: " + day);
		System.out.println("hour: " + hour);
	
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ps.setInt(3, day);
			ps.setInt(4, hour);
			ps.setInt(5, id);
			ps.setString(6, model.getTemperature());
			ps.setString(7, model.getRainFall());
			ps.setString(8, model.getWindDirection());
			ps.setString(9, model.getWindSpeed());
			ps.setString(10, model.getHumidity());
		
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
