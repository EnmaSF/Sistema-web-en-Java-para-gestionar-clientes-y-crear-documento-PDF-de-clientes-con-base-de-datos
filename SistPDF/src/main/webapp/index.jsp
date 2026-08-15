<%@page import="modelo.Persona"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.*"%>
<%@page import="config.Conexion"%>
<%@page import="modelo.Admin"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Verificación de sesión
    HttpSession sesion = request.getSession();
    Admin admin = (Admin) sesion.getAttribute("adminLogueado");
    if(admin == null){
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Panel de Control - SUAREZ TECH</title>
    <link href="./css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    
    <header class="main-header">
        <div class="header-left">
            <img src="img/logo.png" alt="Logo" class="logo">
            <span class="company-name">SUAREZ TECH SOLUTIONS ENTERPRISE</span>
        </div>
        
        <div class="header-right">
            <a href="logout" class="btn-logout">Cerrar sesión</a>
        </div>
    </header>
    
    <main class="container">
        <h3>Bienvenido: <%= admin.getNombre() %></h3>
        
        <div class="section-title">
            <h2>Gestión de clientes</h2>
            <a href="reporte" class="btn-pdf">Generar Reporte</a>
        </div>
        
        <table class="table-clientes">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Correo</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            
            <tbody>
                <% 
                    try {
                        Connection cn = Conexion.getConexion();
                        Statement st = cn.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM persona");
                        while(rs.next()){
                %>    
                <tr>
                    <td><%= rs.getInt("id") %></td>
                    <td><%= rs.getString("nombre") %></td>
                    <td><%= rs.getString("correo") %></td>
                    <td>
                        <button onclick="abrirModal('<%= rs.getInt("id") %>', 
                                    '<%= rs.getString("correo") %>')"
                                class="btn-edit">
                            Editar
                        </button>
                                    
                        <a href="eliminar?id=<%= rs.getInt("id") %>"
                           class="btn-delete" 
                           onclick="return confirm('¿Está seguro de eliminar este registro?')">
                            Eliminar
                        </a>
                    </td>
                </tr>
                <% 
                        }
                        cn.close();
                    } catch(Exception e) {
                        out.print("<tr><td colspan='4'>Error al cargar datos: " + e.getMessage() + "</td></tr>");
                    }
                %>
            </tbody>
        </table>
    </main>

    <div id="modalEdit" class="modal-overlay">
        <div class="modal-window">
            <div class="modal-header">
                <h4>Modificar Correo</h4>
                <button type="button" class="btn-close-x" onclick="cerrarModal()">&times;</button>
            </div>
            
            <div class="modal-body">
                <form action="editar" method="POST">
                    <!-- ID Oculto para la actualización -->
                    <input type="hidden" name="txtid" id="edit_id">
                    
                    <div class="form-group">
                        <label for="edit_correo">Nuevo correo electrónico:</label>
                        <input type="email" name="txtcorreo" id="edit_correo" 
                               required class="input-modal" placeholder="ejemplo@correo.com">
                    </div>
                    
                    <div class="modal-footer">
                        <button type="button" class="btn-cancel" onclick="cerrarModal()">Cancelar</button>
                        <button type="submit" class="btn-save">Guardar Cambios</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    
    <footer>
        <p>© Derechos reservados a SUAREZ TECH SOLUTIONS ENTERPRISE 2026</p>
    </footer>

    <script>
        function abrirModal(id, correo) {
            document.getElementById("edit_id").value = id;
            document.getElementById("edit_correo").value = correo;

            const modal = document.getElementById("modalEdit");
            modal.style.display = 'flex';
        }

        function cerrarModal() {
            document.getElementById('modalEdit').style.display = 'none';
        }

        window.onclick = function(event) {
            const modal = document.getElementById('modalEdit');
            if (event.target === modal) {
                cerrarModal();
            }
        };
    </script>
</body>
</html>