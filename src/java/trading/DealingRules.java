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
public class DealingRules implements Serializable{
    
    
    private MarketOrderPreference marketOrderPref;
    private TrailingStopsPref trailingStopsPref;
    private double maxStopOrLimitDistance;
    private DealingRuleUnit maxStopOrLimitDistance_unit;
    private double minControlledRiskStopDistance;
    private DealingRuleUnit minControlledRiskStopDistance_unit;
    private double minDealSize;
    private DealingRuleUnit minDealSize_unit;
    private double minNormalStopOrLimitDistance;
    private DealingRuleUnit minNormalStopOrLimitDistance_unit;
    private double minStepDistance;
    private DealingRuleUnit minStopDistance_unit;

    public DealingRuleUnit getMaxStopOrLimitDistance_unit() {
        return maxStopOrLimitDistance_unit;
    }

    public void setMaxStopOrLimitDistance_unit(DealingRuleUnit maxStopOrLimitDistance_unit) {
        this.maxStopOrLimitDistance_unit = maxStopOrLimitDistance_unit;
    }

    public DealingRuleUnit getMinControlledRiskStopDistance_unit() {
        return minControlledRiskStopDistance_unit;
    }

    public void setMinControlledRiskStopDistance_unit(DealingRuleUnit minControlledRiskStopDistance_unit) {
        this.minControlledRiskStopDistance_unit = minControlledRiskStopDistance_unit;
    }

    public DealingRuleUnit getMinDealSize_unit() {
        return minDealSize_unit;
    }

    public void setMinDealSize_unit(DealingRuleUnit minDealSize_unit) {
        this.minDealSize_unit = minDealSize_unit;
    }

    public DealingRuleUnit getMinNormalStopOrLimitDistance_unit() {
        return minNormalStopOrLimitDistance_unit;
    }

    public void setMinNormalStopOrLimitDistance_unit(DealingRuleUnit minNormalStopOrLimitDistance_unit) {
        this.minNormalStopOrLimitDistance_unit = minNormalStopOrLimitDistance_unit;
    }

    public DealingRuleUnit getMinStopDistance_unit() {
        return minStopDistance_unit;
    }

    public void setMinStopDistance_unit(DealingRuleUnit minStopDistance_unit) {
        this.minStopDistance_unit = minStopDistance_unit;
    }

    public TrailingStopsPref getTrailingStopsPref() {
        return trailingStopsPref;
    }

    public void setTrailingStopsPref(TrailingStopsPref trailingStopsPref) {
        this.trailingStopsPref = trailingStopsPref;
    }

    public MarketOrderPreference getMarketOrderPref() {
        return marketOrderPref;
    }

    public void setMarketOrderPref(MarketOrderPreference marketOrderPref) {
        this.marketOrderPref = marketOrderPref;
    }

    public double getMaxStopOrLimitDistance() {
        return maxStopOrLimitDistance;
    }

    public void setMaxStopOrLimitDistance(double maxStopOrLimitDistance) {
        this.maxStopOrLimitDistance = maxStopOrLimitDistance;
    }

    public double getMinControlledRiskStopDistance() {
        return minControlledRiskStopDistance;
    }

    public void setMinControlledRiskStopDistance(double minControlledRiskStopDistance) {
        this.minControlledRiskStopDistance = minControlledRiskStopDistance;
    }

    public double getMinDealSize() {
        return minDealSize;
    }

    public void setMinDealSize(double minDealSize) {
        this.minDealSize = minDealSize;
    }

    public double getMinNormalStopOrLimitDistance() {
        return minNormalStopOrLimitDistance;
    }

    public void setMinNormalStopOrLimitDistance(double minNormalStopOrLimitDistance) {
        this.minNormalStopOrLimitDistance = minNormalStopOrLimitDistance;
    }

    public double getMinStepDistance() {
        return minStepDistance;
    }

    public void setMinStepDistance(double minStopDistance) {
        this.minStepDistance = minStopDistance;
    }
    
    
    public static enum MarketOrderPreference{
        /**
         * market orders are allowed for the account type and instrument, and the user has
         * enabled marker orders in their preferences but decided that the default is off
         */
        AVAILABLE_DEFAULT_OFF,
        AVAILBLE_DEFAULT_ON,
        NOT_AVAILABLE
    }
    public static enum DealingRuleUnit{
        PERCENTAGE,POINTS
    }
    public static enum TrailingStopsPref{
        AVAILABLE,NOT_AVAILABLE
    }
}
