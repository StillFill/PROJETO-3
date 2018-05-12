package com.senac.astec.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoBanco {
    
    public static Connection createConnection(){
        String url = "jdbc:mysql://127.0.0.1:3306/imobiliaria";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection (url, "root", "");
            System.out.println("conectado");
            
            return conn;
        } catch (SQLException e) {
            System.out.println("Deu ruim01" +e);
        }catch (Exception e) {
              System.out.println("Deu ruim 02" +e);
        } 
            return null;
    }     
}
    
