package controlador;

import config.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/editar")
public class EditarServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditarServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditarServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("txtid");
        String correo = request.getParameter("txtcorreo");
        
        if(idStr != null && correo != null){
            try(Connection cn = Conexion.getConexion()){
                String sql = "UPDATE persona SET correo =? WHERE id = ?";
                PreparedStatement ps = cn.prepareStatement(sql);
                
                ps.setString(1, correo);
                ps.setInt(2, Integer.parseInt(idStr));
                
                ps.executeUpdate();
                
                ps.close();
                cn.close();
                
            }catch(Exception e){e.printStackTrace();}
        }
        response.sendRedirect("index.jsp");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
