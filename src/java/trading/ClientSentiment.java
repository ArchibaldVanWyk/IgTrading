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

public class ClientSentiment {
    
    private String marketId;
    private double longPositionPercentage;
    private double shortPositionPercentage;

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public double getLongPositionPercentage() {
        return longPositionPercentage;
    }

    public void setLongPositionPercentage(double longPositionPercentage) {
        this.longPositionPercentage = longPositionPercentage;
    }

    public double getShortPositionPercentage() {
        return shortPositionPercentage;
    }

    public void setShortPositionPercentage(double shortPositionPercentage) {
        this.shortPositionPercentage = shortPositionPercentage;
    }
    
}
