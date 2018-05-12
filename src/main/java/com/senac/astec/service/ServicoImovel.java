/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.service;
import com.senac.astec.dao.ImovelDAO;
import com.senac.astec.model.Imovel;
import java.io.IOException;
import java.util.List;
//Classe de Servico de Imovel
public class ServicoImovel {
    ImovelDAO produtoDAO = new ImovelDAO();
    
    public void cadastrarImovel(Imovel produto) throws IOException {
        
        

        try {
            //Realiza a chamada de inserção na fonte de dados
            produtoDAO.inserirImovel(produto);
        } catch (Exception e) {
            //Imprime qualquer erro técnico no console e devolve
            //uma exceção e uma mensagem amigável a camada de visão
            System.out.println("ERRO AO INSERIR" + e);
        }
    }

    //Atualiza um produto na fonte de dados
    public void atualizarImovel(Imovel produto) throws IOException {
        

        try {
            produtoDAO.updateImovel(produto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public  List<Imovel> procurarImovel(String nome, int codigoempresa) throws IOException {
        try {
        return produtoDAO.listarImovel(nome, codigoempresa);
          
        } catch (Exception e) {
            //Imprime qualquer erro técnico no console e devolve
            //uma exceção e uma mensagem amigável a camada de visão
            e.printStackTrace();
            return null;
        }
    }

    public Imovel encontrarImovelPorNome(String nome, int codigoempresa) throws IOException {
        try {
            return produtoDAO.encontrarImovel(nome, codigoempresa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Imovel encontrarImovelPorCodigo(int codigo, int codigoempresa) throws IOException {
        try {
            return produtoDAO.encontrarImovelCodigo(codigo, codigoempresa);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean encontrarImovelCadastro(String nome, int codigoempresa) throws IOException {
        try {
            return produtoDAO.encontrarImovelCadastro(nome, codigoempresa);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void excluirImovel(Integer codigo, int codigoempresa) throws IOException {
        try {
            produtoDAO.deletarImovel(codigo, codigoempresa);
        } catch (Exception e) {
            System.out.println("Erro");
        }
    }
    
    //Lista produtos de determinada empresa
    public List<Imovel> listarImovels(int codigoempresa) throws IOException, Exception {
        try {
            return produtoDAO.listarImoveis(codigoempresa);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //Lista produtos Totais
    public List<Imovel> listarImovelstotais() throws IOException, Exception {
        try {
            return produtoDAO.listarImoveistotais();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
