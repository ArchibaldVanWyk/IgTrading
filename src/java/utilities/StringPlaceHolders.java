/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author Archie
 */
public class StringPlaceHolders {
    
    public  static String fillStringPlaceHolders(String inputString,int... numVals) {
        int openBraces = (int)inputString.chars().filter(c->c=='{').count();
        int closeBraces = (int)inputString.chars().filter(c->c=='}').count();
        if(openBraces!=closeBraces){return "Junk!";}
        int openPos=0;
        int closePos=0;
        StringBuilder sb = new StringBuilder(inputString);
        for(int i=0,j=0;i<inputString.length();i+=closePos,j++){
            openPos=sb.indexOf("{",i);
            closePos=sb.indexOf("}",i);
            sb=sb.replace(openPos, closePos, numVals[j]+"");
        }
        return sb.toString().replace("{", "").replace("}", "");
    }
    public  static String fillStringPlaceHolders(String inputString,long... numVals) {
        int openBraces = (int)inputString.chars().filter(c->c=='{').count();
        int closeBraces = (int)inputString.chars().filter(c->c=='}').count();
        if(openBraces!=closeBraces){return "Junk!";}
        int openPos=0;
        int closePos=0;
        StringBuilder sb = new StringBuilder(inputString);
        for(int i=0,j=0;i<inputString.length();i+=closePos,j++){
            openPos=sb.indexOf("{",i);
            closePos=sb.indexOf("}",i);
            sb=sb.replace(openPos, closePos, numVals[j]+"");
        }
        return sb.toString().replace("{", "").replace("}", "");
    }
    public static String fillStringPlaceHolders(String inputString,double... numVals) {
        int openBraces = (int)inputString.chars().filter(c->c=='{').count();
        int closeBraces = (int)inputString.chars().filter(c->c=='}').count();
        if(openBraces!=closeBraces){return "Junk!";}
        int openPos=0;
        int closePos=0;
        StringBuilder sb = new StringBuilder(inputString);
        for(int i=0,j=0;i<inputString.length();i+=closePos,j++){
            openPos=sb.indexOf("{",i);
            closePos=sb.indexOf("}",i);
            sb=sb.replace(openPos, closePos, numVals[j]+"");
        }
        return sb.toString().replace("{", "").replace("}", "");
    }
    public static String fillStringPlaceHolders(String inputString,Object... objVals) {
        int openBraces = (int)inputString.chars().filter(c->c=='{').count();
        int closeBraces = (int)inputString.chars().filter(c->c=='}').count();
        if(openBraces!=closeBraces){return "Junk!";}
        int openPos=0;
        int closePos=0;
        StringBuilder sb = new StringBuilder(inputString);
        for(int i=0,j=0;i<inputString.length();i+=closePos,j++){
            openPos=sb.indexOf("{",i);
            closePos=sb.indexOf("}",i);
            sb=sb.replace(openPos, closePos, objVals.toString()+"");
        }
        return sb.toString().replace("{", "").replace("}", "");
    }
    public static String fillStringPlaceHolders(String inputString,String... stringVals) {
        int openBraces = (int)inputString.chars().filter(c->c=='{').count();
        int closeBraces = (int)inputString.chars().filter(c->c=='}').count();
        if(openBraces!=closeBraces){return "Junk!";}
        int openPos=0;
        int closePos=0;
        StringBuilder sb = new StringBuilder(inputString);
        for(int i=0,j=0;i<inputString.length();i+=closePos,j++){
            openPos=sb.indexOf("{",i);
            closePos=sb.indexOf("}",i);
            sb=sb.replace(openPos, closePos, stringVals[j]+"");
            p("numVals "+stringVals[j]);
        }
        return sb.toString().replace("{", "").replace("}", "");
    }
    private static void p(Object o){
        System.out.println(o);
    }
    
}
