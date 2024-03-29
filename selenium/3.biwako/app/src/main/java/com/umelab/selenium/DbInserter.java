package com.umelab.selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class DbInserter {
	private static final Logger logger = LogManager.getLogger(DbInserter.class);
	private Connection conn = null;
    private PreparedStatement stmt = null;
    private BiwaDataModel model;
    final String sql = "insert into Temperature (year, month, day, hour, siteID, temperature, ph, do, conductivity, turb) values (?,?,?,?,?,?,?,?,?,?)";

    public DbInserter(BiwaDataModel model){
		logger.info("start DbInserter");
		this.model = model;
    }

    public void initConnection() throws SQLException {
		try {
        conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/biwako1",
				"root",
				"umeda389@"
	       	);
		} catch (SQLException e) {
			logger.error("Error in initConnection", e);
			throw e;
		}
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
		logger.info("currentTime:" + currentTime);
		String ymd[] = currentTime.split("/");
		int year = Integer.parseInt(ymd[0]);
		int month = Integer.parseInt(ymd[1]);
		String tmp[] = ymd[2].split(" ");
		int day = Integer.parseInt(tmp[0]);
		String tmpTime[] = tmp[1].split(":");
		int hour = Integer.parseInt(tmpTime[0]);

		logger.info("year: " + year);
		logger.info("month: " + month);
		logger.info("date: " + day);
		logger.info("hour: " + hour);
	
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, month);
			ps.setInt(3, day);
			ps.setInt(4, hour);
			ps.setInt(5, id);
			ps.setString(6, model.getTemp());
			ps.setString(7, model.getPH());
			ps.setString(8, model.getDO());
			ps.setString(9, model.getConductivity());
			ps.setString(10, model.getTurbidity());
		
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			logger.error("Error in insertData", e);
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
