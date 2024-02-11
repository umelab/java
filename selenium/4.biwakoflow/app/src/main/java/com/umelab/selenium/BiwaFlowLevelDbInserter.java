package com.umelab.selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiwaFlowLevelDbInserter {
	private static final Logger logger = LogManager.getLogger(BiwaFlowLevelDbInserter.class);
	private Connection conn = null;
    private BiwaFlowLevelModel model;
    final String sql = "insert into FlowLevel (year, month, day, hour, waterlevel, outflow, rainfall) values (?,?,?,?,?,?,?)";

    public BiwaFlowLevelDbInserter(BiwaFlowLevelModel model){
		logger.info("start BiwaFlowLevelDbInserter");
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
			logger.error("Unable to initialize Connection", e);
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

   public void insertData() throws Exception {
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
			ps.setString(5, model.getLevel());
			ps.setString(6, model.getOutFlow());
			ps.setString(7, model.getRainFall());
		
			ps.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			logger.error("Unable to insert data", e);
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}
