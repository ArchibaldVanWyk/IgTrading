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
    //Login with username and password and get Session object with oauth token
    /**
     * 
     * @param endpoint
     * @return Session object with user info and authorization token
     */
    public Session login(String endpoint){
        HttpURLConnection connection=null;
        Properties props = new Properties();
        Properties propsVersions = new Properties();
        String key;
        String baseUrl;
        String body;
        StringBuilder responseString = new StringBuilder("");
        JsonObject sessionJson = null;
        OutputStream out=null;
        InputStream in=null;
        try(FileReader propsFile=new FileReader("../trading/properties/IgRest-api.properties");
            FileReader propVersionRdr = new FileReader("../trading/properties/IgRest-api-versions.properties")){
            props.load(propsFile);
            propsVersions.load(propVersionRdr);
            key=props.getProperty("header.X-IG-API-KEY");
            baseUrl=props.getProperty("base-url");
            connection =(HttpURLConnection)(new URL(baseUrl+props.getProperty("session"))).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-IG-API-KEY", key);
            connection.setRequestProperty("VERSION", propsVersions.getProperty("session.POST","3"));
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            body="{\"identifier\":+"+"\""+props.getProperty("username")+"\","+"password:"+"\""+props.getProperty("password")+"\"}";
            out=connection.getOutputStream();
            in = connection.getInputStream();
            out.write(body.getBytes());
            out.flush();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                    throw new RuntimeException("Failed : HTTP error code : "
                            + connection.getResponseCode());
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                String output;
                
                System.out.println("Output from Server .... \n");
                while ((output = br.readLine()) != null) {
                    responseString.append(output);
                }
            }
            try(JsonReader jsonReader = Json.createReader(new StringReader(responseString.toString()))){
                sessionJson = jsonReader.readObject();
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(connection!=null){connection.disconnect();}
            try{if(in!=null){in.close();}
            if(out!=null){out.close();}
            }catch(IOException e){
                Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, e);
            }
            
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
        
        return session;
    }
    
    public void logout(){
        HttpURLConnection connection=null;
        Properties props = new Properties();
        Properties propsVersions = new Properties();
        StringBuilder responseString = new StringBuilder("");
        String key;
        String baseUrl;
        try(FileReader propsFile=new FileReader("../trading/properties/IgRest-api.properties");
            FileReader propVersionRdr = new FileReader("../trading/properties/IgRest-api-versions.properties"))
        {
            props.load(propsFile);
            propsVersions.load(propVersionRdr);
            key=props.getProperty("header.X-IG-API-KEY");
            baseUrl=props.getProperty("base-url");
            connection =(HttpURLConnection)(new URL(baseUrl+props.getProperty("session"))).openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("X-IG-API-KEY", key);
            connection.setRequestProperty("VERSION", propsVersions.getProperty("session.DELETE","1"));
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            InputStream in = connection.getInputStream();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.connect();
                String output;
                while ((output = br.readLine()) != null) {
                    responseString.append(output);
                }
                if(responseString.length()>1){System.out.println(responseString.toString());}
            }
        }
        catch(IOException ex){
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(connection!=null){connection.disconnect();}
        }
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
        HttpURLConnection connection;
        String key;
        String baseUrl;
        String body;
        StringBuilder responseString = new StringBuilder("");
        JsonObject sessionJson = null;
        OutputStream out=null;
        InputStream in=null;
        try(FileReader propsFile=new FileReader("../trading/properties/IgRest-api.properties");
            FileReader propVersionRdr = new FileReader("../trading/properties/IgRest-api-versions.properties")){
            props.load(propsFile);
            propsVersions.load(propVersionRdr);
            key=props.getProperty("header.X-IG-API-KEY");
            baseUrl=props.getProperty("base-url");
            connection =(HttpURLConnection)(new URL(baseUrl+props.getProperty(endpoint))).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(method);
            connection.setRequestProperty("X-IG-API-KEY", key);
            connection.setRequestProperty("VERSION", propsVersions.getProperty("session.POST","3"));
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
}