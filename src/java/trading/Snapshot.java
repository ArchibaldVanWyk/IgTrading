/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trading;

import java.io.Serializable;
import java.util.Arrays;


/**
 *
 * @author Archie
 */
public class Snapshot implements Serializable{
    
    private double bid;
    private double binaryOdds;
    private double controlledRiskExtraSpread;
    private int decimalPlacesFactor;
    private double delayTime;
    private double high;
    private double low;
    private MarketStatus marketStatus;
    private double netChange;
    private double offer;
    private double percentageChange;
    private String updateTime;
    private String updateTimeUTC;

    public String getUpdateTimeUTC() {
        return updateTimeUTC;
    }

    public void setUpdateTimeUTC(String updateTimeUTC) {
        this.updateTimeUTC = updateTimeUTC;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getBinaryOdds() {
        return binaryOdds;
    }

    public void setBinaryOdds(double binaryOdds) {
        this.binaryOdds = binaryOdds;
    }

    public double getControlledRiskExtraSpread() {
        return controlledRiskExtraSpread;
    }

    public void setControlledRiskExtraSpread(double controlledRiskExtraSpread) {
        this.controlledRiskExtraSpread = controlledRiskExtraSpread;
    }

    public int getDecimalPlacesFactor() {
        return decimalPlacesFactor;
    }

    public void setDecimalPlacesFactor(int decimalPlacesFactor) {
        this.decimalPlacesFactor = decimalPlacesFactor;
    }

    public double getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(double delayTime) {
        this.delayTime = delayTime;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public MarketStatus getMarketStatus() {
        return marketStatus;
    }

    public void setMarketStatus(String marketStatus) {
        this.marketStatus=Arrays.stream(MarketStatus.values()).filter(c->c.toString().equals(marketStatus)).findFirst().get();
    }

    public double getNetChange() {
        return netChange;
    }

    public void setNetChange(double netChange) {
        this.netChange = netChange;
    }

    public double getOffer() {
        return offer;
    }

    public void setOffer(double offer) {
        this.offer = offer;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(double percentageChange) {
        this.percentageChange = percentageChange;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    
    
    
    
}
