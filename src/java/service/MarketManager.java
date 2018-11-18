/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import trading.Market;
import trading.MarketNode;

/**
 *
 * @author Archie
 */
@Singleton
@DependsOn("FilesManager")
public class MarketManager {
    
    private Map<String,HashMap<String,List<MarketNode>>> nodeMap_alphabet;
    private Map<String,HashMap<String,List<Market>>> marketMap_alphabet;
    private File nodesdir = Paths.get("C:/GitRepositories/IgTrading/marketNavigation").toFile();
    private File marketsdir = Paths.get("C:/GitRepositories/IgTrading/marketNavigation/markets").toFile();
    private Map<String,List<File>> marketmap;
    private Map<String,List<File>> nodemap;
    private List<MarketNode> selectedNode;
    @Inject
    FilesManager fs;
    
    public MarketManager(){
        
        String abc = "abcdefghijklmnopqrstuvwxyz";
        marketmap=Arrays.stream(marketsdir.listFiles()).flatMap(d->Arrays.stream(d.listFiles()))
        .filter(f->f.isFile()&&f.getName().endsWith(".mkt")).collect(Collectors.groupingBy(t->{
            String name = t.getName();
            return Arrays.stream(name.split("")).filter(c->abc.contains(c.toLowerCase())).findFirst().get().toUpperCase();
        }));
        nodemap=Arrays.stream(nodesdir.listFiles())
        .filter(f->f.isFile()&&f.getName().endsWith(".ig")).collect(Collectors.groupingBy(t->{
            String name = t.getName();
            return Arrays.stream(name.split("")).filter(c->abc.contains(c.toLowerCase())).findFirst().get().toUpperCase();
        }));
    }
    
    public String loadStatus(){
        int nsze = nodeMap_alphabet.size();
        int msze = marketMap_alphabet.size();
        int ncze=nsze;int mcsze=msze;
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(MarketManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(nsze>ncze||msze>mcsze){
            return "busy";
        }
        else{
            return "done"; 
        }
    }
    
    public String numberOfNodeFiles(String k){
        return nodemap.get(k).size()+"";
    }
    
    public String numberOfMarketFiles(String k){
        return marketmap.get(k).size()+"";
    }
    
    public String[] nodefiles(String a){
        return nodemap.get(a).stream().map(f->f.getName().split(".ig")[0]).collect(Collectors.toList()).toArray(new String[]{});
    }
    public String[] marketfiles(String a){
        return marketmap.get(a).stream().map(f->f.getName().split(".mkt")[0]).collect(Collectors.toList()).toArray(new String[]{});
    }
    
    public List<MarketNode> getNode(String letter,String name){
        if(nodemap==null){return null;}
        List<MarketNode> m = (List<MarketNode>)fs
        .readObjectFromFile(ArrayList.class,nodemap.get(letter).stream()
            .filter(f->f.getName().contains(name.split("[.]")[0]))
            .findFirst()
            .get()
            .getAbsolutePath());
        this.selectedNode = m;
        return m;
    }
    
    public List<Market> getMarketNode(String letter,String name){
        if(marketmap==null){return null;}
        List<Market> m = (List<Market>)fs
        .readObjectFromFile(ArrayList.class,marketmap.get(letter).stream()
            .filter(f->f.getName().split(".mkt")[0].contains(name.split("[.]")[0]))
            .findFirst()
            .get()
            .getAbsolutePath());
        return m;
    }
    
    private void loadFiles(){
        
        try{
            
            String abc = "abcdefghijklmnopqrstuvwxyz";
            marketmap=Arrays.stream(marketsdir.listFiles()).flatMap(d->Arrays.stream(d.listFiles()))
            .filter(f->f.isFile()&&f.getName().endsWith(".mkt")).collect(Collectors.groupingBy(t->{
                String name = t.getName();
                return Arrays.stream(name.split("")).filter(c->abc.contains(c.toLowerCase())).findFirst().get();
            }));
            marketmap.forEach((k,v)->{
                HashMap<String,List<Market>> ml = new HashMap<>();
                v.forEach(file->{
                    List<Market> m = (List<Market>)fs.readObjectFromFile(ArrayList.class, marketsdir.getAbsolutePath()+"/"+k);
                    ml.put(file.getName().split(".mkt")[0], m);
                });
                marketMap_alphabet.put(k, ml);
            });
            nodemap=Arrays.stream(nodesdir.listFiles())
            .filter(f->f.isFile()&&f.getName().endsWith(".ig")).collect(Collectors.groupingBy(t->{
                String name = t.getName();
                return Arrays.stream(name.split("")).filter(c->abc.contains(c.toLowerCase())).findFirst().get().toUpperCase();
            }));
            nodemap.forEach((k,v)->{
                HashMap<String,List<MarketNode>> ml = new HashMap<>();
                v.forEach(file->{
                    List<MarketNode> m = (List<MarketNode>)fs.readObjectFromFile(ArrayList.class, nodesdir.getAbsolutePath()+"/"+k);
                    ml.put(file.getName().split(".ig")[0], m);
                });
                nodeMap_alphabet.put(k, ml);
            });
        }catch(Exception exc){
            throw exc;
        }
        
        
        
    }

    public Map<String, HashMap<String, List<MarketNode>>> getNodeMap_alphabet() {
        return nodeMap_alphabet;
    }

    public Map<String, HashMap<String, List<Market>>> getMarketMap_alphabet() {
        return marketMap_alphabet;
    }
}
