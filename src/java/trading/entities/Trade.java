/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trading.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Archie
 */
@Entity
@XmlRootElement
public class Trade implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private boolean cashTransaction;
    private String closeLevel;
    private String currency;
    private String _date;
    private String dateUtc;
    private String instrumentName;
    private String openDateUtc;
    private String openLevel;
    private String _period;//another name for expiry
    private String profitAndLoss;
    private String reference;
    private String _size;
    private String transactionType;

    public boolean isCashTransaction() {
        return cashTransaction;
    }

    public void setCashTransaction(boolean cashTransaction) {
        this.cashTransaction = cashTransaction;
    }

    public String getCloseLevel() {
        return closeLevel;
    }

    public void setCloseLevel(String closeLevel) {
        this.closeLevel = closeLevel;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String _date) {
        this._date = _date;
    }

    public String getDateUtc() {
        return dateUtc;
    }

    public void setDateUtc(String dateUtc) {
        this.dateUtc = dateUtc;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getOpenDateUtc() {
        return openDateUtc;
    }

    public void setOpenDateUtc(String openDateUtc) {
        this.openDateUtc = openDateUtc;
    }

    public String getOpenLevel() {
        return openLevel;
    }

    public void setOpenLevel(String openLevel) {
        this.openLevel = openLevel;
    }

    public String getPeriod() {
        return _period;
    }

    public void setPeriod(String _period) {
        this._period = _period;
    }

    public String getProfitAndLoss() {
        return profitAndLoss;
    }

    public void setProfitAndLoss(String profitAndLoss) {
        this.profitAndLoss = profitAndLoss;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSize() {
        return _size;
    }

    public void setSize(String _size) {
        this._size = _size;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    
    
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
        if (!(object instanceof Trade)) {
            return false;
        }
        Trade other = (Trade) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.interlocr.trading.Transaction[ id=" + id + " ]";
    }
    
}
