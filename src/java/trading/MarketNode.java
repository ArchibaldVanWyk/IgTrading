/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trading;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Archie
 */
public class MarketNode implements Serializable{
    
    private List<Market> markets;
    private List<MarketNode> nodes;
    private String Id;
    private String name;
    
    public List<MarketNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<MarketNode> nodes) {
        this.nodes = nodes;
    }

    public List<Market> getMarkets() {
        return markets;
    }

    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
