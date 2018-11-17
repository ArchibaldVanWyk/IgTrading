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
import javax.ejb.Singleton;
import javax.inject.Inject;
import trading.Market;
import trading.MarketNode;

/**
 *
 * @author Archie
 */
@Singleton
public class MarketManager {
    
    private Map<String,HashMap<String,List<MarketNode>>> nodeMap_alphabet;
    private Map<String,HashMap<String,List<Market>>> marketMap_alphabet;
    @Inject
    FilesManager fs;
    
    @PostConstruct
    public void init(){
        Thread thr = new Thread(()->loadNodes());
        thr.start();
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
    
    private void loadNodes(){
        
        try{
            File nodesdir = Paths.get("C:/GitRepositories/IgTrading/marketNavigation").toFile();
            File marketsdir = Paths.get("C:/GitRepositories/IgTrading/marketNavigation/markets").toFile();
            nodeMap_alphabet = new HashMap<>();
            marketMap_alphabet = new HashMap<>();
            Map<String,List<File>> marketmap;
            Map<String,List<File>> nodemap;
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
