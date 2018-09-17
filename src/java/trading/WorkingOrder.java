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
public class WorkingOrder {
    
    private String createdDate;
    private String createdDateUTC;
    private String currencyCode;
    private String dealId;
    private Deal.Direction dealDirection;
    private boolean dma;//direct market access
    private String epic;
    private String goodTillDate;
    private String goodTillDateISO;
    private boolean guaranteedStop;
    private double limitDistance;
    private double orderLevel;
    private double orderSize;
    private OrderType type;
    private double stopDistance;
    private MarketData marketData;

    public MarketData getMarketData() {
        return marketData;
    }

    public void setMarketData(MarketData marketData) {
        this.marketData = marketData;
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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public Deal.Direction getDealDirection() {
        return dealDirection;
    }

    public void setDealDirection(Deal.Direction dealDirection) {
        this.dealDirection = dealDirection;
    }

    public boolean isDma() {
        return dma;
    }

    public void setDma(boolean dma) {
        this.dma = dma;
    }

    public String getEpic() {
        return epic;
    }

    public void setEpic(String epic) {
        this.epic = epic;
    }

    public String getGoodTillDate() {
        return goodTillDate;
    }

    public void setGoodTillDate(String goodTillDate) {
        this.goodTillDate = goodTillDate;
    }

    public String getGoodTillDateISO() {
        return goodTillDateISO;
    }

    public void setGoodTillDateISO(String goodTillDateISO) {
        this.goodTillDateISO = goodTillDateISO;
    }

    public boolean isGuaranteedStop() {
        return guaranteedStop;
    }

    public void setGuaranteedStop(boolean guaranteedStop) {
        this.guaranteedStop = guaranteedStop;
    }

    public double getLimitDistance() {
        return limitDistance;
    }

    public void setLimitDistance(double limitDistance) {
        this.limitDistance = limitDistance;
    }

    public double getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(double orderLevel) {
        this.orderLevel = orderLevel;
    }

    public double getOrderSize() {
        return orderSize;
    }

    public void setOrderSize(double orderSize) {
        this.orderSize = orderSize;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public double getStopDistance() {
        return stopDistance;
    }

    public void setStopDistance(double stopDistance) {
        this.stopDistance = stopDistance;
    }
    
    
    
    
    static enum TimeInForce{
        GOOD_TILL_CANCELLED,GOOD_TILL_DATE
    }
}
