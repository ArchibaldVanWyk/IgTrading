/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trading;

import java.io.Serializable;


/**
 *
 * @author Archie
 */
public class Position implements Serializable{
    
    private Market market;
    private double contractSize;
    private boolean controlledRisk;
    private String createdDate;
    private String createdDateUTC;
    private String currency;
    private String dealId;
    private String dealReference;
    private Deal.Direction dealDirection;
    private double level;
    private double limitLevel;
    private double size;
    private double stopLevel;
    private double trailingStep;
    private double trailingStopDistance;

    public Market getMarket() {
        return market;
    }

    public void setMarket(Market market) {
        this.market = market;
    }

    public double getContractSize() {
        return contractSize;
    }

    public void setContractSize(double contractSize) {
        this.contractSize = contractSize;
    }

    public boolean isControlledRisk() {
        return controlledRisk;
    }

    public void setControlledRisk(boolean controlledRisk) {
        this.controlledRisk = controlledRisk;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDateUTC() {
        return createdDateUTC;
    }

    public void setCreatedDateUTC(String createdDateUTC) {
        this.createdDateUTC = createdDateUTC;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getDealReference() {
        return dealReference;
    }

    public void setDealReference(String dealReference) {
        this.dealReference = dealReference;
    }

    public Deal.Direction getDealDirection() {
        return dealDirection;
    }

    public void setDealDirection(Deal.Direction dealDirection) {
        this.dealDirection = dealDirection;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public double getLimitLevel() {
        return limitLevel;
    }

    public void setLimitLevel(double limitLevel) {
        this.limitLevel = limitLevel;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getStopLevel() {
        return stopLevel;
    }

    public void setStopLevel(double stopLevel) {
        this.stopLevel = stopLevel;
    }

    public double getTrailingStep() {
        return trailingStep;
    }

    public void setTrailingStep(double trailingStep) {
        this.trailingStep = trailingStep;
    }

    public double getTrailingStopDistance() {
        return trailingStopDistance;
    }

    public void setTrailingStopDistance(double trailingStopDistance) {
        this.trailingStopDistance = trailingStopDistance;
    }
    
    
}
