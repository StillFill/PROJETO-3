/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.servlet;

import com.senac.astec.AbstracClass.CreatedTokenAbstract;
import com.senac.astec.BusinessRule.CreatedToken;
import com.senac.astec.dao.ClienteDAO;
import com.senac.astec.model.Cliente;
import com.senac.astec.model.Token;
import com.senac.astec.service.ServicoCliente;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "cadastrarCliente", urlPatterns = {"/cadastrar-cliente"})
public class CadastrarClienteServlet extends HttpServlet {

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
        String destino = "Pages/CadastrarCliente.jsp";

        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("CHAMOU O POST");
        request.setCharacterEncoding("UTF-8");
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

        ServicoCliente servicoCliente = new ServicoCliente();
        try {
            servicoCliente.cadastrarCliente(newClient);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e);
        }

        response.sendRedirect(request.getContextPath() + "/cadastrar-cliente");
    }

    // deletar
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CHAMOU O DELETE");
    }

    // atualizar
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
