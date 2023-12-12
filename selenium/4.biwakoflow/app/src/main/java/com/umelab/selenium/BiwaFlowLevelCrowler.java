/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.umelab.selenium;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Iterator;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.ibm.icu.text.Transliterator;

public class BiwaFlowLevelCrowler {
    private String url;
    private WebDriver driver;
    private BiwaFlowLevelModel model;

    /**
     * コンストラクタ
     * @param url
     */
    public BiwaFlowLevelCrowler(String url) {
        this.url = url;
        init();
    }

    /**
     * モデルを設定
     * @param model
     */
    public void setModel(BiwaFlowLevelModel model) {
        this.model = model;
    }

    /**
     * init WebDriver
     */
    private void init(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        driver = new ChromeDriver(options);
    }
    
    /**
     * 全角を半角に変換
     * @param text
     */
    public static String ToHankaku(String text) {
        Transliterator transliterator = Transliterator.getInstance("Fullwidth-Halfwidth");
        return transliterator.transliterate(text);
    }

    /**
     * htmlからデータを取得
     */
    public void getConnection() {
        driver.get(url);

        String source = driver.getPageSource();
        
        String title = driver.getTitle();
        System.out.println("Web from: " + title);  

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        // 観測値
        List<WebElement> list = driver.findElements(By.className("mt30"));

        // 観測値
        Object obj[] = list.toArray();
        String rowWaterLevel = ((WebElement)obj[1]).getText();
        String waterLevel    = rowWaterLevel.split(" ")[2];
        String dataArray[] = rowWaterLevel.split(" ");
        //String waterLevel  = dataArray[2].split(" ")[2];
        // 単位を削除
        waterLevel = waterLevel.substring(0, waterLevel.length() - 2);
        // 全角を半角に変換
        waterLevel = ToHankaku(waterLevel);
        waterLevel = waterLevel.substring(0, waterLevel.length() -2);
        System.out.println("waterLevel: " + waterLevel); 

        String rowOutFlow    = dataArray[3];
        String outFlow       = rowOutFlow.split(" ")[2];
        System.out.println("outFlow: " + outFlow);
        if (outFlow.contains("全開")) {
            outFlow = "400";
        } else {
            // 単位を削除
            //outFlow = outFlow.substring(0, outFlow.length() - 4);
            // 全角を半角に変換
            outFlow = ToHankaku(outFlow);
            outFlow = outFlow.substring(0, outFlow.length() - 5);
        }

        String rowRainFall   = dataArray[4];
        String rainFall      = rowRainFall.split(" ")[2];
        // 単位を削除
        rainFall = rainFall.substring(0, rainFall.length() - 2);
        // 全角を半角に変換
        rainFall = ToHankaku(rainFall);

        System.out.println("水位: " + waterLevel);
        System.out.println("放流量: " + outFlow);
        System.out.println("降水量: " + rainFall);
        model.setLevel(waterLevel);
        model.setOutFlow(outFlow);
        model.setRainFall(rainFall);

        // driverオブジェクト破棄
        driver.quit();
    }
}
