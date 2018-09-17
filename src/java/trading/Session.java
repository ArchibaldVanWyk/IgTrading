/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trading;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;


/**
 *
 * @author Archie
 */
@SessionScoped
public class Session implements Serializable{
    
    private String accountId;
    private String clientId;
    private String currency;
    private String lightStreamerEndpoint;
    private String Locale;
    private double timezoneOffset;
    private String encryptionKey;
    private long timeStamp;
    private String access_token;
    private String expires_in;
    private String refresh_token;
    private String scope;
    private String token_type;

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLightStreamerEndpoint() {
        return lightStreamerEndpoint;
    }

    public void setLightStreamerEndpoint(String lightStreamerEndpoint) {
        this.lightStreamerEndpoint = lightStreamerEndpoint;
    }

    public String getLocale() {
        return Locale;
    }

    public void setLocale(String Locale) {
        this.Locale = Locale;
    }

    public double getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(double timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }
    
    
    
}
