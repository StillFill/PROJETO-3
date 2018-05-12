<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <link rel="stylesheet" type="text/css" href="Styles/Login.css">
        <title>Login</title>
    </head>
    <script type="text/javascript">
     function pushTo(route){
         alert(route);
    window.location.pathname = route;
 }
</script>
    <body>
        <form method="post" action="http://localhost:8080/astec/login">
            <div class="application-container">
                <div class="login-block">
                    <div class="login-title">IMOBILIÁRIA AQUAMARINE</div>
                    <div class="inputs-container">
                        <div class="form-box-container">
                            <label>Login:</label>
                            <input type="text" name="username">
                        </div>
                        <div class="form-box-container">
                            <label>Senha:</label>
                            <input type="text" name="senha">
                        </div>
                        <div class="form-button">
                            <button type="submit">Entrar</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
