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

public class BiwaDataCrowler {
    //private static String url = "https://www.river.go.jp/kawabou/pcfull/tm?itmkndCd=6&ofcCd=22039&obsCd=6&isCurrent=true&fld=0";
    private String url;
    private WebDriver driver;
    private BiwaDataModel model;

    public BiwaDataCrowler(String url) {
        this.url = url;
        init();
    }

    public void setModel(BiwaDataModel model) {
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

    public void getConnection() {
        driver.get(url);

        String source = driver.getPageSource();
        System.out.println("source: " + source);
        
        String title = driver.getTitle();
        System.out.println("Web from: " + title);  

        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

        // ページタイトル
        WebElement name = driver.findElement(By.className("tm-pc-detail-frame-info-rvrnm"));
        System.out.println("content: " + name.getText());

        // 観測時間
        WebElement currentTime = driver.findElement(By.cssSelector(".tm-pc-detail-info-latest-value.pb-1"));
        String currentTimeText = currentTime.getText().replace("■最新観測値", "");
        System.out.println("meatured time: " + currentTimeText);

        // 観測値
        List<WebElement> list = driver.findElements(By.className("tm-pc-detail-info-curt-value"));
        Iterator iterator = list.iterator();
        while(iterator.hasNext()) {
            WebElement element = (WebElement)iterator.next();
            System.out.println("content1: " + element.getText());
        }

        // 観測値
        Object obj[] = list.toArray();
        model.setTemp(((WebElement)obj[0]).getText());
        model.setPH(((WebElement)obj[1]).getText());
        model.setDO(((WebElement)obj[2]).getText());
        model.setConductivity(((WebElement)obj[3]).getText());
        model.setTurbidity(((WebElement)obj[4]).getText());
        
        System.out.println("水温: " + ((WebElement)obj[0]).getText());
        System.out.println("pH: " + ((WebElement)obj[1]).getText());
        System.out.println("DO: " + ((WebElement)obj[2]).getText());
        System.out.println("伝導率: " + ((WebElement)obj[3]).getText());
        System.out.println("濁度: " + ((WebElement)obj[4]).getText());

        driver.quit();
    }
    // public static void main(String[] args) {
    //     App app = new App();
    //     app.getConnection();
    // }
}
