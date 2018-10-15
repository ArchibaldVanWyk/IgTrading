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
public class Market implements Serializable{
    
    
    private DealingRules dealingRules;
    private Instrument instrument;
    private Snapshot snapshot;
    private String nodeId;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

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
