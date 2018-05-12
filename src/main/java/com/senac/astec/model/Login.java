/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.model;

public class Login {
   private int idLogin;
   private String nome;
   private String login;
   private String senha;
   private String tipoLogin;
   private int idFuncionario;
   private int idEmpresa;
   private boolean enabled;

    public Login(int idLogin, String nome, String login, String senha, String tipoLogin, int idFuncionario,boolean enabled) {
        this.idLogin = idLogin;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.tipoLogin = tipoLogin;
        this.idFuncionario = idFuncionario;
        this.enabled = enabled;
    }

    public Login() {
    }
    
    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
    public int getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(int idLogin) {
        this.idLogin = idLogin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoLogin() {
        return tipoLogin;
    }

    public void setTipoLogin(String tipoLogin) {
        this.tipoLogin = tipoLogin;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
    }
    
    

}
