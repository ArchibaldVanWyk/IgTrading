/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
    
    private Map<String,List<List<MarketNode>>> nodeMap_alphabet;
    private Map<String,List<List<Market>>> marketMap_alphabet;
    public MarketManager(){
        File marketsdir = Paths.get("C:/GitRepositories/IgTrading/marketNavigation/markets").toFile();
    }
    
    @Inject
    public String loadNodes(FilesManager fs){
//        File nodesdir = Paths.get("C:/GitRepositories/IgTrading/marketNavigation").toFile();
        File marketsdir = Paths.get("C:/GitRepositories/IgTrading/marketNavigation/markets").toFile();
//        File rootNode = Paths.get("C:/GitRepositories/IgTrading/marketNavigation/topNodes.ig").toFile();
        
//        Map<String,MarketNode> nodemap = new HashMap<>();
        Map<String,List<File>> marketmap;
        String abc = "abcdefghijklmnopqrstuvwxyz";
        Arrays.stream(marketsdir.listFiles()).filter(f->f.isDirectory()).forEach(d->{if(!d.delete()){d.deleteOnExit();}});
        marketmap=Arrays.stream(marketsdir.listFiles())
        .filter(f->f.isFile()&&f.getName().endsWith(".mkt")).collect(Collectors.groupingBy(t->{
            String name = t.getName();
            return Arrays.stream(name.split("")).filter(c->abc.contains(c.toLowerCase())).findFirst().get();
        }));
        marketmap.forEach((k,v)->{
            v.forEach(file->{
                try {
                    Files.createDirectories(Paths.get("C:/GitRepositories/IgTrading/marketNavigation/markets"+"/"+k));
                    Files.move(file.toPath(), Paths.get("C:/GitRepositories/IgTrading/marketNavigation/markets"+"/"+k+"/"+file.getName()));
                } catch (IOException ex) {
                    Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        });
        try {
            Files.deleteIfExists(Paths.get(marketsdir.getAbsolutePath()+"/"+"MarketMap"));
        } catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Markets organized "+marketmap.entrySet().size();
    }
}
