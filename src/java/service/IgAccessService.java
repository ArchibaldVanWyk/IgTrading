/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.StringReader;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import trading.Session;

/**
 *
 * @author Archie
 */
@RequestScoped
@Path("/rest")
public class IgAccessService {
    

    @Inject ConnectionManager cm;
    @Inject SessionManager sm;
    @PersistenceContext(unitName = "IgTradingPU")
    private EntityManager em;
    //Login with username and password and get Session object with oauth token

    @POST
    @Path("login")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public String login(String jsonString){
        JsonObject json = Json.createReader(new StringReader(jsonString)).readObject();
        if(json==null){throw new RuntimeException("no json created");}
        String sessionJson=cm.createConnection("POST","/session",json);
        if(sessionJson.length()<2){throw new RuntimeException("no sessionJson");}
        Session session = sm.createSession(Json.createReader(new StringReader(sessionJson)).readObject());
        //em.persist(session);
        sm.getSessions().clear();
        sm.getSessions().put(session.getAccountId(), session);
        return sessionJson;
    }
    
    @POST
    @Path("logout")
    @Consumes({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public String logout(String jsonString){
        String res=cm.createConnection("DELETE","/session",null);
        return res==null?"logged out":res;
    }
    
    public Session getSession(){
        String method = "GET";
        String endpoint ="/session";
        String json=cm.createConnection(method,endpoint,null);
        return sm.retrieveSession(Json.createReader(new StringReader(json)).readObject());
    }
    
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
    @GET
    @Path("marketnavigation")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public String marketNavigation(String in){
        String resp = cm.createConnection("GET", "/positions", null);
        if(resp==null||resp.length()<3){resp="no data";}
        return resp;
    }
    @GET
    @Path("token")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public String token(){
        String resp = cm.token();
        if(resp==null||resp.length()<3){resp="no data";}
        return resp;
    }
    
    
}