/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;

/**
 *
 * @author Archie
 */
@Singleton
public class ConnectionManager {
    
    private List<HttpURLConnection> connections = new ArrayList<>(10);

    public List<HttpURLConnection> getConnections() {
        return connections;
    }

    public void setConnections(List<HttpURLConnection> connections) {
        this.connections = connections;
    }
    
    
}
