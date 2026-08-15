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

@WebServlet("/eliminar")
public class EliminarServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EliminarServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EliminarServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idParam = request.getParameter("id");
        
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);

                Connection cn = Conexion.getConexion();
                String sql = "DELETE FROM persona WHERE id = ?";
                PreparedStatement ps = cn.prepareStatement(sql);
                ps.setInt(1, id);
                
                ps.executeUpdate();
                
                ps.close();
                cn.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
