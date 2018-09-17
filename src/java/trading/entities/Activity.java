/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trading.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import trading.Deal;

/**
 *
 * @author Archie
 */
@Entity
@XmlRootElement
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String _date;
    private String dealId;
    private String description;
    @OneToMany
    private List<ClienAction> actions;
    private String currency;
    private String dealReference;
    private String dealDirection;
    private Channel channel;
    private Type type;
    private String goodTillDate;
    private boolean gauranteedStop;
    private int _level;
    private double limitDistance;
    private double limitLevel;
    private String marketName;
    private double _size;
    private double stopDistance;
    private double stopLevel;
    private double trailingStep;
    private double trailingStopDistance;
    private String epic;
    private String _period;//same as expiry
    private String pagingNext;
    private int pagingSize;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String _date) {
        this._date = _date;
    }

    public int getLevel() {
        return _level;
    }

    public void setLevel(int _level) {
        this._level = _level;
    }

    public double getSize() {
        return _size;
    }

    public void setSize(double _size) {
        this._size = _size;
    }

    public String getPeriod() {
        return _period;
    }

    public void setPeriod(String _period) {
        this._period = _period;
    }

    public String getPagingNext() {
        return pagingNext;
    }

    public void setPagingNext(String pagingNext) {
        this.pagingNext = pagingNext;
    }

    public int getPagingSize() {
        return pagingSize;
    }

    public void setPagingSize(int pagingSize) {
        this.pagingSize = pagingSize;
    }

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<ClienAction> getActions() {
        return actions;
    }

    public void setActions(List<ClienAction> actions) {
        this.actions = actions;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDealReference() {
        return dealReference;
    }

    public void setDealReference(String dealReference) {
        this.dealReference = dealReference;
    }

    public String getDealDirection() {
        return dealDirection;
    }

    public void setDealDirection(String dealDirection) {
        this.dealDirection = dealDirection;
    }

    public String getGoodTillDate() {
        return goodTillDate;
    }

    public void setGoodTillDate(String goodTillDate) {
        this.goodTillDate = goodTillDate;
    }

    public boolean isGauranteedStop() {
        return gauranteedStop;
    }

    public void setGauranteedStop(boolean gauranteedStop) {
        this.gauranteedStop = gauranteedStop;
    }

    public double getLimitDistance() {
        return limitDistance;
    }

    public void setLimitDistance(double limitDistance) {
        this.limitDistance = limitDistance;
    }

    public double getLimitLevel() {
        return limitLevel;
    }

    public void setLimitLevel(double limitLevel) {
        this.limitLevel = limitLevel;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public double getStopDistance() {
        return stopDistance;
    }

    public void setStopDistance(double stopDistance) {
        this.stopDistance = stopDistance;
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

    public String getEpic() {
        return epic;
    }

    public void setEpic(String epic) {
        this.epic = epic;
    }
    
    static enum Type {EDIT_STOP_AND_LIMIT,POSITION,SYSTEM,WORKING_ORDER};
    static enum Channel  {DEALER,MOBILE,PUBLIC_FIX_API,PUBLIC_WEB_API,SYSTEM,WEB};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Activity)) {
            return false;
        }
        Activity other = (Activity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.interlocr.trading.Activity[ id=" + id + " ]";
    }
    
}
