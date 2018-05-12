/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.servlet;

import com.google.gson.JsonObject;
import com.senac.astec.AbstracClass.CreatedTokenAbstract;
import com.senac.astec.BusinessRule.CreatedToken;
import com.senac.astec.dao.LoginDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.senac.astec.model.Login;
import java.util.Date;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dev
 */
@WebServlet(name = "Login Servlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Pages/Login.jsp").forward(request, response);
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

        LoginDAO loginDAO = new LoginDAO();
        Login user = loginDAO.encontrarLogin(request.getParameter("username"), request.getParameter("senha"));
        if (user != null) {
            
            Date dateNow = new Date();
            
            Date expires = new Date(dateNow.getTime() + 3600);

            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("iss", dateNow.getTime());

            jsonObject.addProperty("exp", expires.getTime());

            jsonObject.addProperty("id", user.getIdLogin());
            
            jsonObject.addProperty("tipoLogin", user.getTipoLogin());
            jsonObject.addProperty("idEmpresa", user.getIdEmpresa());

            jsonObject.addProperty("idFuncionario", user.getIdFuncionario());
            CreatedTokenAbstract token = new CreatedToken();

            HttpSession sessao = request.getSession();
            sessao.setAttribute("token", token.token(jsonObject));
            String path = "Pages/CadastrarCliente.jsp";
            if (user.getTipoLogin().equals("dono")) {
               path = "Pages/CadastrarEmpresa.jsp";
            } else if (user.getTipoLogin().equals("funcionario")) {
               path = "Pages/EfetuarVenda.jsp";
            } else if (user.getTipoLogin().equals("admin")) {
                path = "Pages/CadastrarEmpresa.jsp";
            }
            System.out.println("mandou pro path");
            System.out.println(path);
            request.getRequestDispatcher(path).forward(request, response);
        } else {
            System.out.println("entro no else");
            request.getRequestDispatcher("Pages/Login.jsp").forward(request, response);
        }

    }

}
