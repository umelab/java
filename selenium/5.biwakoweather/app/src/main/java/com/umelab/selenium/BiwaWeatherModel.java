package com.umelab.selenium;

/**
 * 琵琶湖天候モデル
 *   - 気温
 *   - 降水量
 *   - 風向き
 *   - 風速
 *   - 湿度
 */
public class BiwaWeatherModel {
    private String currentTime;
    private String temperature;
    private String rainfall;
    private String windDirection;
    private String windSpeed;
    private String humidity;

    public BiwaWeatherModel() {
    
    }

    public void setCurrentTime(String currentTime){
       this.currentTime = currentTime;
    }

    public String getCurrentTime(){
       return currentTime;
    }

    /**
     * 気温
     * @param temperature
     */
    public void setTemperature(String temp) {
        this.temperature = temp;
    }
    
    /**
     * 気温
     * @return temperature
     */
    public String getTemperature() {
        return temperature;
    } 

    /**
     * 降水量
     * @param rainfall
     */
    public void setRainFall(String rainfall) {
        this.rainfall = rainfall;
    }

    /**
     * 降水量
     * @return rainfall
     */
    public String getRainFall() {
        return rainfall;
    }

    /**
     * 風向き
     * @param windDirection
     */
    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    /**
     * 風向き
     * @return windDirection
     */
    public String getWindDirection() {
        return windDirection;
    }

    /**
     * 風速
     * @param windSpeed
     */
    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    /**
     * 風速
     * @return windSpeed
     */
    public String getWindSpeed() {
        return windSpeed;
    }

    /**
     * 湿度
     * @param humidity
     */
    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    /**
     * 湿度
     * @return humidity
     */
    public String getHumidity() {
        return humidity;
    }
}
