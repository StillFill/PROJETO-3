/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senac.astec.dao;

import com.senac.astec.model.Funcionario;
import com.senac.astec.utils.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author msgre
 */
public class FuncionarioDAO {

    ConexaoBanco conexaoBanco = new ConexaoBanco();

    Connection conn = conexaoBanco.createConnection();

    public int inserirFuncionario(Funcionario funcionario) {

        String query = "INSERT INTO funcionario(nome, comissao, rg, cpf, telefone, email,"
                + "cep, logradouro, numero, complemento, bairro, cidade, estado, idEmpresa, enabled) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println("começou a inserir o funcionario");
        try {
            System.out.println(query);
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            System.out.println("passou da conexão");
            preparedStatement.setString(1, funcionario.getNome());
            preparedStatement.setDouble(2, funcionario.getComissao());
            preparedStatement.setString(3, funcionario.getRg());
            preparedStatement.setString(4, funcionario.getCpf());
            preparedStatement.setString(5, funcionario.getTelefone());
            preparedStatement.setString(6, funcionario.getEmail());
            preparedStatement.setString(7, funcionario.getCep());
            preparedStatement.setString(8, funcionario.getLogradouro());
            preparedStatement.setString(9, funcionario.getNumero());
            preparedStatement.setString(10, funcionario.getComplemento());
            preparedStatement.setString(11, funcionario.getBairro());
            preparedStatement.setString(12, funcionario.getCidade());
            preparedStatement.setString(13, funcionario.getEstado());
            preparedStatement.setInt(14, funcionario.getIdEmpresa());
            preparedStatement.setBoolean(15, true);
            System.out.println("vai tentar inserir no banco");
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                System.out.println("Inserted ID -" + id); // display inserted record
                return id;
            }
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println("deu ruim");
            System.out.println("Erro ao inserir funcionario" + ex);
        }
        return 0;
    }

    public void deletarFuncionario(int funcionarioId) throws Exception {
        System.out.println("Deletando funcionario de id: " + funcionarioId);
        String query = "UPDATE funcionario SET enabled='0' WHERE idFuncionario = ?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, funcionarioId);

            preparedStatement.execute();
            System.out.println("Funcionario deletado");
        } catch (SQLException ex) {
            throw new Exception("Erro ao deletar o cliente", ex);

        }
    }

    public Funcionario encontrarFuncionarioPorId(int funcionarioId) throws Exception {
        System.out.println("procurando funcionario de id: " + funcionarioId);
        String query = "SELECT * FROM funcionario WHERE idFuncionario = ?";
        Funcionario funcionario = new Funcionario();
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, funcionarioId);

            ResultSet rs = preparedStatement.executeQuery();
            System.out.println("Busca efetuada com sucesso");
            while (rs.next()) {
                funcionario.setIdFuncionario(rs.getInt(1));
                funcionario.setNome(rs.getString(2));
                funcionario.setRg(rs.getString(4));
                funcionario.setCpf(rs.getString(5));
                funcionario.setTelefone(rs.getString(7));
                funcionario.setEmail(rs.getString(9));
                funcionario.setCep(rs.getString(10));
                funcionario.setLogradouro(rs.getString(11));
                funcionario.setNumero(rs.getString(12));
                funcionario.setComplemento(rs.getString(13));
                funcionario.setBairro(rs.getString(14));
                funcionario.setCidade(rs.getString(15));
                funcionario.setEstado(rs.getString(16));
                funcionario.setEnabled(true);
                funcionario.setIdEmpresa(rs.getInt("idEmpresa"));
            }
            System.out.println("FUNCIONARIO ACHADO: " + funcionario.getIdFuncionario());
        } catch (SQLException ex) {
            System.out.println("Erro ao procurar o cliente" + ex);
        }
        return funcionario;
    }

    public void updateFuncionario(Funcionario funcionario) throws Exception {
        String query = "UPDATE funcionario SET nome=?, comissao=?, rg=?, cpf=?, telefone=?, email=?, cep=?, logradouro=?, numero=?"
                + ", complemento=?, bairro=?, cidade=?, estado=?, idEmpresa=?, enabled='1' where idFuncionario=?";

        try {
            System.out.println(funcionario.getIdFuncionario());
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, funcionario.getNome());
            preparedStatement.setDouble(2, funcionario.getComissao());
            preparedStatement.setString(3, funcionario.getRg());
            preparedStatement.setString(4, funcionario.getCpf());
            preparedStatement.setString(5, funcionario.getTelefone());
            preparedStatement.setString(6, funcionario.getEmail());
            preparedStatement.setString(7, funcionario.getCep());
            preparedStatement.setString(8, funcionario.getLogradouro());
            preparedStatement.setString(9, funcionario.getNumero());
            preparedStatement.setString(10, funcionario.getComplemento());
            preparedStatement.setString(11, funcionario.getBairro());
            preparedStatement.setString(12, funcionario.getCidade());
            preparedStatement.setString(13, funcionario.getEstado());
            preparedStatement.setInt(14, funcionario.getIdEmpresa());
            preparedStatement.setInt(15, funcionario.getIdFuncionario());

            preparedStatement.execute();
            System.out.println("Funcionario atualizado");
        } catch (SQLException ex) {
            throw new Exception("Erro ao atualizar o cliente", ex);

        }
    }
}
