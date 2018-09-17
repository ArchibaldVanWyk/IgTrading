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
public abstract class Price {
    
    protected double bid;
    protected double ask;
    protected double lastTraded;
    protected long lastTradedVolume;
    protected String snapshotTime;
    protected String snapshotTimeUTC;

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk(double ask) {
        this.ask = ask;
    }

    public double getLastTraded() {
        return lastTraded;
    }

    public void setLastTraded(double lastTraded) {
        this.lastTraded = lastTraded;
    }

    public long getLastTradedVolume() {
        return lastTradedVolume;
    }

    public void setLastTradedVolume(long lastTradedVolume) {
        this.lastTradedVolume = lastTradedVolume;
    }

    public String getSnapshotTime() {
        return snapshotTime;
    }

    public void setSnapshotTime(String snapshotTime) {
        this.snapshotTime = snapshotTime;
    }

    public String getSnapshotTimeUTC() {
        return snapshotTimeUTC;
    }

    public void setSnapshotTimeUTC(String snapshotTimeUTC) {
        this.snapshotTimeUTC = snapshotTimeUTC;
    }
    
    
    
}
