/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trading;


/**
 *
 * @author Archie
 */
public class DealingRules {
    
    
    private MarketOrderPreference marketOrderPref;
    private DealingRuleUnit unit;
    private double maxStopOrLimitDistance;
    private double minControlledRiskStopDistance;
    private double minDealSize;
    private double minNormalStopOrLimitDistance;
    private double minStopDistance;

    public MarketOrderPreference getMarketOrderPref() {
        return marketOrderPref;
    }

    public void setMarketOrderPref(MarketOrderPreference marketOrderPref) {
        this.marketOrderPref = marketOrderPref;
    }

    public DealingRuleUnit getUnit() {
        return unit;
    }

    public void setUnit(DealingRuleUnit unit) {
        this.unit = unit;
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

    public double getMinStopDistance() {
        return minStopDistance;
    }

    public void setMinStopDistance(double minStopDistance) {
        this.minStopDistance = minStopDistance;
    }
    
    
    static enum MarketOrderPreference{
        /**
         * market orders are allowed for the account type and instrument, and the user has
         * enabled marker orders in their preferences but decided that the default is off
         */
        AVAILABLE_DEFAULT_OFF,
        AVAILBLE_DEFAULT_ON,
        NOT_AVAILABLE
    }
    static enum DealingRuleUnit{
        PERCENTAGE,POINTS
    }
    static enum TrailingStopsPref{
        AVAILABLE,NOT_AVAILABLE
    }
}
