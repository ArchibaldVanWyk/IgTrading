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
public class ClienAction implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -4670565497916334887L;
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    private String affectedDealId;
    private ActionType actionType;

    public String getAffectedDealId() {
        return affectedDealId;
    }

    public void setAffectedDealId(String affectedDealId) {
        this.affectedDealId = affectedDealId;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
    static enum Status {
        ACCPTED,REJECTED,UNKNOWN
    };
    static enum ActionType {
        LIMIT_ORDER_AMENDED,
        LIMIT_ORDER_DELETED,
        LIMIT_ORDER_FILLED,
        LIMIT_ORDER_OPENED,
        LIMIT_ORDER_ROLLED,
        POSITION_CLOSED,
        POSITION_DELETED,
        POSITION_OPENED,
        POSITION_PARTIALLY_CLOSED,
        POSITION_ROLLED,
        STOP_LIMIT_AMENDED,
        STOP_ORDER_AMENDED,
        STOP_ORDER_DELETED,
        STOP_ORDER_FILLED,
        STOP_ORDER_OPENED,
        STOP_ORDER_ROLLED,
        UNKNOWN,
        WORKING_ORDER_DELETED
    };
    
}
