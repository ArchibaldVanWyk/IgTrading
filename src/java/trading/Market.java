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
public class Market {
    
    
    private DealingRules dealingRules;
    private Instrument instrument;
    private Snapshot snapshot;

    public Snapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }
    

    public DealingRules getDealingRules() {
        return dealingRules;
    }

    public void setDealingRules(DealingRules dealingRules) {
        this.dealingRules = dealingRules;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
    
}
