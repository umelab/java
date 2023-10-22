package com.umelab.selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {

    public static void main(String args[]) {
        BiwaDataCrowler crowler = new BiwaDataCrowler("https://www.river.go.jp/kawabou/pcfull/tm?itmkndCd=6&ofcCd=22039&obsCd=6&isCurrent=true&fld=0");
        BiwaDataModel model = new BiwaDataModel();
        crowler.setModel(model);
        crowler.getConnection();
	try {
	    DbInserter db = new DbInserter(model);
	    db.initConnection();
	    db.insertData();
	} catch (Exception e) {
	   e.printStackTrace();
        }
    }
}
