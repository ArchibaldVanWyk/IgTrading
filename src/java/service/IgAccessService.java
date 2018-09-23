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
        HttpURLConnection connection=createConnection(method, endpoint);
        String body;
        StringBuilder responseString = new StringBuilder("");
        JsonObject sessionJson = null;
        try {
            body="{\"identifier\":+"+"\""+username+"\","+"password:"+"\""+password+"\"}";

            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "+ connection.getResponseCode());
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    OutputStream out = connection.getOutputStream();
                    JsonReader jsonReader = Json.createReader(new StringReader(responseString.toString()))) {
                String output;
                out.write(body.getBytes());
                out.flush();
                while ((output = br.readLine()) != null) {
                    responseString.append(output);
                }
                sessionJson = jsonReader.readObject();
            } catch (IOException ex) {
                Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(sessionJson!=null){
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
        } catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            connection.disconnect();
        }
        return session;
    }
    
    public void logout(){
        String method ="DELETE";
        String enpoint = "session";
        StringBuilder responseString =new StringBuilder("");
        HttpURLConnection connection=createConnection(method, enpoint);
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
    
    //return connection to specified endpoint for the method specified
    private HttpURLConnection createConnection(String method,String endpoint){
        Properties props = new Properties();
        Properties propsVersions = new Properties();
        HttpURLConnection connection = null;
        String key;
        String baseUrl;
        try(FileReader propsFile=new FileReader("../trading/properties/IgRest-api.properties");
            FileReader propVersionRdr = new FileReader("../trading/properties/IgRest-api-versions.properties")){
            props.load(propsFile);
            propsVersions.load(propVersionRdr);
            key=props.getProperty("header.X-IG-API-KEY");
            baseUrl=props.getProperty("base-url");
            connection =(HttpURLConnection)(new URL(baseUrl+props.getProperty(endpoint))).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(method.toUpperCase().trim());
            connection.setRequestProperty("X-IG-API-KEY", key);
            connection.setRequestProperty("VERSION", propsVersions.getProperty(endpoint.replace('/', '.').replace("{","").replace("{","")+"."+method.toUpperCase().trim(),"3"));
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return connection;
    }
    
    
}