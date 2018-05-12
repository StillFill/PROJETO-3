package com.senac.astec.servlet;

import com.senac.astec.AbstracClass.CreatedTokenAbstract;
import com.senac.astec.BusinessRule.CreatedToken;
import com.senac.astec.model.Empresa;
import com.senac.astec.model.Login;
import com.senac.astec.model.Token;
import com.senac.astec.service.ServicoEmpresa;
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

/**
 *
 * @author ana.cacosta1
 */
@WebServlet(name = "cadastrarEmpresa", urlPatterns = {"/cadastrar-empresa"})
public class CadastrarEmpresaServlet extends HttpServlet{
    
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
        
        
        
        String destino = "Pages/CadastrarEmpresa.jsp";

        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);

    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("chamou o cadastro de empresa");
        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("nome");
        String cnpj = request.getParameter("cnpj");
        String cep = request.getParameter("cep");
        String logradouro = request.getParameter("logradouro");
        String addressNumber = request.getParameter("numero");
        String complement = request.getParameter("complemento");
        String neighborhood = request.getParameter("bairro");
        String city = request.getParameter("cidade");
        String state = request.getParameter("estado");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        Empresa newEmpresa = new Empresa();
        newEmpresa.setName(name);
        newEmpresa.setCnpj(cnpj);
        newEmpresa.setCEP(cep);
        newEmpresa.setLogradouro(logradouro);
        newEmpresa.setAddressNumber(addressNumber);
        newEmpresa.setComplement(complement);
        newEmpresa.setNeighborhood(neighborhood);
        newEmpresa.setCity(city);
        newEmpresa.setState(state);
        newEmpresa.setTipo("filialDono");
        
        ServicoEmpresa servicoEmpresa = new ServicoEmpresa();
        ServicoLogin servicoLogin = new ServicoLogin();
        int insertedId = 0;
        try {
            insertedId = servicoEmpresa.cadastrarEmpresa(newEmpresa);
            System.out.println("ID EMPRESA" + insertedId);
        } catch (Exception e) {
            System.out.println("OCORREU UM ERRO" + e);
        }
        
        Login loginEmpresa = new Login();
        loginEmpresa.setEnabled(true);
        loginEmpresa.setLogin(login);
        loginEmpresa.setSenha(senha);
        System.out.println("inserindo no objeto o id: " + insertedId);
        loginEmpresa.setTipoLogin("filialDono");
        loginEmpresa.setIdEmpresa(insertedId);
        try {
            servicoLogin.cadastrarLogin(loginEmpresa);
        } catch (Exception e) {
            System.out.println("OCORREU UM ERRO" + e);
        }
        
        String destino = "Pages/CadastrarEmpresa.jsp";

        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);
    }
    
}