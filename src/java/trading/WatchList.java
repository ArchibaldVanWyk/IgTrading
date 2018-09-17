/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trading;

import java.util.List;

/**
 *
 * @author Archie
 */
public class WatchList {
    
    private boolean defaultSystemWatchlist;
    private boolean deletable;
    private boolean editable;
    private String Id;
    private String name;
    private List<Market> markets;

    public boolean isDefaultSystemWatchlist() {
        return defaultSystemWatchlist;
    }

    public void setDefaultSystemWatchlist(boolean defaultSystemWatchlist) {
        this.defaultSystemWatchlist = defaultSystemWatchlist;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
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

    public List<Market> getMarkets() {
        return markets;
    }

    public void setMarkets(List<Market> markets) {
        this.markets = markets;
    }
    
}
