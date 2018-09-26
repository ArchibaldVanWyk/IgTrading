/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.StringReader;
import java.net.HttpURLConnection;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import trading.OAuthToken;
import trading.Session;

/**
 *
 * @author Archie
 */
@RequestScoped
@Path("/rest")
public class IgAccessService {
    
    @Inject SessionManager sm;
    @Inject ConnectionManager cm;
    //Login with username and password and get Session object with oauth token

    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public String login(String jsonString){
        JsonObject json = Json.createReader(new StringReader(jsonString)).readObject();
        String sessionJson=cm.createConnection("POST","/session",json);
        return sessionJson;
    }
    
//    public void logout(){
//        String method ="DELETE";
//        String enpoint = "/session";
//        HttpURLConnection connection=cm.createConnection(method, enpoint);
//        String json = cm.send(null, connection);
//        System.out.println(json);
//    }
    
//    public Session getSession(){
//        String method = "GET";
//        String endpoint ="/session";
//        HttpURLConnection connection=cm.createConnection(method, endpoint);
//        String json = cm.send(null, connection);
//        return sm.retrieveSession(json);
//    }
    
//    public String getEncryptionKey(){
//        String endpoint ="/session/encryptionKey";
//        String method ="GET";
//        String json = cm.send(null, cm.createConnection(method, endpoint));
//        Session session = this.getSession();
//        session.setEncryptionKey(json.getString("encryptionKey"));
//        session.setTimeStamp(json.getInt("timeStamp"));
//        return session.getEncryptionKey();
//    }
    
//    public void refreshToken(){
//        String endpoint ="/session/resfresh-token";
//        String method ="POST";
//        Session session = getSession();
//        OAuthToken auth = session.getOauthToken();
//        String json = cm.send("{\"refresh_token\":"+"\""+auth.getRefresh_token()+"\""+"}", cm.createConnection(method, endpoint));
//        auth.setRefresh_token(json.getString("refresh_token"));
//        auth.setAccess_token(json.getString("access_token"));
//        auth.setExpires_in(json.getString("expires_in"));
//        auth.setScope(json.getString("scope"));
//        auth.setToken_type(json.getString("token_type"));
//    }
    
    
}