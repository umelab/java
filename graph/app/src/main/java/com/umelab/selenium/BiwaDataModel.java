package com.umelab.selenium;

public class BiwaDataModel {
    private String currentTime;
    private String temp;
    private String ph;
    private String dox;
    private String conductivity;
    private String turbidity;

    public BiwaDataModel() {
    
    }

    public void setCurrentTime(String currentTime){
       this.currentTime = currentTime;
    }

    public String getCurrentTime(){
       return currentTime;
    }

    /**
     * 水温
     * @param temp
     */
    public void setTemp(String temp) {
        this.temp = temp;
    }
    
    /**
     * 水温
     * @return
     */
    public String getTemp() {
        return temp;
    } 

    /**
     * pH
     * @param ph
     */
    public void setPH(String ph) {
        this.ph = ph;
    }

    /**
     * pH
     * @return
     */
    public String getPH() {
        return ph;
    }

    /**
     * DO
     * @param dox
     */
    public void setDO(String dox) {
        this.dox = dox;
    }

    /**
     * DO
     * @return
     */
    public String getDO() {
        return dox;
    }

    /**
     * 伝導率
     * @param conductivity
     */
    public void setConductivity(String conductivity) {
        this.conductivity = conductivity;
    }

    /**
     * 伝導率
     * @return
     */
    public String getConductivity() {
        return conductivity;
    }

    /**
     * 濁度
     * @param turbidity
     */
    public void setTurbidity(String turbidity) {
        this.turbidity = turbidity;
    }

    /**
     * 濁度
     * @return
     */
    public String getTurbidity() {
        return turbidity;
    }
}
