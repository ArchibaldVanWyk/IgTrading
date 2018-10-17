/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trading;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Archie
 */
public class Instrument implements Serializable{
    
    
    private String chartCode;
    private String contractSize;
    private boolean controlledRiskAllowed;
    private String country;
    private List<Currency> currencies;
    private String epic;
    private String expiry;
    private ExpiryDetails expiryDetials;
    private boolean forceOpenAllowed;
    private LimitedRiskPremium limitedRiskPrem;
    private double lotSize;
    private List<MarginDepositBand> marginDepBands;
    private double marginFactor;
    private Unit marginFactorUnit;
    private String marketId;
    private String name;
    private String newsCode;
    private String onePipMeans;
    private OpeningHours openingHours;
    private RolloverDetails rolloverDetails;
    private SlippageFactor slippageFactor;
    private List<String> specialInfo;
    private double sprintMarketsMaximumExpiryTime;
    private double sprintMarketsMinimumExpiryTime;
    private boolean stopLimitsAllowed;
    private boolean streamingPricesAvailable;
    private Unit tradeSizeUnit;
    private String valueOfOnePip;
    private InstrumentType type;
    private double scalingFactor;

    public InstrumentType getType() {
        return type;
    }

    public double getScalingFactor() {
        return scalingFactor;
    }

    public void setScalingFactor(double scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    public void setType(String type) {
        this.type=Arrays.stream(InstrumentType.values()).filter(c->c.toString().equals(type)).findFirst().get();
    }

    public String getChartCode() {
        return chartCode;
    }

    public void setChartCode(String chartCode) {
        this.chartCode = chartCode;
    }

    public String getContractSize() {
        return contractSize;
    }

    public void setContractSize(String contractSize) {
        this.contractSize = contractSize;
    }

    public boolean isControlledRiskAllowed() {
        return controlledRiskAllowed;
    }

    public void setControlledRiskAllowed(boolean controlledRiskAllowed) {
        this.controlledRiskAllowed = controlledRiskAllowed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public String getEpic() {
        return epic;
    }

    public void setEpic(String epic) {
        this.epic = epic;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public ExpiryDetails getExpiryDetials() {
        return expiryDetials;
    }

    public void setExpiryDetials(ExpiryDetails expiryDetials) {
        this.expiryDetials = expiryDetials;
    }

    public boolean isForceOpenAllowed() {
        return forceOpenAllowed;
    }

    public void setForceOpenAllowed(boolean forceOpenAllowed) {
        this.forceOpenAllowed = forceOpenAllowed;
    }

    public LimitedRiskPremium getLimitedRiskPrem() {
        return limitedRiskPrem;
    }

    public void setLimitedRiskPrem(LimitedRiskPremium limitedRiskPrem) {
        this.limitedRiskPrem = limitedRiskPrem;
    }

    public double getLotSize() {
        return lotSize;
    }

    public void setLotSize(double lotSize) {
        this.lotSize = lotSize;
    }

    public List<MarginDepositBand> getMarginDepBands() {
        return marginDepBands;
    }

    public void setMarginDepBands(List<MarginDepositBand> marginDepBands) {
        this.marginDepBands = marginDepBands;
    }

    public double getMarginFactor() {
        return marginFactor;
    }

    public void setMarginFactor(double marginFactor) {
        this.marginFactor = marginFactor;
    }

    public Unit getMarginFactorUnit() {
        return marginFactorUnit;
    }

    public void setMarginFactorUnit(Unit marginFactorUnit) {
        this.marginFactorUnit = marginFactorUnit;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNewsCode() {
        return newsCode;
    }

    public void setNewsCode(String newsCode) {
        this.newsCode = newsCode;
    }

    public String getOnePipMeans() {
        return onePipMeans;
    }

    public void setOnePipMeans(String onePipMeans) {
        this.onePipMeans = onePipMeans;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public RolloverDetails getRolloverDetails() {
        return rolloverDetails;
    }

    public void setRolloverDetails(RolloverDetails rolloverDetails) {
        this.rolloverDetails = rolloverDetails;
    }

    public SlippageFactor getSlippageFactor() {
        return slippageFactor;
    }

    public void setSlippageFactor(SlippageFactor slippageFactor) {
        this.slippageFactor = slippageFactor;
    }

    public List<String> getSpecialInfo() {
        return specialInfo;
    }

    public void setSpecialInfo(List<String> specialInfo) {
        this.specialInfo = specialInfo;
    }

    public double getSprintMarketsMaximumExpiryTime() {
        return sprintMarketsMaximumExpiryTime;
    }

    public void setSprintMarketsMaximumExpiryTime(double sprintMarketsMaximumExpiryTime) {
        this.sprintMarketsMaximumExpiryTime = sprintMarketsMaximumExpiryTime;
    }

    public double getSprintMarketsMinimumExpiryTime() {
        return sprintMarketsMinimumExpiryTime;
    }

    public void setSprintMarketsMinimumExpiryTime(double sprintMarketsMinimumExpiryTime) {
        this.sprintMarketsMinimumExpiryTime = sprintMarketsMinimumExpiryTime;
    }

    public boolean isStopLimitsAllowed() {
        return stopLimitsAllowed;
    }

    public void setStopLimitsAllowed(boolean stopLimitsAllowed) {
        this.stopLimitsAllowed = stopLimitsAllowed;
    }

    public boolean isStreamingPricesAvailable() {
        return streamingPricesAvailable;
    }

    public void setStreamingPricesAvailable(boolean streamingPricesAvailable) {
        this.streamingPricesAvailable = streamingPricesAvailable;
    }

    public Unit getTradeSizeUnit() {
        return tradeSizeUnit;
    }

    public void setTradeSizeUnit(Unit tradeSizeUnit) {
        this.tradeSizeUnit = tradeSizeUnit;
    }

    public String getValueOfOnePip() {
        return valueOfOnePip;
    }

    public void setValueOfOnePip(String valueOfOnePip) {
        this.valueOfOnePip = valueOfOnePip;
    }
    
    
    public static class SlippageFactor{
        private Unit unit;
        private double value;

        public Unit getUnit() {
            return unit;
        }

        public void setUnit(Unit unit) {
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
        
    }
    public static class RolloverDetails{
        private Unit unit;
        private double value;

        public Unit getUnit() {
            return unit;
        }

        public void setUnit(Unit unit) {
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
        
    }
    public static class OpeningHours{
        
        private List<MarketTime> marketTimes;

        public List<MarketTime> getMarketTimes() {
            return marketTimes;
        }

        public void setMarketTimes(List<MarketTime> marketTimes) {
            this.marketTimes = marketTimes;
        }
        
        
        public static class MarketTime{
            private String openTime;
            private String closeTime;

            public String getOpenTime() {
                return openTime;
            }

            public void setOpenTime(String openTime) {
                this.openTime = openTime;
            }

            public String getCloseTime() {
                return closeTime;
            }

            public void setCloseTime(String closeTime) {
                this.closeTime = closeTime;
            }
            
        }
    }
    
    public static class MarginDepositBand{
        private String currency;
        private double margin;
        private double bandMax;
        private double bandMin;

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public double getMargin() {
            return margin;
        }

        public void setMargin(double margin) {
            this.margin = margin;
        }

        public double getBandMax() {
            return bandMax;
        }

        public void setBandMax(double bandMax) {
            this.bandMax = bandMax;
        }

        public double getBandMin() {
            return bandMin;
        }

        public void setBandMin(double bandMin) {
            this.bandMin = bandMin;
        }
        
    }
    
    public static class LimitedRiskPremium{
        private Unit unit;
        private double value;

        public Unit getUnit() {
            return unit;
        }

        public void setUnit(Unit unit) {
            this.unit = unit;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
        
    }
    public static class ExpiryDetails{
        private String lastDealingDate;
        private String settlementInfo;

        public String getLastDealingDate() {
            return lastDealingDate;
        }

        public void setLastDealingDate(String lastDealingDate) {
            this.lastDealingDate = lastDealingDate;
        }

        public String getSettlementInfo() {
            return settlementInfo;
        }

        public void setSettlementInfo(String settlementInfo) {
            this.settlementInfo = settlementInfo;
        }
        
    }
    
}
