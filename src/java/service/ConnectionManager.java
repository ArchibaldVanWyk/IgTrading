/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import trading.Session;

/**
 *
 * @author Archie
 */
@Singleton(name="connectionmanager")
@ApplicationScoped
@DependsOn({"sessionManager"})
public class ConnectionManager {
    
    @Inject IgAccessService ig;
    @Inject SessionManager sm;
    private String AuthKey="";
    private String cst ="";
    private String x_security_token="";
    private File trcLog;
    private final HashMap<String,List<HttpURLConnection>> CONNECTIONS = new HashMap<>();
    
    public ConnectionManager(){
        try{
            Files.deleteIfExists(Paths.get("c:/logs/TraceLog.txt"));
            trcLog=Files.createFile(Paths.get("c:/logs/TraceLog.txt")).toFile();
            trcLog.setWritable(true);
            System.setOut(new PrintStream(trcLog));
        } catch (IOException ex) {
            Logger.getLogger(AbstractService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PostConstruct
    public void init(){p("starting: "+LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME).replace("T"," "));}
    
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
            this.p("properties loaded");
            key=props.getProperty("header.X-IG-API-KEY");
            AuthKey=key;
            baseUrl=props.getProperty("base-url");
            String ep = endpoint;
            if(baseUrl.endsWith("/")&&endpoint.startsWith("/")){ep=endpoint.substring(1);}
//            if(ep.contains("marketnavigation")){ep=""+ep+"/668394";}
            version=propsVersions.getProperty(endpoint.substring(1).replace('/', '.').replace("{", "").replace("}", "")+"."+method.trim().toUpperCase(),"1");
            p("about to open connection");
            connection =(HttpURLConnection)(new URL(baseUrl+ep)).openConnection();
            if(connection==null){
                p("error: no connection to remote server");
                throw new RuntimeException("no connection to remote server");
            }
            else
            {
                this.p("connection was allocated");
                connection.setRequestMethod(method.toUpperCase().trim());
                connection.setRequestProperty("X-IG-API-KEY", key);
                connection.setRequestProperty("VERSION", version);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                this.p("connection properties set");
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
                this.p("about to create in and out streams");
                try(OutputStream os = connection.getOutputStream();InputStream in = connection.getInputStream();){
                    p("in and out stream created");
                    if(json!=null){
                        p("about to write to remote");
                        os.write(json.toString().getBytes());os.flush();
                        p("done writing to remote");
                    }
                    
                    p("available ro read = "+in.available());
                    StringBuilder b = new StringBuilder("");
                    int c=0;
                    p("about to read from remote");
                    while((c=in.read())!=-1){b.append((char)c);}
                    res=b.toString();
                    p("read from remote = "+res.length());
                    if(isLogin){
                        cst = connection.getHeaderField("cst");
                        x_security_token = connection.getHeaderField("x-security-token");
                        if(res==null||res.length()<2){throw new RuntimeException("no response after login");}
                    }
                    p("the res value is = "+res);
                }catch(Exception ex){p("problem creating streams "+ex.getMessage());}
            }
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

    public IgAccessService getIg() {
        return ig;
    }

    public void setIg(IgAccessService ig) {
        this.ig = ig;
    }

    public SessionManager getSm() {
        return sm;
    }

    public void setSm(SessionManager sm) {
        this.sm = sm;
    }

    public String getAuthKey() {
        return AuthKey;
    }

    public void setAuthKey(String AuthKey) {
        this.AuthKey = AuthKey;
    }

    public String getCst() {
        return cst;
    }

    public void setCst(String cst) {
        this.cst = cst;
    }

    public String getX_security_token() {
        return x_security_token;
    }

    public void setX_security_token(String x_security_token) {
        this.x_security_token = x_security_token;
    }

    public File getTrcLog() {
        return trcLog;
    }

    public void setTrcLog(File trcLog) {
        this.trcLog = trcLog;
    }
    
    
    public void p(Object o){
        System.out.println(o.toString().toUpperCase());
    };
    
    
}
