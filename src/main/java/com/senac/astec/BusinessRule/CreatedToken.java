/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.BusinessRule;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.senac.astec.AbstracClass.CreatedTokenAbstract;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import com.senac.astec.model.Login;
import com.senac.astec.model.Token;

public class CreatedToken extends CreatedTokenAbstract {

    //constante chave de acesso
    private static final String KEY = "pI3";

    public CreatedToken() {
        super();
    }

    @Override
    public boolean codificarToken(String token) {
        //instaiciando metudo para utura conversao de String pra JsonObject 
        JsonParser parser = new JsonParser();
        //indentificando o primeiro ponto do token
        int indexof = token.indexOf(".");
        //indentificando o ultimo ponto do token
        int lasttindexof = token.lastIndexOf(".");
        //adquirindo conteudo do token
        String tokenPayload = token.substring(indexof + 1, lasttindexof);
        //descriptografando conteudo do usuario
        String tokenDecoded = null;
        try {
            tokenDecoded = new String(DatatypeConverter.parseBase64Binary(tokenPayload), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        //fazendo a conversao do conteudo de String para JsonObject
        JsonObject jsonObject = parser.parse(tokenDecoded).getAsJsonObject();
        //criando um novo token para comparação
        String TesteToken = token(jsonObject);
        //retornando analize se o token nao foi alterado
        return TesteToken.equals(token);
    }

    @Override
    public Login tokemUser(String token) {
        //indentificando o primeiro ponto do token
        int indexof = token.indexOf(".");
        //indentificando o ultimo ponto do token
        int lasttindexof = token.lastIndexOf(".");
        //adquirindo conteudo do token
        String tokenPayload = token.substring(indexof + 1, lasttindexof);
        //descriptografando conteudo do usuario
        String tokenDecoded = null;
        try {
            tokenDecoded = new String(DatatypeConverter.parseBase64Binary(tokenPayload), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        //instaiciando gson da google
        Gson gson = new Gson();
        //retornando json convertido para um objeto usuario
        return gson.fromJson(tokenDecoded, Login.class);
    }

    //metudo de criaçao do cabeçalho
    @Override
    public String header() {
        //type de token
        String typ = "jwt";
        //alg do token
        String alg = "alg";
        //gerando cabeçalho em Json
        JsonObject jsonObject = new JsonObject();
        //adicionando novo json ao typ
        jsonObject.addProperty(typ, "JWT");
        //adicionando metudo do token
        jsonObject.addProperty(alg, "HS256");
        //classe de conversao ao Json da google
        Gson gson = new Gson();
        //retornando json a uma string
        String header = gson.toJson(jsonObject);
        //convertendo string em json para uma sequencia de bytes
        return Base64.getEncoder().encodeToString(header.getBytes());
    }

    //armazendo dados do usuario
    @Override
    public String payload(JsonObject object) {
        //classe de conversao ao Json da google
        Gson gson = new Gson();
        //passando dados enviados por parametros para um json em uma string
        String payload = gson.toJson(object);
        //convertendo string em json para uma sequencia de bytes
        return Base64.getEncoder().encodeToString(payload.getBytes());
    }

    //metudo de criptografia de chaves
    @Override
    public String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException {
        //criando uma chave secreta
        SecretKey secretKey = null;
        //gerando um array de bytes se baseando na chave passada por parametro 
        byte[] keyBytes = keyString.getBytes();
        //gerando uma nova chave secreta com a chave criptografada em um array de bytes e o MAC dela
        secretKey = new SecretKeySpec(keyBytes, "HMACSHA256");
        //gerando instancia do MAC
        Mac mac = Mac.getInstance("HMACSHA256");
        //iniciando um MAC de cripstografia
        mac.init(secretKey);
        //gerando criptografia das bases passadas por parametro em um array de bytes
        byte[] text = baseString.getBytes();
        //retornando a junção dessas criptografias juntas
        return new String(Base64.getEncoder().encode(mac.doFinal(text))).trim();
    }

    //metudo de criação do token
    @Override
    public String token(JsonObject payloadObject) {
        //classe de conversao ao Json da google
        Gson gson = new Gson();
        //recebendo cabeçalho criptografado
        String header = header();
        //recebendo dados do usuario criptografado
        String payload = payload(payloadObject);
        //tratando futuros erros
        try {
            //juntando cabeçalho com dados separados por "."
            String estrutura = header + "." + payload;
            //cruando um assinatura unica de token com os dados da classe
            String computeSignature = computeSignature(estrutura, KEY);
            //retornando a junção das criptografias
            return estrutura + "." + computeSignature;
            //possiveis erros
        } catch (GeneralSecurityException | UnsupportedEncodingException e) {
            //retornando messagem de erro
            return "erro ao processar";
        }
    }

    @Override
    public Token decodeToken(String token) {
        //instanciando objeto para a conversao para json
        JsonParser parser = new JsonParser();
        //instanciando gson da google
        Gson gson = new Gson();
        //capiturando inicio do payload no token
        int indexOne = token.indexOf(".");
        //capiturando fim do payload no token
        int indexTwo = token.lastIndexOf(".");
        //decodificando o payload
        String tokenjwt = null;
        try {
            tokenjwt = new String(DatatypeConverter.parseBase64Binary(token.substring(indexOne + 1, indexTwo)), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }
        //convertendo a string para um json
        JsonObject jsonObject = parser.parse(tokenjwt).getAsJsonObject();
        //tranformando e retornando json em um objeto do tipo token
        return gson.fromJson(jsonObject, Token.class);
    }

}
