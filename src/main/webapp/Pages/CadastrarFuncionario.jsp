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
        <title>Cadastro de Funcionario</title>
    </head>
    <script type="text/javascript">
        var fields = document.getElementsByTagName('input');
        $(document).ready(function () {
            $('#telefone').mask('(00) 00000-0000');
            $('#celular').mask('(00) 00000-0000');
            $('#rg').mask('00.000.000-0');
            $('#cpf').mask('000.000.000-00');
            $('#cep').mask('00000-000');
            $('#estado').attr('maxlength', 2);
        });

        function onSubmit() {
            console.log('aqui')
            var hasErros = false;
            for (var i = 0; i < fields.length; i++) {
                if (fields[i].id === 'estado' && fields[14].value.length > 2) {
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
    </script>
    <style>
        .drop-down {
            width: 100%;
        }
        #clientes-container {
            display: none;
            position: absolute;
            width: 100%;
            background-color: grey;
            color: white;
        }
        .principal-navbar {
            display: flex;
            background-color: white;
        }
        .input-container {
            display: flex;
            flex-direction: column;
        }
        
        .form-block label {
            margin-top: 1em;
        }
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
        <div class="principal-navbar">
                <div class="drop-down">
                    <div class="navItem" onclick="showClienteOptions()">Clientes</div>
                    <div id="clientes-container">
                        <div onClick="pushTo('astec/cadastrar-cliente')" class="drop-item">Cadastrar</div>
                        <div onClick="pushTo('astec/consultar-clientes')" class="drop-item">Consultar</div>
                    </div>
                </div>
                <div class="drop-down">
                    <div class="navItem" onclick="showFuncionarioOptions()">Funcionarios</div>
                    <div id="funcionarios-container">
                        <div class="drop-item">Cadastrar</div>
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
        <form id="form" method="post" action="http://localhost:8080/astec/cadastrar-funcionario">
            <div class="application-container">
                <div class="form-container">
                    <div class="form-header">CADASTRO DE FUNCIONÁRIOS</div>
                    <div class="form-content">
                        <div class="form-block-row">
                            <div class="input-container">
                                <label>Nome:</label>
                                <input id="nome" type="text" name='nome'>
                            </div>
                            <div class="input-container">
                                <label>Comissão:</label>
                                <input id="comissao" type="text" name='comissao'>
                            </div>
                        </div>
                        <div class='form-block-row'>
                            <div class="input-container">
                                <label>RG:</label>
                                <input id="rg" type="text" name='rg'>
                            </div>
                            <div class="input-container">
                                <label>CPF:</label>
                                <input id="cpf" type="text" name='cpf'>
                            </div>
                        </div>
                        <div class='form-block-row'>
                            <div class="input-container">
                                <label>Email</label>
                                <input id="email" type="text" name='email'>
                            </div>
                            <div class="input-container">
                                <label>Telefone:</label>
                                <input id="telefone" type="text" name='telefone'>
                            </div>
                        </div>
                        <div class="form-block-row">
                            <div class="input-container">
                                <label>Login:</label>
                                <input id="login" type="text" name='login'>
                            </div>
                            <div class="input-container">
                                <label>Senha:</label>
                                <input id="senha" type="password" name='senha'>
                            </div>
                        </div>
                        <div class='form-block'>
                            <label>CEP:</label>
                            <input id="cep" type="text" name="cep">
                            <label>Logradouro:</label>
                            <input id="logradouro" type="text" name="logradouro">
                            <label>Número:</label>
                            <input id="numero" type="text" name="numero">
                            <label>Complemento:</label>
                            <input id="complemento" type="text" name="complemento">					
                            <label>Bairro:</label>
                            <input id="bairro" type="text" name="bairro">
                            <label>Cidade:</label>
                            <input id="cidade" type="text" name="cidade">
                            <label>Estado:</label>
                            <input id="estado" type="text" name="estado">
                        </div>
                    </div>

                    <div class="form-button">
                        <button type="button" onclick="onSubmit()">Salvar</button>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
