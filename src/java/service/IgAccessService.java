/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import trading.Currency;
import trading.Deal;
import trading.DealingRules;
import trading.Instrument;
import trading.Instrument.OpeningHours;
import trading.InstrumentType;
import trading.Market;
import trading.MarketNode;
import trading.Position;
import trading.Session;
import trading.Snapshot;
import trading.Unit;


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
    private int num_of_markets=0;
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
//        cm.p(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME).replace("T"," ")+" market navigation");
//        cm.p("input received = "+in);
//        cm.p("about to call remote");
        String nodeId=in==null||in.isEmpty()?"":in.trim();
        String resp = cm.createConnection("GET", "/marketnavigation"+"/"+nodeId, null);
        List<Market> markets = null;
        List<MarketNode> nodes = null;
//        cm.p(resp==null?"no fucking response":"success");
        JsonObject json = Json.createReader(new StringReader(resp)).readObject();
//        cm.p("json string collected:");
//        cm.p(json);
        JsonArray marketsArr = null;
        JsonArray nodesArr = null;
        

        try{
            if(json.get("markets").getValueType().equals(JsonValue.ValueType.ARRAY)){
                marketsArr = json.getJsonArray("markets");
                markets = parseMarketList(marketsArr,nodeId);
                saveOjectLocally(markets, "C:/GitRepositories/IgTrading/marketnavigation/markets/nullMkt.mkt");
            }
            else if(json.get("nodes").getValueType().equals(JsonValue.ValueType.ARRAY)){
    //                cm.p(marketsArr==null?"no market array":"market array collected:\n"+marketsArr);
                    nodesArr = json.getJsonArray("nodes");
    //                cm.p(nodesArr==null?"no node array":"node array collected:\n"+nodesArr);
                    nodes = parseMarketNodes(nodesArr);
    //                cm.p("nodes_list size: "+nodes.size());
                    saveOjectLocally(nodes, "C:/GitRepositories/IgTrading/marketNavigation/topNodes.ig");
                    for(int i = 0;i<nodes.size();i++){
                        parseNodesRecursively(nodes.get(i), "/marketnavigation"+"/"+nodes.get(i).getId());
                        
                    }
    //                cm.p("number of markets parsed = "+num_of_markets);
            }
        }
        catch(Exception exc){
            throw exc;
        }
        
        
        if(nodes!=null&&nodes.size()>0){
            resp = "Local Market is ready";
        }
        else{resp = "something went wrong";}
        return resp;
    }
    
    private void parseNodesRecursively(MarketNode node,String endpoint){
        try{Thread.sleep(1200);} catch (InterruptedException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        String resp = cm.createConnection("GET", endpoint, null);
        List<Market> markets = null;
        List<MarketNode> nodes = null;
        
        JsonObject json = Json.createReader(new StringReader(resp)).readObject();
        
        
        try{
            JsonValue marketsArr = json.get("markets");
            JsonValue nodesArr = json.get("nodes");
            if(marketsArr!=null&&marketsArr.getValueType().equals(JsonValue.ValueType.ARRAY)){
                JsonArray marketsArray = json.getJsonArray("markets");
                markets = parseMarketList((JsonArray)marketsArray,node.getId());
                node.setMarkets(markets);
                String name = node.getName().replace("/", ".").replace("\\", ".");
                saveOjectLocally(markets, "C:/GitRepositories/IgTrading/marketnavigation/markets/"+name+"."+node.getId()+".mkt");
            }
            else if(nodesArr!=null&&nodesArr.getValueType().equals(JsonValue.ValueType.ARRAY)){
                JsonArray nodesArray = json.getJsonArray("nodes");
                nodes = parseMarketNodes(nodesArray);
                String name = node.getName().replace("/", ".").replace("\\", ".");
                saveOjectLocally(nodes, "C:/GitRepositories/IgTrading/marketnavigation/"+name+"."+node.getId()+".ig");
                node.setNodes(nodes);
                for(int i = 0;i<nodes.size();i++){
                    parseNodesRecursively(nodes.get(i), "/marketnavigation/"+nodes.get(i).getId());
                }
            }
        }
        catch(Exception exc){
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, exc.toString(), exc);
        }
    }
    
    private void saveOjectLocally(Object obj,String path){
        try(ObjectOutputStream oo = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)));){
            oo.writeObject(obj);
        } 
        catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private <T> T readObjectFromFile(Class<T> type, String path){
        T obj = null;
        try(ObjectInputStream oi = new ObjectInputStream(Files.newInputStream(Paths.get(path)));){
            obj = type.cast(oi.readObject());
        } 
        catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
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
        if(fw.getPositions().size()<jsonArr.size()){
           fw.getPositions().clear();
            for(int i=0;i<jsonArr.size();i++){
            
                JsonObject obj = jsonArr.getJsonObject(i);
                JsonObject mktjs = obj.getJsonObject("market");
                JsonObject posjs = obj.getJsonObject("position");
                Market market = parseMarket(mktjs,null);

                Position position = new Position();
                position.setContractSize(posjs.getJsonNumber("contractSize").doubleValue());
                position.setControlledRisk(posjs.getBoolean("controlledRisk"));
                position.setCreatedDate(posjs.getString("createdDate"));
                position.setCreatedDateUTC(posjs.getString("createdDateUTC"));
                position.setCurrency(posjs.getString("currency"));
                position.setDealId(posjs.getString("dealId"));
                position.setDealReference("dealReference");
                position.setDealDirection(posjs.getString("direction").equals("SELL")?Deal.Direction.SELL:Deal.Direction.BUY);
                position.setLevel(posjs.getJsonNumber("level").doubleValue());
                position.setLimitLevel(posjs.getJsonNumber("limitLevel").doubleValue());
                position.setSize(posjs.getJsonNumber("size").doubleValue());
                position.setStopLevel(posjs.getJsonNumber("stopLevel").doubleValue());
                position.setTrailingStep(posjs.getJsonNumber("trailingStep").doubleValue());
                position.setTrailingStopDistance(posjs.getJsonNumber("trailingStopDistance").doubleValue());
                position.setMarket(market);
                fw.getPositions().add(position);
            
            }
        }
        resp=jsonArr.toString();
        return resp;
    }
    private List<Market> parseMarketList(JsonArray marketsArr, String nodeId){
        if(marketsArr.isEmpty()){cm.p("market array is empty");return null;}
        cm.p("market array size to be parsed = "+marketsArr.size());
        List<Market> markets = new ArrayList<>();
        for(int i=0;i<marketsArr.size();i++){
            markets.add(parseMarket(marketsArr.getJsonObject(i),nodeId));
        }
        return markets;
    }
    private List<MarketNode> parseMarketNodes(JsonArray nodeArr){
        if(nodeArr==null||nodeArr.isEmpty()){cm.p("node array is empty");return null;}
        cm.p("node array size to be parsed = "+nodeArr.size());
        List<MarketNode> nodes = new ArrayList<>();
        for(int i=0;i<nodeArr.size();i++){
            nodes.add(parseNode(nodeArr.getJsonObject(i)));
        }
        return nodes;
    }
    
    private MarketNode parseNode(JsonObject json){
        MarketNode node = new MarketNode();
        node.setId(json.getString("id"));
        node.setName(json.getString("name"));
        return node;
    }
    
    private Market parseMarket(JsonObject json, String nodeId){
        
        JsonObject instrument = json.getJsonObject("instrument");
        JsonObject dealingRules = json.getJsonObject("dealingRules");
        JsonObject snapshot = json.getJsonObject("snapshot");
        
        
        Market market = new Market();
        market.setNodeId(nodeId);
        
        DealingRules rules = new DealingRules();
        
        rules.setMarketOrderPref(DealingRules.MarketOrderPreference.valueOf(dealingRules.getString("marketOrderPreference")));
        
        rules.setMaxStopOrLimitDistance(dealingRules.getJsonNumber("maxStopOrLimitDistance").doubleValue());
        rules.setMinControlledRiskStopDistance(dealingRules.getJsonNumber("minControlledRiskStopDistance").doubleValue());
        rules.setMinDealSize(dealingRules.getJsonNumber("minDealSize").doubleValue());
        rules.setMinNormalStopOrLimitDistance(dealingRules.getJsonNumber("minNormalStopOrLimitDistance").doubleValue());
        rules.setMinStepDistance(dealingRules.getJsonNumber("minStepDistance").doubleValue());
        rules.setTrailingStopsPref(DealingRules.TrailingStopsPref.valueOf("trailingStopsPreference"));
        
        
        
        Instrument ins = new Instrument();
        
        Instrument.ExpiryDetails expDetails = new Instrument.ExpiryDetails();
        Instrument.LimitedRiskPremium limitedRiskPremium = new Instrument.LimitedRiskPremium();
        Instrument.RolloverDetails rolloverDetails = new Instrument.RolloverDetails();
        Instrument.SlippageFactor slippageFactor =  new Instrument.SlippageFactor();
        
        JsonObject expDet = json.getJsonObject("expiryDetails");
        JsonObject limitedRiskPrem = json.getJsonObject("limitedRiskPremium");
        JsonArray marginDepositBs = json.getJsonArray("marginDepositBand");
        JsonArray currencies = json.getJsonArray("currencies");
        JsonArray specialInfo = json.getJsonArray("specialInfo");
        JsonObject openingHrs = json.getJsonObject("openingHours");
        JsonObject rolloverDet = json.getJsonObject("rolloverDetails");
        JsonObject slippageFact = json.getJsonObject("slippageFactor");
        
        expDetails.setLastDealingDate(expDet.getString("lastDealingDate"));
        expDetails.setSettlementInfo(expDet.getString("settlementInfo"));
        ins.setExpiryDetials(expDetails);
        
        limitedRiskPremium.setValue(limitedRiskPrem.getJsonNumber("value").doubleValue());
        limitedRiskPremium.setUnit(Unit.valueOf(limitedRiskPrem.getString("unit")));
        ins.setLimitedRiskPrem(limitedRiskPremium);
        
        rolloverDetails.setLastRolloverTime(rolloverDet.getString("lastRolloverTime"));
        rolloverDetails.setRolloverInfo(rolloverDet.getString("rolloverInfo"));
        ins.setRolloverDetails(rolloverDetails);
        
        slippageFactor.setUnit(Unit.valueOf(slippageFact.getString("unit")));
        slippageFactor.setValue(slippageFact.getJsonNumber("value").doubleValue());
        ins.setSlippageFactor(slippageFactor);
        
        ins.setMarginDepBands(parseMarginDepBands(marginDepositBs));
        ins.setCurrencies(parseCurrencies(currencies));
        ins.setSpecialInfo(parseSpecialinfo(specialInfo));
        ins.setOpeningHours(parseOpeningHours(openingHrs));
        
        ins.setContractSize(instrument.getString("contractSize"));
        ins.setChartCode(instrument.getString("chartCode"));
        ins.setControlledRiskAllowed(instrument.getBoolean("controlledRiskAllowed"));
        ins.setCountry(instrument.getString("country"));
        ins.setEpic(instrument.getString("epic"));
        ins.setExpiry(instrument.getString("expiry"));
        ins.setName(instrument.getString("instrumentName"));
        ins.setLotSize(instrument.getJsonNumber("lotSize").doubleValue());
        ins.setScalingFactor(instrument.getJsonNumber("scalingFactor").doubleValue());
        ins.setStreamingPricesAvailable(instrument.getBoolean("streamingPricesAvailable"));
        ins.setType(instrument.getString("instrumentType"));
        ins.setForceOpenAllowed(instrument.getBoolean("forceOpenAllowed"));
        ins.setMarketId(instrument.getString("marketId"));
        ins.setMarginFactor(instrument.getJsonNumber("marginFactor").doubleValue());
        ins.setMarginFactorUnit(Unit.valueOf(instrument.getString("marginFactorUnit")));
        ins.setNewsCode(instrument.getString("newsCode"));
        ins.setOnePipMeans(instrument.getString("onePipMeans"));
        ins.setSprintMarketsMaximumExpiryTime(instrument.getJsonNumber("sprintMarketsMaximumExpiryTime").doubleValue());
        ins.setSprintMarketsMinimumExpiryTime(instrument.getJsonNumber("sprintMarketsMinimumExpiryTime").doubleValue());
        ins.setStopLimitsAllowed(instrument.getBoolean("stopsLimitsAllowed"));
        

        Snapshot ss = new Snapshot();
        ss.setBid(snapshot.getJsonNumber("bid").doubleValue());
        ss.setDelayTime(snapshot.getJsonNumber("delayTime").doubleValue());
        ss.setLow(snapshot.getJsonNumber("low").doubleValue());
        ss.setOffer(snapshot.getJsonNumber("offer").doubleValue());
        ss.setNetChange(snapshot.getJsonNumber("netChange").doubleValue());
        ss.setPercentageChange(snapshot.getJsonNumber("percentageChange").doubleValue());
        ss.setUpdateTime(snapshot.getString("updateTime"));
        ss.setUpdateTimeUTC(snapshot.getString("updateTimeUTC"));
        ss.setMarketStatus(snapshot.getString("marketStatus"));

        market.setDealingRules(rules);
        market.setInstrument(ins);
        market.setSnapshot(ss);
        
        return market;
    }
    
    private List<Instrument.MarginDepositBand> parseMarginDepBands(JsonArray marginDepositBs){
        if(marginDepositBs==null){throw new RuntimeException("JsonArray passed is null");}
        if(!marginDepositBs.getValueType().equals(JsonValue.ValueType.ARRAY)){throw new RuntimeException("JsonValue passed is not a JsonArray");}
        List<Instrument.MarginDepositBand> bands = new ArrayList<>(marginDepositBs.size());
        Instrument.MarginDepositBand band;
        JsonObject obj;
        for(int i = 0;i<marginDepositBs.size();i++){
            band = new Instrument.MarginDepositBand();
            obj = marginDepositBs.getJsonObject(i);
            band.setBandMax(obj.getJsonNumber("max").doubleValue());
            band.setBandMin(obj.getJsonNumber("min").doubleValue());
            band.setCurrency(obj.getString("currency"));
            band.setMargin(obj.getJsonNumber("margin").doubleValue());
        }
        return bands;
    }
    private List<Currency> parseCurrencies(JsonArray currencies){
        if(currencies==null){throw new RuntimeException("JsonArray passed is null");}
        if(!currencies.getValueType().equals(JsonValue.ValueType.ARRAY)){throw new RuntimeException("JsonValue passed is not a JsonArray");}
        List<Currency> currs = new ArrayList<>(currencies.size());
        Currency curr;
        JsonObject obj;
        for(int i = 0;i<currencies.size();i++){
            curr = new Currency();
            obj = currencies.getJsonObject(i);
            curr.setBaseExchangeRate(obj.getJsonNumber("baseExchangeRate").doubleValue());
            curr.setCode(obj.getString("code"));
            curr.setExchangeRate(obj.getJsonNumber("exchangeRate").doubleValue());
            curr.setIsDefault(obj.getBoolean("isDefault"));
            curr.setSymbol(obj.getString("symbol"));
        }
        return currs;
    }
    
    private List<String> parseSpecialinfo(JsonArray infoArray){
        if(infoArray==null){throw new RuntimeException("JsonArray passed is null");}
        if(!infoArray.getValueType().equals(JsonValue.ValueType.ARRAY)){throw new RuntimeException("JsonValue passed is not a JsonArray");}
        List<String> infos = new ArrayList<>(infoArray.size());
        for(int i = 0;i<infoArray.size();i++){
            infos.add(infoArray.getString(i));
        }
        return infos;
    }
    
    private Instrument.OpeningHours parseOpeningHours(JsonObject obj){
        if(obj==null){throw new RuntimeException("JsonObject passed is null");}
        Instrument.OpeningHours openHrs = new Instrument.OpeningHours();
        JsonArray marketTimes=obj.getJsonArray("marketTimes");
        List<OpeningHours.MarketTime> times = new ArrayList<>(marketTimes.size());
        JsonObject jo;
        OpeningHours.MarketTime time;
        for(int i = 0;i<marketTimes.size();i++){
            jo=marketTimes.getJsonObject(i);
            time = new OpeningHours.MarketTime();
            time.setCloseTime(jo.getString("closeTime"));
            time.setOpenTime(jo.getString("openTime"));
            times.add(time);
        }
        openHrs.setMarketTimes(times);
        return openHrs;
    }
    
}