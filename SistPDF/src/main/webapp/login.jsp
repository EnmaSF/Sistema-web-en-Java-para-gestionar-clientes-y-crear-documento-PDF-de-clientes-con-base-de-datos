<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login - Admin</title>
        <link rel="stylesheet" href="./css/style.css" type="text/css"/>
    </head>
    <body class="login-body">
        <div>
            <!-- Logo de la empresa -->
            <div class="login-logo-container">
                <img src="img/logo.png" alt="Logo SistPDF" class="login-logo"/>
            </div>
            
            <h2>Iniciar sesión</h2>
            
            <form action="login" method="POST">
                <div class="input-group">
                    <input type="text" name="txtuser" 
                           placeholder="Nombre de usuario" required>
                </div>
                
                <div class="input-group">
                    <input type="password" name="txtpass" 
                           placeholder="contraseña" required>
                </div>
                
                <input type="submit" value="Ingresar" class="btn-login">
            </form>
            
            <%-- Mensajes de error --%>
            <% if(request.getAttribute("error") != null){ %>
            <div class="error-msg">
                <%= request.getAttribute("error") %>
            </div>
            <% } %>
        </div>
    </body>
</html>
