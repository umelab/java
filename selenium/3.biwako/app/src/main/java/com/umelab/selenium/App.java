package com.umelab.selenium;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    public static void main(String args[]) {
        String[] url = {"https://www.river.go.jp/kawabou/pcfull/tm?itmkndCd=6&ofcCd=22039&obsCd=6&isCurrent=true&fld=0",
                        "https://www.river.go.jp/kawabou/pcfull/tm?itmkndCd=6&ofcCd=22039&obsCd=3&isCurrent=true&fld=0",
                        "https://www.river.go.jp/kawabou/pcfull/tm?itmkndCd=6&ofcCd=22039&obsCd=2&isCurrent=true&fld=0",
                        "https://www.river.go.jp/kawabou/pcfull/tm?itmkndCd=6&ofcCd=22039&obsCd=7&isCurrent=true&fld=0",
                        "https://www.river.go.jp/kawabou/pcfull/tm?itmkndCd=6&ofcCd=22039&obsCd=1&isCurrent=true&fld=0",
                        "https://www.river.go.jp/kawabou/pcfull/tm?itmkndCd=6&ofcCd=22039&obsCd=9&isCurrent=true&fld=0"
                        };
        int siteID[] = {1, 2, 3, 4, 5, 6};
        String name[] = {"安曇川", "琵琶湖大橋", "雄琴", "三保ヶ崎", "唐橋", "瀬田川"};
        for (int i = 0; i < url.length; i++) {
            BiwaDataCrowler crowler = new BiwaDataCrowler(name[i], url[i]);
            BiwaDataModel model = new BiwaDataModel();
            crowler.setModel(model);
            crowler.getConnection();
            try {
                DbInserter db = new DbInserter(model);
                db.initConnection();
                db.insertData(siteID[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
