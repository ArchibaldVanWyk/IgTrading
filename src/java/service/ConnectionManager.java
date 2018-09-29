/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.JsonObject;
import trading.Session;

/**
 *
 * @author Archie
 */
@Singleton(name="connectionmanager")
@DependsOn({"sessionManager"})
public class ConnectionManager{
    
    @Inject IgAccessService ig;
    @Inject SessionManager sm;
    private String AuthKey="";
    private String cst ="";
    private String x_security_token="";
    
    private final HashMap<String,List<HttpURLConnection>> CONNECTIONS = new HashMap<>();
    
    //return connection to specified endpoint for the method specified
    public String createConnection(String method,String endpoint,JsonObject json){
        Properties props = new Properties();
        Properties propsVersions = new Properties();
        HttpURLConnection connection = null;
        String key;
        String version;
        String baseUrl;
        String res=null;
        try(FileReader propsFile=new FileReader("/GitRepositories/IgTrading\\properties/IgRest-api.properties");
            FileReader propVersionRdr = new FileReader("/GitRepositories/IgTrading\\properties/IgRest-api-versions.properties")){
            props.load(propsFile);
            propsVersions.load(propVersionRdr);
            key=props.getProperty("header.X-IG-API-KEY");
            AuthKey=key;
            baseUrl=props.getProperty("base-url");
            String ep = endpoint;
            if(baseUrl.endsWith("/")&&endpoint.startsWith("/")){ep=endpoint.substring(1);}
//            if(ep.contains("marketnavigation")){ep=""+ep+"/668394";}
            version=propsVersions.getProperty(endpoint.substring(1).replace('/', '.').replace("{", "").replace("}", "")+"."+method.trim().toUpperCase(),"1");
            connection =(HttpURLConnection)(new URL(baseUrl+ep)).openConnection();
            connection.setRequestMethod(method.toUpperCase().trim());
            connection.setRequestProperty("X-IG-API-KEY", key);
            connection.setRequestProperty("VERSION", version);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            boolean isLogin = "POST".equals(method.toUpperCase())&&"/session".equals(endpoint);
            if(!isLogin){
                Session session = sm.getSessions().values().stream().findFirst().get();
                if(session==null){throw new RuntimeException("no session");}
                connection.setRequestProperty("IG-ACCOUNT-ID", session.getAccountId());
                connection.setRequestProperty("Authorization", session.getOauthToken().getAccess_token());
                connection.setRequestProperty("CST", cst);
                connection.setRequestProperty("X-SECURITY-TOKEN", x_security_token);
                
            }
            connection.setDoOutput(true);
            
            OutputStream os = connection.getOutputStream();
            if(json!=null){os.write(json.toString().getBytes());os.flush();}
            InputStream in = connection.getInputStream();
            System.out.println("available ro read = "+in.available());
            StringBuilder b = new StringBuilder("");
            int c=0;
            while((c=in.read())!=-1){b.append((char)c);}
            res=b.toString();

            if(isLogin){
                cst = connection.getHeaderField("cst");
                x_security_token = connection.getHeaderField("x-security-token");
                if(res==null||res.length()<2){throw new RuntimeException("no response after login");}
            }
            
            System.out.print("the res value is = "+res);
            in.close();
            os.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            if(connection!=null)connection.disconnect();
        }
        return res;
    }
    
    public String token(){
        String res=null;
        try {
            HttpURLConnection connection;
            connection =(HttpURLConnection)(new URL("https://demo-api.ig.com/gateway/deal/session?fetchSessionTokens=true")).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-IG-API-KEY", AuthKey);
            connection.setRequestProperty("VERSION", "1");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Session session = sm.getSessions().values().stream().findFirst().get();
            if(session==null){throw new RuntimeException("no session");}
            connection.setRequestProperty("IG-ACCOUNT-ID", session.getAccountId());
            connection.setRequestProperty("Authorization", "Bearer "+session.getOauthToken().getAccess_token());
            connection.setDoInput(true);
            InputStream in = connection.getInputStream();
            StringBuilder b = new StringBuilder("");
            int c=0;
            while((c=in.read())!=-1){b.append((char)c);}
            cst = connection.getHeaderField("cst");
            x_security_token = connection.getHeaderField("x-security-token");
            System.out.println("CST is = "+cst);
            System.out.println("XST is = "+x_security_token);
            c=0;
            res = b.toString()+" CST:"+cst+" XST:"+x_security_token;
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
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
