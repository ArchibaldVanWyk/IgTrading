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
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author Archie
 */
@Singleton
public class ConnectionManager {
    
    private static final HashMap<String,List<HttpURLConnection>> CONNECTIONS = new HashMap<>();
    
    //return connection to specified endpoint for the method specified
    public HttpURLConnection createConnection(String method,String endpoint){
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
            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "+ connection.getResponseCode());
            }
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
        CONNECTIONS.put(endpoint, new ArrayList<>(3));
        CONNECTIONS.get(endpoint).set(0, connection);
        return connection;
    }
    
    public JsonObject send(String body,HttpURLConnection connection){
        StringBuilder responseString = new StringBuilder("");
        JsonObject json = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            OutputStream out = connection.getOutputStream();
            ) {
            String output;
            connection.connect();
            if(body!=null){
                out.write(body.getBytes());
                out.flush();
            }
            while (br.ready()&&(output = br.readLine()) != null) {
                responseString.append(output);
            }
            try(JsonReader jsonReader = Json.createReader(new StringReader(responseString.toString()))){
                json = jsonReader.readObject();
            }
        } catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            connection.disconnect();//manage connections a different way
        }
        return json;
    }
    
    public HashMap<String,List<HttpURLConnection>> getConnections() {
        return CONNECTIONS;
    }
}
