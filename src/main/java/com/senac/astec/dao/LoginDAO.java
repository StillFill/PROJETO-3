package com.senac.astec.dao;

import com.senac.astec.model.Login;
import com.senac.astec.utils.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO extends ConexaoBanco {
    
    ConexaoBanco conexaoBanco = new ConexaoBanco();
    Connection conn = conexaoBanco.createConnection();

    //insere novo usuário
    public void inserirLogin(Login login) {
        System.out.println("Iniciando processo de inserção de Login...");
        String query = "INSERT INTO login(idFuncionario, login, senha, tipoLogin, idEmpresa, enabled) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setInt(1, login.getIdFuncionario());
            preparedStatement.setString(2, login.getLogin());
            preparedStatement.setString(3, login.getSenha());
            preparedStatement.setString(4, login.getTipoLogin());
            preparedStatement.setInt(5, login.getIdEmpresa());
            preparedStatement.setBoolean(6, true);
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Login inserido com sucesso.");
            
        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("Erro ao salvar Usuário");
        }
    }

    //altera usuário existente
    public void updateLogin(int idLogin, String nome, String login, String senha, String tipoLogin) throws Exception {
        System.out.println("Atualizando Usuário...");
        String query = "UPDATE login SET nomeFuncionario='?', login='?', "
                + "senha='?', tipoLogin='?' where idLogin='?'";
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, senha);
            preparedStatement.setString(4, tipoLogin);
            preparedStatement.setInt(5, idLogin);
            
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar login");
            throw new Exception("Erro ao atualizar login", ex);
        }
    }

    //lista usuários
    public List<Login> listarLogin(String nome, int codigoempresa) { //retorna todos itens
        List<Login> lista = new ArrayList<>();
        System.out.println("Buscando produto na base de dados...");
        String query = "";
        boolean vazio = false;
        
        if ("".equals(nome)) {
            vazio = true;
            query = "SELECT * FROM login WHERE enabled='true'";
        } else {
            vazio = false;
            query = "SELECT * FROM login WHERE nome LIKE ? and codigoempresa=?";
        }
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            if (vazio == true) {
                preparedStatement.setInt(1, codigoempresa);
            } else {
                preparedStatement.setString(1, nome + "%");
                preparedStatement.setInt(2, codigoempresa);
            }
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                Login login = new Login();
                login.setIdLogin(rs.getInt(1));
                login.setNome(rs.getString(2));
                login.setLogin(rs.getString(3));
                login.setSenha(rs.getString(4));
                login.setTipoLogin(rs.getString(5));
                lista.add(login);
            }
            
            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar usuário: " + ex);
        }
        return lista;
        
    }

    //encontra usuário por código
    public Login encontrarLogin(String username, String senha) {//retorna um item
        System.out.println("Buscando Usuário na base de dados...");
        System.out.println("estranho");
        String query = "SELECT idLogin, idFuncionario, login, senha, tipoLogin, idEmpresa, enabled FROM login WHERE login = ? and senha = ?";
        Login login = new Login();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, senha);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                login.setIdLogin(rs.getInt(1));
                login.setIdFuncionario(rs.getInt(2));
                login.setLogin(rs.getString(3));
                login.setSenha(rs.getString(4));
                login.setTipoLogin(rs.getString(5));
                login.setIdEmpresa(rs.getInt(6));
                login.setEnabled(rs.getBoolean(7));
            } else {
                System.out.println("não achou ninguem");
                return null;
            }
            return login;
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar login" + ex);
            return null;
        }
        
    }

    //encontra usuário por nome
    public boolean encontrarLoginNome(String usuario) {//retorna um boolean
        Login login = new Login();
        boolean verifica = false;
        System.out.println("Buscando Usuário na base de dados...");
        String query = "SELECT * FROM login WHERE login='?'";
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setString(1, usuario);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
                login.setIdLogin(rs.getInt(1));
                login.setNome(rs.getString(2));
                login.setLogin(rs.getString(3));
                login.setSenha(rs.getString(4));
                login.setTipoLogin(rs.getString(5));
                verifica = true;
            }
            
            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar login" + ex);
            
        }
        return verifica;
    }

    //deletar usuário
    public void deletarLogin(int idLogin) throws Exception {
        System.out.println("Deletando de codigo: " + idLogin);
        String query = "UPDATE login SET enabled='?' WHERE idLogin='?'";
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            
            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, idLogin);
            preparedStatement.execute();
            
            System.out.println("Usuário deletado");
        } catch (SQLException ex) {
            throw new Exception("Erro ao deletar usuário", ex);
        }
    }
    
    public Login encontrarUmLogin(int idLogin, String usuario) {
        List<Login> lista = new ArrayList<>();
        Login login = new Login();
        System.out.println("Buscando produto na base de dados...");
        String query = "SELECT * FROM login WHERE idLogin='?' or usuario='%?%'";
        boolean verifica = false;
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            
            preparedStatement.setInt(1, idLogin);
            preparedStatement.setString(2, usuario);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {;
                login.setIdLogin(rs.getInt("idLogin"));
                login.setLogin(rs.getString("login"));
                login.setNome(rs.getString("nome"));
                login.setSenha(rs.getString("senha"));
                login.setTipoLogin(rs.getString("tipoLogin"));
                
                lista.add(login);
                verifica = true;
                System.out.println("Busca efetuada com sucesso");
            }
            
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar produto" + ex);
        }
        
        if (verifica == false) {
            login = null;
        }
        
        return login;
        
    }
    
}
