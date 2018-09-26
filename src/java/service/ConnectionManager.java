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
import java.net.URI;
import java.net.URISyntaxException;
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
    public String createConnection(String method,String endpoint,JsonObject json){
        Properties props = new Properties();
        Properties propsVersions = new Properties();
        HttpURLConnection connection = null;
        String key;
        String baseUrl;
        String fileStatus="NOT FOUND";
        String res=null;
        try(FileReader propsFile=new FileReader("/GitRepositories/IgTrading\\properties/IgRest-api.properties");
            FileReader propVersionRdr = new FileReader("/GitRepositories/IgTrading\\properties/IgRest-api-versions.properties")){
            fileStatus="FILES WERE FOUND";
            props.load(propsFile);
            propsVersions.load(propVersionRdr);
            key=props.getProperty("header.X-IG-API-KEY");
            baseUrl=props.getProperty("base-url");//new URL(baseUrl+props.getProperty(endpoint))
            connection =(HttpURLConnection)(new URL("https://demo-api.ig.com/gateway/deal/session")).openConnection();
            connection.setRequestMethod(method.toUpperCase().trim());
            connection.setRequestProperty("X-IG-API-KEY", key);
            connection.setRequestProperty("VERSION", "3");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.getOutputStream().write(json.toString().getBytes());
            
            InputStream in = connection.getInputStream();
            StringBuilder b = new StringBuilder("");
            int c=0;
            while((c=in.read())!=-1){b.append((char)c);}
            res=b.toString();
            in.close();
            if(res.length()<5){throw new RuntimeException("FUUUUUUCK");}
//            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
//                throw new RuntimeException("Failed : HTTP error code : "+ connection.getResponseCode()+" "+connection.getResponseMessage());
//            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
//        CONNECTIONS.put(endpoint, new ArrayList<>(3));
//        CONNECTIONS.get(endpoint).set(0, connection);
        return res;
    }
    
    public String send(String body,HttpURLConnection connection){
        StringBuilder responseString = new StringBuilder("");
        JsonObject json = null;
        Object content="";
        if(connection==null){throw new RuntimeException("Connection is Null");}
        
        if(!connection.getDoOutput()){connection.setDoOutput(true);}
        try (OutputStream out = connection.getOutputStream();)
        {
            int output;
            if(body!=null){
                out.write(body.getBytes());
                out.flush();
            }
            if(!connection.getDoInput()){connection.setDoInput(true);}
            try (InputStream br = connection.getInputStream()) {
                while ((output = br.read()) != -1) {
                    responseString.append((char)output);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
        finally{
            connection.disconnect();//manage connections a different way
        }
        
        return responseString.toString();
    }
    
    public HashMap<String,List<HttpURLConnection>> getConnections() {
        return CONNECTIONS;
    }
}
