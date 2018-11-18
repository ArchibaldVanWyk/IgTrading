/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Archie
 */
@Singleton
public class FilesManager {
    
    
    public <T> T readObjectFromFile(Class<T> type, String path){
        T obj = null;
        try(ObjectInputStream oi = new ObjectInputStream(Files.newInputStream(Paths.get(path)));){
            obj = type.cast(oi.readObject());
        } 
        catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }
        public void saveOjectLocally(Object obj,String path){
        try(ObjectOutputStream oo = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)));){
            oo.writeObject(obj);
        } 
        catch (IOException ex) {
            Logger.getLogger(IgAccessService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
