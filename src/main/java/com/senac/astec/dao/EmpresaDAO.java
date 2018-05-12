/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.dao;

import com.senac.astec.model.Empresa;
import com.senac.astec.utils.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDAO {

    ConexaoBanco conexaoBanco = new ConexaoBanco();

    Connection conn = conexaoBanco.createConnection();

    public int inserirEmpresa(Empresa empresa) {

        String query = " insert into empresa (nome, cnpj, cep, logradouro, numero, complemento, bairro, cidade, estado, tipo, enabled)"
                + " values (?, ?, ? ,? ,? ,? ,? ,? ,?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println(empresa.getName());
            System.out.println(empresa.getCnpj());
            System.out.println(empresa.getCEP());
            System.out.println(empresa.getLogradouro());
            System.out.println(empresa.getTipo());
            preparedStatement.setString(1, empresa.getName());
            preparedStatement.setString(2, empresa.getCnpj());
            preparedStatement.setString(3, empresa.getCEP());
            preparedStatement.setString(4, empresa.getLogradouro());
            preparedStatement.setString(5, empresa.getAddressNumber());
            preparedStatement.setString(6, empresa.getComplement());
            preparedStatement.setString(7, empresa.getNeighborhood());
            preparedStatement.setString(8, empresa.getCity());
            preparedStatement.setString(9, empresa.getState());
            preparedStatement.setString(10, empresa.getTipo());
            preparedStatement.setBoolean(11, true);

            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                System.out.println("tem next");
                int id = rs.getInt(1);
                System.out.println("Inserted ID -" + id); // display inserted record
                return id;
            }

            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao salvar empresa" + ex);
        }
        return 0;
    }

    public Empresa updateEmpresa(Empresa empresa) throws Exception {
        System.out.println("Iniciando processo de atualização de empresa...");
        String query = "UPDATE empresas SET nome=?,cnpj=?,cep=?,logradouro=?,numero=?,compemento=?,bairro=?,cidade=?,estado=?,idLogin=? WHERE id=?";

        System.out.println(empresa.toString());
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, empresa.getName());
            preparedStatement.setString(2, empresa.getCnpj());
            preparedStatement.setString(3, empresa.getCEP());
            preparedStatement.setString(4, empresa.getLogradouro());
            preparedStatement.setString(5, empresa.getAddressNumber());
            preparedStatement.setString(6, empresa.getComplement());
            preparedStatement.setString(7, empresa.getNeighborhood());
            preparedStatement.setString(8, empresa.getCity());
            preparedStatement.setString(9, empresa.getState());
            preparedStatement.setString(10, empresa.getIdGerente());
            preparedStatement.setInt(11, empresa.getId());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new Exception("Erro ao atualizar empresa!", ex);
        }

        return empresa;

    }

    public List<Empresa> listarEmpresa(String nome, int codigoempresa) throws Exception {
        System.out.println("Iniciando listagem de empresa...");
        List<Empresa> lista = new ArrayList<>();
        String query = "";

        boolean vazio = true;

        if (nome.length() == 0) {
            vazio = true;
            query = "SELECT * FROM empresas WHERE codigoempresa=?";
        } else {
            vazio = false;
            query = "SELECT * FROM empresas WHERE nome LIKE ? and codigoempresa = ?";
        }

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            if (vazio != true) {
                preparedStatement.setString(1, nome + "%");
                preparedStatement.setInt(2, codigoempresa);
            } else {
                preparedStatement.setInt(1, codigoempresa);
            }

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Empresa empresa = new Empresa();

                empresa.setId(rs.getInt("id"));
                empresa.setName(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setCEP(rs.getString("cep"));
                empresa.setLogradouro(rs.getString("logradouro"));
                empresa.setAddressNumber(rs.getString("numero"));
                empresa.setComplement(rs.getString("complemento"));
                empresa.setNeighborhood(rs.getString("bairro"));
                empresa.setCity(rs.getString("cidade"));
                empresa.setState(rs.getString("estado"));
                empresa.setIdGerente(rs.getString("idGerente"));

                lista.add(empresa);
            }

        } catch (SQLException ex) {
            throw new Exception("Erro ao listar empresa", ex);
        }

        return lista;

    }

    public List<Empresa> listarEmpresas(int codigoempresa) throws Exception {
        System.out.println("Iniciando listagem de empresa...");
        List<Empresa> lista = new ArrayList<>();
        String query = "";

        query = "SELECT * FROM empresas WHERE codigoempresa=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, codigoempresa);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Empresa empresa = new Empresa();

                empresa.setId(rs.getInt("id"));
                empresa.setName(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setCEP(rs.getString("cep"));
                empresa.setLogradouro(rs.getString("logradouro"));
                empresa.setAddressNumber(rs.getString("numero"));
                empresa.setComplement(rs.getString("complement"));
                empresa.setNeighborhood(rs.getString("bairro"));
                empresa.setCity(rs.getString("cidade"));
                empresa.setState(rs.getString("estado"));
                empresa.setIdGerente(rs.getString("idGerente"));
                lista.add(empresa);
            }

        } catch (SQLException ex) {
            throw new Exception("Erro ao listar empresa", ex);
        }

        return lista;

    }

    public List<Empresa> listarEmpresastotais() throws Exception {
        System.out.println("Iniciando listagem de empresa...");
        List<Empresa> lista = new ArrayList<>();
        String query = "";

        boolean vazio = true;

        query = "SELECT * FROM empresas";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Empresa empresa = new Empresa();

                empresa.setId(rs.getInt("id"));
                empresa.setName(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setCEP(rs.getString("cep"));
                empresa.setLogradouro(rs.getString("logradouro"));
                empresa.setAddressNumber(rs.getString("numero"));
                empresa.setComplement(rs.getString("complement"));
                empresa.setNeighborhood(rs.getString("bairro"));
                empresa.setCity(rs.getString("cidade"));
                empresa.setState(rs.getString("estado"));
                empresa.setIdGerente(rs.getString("idGerente"));
                lista.add(empresa);
            }

        } catch (SQLException ex) {
            throw new Exception("Erro ao listar empresa", ex);
        }

        return lista;

    }

    public Empresa encontrarEmpresaPorCpf(String cpf, int codigoempresa) throws Exception {
        System.out.println("Iniciando listagem de empresa...");

        Empresa empresa = new Empresa();
        String query = "SELECT * FROM empresas WHERE cpf LIKE ? and codigoempresa=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cpf + "%");
            preparedStatement.setInt(2, codigoempresa);

            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("Busca efetuada com sucesso");

            while (rs.next()) {
                empresa.setId(rs.getInt("id"));
                empresa.setName(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setCEP(rs.getString("cep"));
                empresa.setLogradouro(rs.getString("logradouro"));
                empresa.setAddressNumber(rs.getString("numero"));
                empresa.setComplement(rs.getString("complement"));
                empresa.setNeighborhood(rs.getString("bairro"));
                empresa.setCity(rs.getString("cidade"));
                empresa.setState(rs.getString("estado"));
                empresa.setIdGerente(rs.getString("idGerente"));
            }

        } catch (SQLException ex) {
            throw new Exception("Erro ao listar empresa", ex);
        }

        return empresa;
    }

    public Empresa encontrarEmpresaPorIdCarrinho(int id, int codigoempresa) throws Exception {
        System.out.println("Iniciando listagem de empresa...");

        Empresa empresa = new Empresa();
        String query = "SELECT * FROM empresas WHERE id=? and codigoempresa = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.setString(2, String.valueOf(codigoempresa));

            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("Busca efetuada com sucesso");

            while (rs.next()) {
                empresa.setId(rs.getInt("id"));
                empresa.setName(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setCEP(rs.getString("cep"));
                empresa.setLogradouro(rs.getString("logradouro"));
                empresa.setAddressNumber(rs.getString("numero"));
                empresa.setComplement(rs.getString("complement"));
                empresa.setNeighborhood(rs.getString("bairro"));
                empresa.setCity(rs.getString("cidade"));
                empresa.setState(rs.getString("estado"));
                empresa.setIdGerente(rs.getString("idGerente"));
            }

        } catch (SQLException ex) {
            throw new Exception("Erro ao listar empresa", ex);
        }

        return empresa;
    }

    public Empresa encontrarEmpresaPorId(int id) throws Exception {
        System.out.println("Iniciando listagem de empresa...");

        Empresa empresa = new Empresa();
        String query = "SELECT * FROM empresas WHERE id=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(id));

            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("Busca efetuada com sucesso");

            while (rs.next()) {
                empresa.setId(rs.getInt("id"));
                empresa.setName(rs.getString("nome"));
                empresa.setCnpj(rs.getString("cnpj"));
                empresa.setCEP(rs.getString("cep"));
                empresa.setLogradouro(rs.getString("logradouro"));
                empresa.setAddressNumber(rs.getString("numero"));
                empresa.setComplement(rs.getString("complement"));
                empresa.setNeighborhood(rs.getString("bairro"));
                empresa.setCity(rs.getString("cidade"));
                empresa.setState(rs.getString("estado"));
                empresa.setIdGerente(rs.getString("idGerente"));
            }

        } catch (SQLException ex) {
            throw new Exception("Erro ao listar empresa", ex);
        }

        return empresa;
    }

    public void deletarEmpresa(String cpf, int codigoempresa) throws Exception {
        System.out.println("Deletando empresas de cpf: " + cpf);
        String query = "DELETE FROM empresas WHERE cpf=? and codigoempresa=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, cpf);
            preparedStatement.setInt(2, codigoempresa);

            preparedStatement.execute();
            System.out.println("Cliente deletado");
        } catch (SQLException ex) {
            throw new Exception("Erro ao deletar o empresa", ex);

        }
    }
}
