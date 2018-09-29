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
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import trading.Deal;
import trading.DealingRules;
import trading.Instrument;
import trading.Market;
import trading.Position;
import trading.Session;
import trading.Snapshot;

/**
 *
 * @author Archie
 */
@RequestScoped
@Path("/rest")
public class IgAccessService {
    
    @Inject Framework fw;
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
        if(sessionJson==null||sessionJson.length()<2){throw new RuntimeException("no sessionJson");}
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
        String resp = cm.createConnection("GET", "/marketnavigation", null);
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
    @GET
    @Path("account")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public String accountInfo(){
        String resp = cm.createConnection("GET", "/accounts", null);
        if(resp==null||resp.length()<3){resp="no data";}
        return resp;
    }
    @GET
    @Path("positions")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public String positions(){
        String resp = cm.createConnection("GET", "/positions", null);
        if(resp==null||resp.length()<3){return "No data";}
        JsonArray jsonArr = Json.createReader(new StringReader(resp)).readArray();
        
        for(int i=0;i<jsonArr.size();i++){
            JsonObject obj = jsonArr.getJsonObject(i);
            JsonObject mktjs = obj.getJsonObject("market");
            JsonObject posjs = obj.getJsonObject("position");
            Market market = new Market();
            
            DealingRules rules = new DealingRules();
            
            Instrument ins = new Instrument();
            
            Snapshot ss = new Snapshot();
            
            Position position = new Position();
            position.setContractSize(posjs.getJsonNumber("contractSize").doubleValue());
            position.setControlledRisk(posjs.getBoolean("controlledRisk"));
            position.setCreatedDate(posjs.getString("createdDate"));
            position.setCreatedDateUTC(posjs.getString("createdDateUTC"));
            position.setCurrency(posjs.getString("currency"));
            position.setDealId(posjs.getString("dealId"));
            position.setDealReference("dealReference ");
            position.setDealDirection(posjs.getString("direction").equals("SELL")?Deal.Direction.SELL:Deal.Direction.BUY);
            position.setLevel(posjs.getJsonNumber("level").doubleValue());
            position.setLimitLevel(posjs.getJsonNumber("limitLevel").doubleValue());
            position.setSize(posjs.getJsonNumber("size").doubleValue());
            position.setStopLevel(posjs.getJsonNumber("stopLevel").doubleValue());
            position.setTrailingStep(posjs.getJsonNumber("trailingStep").doubleValue());
            position.setTrailingStopDistance(posjs.getJsonNumber("trailingStopDistance").doubleValue());
            position.setMarket(market);
            
        }
        
        if(resp==null||resp.length()<3){resp="no data";}
        return resp;
    }
    
}