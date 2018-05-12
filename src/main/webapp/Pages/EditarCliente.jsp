<%@page import="java.sql.ResultSet"%>
<%@page import="com.senac.astec.utils.ConexaoBanco"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page session="false" %>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="Styles/Login.css">
        <link rel="stylesheet" type="text/css" href="Styles/Cadastros.css">
        <link rel="stylesheet" type="text/css" href="Styles/Geral.css">  
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.10/jquery.mask.js"></script>
        <meta charset="utf-8">
        <title>Cadastro de clientes</title>
    </head>
    <script type="text/javascript">
        var fields = document.getElementsByTagName('input');
        $(document).ready(function () {
            $('#birthday').mask('00/00/0000');
            $('#telefone').mask('(00) 00000-0000');
            $('#celular').mask('(00) 00000-0000');
            $('#rg').mask('00.000.000-0');
            $('#cpf').mask('000.000.000-00');
            $('#cep').mask('00000-000');
            $('#estado').attr('maxlength', 2);
        });

        function pushTo(route) {
            window.location.pathname = route;
        }
        function showClienteOptions() {
            if (document.getElementById('clientes-container').style.display === 'block') {
                document.getElementById('clientes-container').style.display = 'none';
            } else {
                document.getElementById('clientes-container').style.display = 'block';
            }
        }
        function showFuncionarioOptions() {
            if (document.getElementById('funcionarios-container').style.display === 'block') {
                document.getElementById('funcionarios-container').style.display = 'none';
            } else {
                document.getElementById('funcionarios-container').style.display = 'block';
            }
        }
        function onSubmit() {
            var hasErros = false;
            for (var i = 0; i < fields.length; i++) {
                if (fields[i].id === 'estado' && fields[i].value.length > 2) {
                    console.log(fields[14]);
                    console.log('estado ta zoado')
                    hasErros = true;
                }
                if (!fields[i].value || fields[i].value === '' || fields[i].value === null) {
                    console.log(fields[i].id);
                    hasErros = true;
                }
            }
            if (!hasErros) {
                alert('Cliente cadastrado com sucesso!');
                document.getElementById("form").submit();
                return;
            }
            alert('Erros foram encontrados, por favor, corrija e tente novamente!');
        }
    </script>
    <style>
        .drop-down {
            width: 100%;
        }
        #clientes-container {
            display: none;
            position: absolute;
            width: 20vw;
            padding: 0 1em;
            background-color: grey;
            color: white;
        }
        #funcionarios-container {
            display: none;
            position: absolute;
            width: 20vw;
            padding: 0 1em;
            background-color: grey;
            color: white;
        }
        .principal-navbar {
            display: flex;
            background-color: white;
        }
        .navItem {
            width: 20vw;
        }

        .drop-item {
            padding: 1em 0;
            cursor: pointer;
            width: 100%;
            text-align: center;
            text-transform: uppercase;
        }
    </style>
    <body>
        <form id="form" method="post" action="http://localhost:8080/astec/consultar-clientes">
            <input type="hidden" name="clienteId" value="${cliente.getId()}">
            <div class="principal-navbar">
                <div class="drop-down">
                    <div class="navItem" onclick="showClienteOptions()">Clientes</div>
                    <div id="clientes-container">
                        <div class="drop-item">Cadastrar</div>
                        <div onClick="pushTo('astec/consultar-clientes')" class="drop-item">Consultar</div>
                    </div>
                </div>
                <div class="drop-down">
                    <div class="navItem" onclick="showFuncionarioOptions()">Funcionarios</div>
                    <div id="funcionarios-container">
                        <div onClick="pushTo('astec/cadastrar-funcionario')" class="drop-item">Cadastrar</div>
                        <div onClick="pushTo('astec/consultar-funcionarios')" class="drop-item">Consultar</div>
                    </div>
                </div>
                <div class="drop-down">
                    <div class="navItem" onClick="pushTo('astec/cadastrar-cliente')">Vendas</div>
                </div>
                <div class="drop-down">
                    <div class="navItem" onClick="pushTo('astec/cadastrar-cliente')">Relatório</div>
                </div>
            </div>
            <div class="application-container">
                <div class="form-container">
                    <form action = "CadastroClientes" method = "GET">
                        <div class="form-header">EDITAR CLIENTE</div>
                        <div class="form-content">
                            <div class="form-block-row">
                                <div>
                                    <label>Nome:</label>
                                    <input value="${cliente.getNome()}" id="nome" type="text" name="name">
                                </div>
                                <div>
                                    <label>Data de Nascimento</label>
                                    <input value="${cliente.getDataNasc()}" id="birthday" type="text" name="birthday">
                                </div>
                                <div>
                                    <label>RG</label>
                                    <input value="${cliente.getRg()}" id="rg" type="text" name="documentNumber">
                                </div>
                            </div>
                            <div class="form-block-row">
                                <div>
                                    <label>CPF</label>
                                    <input value="${cliente.getCpf()}" id="cpf" type="text" name="cpf">
                                </div>
                                <div>
                                    <label>SEXO</label>
                                    <input value="${cliente.getSexo()}" id="sexo" type="text" name="gender">
                                </div>
                                <div>
                                    <label>TELEFONE</label>
                                    <input value="${cliente.getTelefone()}" id="telefone" type="text" name="phone">
                                </div>
                            </div>
                            <div class="form-block-row">
                                <div>
                                    <label>CELULAR</label>
                                    <input value="${cliente.getCelular()}" id="celular" type="text" name="cellphone">
                                </div>
                                <div>
                                    <label>E-MAIL</label>
                                    <input value="${cliente.getEmail()}" id="email" type="text" name="email">
                                </div>
                                <div>
                                    <label>CEP</label>
                                    <input value="${cliente.getCep()}" id="cep" type="text" name="cep">
                                </div>
                            </div>
                            <div class="form-block-row">
                                <div>
                                    <label>LOGRADOURO</label>
                                    <input value="${cliente.getLogradouro()}" id="logradouro" type="text" name="logradouro">
                                </div>
                                <div>
                                    <label>NÚMERO</label>
                                    <input value="${cliente.getNumero()}" id="numero" type="text" name="addressNumber">
                                </div>
                                <div>
                                    <label>COMPLEMENTO</label>
                                    <input value="${cliente.getComplemento()}" id="complemento" type="text" name="complement">
                                </div>
                            </div>

                            <div class="form-block-row">
                                <div>
                                    <label>BAIRRO</label>
                                    <input value="${cliente.getBairro()}" id="bairro" type="text" name="neighborhood">
                                </div>
                                <div>
                                    <label>CIDADE</label>
                                    <input value="${cliente.getCidade()}" id="cidade" type="text" name="city">
                                </div>
                                <div>
                                    <label>ESTADO</label>
                                    <input value="${cliente.getEstado()}" id="estado" type="text" name="state">
                                </div>
                            </div>
                        </div>
                        <div class="form-button">
                            <input type="hidden" name="salvar" value="want-save">
                            <button type="submit" onclick="onSubmit()">Salvar</button>
                        </div>
                    </form>
                </div>
            </div>
        </form>
    </body>
</html>
