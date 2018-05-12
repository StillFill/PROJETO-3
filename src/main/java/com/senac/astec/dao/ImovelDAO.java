package com.senac.astec.dao;

import com.senac.astec.model.Imovel;
import com.senac.astec.utils.ConexaoBanco;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImovelDAO {

    ConexaoBanco conexaoBanco = new ConexaoBanco();
    Connection conn = conexaoBanco.createConnection();

    //insere imovel
    public void inserirImovel(Imovel imovel) {
        System.out.println("Iniciando processo de inserção de imovel...");
        String query = "INSERT INTO imovel(nome, cep, logradouro, numero, "
                + "complemento, bairro, cidade, estado, descricao, "
                + "numDormitorios, tamanho, vagas, andar, mobiliado, pet, "
                + "tipoImovel, valor, condominio, iptu, seguro, parcela, "
                + "valorEntrada, codigoEmpresa, enabled) VALUES (?, ?, ?, ?, ?, ?,"
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, imovel.getNome());
            preparedStatement.setString(2, imovel.getCep());
            preparedStatement.setString(3, imovel.getLogradouro());
            preparedStatement.setString(4, imovel.getNumero());
            preparedStatement.setString(5, imovel.getComplemento());
            preparedStatement.setString(6, imovel.getBairro());
            preparedStatement.setString(7, imovel.getCidade());
            preparedStatement.setString(8, imovel.getEstado());
            preparedStatement.setString(9, imovel.getDescricao());
            preparedStatement.setInt(10, imovel.getNumDormitorios());
            preparedStatement.setDouble(11, imovel.getTamanho());
            preparedStatement.setInt(12, imovel.getVagas());
            preparedStatement.setInt(13, imovel.getAndar());
            preparedStatement.setBoolean(14, imovel.isMobiliado());
            preparedStatement.setBoolean(15, imovel.isPet());
            preparedStatement.setString(16, imovel.getTipoImovel());
            preparedStatement.setDouble(17, imovel.getValor());
            preparedStatement.setDouble(18, imovel.getCondominio());
            preparedStatement.setDouble(19, imovel.getIptu());
            preparedStatement.setDouble(20, imovel.getSeguro());
            preparedStatement.setBoolean(21, imovel.isParcela());
            preparedStatement.setDouble(22, imovel.getValorEntrada());
            preparedStatement.setInt(23, 1);
            preparedStatement.setBoolean(24, true);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Imovel inserido com sucesso.");

        } catch (SQLException ex) {
            System.out.println(ex);
            System.out.println("Erro ao salvar imovel");
        }
    }

    //atualiza imovel
    public Imovel updateImovel(Imovel imovel) throws Exception {
        System.out.println("Atualizando imovel...");
        String query = "UPDATE imovel SET nome='?', cep'?', logradouro'?', "
                + "numero'?', complemento='?', bairro='?', cidade='?', "
                + "estado='?', descricao='?', numDormitorios='?', "
                + "tamanho='?', vagas='?', andar='?', mobiliado='?', "
                + "pet='?', tipoImovel='?', valor='?', condominio='?', "
                + "iptu='?', seguro='?', parcela='?', valorEntrada='?', "
                + "codigoEmpresa='?', enabled='?' WHERE idImovel='?'";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, imovel.getNome());
            preparedStatement.setString(2, imovel.getCep());
            preparedStatement.setString(3, imovel.getLogradouro());
            preparedStatement.setString(4, imovel.getNumero());
            preparedStatement.setString(5, imovel.getComplemento());
            preparedStatement.setString(6, imovel.getBairro());
            preparedStatement.setString(7, imovel.getCidade());
            preparedStatement.setString(8, imovel.getEstado());
            preparedStatement.setString(8, imovel.getDescricao());
            preparedStatement.setInt(9, imovel.getNumDormitorios());
            preparedStatement.setDouble(10, imovel.getTamanho());
            preparedStatement.setInt(11, imovel.getVagas());
            preparedStatement.setInt(12, imovel.getAndar());
            preparedStatement.setBoolean(13, imovel.isMobiliado());
            preparedStatement.setBoolean(14, imovel.isPet());
            preparedStatement.setString(15, imovel.getTipoImovel());
            preparedStatement.setDouble(16, imovel.getValor());
            preparedStatement.setDouble(17, imovel.getCondominio());
            preparedStatement.setDouble(18, imovel.getIptu());
            preparedStatement.setDouble(19, imovel.getSeguro());
            preparedStatement.setBoolean(20, imovel.isParcela());
            preparedStatement.setDouble(21, imovel.getValorEntrada());
            preparedStatement.setInt(22, imovel.getCodigoempresa());
            preparedStatement.setBoolean(23, imovel.isEnabled());
            preparedStatement.setInt(24, imovel.getIdImovel());

            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar imovel");
            throw new Exception("Erro ao atualizar imovel", ex);
        }

        return imovel;
    }

    //lista imovel
    public List<Imovel> listarImovel(String nome, int codigoempresa) { //retorna todos itens
        List<Imovel> lista = new ArrayList<>();
        System.out.println("Buscando imovel na base de dados...");
        String query = "";

        boolean vazio = true;

        if (nome.length() == 0) {
            vazio = true;
            query = "SELECT * FROM imovel WHERE codigoempresa = '?'";
        } else {
            vazio = false;
            query = "SELECT * FROM imovel WHERE nome LIKE '?' and codigoempresa = '?'";
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
                Imovel imovel = new Imovel();

                imovel.setIdImovel(rs.getInt(1));
                imovel.setNome(rs.getString(2));
                imovel.setCep(rs.getString(3));
                imovel.setLogradouro(rs.getString(4));
                imovel.setNumero(rs.getString(5));
                imovel.setComplemento(rs.getString(6));
                imovel.setBairro(rs.getString(7));
                imovel.setCidade(rs.getString(8));
                imovel.setEstado(rs.getString(9));
                imovel.setDescricao(rs.getString(10));
                imovel.setNumDormitorios(rs.getInt(11));
                imovel.setTamanho(rs.getDouble(12));
                imovel.setVagas(rs.getInt(13));
                imovel.setAndar(rs.getInt(14));
                imovel.setMobiliado(rs.getBoolean(15));
                imovel.setPet(rs.getBoolean(16));
                imovel.setTipoImovel(rs.getString(17));
                imovel.setValor(rs.getDouble(18));
                imovel.setCondominio(rs.getDouble(19));
                imovel.setIptu(rs.getDouble(20));
                imovel.setSeguro(rs.getDouble(21));
                imovel.setParcela(rs.getBoolean(22));
                imovel.setValorEntrada(rs.getDouble(23));
                imovel.setCodigoempresa(rs.getInt(24));
                imovel.setEnabled(rs.getBoolean(25));

                lista.add(imovel);
            }

            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar imovel" + ex);
        }
        return lista;

    }

    //lista imovel
    public List<Imovel> listarImoveis(int codigoempresa) { //retorna todos itens
        List<Imovel> lista = new ArrayList<>();
        System.out.println("Buscando imovel na base de dados...");
        String query = "";

        query = "SELECT * FROM imovel WHERE codigoempresa = '?'";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, codigoempresa);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Imovel imovel = new Imovel();
                imovel.setIdImovel(rs.getInt(1));
                imovel.setNome(rs.getString(2));
                imovel.setCep(rs.getString(3));
                imovel.setLogradouro(rs.getString(4));
                imovel.setNumero(rs.getString(5));
                imovel.setComplemento(rs.getString(6));
                imovel.setBairro(rs.getString(7));
                imovel.setCidade(rs.getString(8));
                imovel.setEstado(rs.getString(9));
                imovel.setDescricao(rs.getString(10));
                imovel.setNumDormitorios(rs.getInt(11));
                imovel.setTamanho(rs.getDouble(12));
                imovel.setVagas(rs.getInt(13));
                imovel.setAndar(rs.getInt(14));
                imovel.setMobiliado(rs.getBoolean(15));
                imovel.setPet(rs.getBoolean(16));
                imovel.setTipoImovel(rs.getString(17));
                imovel.setValor(rs.getDouble(18));
                imovel.setCondominio(rs.getDouble(19));
                imovel.setIptu(rs.getDouble(20));
                imovel.setSeguro(rs.getDouble(21));
                imovel.setParcela(rs.getBoolean(22));
                imovel.setValorEntrada(rs.getDouble(23));
                imovel.setCodigoempresa(rs.getInt(24));
                imovel.setEnabled(rs.getBoolean(25));
                lista.add(imovel);
            }

            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar imovel" + ex);
        }
        return lista;

    }

    //lista imovel
    public List<Imovel> listarImoveistotais() { //retorna todos itens
        List<Imovel> lista = new ArrayList<>();
        System.out.println("Buscando imovel na base de dados...");
        String query = "";

        query = "SELECT * FROM imovel";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Imovel imovel = new Imovel();
                imovel.setIdImovel(rs.getInt(1));
                imovel.setNome(rs.getString(2));
                imovel.setCep(rs.getString(3));
                imovel.setLogradouro(rs.getString(4));
                imovel.setNumero(rs.getString(5));
                imovel.setComplemento(rs.getString(6));
                imovel.setBairro(rs.getString(7));
                imovel.setCidade(rs.getString(8));
                imovel.setEstado(rs.getString(9));
                imovel.setDescricao(rs.getString(10));
                imovel.setNumDormitorios(rs.getInt(11));
                imovel.setTamanho(rs.getDouble(12));
                imovel.setVagas(rs.getInt(13));
                imovel.setAndar(rs.getInt(14));
                imovel.setMobiliado(rs.getBoolean(15));
                imovel.setPet(rs.getBoolean(16));
                imovel.setTipoImovel(rs.getString(17));
                imovel.setValor(rs.getDouble(18));
                imovel.setCondominio(rs.getDouble(19));
                imovel.setIptu(rs.getDouble(20));
                imovel.setSeguro(rs.getDouble(21));
                imovel.setParcela(rs.getBoolean(22));
                imovel.setValorEntrada(rs.getDouble(23));
                imovel.setCodigoempresa(rs.getInt(24));
                imovel.setEnabled(rs.getBoolean(25));

                lista.add(imovel);
            }

            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar imovel" + ex);
        }
        return lista;

    }

    //encontra imovel por nome
    public Imovel encontrarImovel(String nome, int codigoempresa) {
        Imovel imovel = new Imovel();
        System.out.println("Buscando imovel na base de dados...");
        String query = "";
        boolean vazio = false;

        if (nome.length() == 0) {
            vazio = true;
            query = "SELECT * FROM imovel WHERE codigoempresa='%?%'";
        } else {
            query = "SELECT * FROM imovel WHERE nome='%?%' and codigoempresa='?'";
        }

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            if (vazio = false) {
                preparedStatement.setString(1, nome);
                preparedStatement.setInt(2, codigoempresa);
            } else {
                preparedStatement.setInt(1, codigoempresa);
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                imovel.setIdImovel(rs.getInt(1));
                imovel.setNome(rs.getString(2));
                imovel.setCep(rs.getString(3));
                imovel.setLogradouro(rs.getString(4));
                imovel.setNumero(rs.getString(5));
                imovel.setComplemento(rs.getString(6));
                imovel.setBairro(rs.getString(7));
                imovel.setCidade(rs.getString(8));
                imovel.setEstado(rs.getString(9));
                imovel.setDescricao(rs.getString(10));
                imovel.setNumDormitorios(rs.getInt(11));
                imovel.setTamanho(rs.getDouble(12));
                imovel.setVagas(rs.getInt(13));
                imovel.setAndar(rs.getInt(14));
                imovel.setMobiliado(rs.getBoolean(15));
                imovel.setPet(rs.getBoolean(16));
                imovel.setTipoImovel(rs.getString(17));
                imovel.setValor(rs.getDouble(18));
                imovel.setCondominio(rs.getDouble(19));
                imovel.setIptu(rs.getDouble(20));
                imovel.setSeguro(rs.getDouble(21));
                imovel.setParcela(rs.getBoolean(22));
                imovel.setValorEntrada(rs.getDouble(23));
                imovel.setCodigoempresa(rs.getInt(24));
                imovel.setEnabled(rs.getBoolean(25));
            }

            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar imovel" + ex);
        }
        return imovel;

    }

    //encontra imovel por nome
    public Imovel encontrarImovelCodigo(int codigo, int codigoempresa) {
        Imovel imovel = new Imovel();
        System.out.println("Buscando imovel na base de dados...");
        String query = "";
        query = "SELECT * FROM imovel WHERE idImovel=? and codigoempresa=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, codigo);
            preparedStatement.setInt(2, codigoempresa);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                imovel.setIdImovel(rs.getInt(1));
                imovel.setNome(rs.getString(2));
                imovel.setCep(rs.getString(3));
                imovel.setLogradouro(rs.getString(4));
                imovel.setNumero(rs.getString(5));
                imovel.setComplemento(rs.getString(6));
                imovel.setBairro(rs.getString(7));
                imovel.setCidade(rs.getString(8));
                imovel.setEstado(rs.getString(9));
                imovel.setDescricao(rs.getString(10));
                imovel.setNumDormitorios(rs.getInt(11));
                imovel.setTamanho(rs.getDouble(12));
                imovel.setVagas(rs.getInt(13));
                imovel.setAndar(rs.getInt(14));
                imovel.setMobiliado(rs.getBoolean(15));
                imovel.setPet(rs.getBoolean(16));
                imovel.setTipoImovel(rs.getString(17));
                imovel.setValor(rs.getDouble(18));
                imovel.setCondominio(rs.getDouble(19));
                imovel.setIptu(rs.getDouble(20));
                imovel.setSeguro(rs.getDouble(21));
                imovel.setParcela(rs.getBoolean(22));
                imovel.setValorEntrada(rs.getDouble(23));
                imovel.setCodigoempresa(rs.getInt(24));
                imovel.setEnabled(rs.getBoolean(25));
            }

            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar imovel" + ex);
        }
        return imovel;

    }

    //encontra imovel por nome
    public boolean encontrarImovelCadastro(String nome, int codigoempresa) {
        Imovel imovel = new Imovel();
        System.out.println("Buscando imovel na base de dados...");
        String query = "SELECT * FROM imovel WHERE nome='%?%' and codigoempresa='?'";
        boolean encontra = false;

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, nome);
            preparedStatement.setInt(2, codigoempresa);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                encontra = true;
                imovel.setIdImovel(rs.getInt(1));
                imovel.setNome(rs.getString(2));
                imovel.setCep(rs.getString(3));
                imovel.setLogradouro(rs.getString(4));
                imovel.setNumero(rs.getString(5));
                imovel.setComplemento(rs.getString(6));
                imovel.setBairro(rs.getString(7));
                imovel.setCidade(rs.getString(8));
                imovel.setEstado(rs.getString(9));
                imovel.setDescricao(rs.getString(10));
                imovel.setNumDormitorios(rs.getInt(11));
                imovel.setTamanho(rs.getDouble(12));
                imovel.setVagas(rs.getInt(13));
                imovel.setAndar(rs.getInt(14));
                imovel.setMobiliado(rs.getBoolean(15));
                imovel.setPet(rs.getBoolean(16));
                imovel.setTipoImovel(rs.getString(17));
                imovel.setValor(rs.getDouble(18));
                imovel.setCondominio(rs.getDouble(19));
                imovel.setIptu(rs.getDouble(20));
                imovel.setSeguro(rs.getDouble(21));
                imovel.setParcela(rs.getBoolean(22));
                imovel.setValorEntrada(rs.getDouble(23));
                imovel.setCodigoempresa(rs.getInt(24));
                imovel.setEnabled(rs.getBoolean(25));
            }

            System.out.println("Busca efetuada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar imovel" + ex);
        }
        return encontra;

    }

    //
    public void deletarImovel(int codigo, int codigoempresa) throws Exception {
        System.out.println("Deletando imovel de codigo: " + codigo);
        String query = "UPDATE imovel SET enabled='?' WHERE idImovel='?' and codigoempresa='?'";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            preparedStatement.setBoolean(1, false);
            preparedStatement.setInt(2, codigo);
            preparedStatement.setInt(3, codigoempresa);
            preparedStatement.execute();

            System.out.println("Imovel deletado");
        } catch (SQLException ex) {
            throw new Exception("Erro ao deletar imovel", ex);
        }
    }
}
