/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import trading.OAuthToken;
import trading.Session;

/**
 *
 * @author Archie
 */
@RequestScoped
public class IgAccessService {
    
    @Inject Session session;
    @Inject SessionManager sm;
    @Inject ConnectionManager cm;
    //Login with username and password and get Session object with oauth token
    /**
     * 
     * @param username
     * @param password
     * @return Session object with user info and authorization token
     */
    public Session login(String username,String password){
        String method = "POST";
        String endpoint ="session";
        HttpURLConnection connection=cm.createConnection(method, endpoint);
        JsonObject sessionJson = cm.send("{\"identifier\":+"+"\""+username+"\","+"password:"+"\""+password+"\"}",
                connection);
        return sm.createSession(sessionJson);
    }
    
    public void logout(){
        String method ="DELETE";
        String enpoint = "session";
        StringBuilder responseString =new StringBuilder("");
        HttpURLConnection connection=cm.createConnection(method, enpoint);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            connection.connect();
            String output;
            while ((output = br.readLine()) != null) {
                responseString.append(output);
            }
            if(responseString.length()>1){System.out.println(responseString.toString());}
        } catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            connection.disconnect();
        }
        System.out.println(responseString);
    }
    
    public void get(){
        JsonObject sessionJson = null;
        
        session.setAccountId(sessionJson.getJsonString("accountId").getString());
        session.setClientId(sessionJson.getJsonString("clientId").getString());
        session.setTimezoneOffset(sessionJson.getJsonNumber("timezoneOffset").doubleValue());
        session.setLightStreamerEndpoint(sessionJson.getJsonString("lightstreamerEndpoint").getString());
        
    }
    

    
    
}