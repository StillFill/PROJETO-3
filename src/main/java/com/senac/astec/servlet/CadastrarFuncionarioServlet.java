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
import com.senac.astec.service.ServicoFuncionario;
import com.senac.astec.service.ServicoLogin;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "cadastrarFuncionario", urlPatterns = {"/cadastrar-funcionario"})
public class CadastrarFuncionarioServlet extends HttpServlet {

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
        String destino = "Pages/CadastrarFuncionario.jsp";

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

        doHead(request, response);

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

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        Funcionario newFuncionario = new Funcionario();
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
            ServicoFuncionario servicoFuncionario = new ServicoFuncionario();
            servicoFuncionario.cadastrarFuncionario(newFuncionario);
        } catch(Exception e) {
            System.out.println("ERRO AO CRIAR FUNCIONARIO: " +e);
        }
        Login loginFuncionario = new Login();

        loginFuncionario.setIdEmpresa(idEmpresa);
        loginFuncionario.setLogin(login);
        loginFuncionario.setSenha(senha);
        loginFuncionario.setIdFuncionario(1);
        loginFuncionario.setTipoLogin("funcionario");
        
        ServicoLogin servicoLogin = new ServicoLogin();
        try {
            servicoLogin.cadastrarLogin(loginFuncionario);
        } catch (Exception e) {
            System.out.println("ERRO AO CADASTRAR FUNCIONARIO: " + e);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("Pages/CadastrarFuncionario.jsp");
        dispatcher.forward(request, response);

    }

}
