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
    private OAuthToken oauthToken;

    public OAuthToken getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(OAuthToken oauthToken) {
        this.oauthToken = oauthToken;
    }

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
