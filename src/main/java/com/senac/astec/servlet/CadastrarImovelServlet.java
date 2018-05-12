/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.servlet;

import com.senac.astec.AbstracClass.CreatedTokenAbstract;
import com.senac.astec.BusinessRule.CreatedToken;
import com.senac.astec.dao.ImovelDAO;
import com.senac.astec.model.Imovel;
import com.senac.astec.service.ServicoImovel;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "cadastrarImovel", urlPatterns = {"/cadastrar-imovel"})
public class CadastrarImovelServlet extends HttpServlet {

       @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null) {
            req.getRequestDispatcher("Pages/Login.jsp").forward(req, resp);
        }

        if (session.getAttribute("token") == null) {
            req.getRequestDispatcher("Pages/Login.jsp").forward(req, resp);
        }


        String token = (String) session.getAttribute("token");
        //instanciando classe responsavel pelo token
        CreatedTokenAbstract jwt = new CreatedToken();
        //analisando token
        if (!jwt.codificarToken(token)) {
            resp.setStatus(resp.SC_FORBIDDEN);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        doHead(request, response);
        
        RequestDispatcher dispatcher
                = request.getRequestDispatcher("Pages/CadastrarImoveis.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String nome = request.getParameter("nome");
            String cep = request.getParameter("cep");
            String logradouro = request.getParameter("logradouro");
            String numero = request.getParameter("numero");
            String complemento = request.getParameter("complemento");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String descricao = request.getParameter("descricao");
            int numDorm = Integer.parseInt(request.getParameter("numDorm"));
            double tamanho = Double.parseDouble(request.getParameter("tamanho"));
            int vagas = Integer.parseInt(request.getParameter("vagas"));
            int andar = Integer.parseInt(request.getParameter("andar"));
            String[] checkBoxMobiliado = request.getParameterValues("mobiliado");
            boolean mobiliado = false;
            if (checkBoxMobiliado[0] != null) {
                mobiliado = true;
            }
            String[] checkBoxPets = request.getParameterValues("pets");
            boolean pets = false;
            if (checkBoxPets.length > 0 && checkBoxPets[0] != null) {
                pets = true;
            }
            String tipoImovel = request.getParameter("tipoImovel");
            double valor = Double.parseDouble(request.getParameter("valor"));
            double condominio = Double.parseDouble(request.getParameter("condominio"));
            double iptu = Double.parseDouble(request.getParameter("iptu"));
            double seguro = Double.parseDouble(request.getParameter("seguro"));
            boolean parcelaImovel = false;
            String[] checkBoxParcela = request.getParameterValues("pets");
            if (checkBoxParcela.length > 0 && checkBoxParcela[0] != null) {
                parcelaImovel = true;
            }
            double valorEntrada = Double.parseDouble(request.getParameter("valorEntrada"));
            Imovel newImovel = new Imovel();
            newImovel.setNome(nome);
            newImovel.setCep(cep);
            newImovel.setLogradouro(logradouro);
            newImovel.setNumero(numero);
            newImovel.setComplemento(complemento);
            newImovel.setBairro(bairro);
            newImovel.setCidade(cidade);
            newImovel.setEstado(estado);
            newImovel.setDescricao(descricao);
            newImovel.setNumDormitorios(numDorm);
            newImovel.setTamanho(tamanho);
            newImovel.setVagas(vagas);
            newImovel.setAndar(andar);
            newImovel.setMobiliado(mobiliado);
            newImovel.setTipoImovel(tipoImovel);
            newImovel.setValor(valor);
            newImovel.setCondominio(condominio);
            newImovel.setPet(pets);
            newImovel.setIptu(iptu);
            newImovel.setSeguro(seguro);
            newImovel.setParcela(parcelaImovel);
            newImovel.setValorEntrada(valorEntrada);
            newImovel.setEnabled(true);
            ServicoImovel servico = new ServicoImovel();
            servico.cadastrarImovel(newImovel);
        } catch (Exception e) {
            System.out.println("DEU RUIM NO IMOVEL" + e);
        }
    }
}
