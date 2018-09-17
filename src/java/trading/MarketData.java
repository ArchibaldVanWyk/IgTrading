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
public class MarketData {
    
    private double bid;
    private double delayTime;
    private String epic;
    private String exchangeId;
    private String expiry;
    private double high;
    private String instrumentName;
    private InstrumentType type;
    private double lotSize;
    private double low;
    private MarketStatus status;
    private double netChange;
    private double offer;
    private double percentageChange;
    private double scalingFactor;
    private boolean streamingPricesAvailable;
    private String updateTime;
    private String updateTimeUTC;
	public double getBid() {
		return bid;
	}
	public void setBid(double bid) {
		this.bid = bid;
	}
	public double getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(double delayTime) {
		this.delayTime = delayTime;
	}
	public String getEpic() {
		return epic;
	}
	public void setEpic(String epic) {
		this.epic = epic;
	}
	public String getExchangeId() {
		return exchangeId;
	}
	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}
	public String getExpiry() {
		return expiry;
	}
	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public InstrumentType getType() {
		return type;
	}
	public void setType(InstrumentType type) {
		this.type = type;
	}
	public double getLotSize() {
		return lotSize;
	}
	public void setLotSize(double lotSize) {
		this.lotSize = lotSize;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public MarketStatus getStatus() {
		return status;
	}
	public void setStatus(MarketStatus status) {
		this.status = status;
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
	public double getScalingFactor() {
		return scalingFactor;
	}
	public void setScalingFactor(double scalingFactor) {
		this.scalingFactor = scalingFactor;
	}
	public boolean isStreamingPricesAvailable() {
		return streamingPricesAvailable;
	}
	public void setStreamingPricesAvailable(boolean streamingPricesAvailable) {
		this.streamingPricesAvailable = streamingPricesAvailable;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateTimeUTC() {
		return updateTimeUTC;
	}
	public void setUpdateTimeUTC(String updateTimeUTC) {
		this.updateTimeUTC = updateTimeUTC;
	}
    
    
    
}
