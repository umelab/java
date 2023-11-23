package com.umelab.selenium;

/**
 * 湖水レベルモデル
 *   - 水位
 *   - 放水量
 *   - 雨量
 */
public class BiwaFlowLevelModel {
    private String currentTime;
    private String level;
    private String outflow;
    private String rainfall;

    public BiwaFlowLevelModel() {
    
    }

    public void setCurrentTime(String currentTime){
       this.currentTime = currentTime;
    }

    public String getCurrentTime(){
       return currentTime;
    }

    /**
     * 放水量
     * @param flow
     */
    public void setOutFlow(String flow) {
        this.outflow = flow;
    }
    
    /**
     * 放水量
     * @return flow
     */
    public String getOutFlow() {
        return outflow;
    } 

    /**
     * 水位
     * @param level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 水位
     * @return level
     */
    public String getLevel() {
        return level;
    }

    /**
     * 雨量
     * @param rainfall
     */
    public void setRainFall(String rainfall) {
        this.rainfall = rainfall;
    }

    /**
     * 雨量
     * @return rainfall
     */
    public String getRainFall() {
        return rainfall;
    }

}
