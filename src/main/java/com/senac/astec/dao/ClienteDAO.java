/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.dao;

import com.senac.astec.model.Cliente;
import com.senac.astec.utils.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    ConexaoBanco conexaoBanco = new ConexaoBanco();

    Connection conn;
    
    public ClienteDAO(){
        this.conn = ConexaoBanco.createConnection();
    }

    public void inserirCliente(Cliente cliente) {

        String query = "INSERT INTO cliente(nome, dataNasc, rg, cpf, sexo, telefone, celular, email,"
                + "cep, logradouro, numero, complemento, bairro, cidade, estado, idEmpresa, enabled) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getDataNasc());
            preparedStatement.setString(3, cliente.getRg());
            preparedStatement.setString(4, cliente.getCpf());
            preparedStatement.setString(5, cliente.getSexo());
            preparedStatement.setString(6, cliente.getTelefone());
            preparedStatement.setString(7, cliente.getCelular());
            preparedStatement.setString(8, cliente.getEmail());
            preparedStatement.setString(9, cliente.getCep());
            preparedStatement.setString(10, cliente.getLogradouro());
            preparedStatement.setString(11, cliente.getNumero());
            preparedStatement.setString(12, cliente.getComplemento());
            preparedStatement.setString(13, cliente.getBairro());
            preparedStatement.setString(14, cliente.getCidade());
            preparedStatement.setString(15, cliente.getEstado());
            System.out.println(cliente.getCodigoempresa());
            preparedStatement.setInt(16, cliente.getCodigoempresa());
            preparedStatement.setBoolean(17, true);
            System.out.println("deu bom");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println("deu ruim");
            System.out.println("Erro ao salvar cliente" + ex);
        }
    }

    public Cliente updateCliente(Cliente cliente) throws Exception {
        System.out.println("Iniciando processo de atualização de cliente...");
        String query = "UPDATE cliente SET nome=?, dataNasc=?, rg=?,"
                + "cpf=?, sexo=?, telefone=?, celular=?, email=?, "
                + "cep=?, logradouro=?, numero=?, complemento=?, "
                + "bairro=?, cidade=?, estado=?, idEmpresa=?, enabled=? "
                + "WHERE idCliente=?";

        try {
            System.out.println(cliente.getNome());
            System.out.println(cliente.getId());
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getDataNasc());
            preparedStatement.setString(3, cliente.getRg());
            preparedStatement.setString(4, cliente.getCpf());
            preparedStatement.setString(5, cliente.getSexo());
            preparedStatement.setString(6, cliente.getTelefone());
            preparedStatement.setString(7, cliente.getCelular());
            preparedStatement.setString(8, cliente.getEmail());
            preparedStatement.setString(9, cliente.getCep());
            preparedStatement.setString(10, cliente.getLogradouro());
            preparedStatement.setString(11, cliente.getNumero());
            preparedStatement.setString(12, cliente.getComplemento());
            preparedStatement.setString(13, cliente.getBairro());
            preparedStatement.setString(14, cliente.getCidade());
            preparedStatement.setString(15, cliente.getEstado());
            preparedStatement.setInt(16, cliente.getCodigoempresa());
            preparedStatement.setBoolean(17, true);
            preparedStatement.setInt(18, cliente.getId());

            System.out.println("cpf: " + cliente.getCpf());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            throw new Exception("Erro ao atualizar cliente!", ex);
        }

        return cliente;

    }

    public List<Cliente> listarCliente(String nome, int codigoempresa) throws Exception {
        System.out.println("Iniciando listagem de cliente...");
        List<Cliente> lista = new ArrayList<>();
        String query = "";

        boolean vazio = true;

        if (nome.length() == 0) {
            vazio = true;
            query = "SELECT * FROM clientes WHERE codigoempresa=?";
        } else {
            vazio = false;
            query = "SELECT * FROM clientes WHERE nome LIKE ? and codigoempresa = ?";
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
                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNasc(rs.getString("dataNasc"));
                cliente.setRg(rs.getString("rg"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setSexo(rs.getString("sexo"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setCelular(rs.getString("celular"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCep(rs.getString("cep"));
                cliente.setLogradouro(rs.getString("logradouro"));
                cliente.setNumero(rs.getString("numero"));
                cliente.setComplemento(rs.getString("complemento"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setEstado(rs.getString("estado"));
                cliente.setEnabled(true);
                cliente.setCodigoempresa(rs.getInt("codigoempresa"));
                lista.add(cliente);
            }

        } catch (SQLException ex) {
            throw new Exception("Erro ao listar cliente", ex);
        }

        return lista;

    }

    public List<Cliente> listarClientes(int codigoempresa) throws Exception {
        System.out.println("Iniciando listagem de cliente...");
        List<Cliente> lista = new ArrayList<>();
        String query = "";

        boolean vazio = true;

        query = "SELECT * FROM clientes WHERE codigoempresa=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, codigoempresa);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNasc(rs.getString("dataNasc"));
                cliente.setRg(rs.getString("rg"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setSexo(rs.getString("sexo"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setCelular(rs.getString("celular"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCep(rs.getString("cep"));
                cliente.setLogradouro(rs.getString("logradouro"));
                cliente.setNumero(rs.getString("numero"));
                cliente.setComplemento(rs.getString("complemento"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setEstado(rs.getString("estado"));
                cliente.setEnabled(true);
                cliente.setCodigoempresa(rs.getInt("codigoempresa"));
                lista.add(cliente);
            }

        } catch (SQLException ex) {
            throw new Exception("Erro ao listar cliente", ex);
        }

        return lista;

    }

    public List<Cliente> listarClientestotais() throws Exception {
        System.out.println("Iniciando listagem de cliente...");
        List<Cliente> lista = new ArrayList<>();
        String query = "";

        boolean vazio = true;

        query = "SELECT * FROM clientes";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNasc(rs.getString("dataNasc"));
                cliente.setRg(rs.getString("rg"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setSexo(rs.getString("sexo"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setCelular(rs.getString("celular"));
                cliente.setEmail(rs.getString("email"));
                cliente.setCep(rs.getString("cep"));
                cliente.setLogradouro(rs.getString("logradouro"));
                cliente.setNumero(rs.getString("numero"));
                cliente.setComplemento(rs.getString("complemento"));
                cliente.setBairro(rs.getString("bairro"));
                cliente.setCidade(rs.getString("cidade"));
                cliente.setEstado(rs.getString("estado"));
                cliente.setEnabled(true);
                cliente.setCodigoempresa(rs.getInt("codigoempresa"));
                lista.add(cliente);
            }

        } catch (SQLException ex) {
            throw new Exception("Erro ao listar cliente", ex);
        }

        return lista;

    }

    public Cliente encontrarClientePorCpf(String cpf, int codigoempresa) throws Exception {
        System.out.println("Iniciando listagem de cliente...");

        Cliente cliente = new Cliente();
        String query = "SELECT * FROM clientes WHERE cpf LIKE ? and codigoempresa=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cpf + "%");
            preparedStatement.setInt(2, codigoempresa);

            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("Busca efetuada com sucesso");

            while (rs.next()) {
                cliente.setId(rs.getInt(1));
                cliente.setNome(rs.getString(2));
                cliente.setDataNasc(rs.getString(3));
                cliente.setRg(rs.getString(4));
                cliente.setCpf(rs.getString(5));
                cliente.setSexo(rs.getString(6));
                cliente.setTelefone(rs.getString(7));
                cliente.setCelular(rs.getString(8));
                cliente.setEmail(rs.getString(9));
                cliente.setCep(rs.getString(10));
                cliente.setLogradouro(rs.getString(11));
                cliente.setNumero(rs.getString(12));
                cliente.setComplemento(rs.getString(13));
                cliente.setBairro(rs.getString(14));
                cliente.setCidade(rs.getString(15));
                cliente.setEstado(rs.getString(17));
                cliente.setEnabled(true);
                cliente.setCodigoempresa(rs.getInt("codigoempresa"));
                
            }

        } catch (SQLException ex) {
            throw new Exception("Erro ao listar cliente", ex);
        }

        return cliente;
    }

    public Cliente encontrarClientePorIdCarrinho(int id, int codigoempresa) throws Exception {
        System.out.println("Iniciando listagem de cliente...");

        Cliente cliente = new Cliente();
        String query = "SELECT * FROM clientes WHERE id=? and codigoempresa = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(id));
            preparedStatement.setString(2, String.valueOf(codigoempresa));

            ResultSet rs = preparedStatement.executeQuery();

            System.out.println("Busca efetuada com sucesso");

            while (rs.next()) {
                cliente.setId(rs.getInt(1));
                cliente.setNome(rs.getString(2));
                cliente.setDataNasc(rs.getString(3));
                cliente.setRg(rs.getString(4));
                cliente.setCpf(rs.getString(5));
                cliente.setSexo(rs.getString(6));
                cliente.setTelefone(rs.getString(7));
                cliente.setCelular(rs.getString(8));
                cliente.setEmail(rs.getString(9));
                cliente.setCep(rs.getString(10));
                cliente.setLogradouro(rs.getString(11));
                cliente.setNumero(rs.getString(12));
                cliente.setComplemento(rs.getString(13));
                cliente.setBairro(rs.getString(14));
                cliente.setCidade(rs.getString(15));
                cliente.setEstado(rs.getString(17));
                cliente.setEnabled(true);
                cliente.setCodigoempresa(rs.getInt("codigoempresa"));
            }

        } catch (SQLException ex) {
            throw new Exception("Erro ao listar cliente", ex);
        }

        return cliente;
    }

    public Cliente encontrarClientePorId(int id) throws Exception {
        System.out.println("Iniciando listagem de cliente...");

        Cliente cliente = new Cliente();
        String query = "SELECT * FROM cliente WHERE idCliente=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, id);

            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Busca efetuada com sucesso");

            while (rs.next()) {
                cliente.setId(rs.getInt(1));
                cliente.setNome(rs.getString(2));
                cliente.setDataNasc(rs.getString(3));
                cliente.setRg(rs.getString(4));
                cliente.setCpf(rs.getString(5));
                cliente.setSexo(rs.getString(6));
                cliente.setTelefone(rs.getString(7));
                cliente.setCelular(rs.getString(8));
                cliente.setEmail(rs.getString(9));
                cliente.setCep(rs.getString(10));
                cliente.setLogradouro(rs.getString(11));
                cliente.setNumero(rs.getString(12));
                cliente.setComplemento(rs.getString(13));
                cliente.setBairro(rs.getString(14));
                cliente.setCidade(rs.getString(15));
                cliente.setEstado(rs.getString(17));
                cliente.setEnabled(true);
                cliente.setCodigoempresa(rs.getInt("idEmpresa"));
            }

        } catch (Exception ex) {
            System.out.println("ERRO AO ENCONTRAR CLIENTE NO BANCO: " + ex);
        }

        return cliente;
    }

    public void deletarCliente(int clienteId) throws Exception {
        System.out.println("Deletando clientes de cpf: " + clienteId);
        String query = "UPDATE cliente SET enabled='0' WHERE idCliente = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, clienteId);

            preparedStatement.execute();
            System.out.println("Cliente deletado");
        } catch (SQLException ex) {
            throw new Exception("Erro ao deletar o cliente", ex);

        }
    }
}
