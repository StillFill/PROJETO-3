    /*
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.service;
import com.senac.astec.dao.LoginDAO;
import com.senac.astec.model.Login;
import java.io.IOException;
import java.util.List;
//Classe de servico do cliente
public class ServicoLogin {
     LoginDAO usuarioDAO = new LoginDAO();
     

    public void cadastrarLogin(Login usuario) throws IOException, Exception {


        try {
            usuarioDAO.inserirLogin(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Atualiza um cliente na fonte de dados
    public void atualizarLogin(int codigo, int codigoempresa, int codigoperfil, String nome, String login, String senha) throws IOException, Exception {
        
        //ValidadorCliente.validar(cliente);

//        try {
//            usuarioDAO.updateLogin(codigo, codigoempresa, codigoperfil, nome, login, senha);
//        } catch (Exception e) {
//            //Imprime qualquer erro técnico no console e devolve
//            //uma exceção e uma mensagem amigável a camada de visão
//            e.printStackTrace();
//        }
    }

    //Realiza a pesquisa de um cliente por nome na fonte de dados
    public List<Login> listarLogins(String nome, int empresa) throws IOException, Exception {
        try {
            return usuarioDAO.listarLogin(nome, empresa);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
            
        }
    }
    
    //Pesquisa usuario especificado 
    public Login retornaLogin(int codigousuario, int empresa) throws IOException{
        try {
//            return usuarioDAO.encontrarUmLogin(codigousuario, empresa);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return new Login();
    }
    
    //Pesquisa usuario especificado por login para saber se já existe
    public boolean retornaLogin(String login, int empresa) throws IOException{
//        try {
//            return usuarioDAO.encontrarLoginNome(login, empresa);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
    return true;
    }
    
    //Pesquisa usuario por login e senha 
    public Login retornaLoginLogin(String login, String senha, int empresa) throws IOException{
//        try {
//            return usuarioDAO.encontrarLogin(login, senha, empresa);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    return null;
    }

    //Exclui o cliente com ID informado do mock
    public void excluirLogin(int codigo, int  codigoempresa) throws IOException, Exception {
//        try {
//            //Solicita ao DAO a exclusão do cliente informado
//            usuarioDAO.deletarLogin(codigo, codigoempresa);
//        } catch (Exception e) {
//            //Imprime qualquer erro técnico no console e devolve
//            //uma exceção e uma mensagem amigável a camada de visão
//            e.printStackTrace();
//        }
    }
}
