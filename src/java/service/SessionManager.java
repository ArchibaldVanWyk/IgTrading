/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.HashMap;
import java.util.List;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import trading.OAuthToken;
import trading.Session;

/**
 *
 * @author Archie
 */
@Singleton(name="sessionManager")
//@DependsOn({"connectionmanager"})
public class SessionManager extends AbstractService{
    
    
    private final HashMap<String,Session> SESSIONS = new HashMap<>();
    private String sessionKey = "";
    
    //retrieve the last session addedd/created
    public Session retrieveSession(JsonObject sessionJson){
        Session session;
        long id = Long.parseLong(sessionJson.getJsonString("accountId").getString());
        if(SESSIONS.containsKey(id)){
            session=SESSIONS.get(id);
        }
        else{
            session=createSession(sessionJson);
        }
        return session;
    }
    public Session createSession(JsonObject sessionJson){
        Session session=null;
        if(sessionJson!=null&&sessionJson.toString().contains("accountId")){
            session=new Session();
            session.setAccountId(
                    sessionJson.getString("accountId"));
            session.setClientId(sessionJson.getString("clientId"));
            session.setTimezoneOffset(sessionJson.getJsonNumber("timezoneOffset").doubleValue());
            session.setLightStreamerEndpoint(sessionJson.getString("lightstreamerEndpoint"));
            session.setOauthToken(new OAuthToken(sessionJson.getJsonObject("oauthToken").getString("access_token"),
                    sessionJson.getJsonObject("oauthToken").getString("refresh_token"),
                    sessionJson.getJsonObject("oauthToken").getString("scope"),
                    sessionJson.getJsonObject("oauthToken").getString("token_type"),
                    sessionJson.getJsonObject("oauthToken").getString("expires_in")));
        }
        else{
            throw new RuntimeException("The funcking sessionJson is null");
        }
        return session;
    }
    public HashMap<String, Session> getSessions() {
        return SESSIONS;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
    
}
