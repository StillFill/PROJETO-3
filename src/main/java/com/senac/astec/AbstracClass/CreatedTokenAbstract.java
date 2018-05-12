/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.AbstracClass;

import com.senac.astec.model.Login;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;;
import com.google.gson.JsonObject;
import com.senac.astec.model.Token;

public abstract class CreatedTokenAbstract extends Token {

    public CreatedTokenAbstract() {
        super();
    }

    public abstract boolean codificarToken(String token);

    public abstract Login tokemUser(String token);

    public abstract String header();

    public abstract String payload(JsonObject object);

    public abstract String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException;

    public abstract String token(JsonObject payloadObject);

    public abstract Token decodeToken(String token);
}
