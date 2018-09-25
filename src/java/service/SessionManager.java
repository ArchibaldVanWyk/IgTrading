/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.HashMap;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.JsonObject;
import trading.OAuthToken;
import trading.Session;

/**
 *
 * @author Archie
 */
@Singleton
public class SessionManager {
    
    
    private static final HashMap<Long,List<Session>> SESSIONS = new HashMap<>();
    //see if a session is open for accountId
    public synchronized boolean hasSession(Long accountId){
        return SESSIONS.containsKey(accountId);
    }
    //retrieve the last session addedd/created
    public Session retrieveSession(JsonObject sessionJson){
        Session session;
        long id = Long.parseLong(sessionJson.getJsonString("accountId").getString());
        if(hasSession(id)&&SESSIONS.get(id).size()>0){
            session=SESSIONS.get(id).get(SESSIONS.get(id).size()-1);
        }
        else{
            session=createSession(sessionJson);
        }
        return session;
    }
    public Session createSession(JsonObject sessionJson){
        Session session=null;
        if(sessionJson!=null){
            session=new Session();
            session.setAccountId(sessionJson.getJsonString("accountId").getString());
            session.setClientId(sessionJson.getJsonString("clientId").getString());
            session.setTimezoneOffset(sessionJson.getJsonNumber("timezoneOffset").doubleValue());
            session.setLightStreamerEndpoint(sessionJson.getJsonString("lightstreamerEndpoint").getString());
            session.setOauthToken(new OAuthToken(sessionJson.getJsonObject("oauthToken").getJsonString("access_token").getString(),
                    sessionJson.getJsonObject("oauthToken").getJsonString("refresh_token").getString(),
                    sessionJson.getJsonObject("oauthToken").getJsonString("scope").getString(),
                    sessionJson.getJsonObject("oauthToken").getJsonString("token_type").getString(),
                    sessionJson.getJsonObject("oauthToken").getJsonString("expires_in").getString()));
        }
        else{
            throw new RuntimeException("The funcking sessionJson is null");
        }
        return session;
    }
    public HashMap<Long, List<Session>> getSessions() {
        return SESSIONS;
    }
    
}
