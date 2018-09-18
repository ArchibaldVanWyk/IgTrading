/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trading;

import javax.persistence.Embeddable;

/**
 *
 * @author Archie
 * 
 */

public class OAuthToken {
    String access_token;
    String refresh_token;
    String scope;
    String token_type;
    String expires_in;
    public OAuthToken(){
        
    }

    public OAuthToken(String access_token, String refresh_token, String scope, String token_type, String expires_in) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.scope = scope;
        this.token_type = token_type;
        this.expires_in = expires_in;
    }
    
}
