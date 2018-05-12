/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.servlet;

import com.senac.astec.AbstracClass.CreatedTokenAbstract;
import com.senac.astec.BusinessRule.CreatedToken;
import com.senac.astec.dao.LoginDAO;
import com.senac.astec.model.Cliente;
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

@WebServlet(name = "consultarClientes", urlPatterns = {"/consultar-clientes"})
public class ConsultarClientesServlet extends HttpServlet {

    private int clientId = 0;

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
        String destino = "Pages/ConsultarClientes.jsp";

        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);
    }

    // vai deletar um cliente
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = "Pages/ConsultarClientes.jsp";
        System.out.println("do post");
        doHead(request, response);
        System.out.println(request.getParameter("clienteId"));
        int clienteId = Integer.parseInt(request.getParameter("clienteId"));
        this.clientId = clienteId;
        ServicoCliente servicoCliente = new ServicoCliente();
        if (request.getParameter("salvar") != null) {
            System.out.println("FOI ESSE SALVAR CARALHO");
            try {
                servicoCliente.excluirCliente(clienteId);
                String name = request.getParameter("name");
                String birthday = request.getParameter("birthday");
                String documentNumber = request.getParameter("documentNumber");
                String cpf = request.getParameter("cpf");
                String gender = request.getParameter("gender");
                String phone = request.getParameter("phone");
                String cellphone = request.getParameter("cellphone");
                String email = request.getParameter("email");
                String cep = request.getParameter("cep");
                String logradouro = request.getParameter("logradouro");
                String addressNumber = request.getParameter("addressNumber");
                String complement = request.getParameter("complement");
                String neighborhood = request.getParameter("neighborhood");
                String city = request.getParameter("city");
                String state = request.getParameter("state");

                HttpSession session = request.getSession(false);

                String tokenJwt = (String) session.getAttribute("token");

                CreatedTokenAbstract jwt = new CreatedToken();

                Token token = (Token) jwt.decodeToken(tokenJwt);

                int idEmpresa = token.getIdEmpresa();
                System.out.println(token.getIdEmpresa());
                Cliente newClient = new Cliente();
                System.out.println("ID DO CLIENTE: " + clientId);
                newClient.setId(clientId);
                newClient.setNome(name);
                newClient.setDataNasc(birthday);
                newClient.setRg(documentNumber);
                newClient.setCpf(cpf);
                newClient.setSexo(gender);
                newClient.setTelefone(phone);
                newClient.setCelular(cellphone);
                newClient.setEmail(email);
                newClient.setCep(cep);
                newClient.setLogradouro(logradouro);
                newClient.setNumero(addressNumber);
                newClient.setComplemento(complement);
                newClient.setBairro(neighborhood);
                newClient.setCidade(city);
                newClient.setEstado(state);
                newClient.setEnabled(true);
                newClient.setCodigoempresa(idEmpresa);
                servicoCliente.atualizarCliente(newClient);
            } catch (Exception ex) {
                System.out.println("ERRO AO EXCLUIR CLIENTE: " + ex);
            }
        } else if (request.getParameter("editar") != null) {
            try {
                Cliente cliente = servicoCliente.procurarCliente(clientId);
                request.setAttribute("cliente", cliente);
                path = "Pages/EditarCliente.jsp";
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (request.getParameter("remover") != null) {
            try {
                servicoCliente.excluirCliente(clienteId);
            } catch (Exception ex) {
                System.out.println("ERRO AO EXCLUIR CLIENTE: " + ex);
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);

    }

}
