/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.servlet;

import com.senac.astec.AbstracClass.CreatedTokenAbstract;
import com.senac.astec.BusinessRule.CreatedToken;
import com.senac.astec.dao.LoginDAO;
import com.senac.astec.model.Funcionario;
import com.senac.astec.model.Login;
import com.senac.astec.model.Token;
import com.senac.astec.service.ServicoCliente;
import com.senac.astec.service.ServicoFuncionario;
import com.senac.astec.service.ServicoLogin;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "consultarFuncionarios", urlPatterns = {"/consultar-funcionarios"})
public class ConsultarFuncionariosServlet extends HttpServlet {
    
    int funcionarioId = 0;

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

        System.out.println(request.getParameter("method"));
        HttpSession session = request.getSession(false);

        String tokenJwt = (String) session.getAttribute("token");

        CreatedTokenAbstract jwt = new CreatedToken();

        Token token = (Token) jwt.decodeToken(tokenJwt);
        if (token.getTipoLogin().equals("funcionario")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("Pages/EfetuarVenda.jsp");
            dispatcher.forward(request, response);
            return;
        }
        System.out.println("TOKEN DO CLIENTE " + token.getTipoLogin());
        String destino = "Pages/ConsultarFuncionarios.jsp";

        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CHEGOU NO SERVLET CARAIO");
        doHead(request, response);
        String path = "Pages/ConsultarFuncionarios.jsp";
        funcionarioId = Integer.parseInt(request.getParameter("funcionarioId"));
        ServicoFuncionario servicoFuncionario = new ServicoFuncionario();
        System.out.println(request.getParameter("salvar") != null);
        if (request.getParameter("salvar") != null) {
            System.out.println("ENTROU NO SAVE");
            HttpSession session = request.getSession(false);

            String tokenJwt = (String) session.getAttribute("token");

            CreatedTokenAbstract jwt = new CreatedToken();

            Token token = (Token) jwt.decodeToken(tokenJwt);

            String name = request.getParameter("nome");
            double comissao = Double.parseDouble(request.getParameter("comissao"));
            String rg = request.getParameter("rg");
            String cpf = request.getParameter("cpf");
            String telefone = request.getParameter("telefone");
            String email = request.getParameter("email");
            String cep = request.getParameter("cep");
            String logradouro = request.getParameter("logradouro");
            String numero = request.getParameter("numero");
            String complemento = request.getParameter("complemento");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            int idEmpresa = token.getIdEmpresa();

            Funcionario newFuncionario = new Funcionario();
            newFuncionario.setIdFuncionario(funcionarioId);
            newFuncionario.setNome(name);
            newFuncionario.setComissao(comissao);
            newFuncionario.setRg(rg);
            newFuncionario.setCpf(cpf);
            newFuncionario.setTelefone(telefone);
            newFuncionario.setEmail(email);
            newFuncionario.setCep(cep);
            newFuncionario.setLogradouro(logradouro);
            newFuncionario.setNumero(numero);
            newFuncionario.setComplemento(complemento);
            newFuncionario.setBairro(bairro);
            newFuncionario.setCidade(cidade);
            newFuncionario.setEstado(estado);
            newFuncionario.setIdEmpresa(idEmpresa);

            try {
                System.out.println("NOME DO NOVO USUARIO: " + newFuncionario.getNome());
                servicoFuncionario.atualizarFuncionario(newFuncionario);
            } catch (Exception e) {
                System.out.println("Erro ao atualizar funcionario: " + e);
            }
        } else if (request.getParameter("editar") != null) {
            Funcionario funcionario;
            System.out.println("ENTROU NO EDITAR CARAIO");
            try {
                funcionario = servicoFuncionario.procurarCliente(funcionarioId);
                System.out.println("FUNCIONARIO ACHADO: "+ funcionario.getNome());
                request.setAttribute("funcionario", funcionario);
                path = "Pages/EditarFuncionario.jsp";
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else if (request.getParameter("remover") != null) {
            try {
                servicoFuncionario.excluirFuncionario(funcionarioId);
            } catch (Exception ex) {
                System.out.println("ERRO AO EXCLUIR CLIENTE: " + ex);
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

}
