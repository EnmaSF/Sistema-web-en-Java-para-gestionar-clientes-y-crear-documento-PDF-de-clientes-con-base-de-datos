package controlador;

//import jakarta.resource.cci.ResultSet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import config.Conexion;
//import org.w3c.dom.Document;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.annotation.WebServlet;
import java.sql.ResultSet;

@WebServlet("/reporte")
public class ReporteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReporteServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReporteServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf");

        try {

            // CONEXION
            Connection cn = Conexion.getConexion();

            // CONSULTA
            String sql = "SELECT * FROM persona";

            PreparedStatement ps = cn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            // PDF
            Document documento = new Document();

            OutputStream out = response.getOutputStream();

            PdfWriter pw = PdfWriter.getInstance(documento, out);

            documento.open();
            
            /*String ruta = getServletContext().getRealPath("/img/logo.png");

            Image logo = Image.getInstance(ruta);

            logo.scaleToFit(120, 120);

            logo.setAlignment(Element.ALIGN_CENTER);
            //logo.setAlignment(Element.ALIGN_LEFT);
            //logo.setAlignment(Element.ALIGN_RIGHT);

            documento.add(logo);*/
            
            // LOGO
    String ruta = getServletContext().getRealPath("/img/logo.png");

    Image logo = Image.getInstance(ruta);
    logo.scaleToFit(100, 100);
    logo.setAlignment(Element.ALIGN_CENTER);

    documento.add(logo);

    // ESPACIO
    documento.add(new Paragraph(" "));

    // TITULOS
    Font titulo = FontFactory.getFont(
        FontFactory.HELVETICA_BOLD, 18,
            BaseColor.PINK);

    Font subtitulo = FontFactory.getFont(
        FontFactory.HELVETICA, 12);

    // EMPRESA
    Paragraph empresa = new Paragraph(
        "SUAREZ TECH SOLUTIONS ENTERPRISE", titulo);

    empresa.setAlignment(Element.ALIGN_CENTER);

    documento.add(empresa);

    // DESCRIPCION
    Paragraph desc = new Paragraph(
        "Soluciones Tecnologicas Integrales",
        subtitulo);

    desc.setAlignment(Element.ALIGN_CENTER);

    documento.add(desc);

    // DATOS
    Paragraph datos = new Paragraph(
        "RUC: 22222654355\nLima - Peru",
        subtitulo);

    datos.setAlignment(Element.ALIGN_CENTER);

    documento.add(datos);

    documento.add(new Paragraph(" "));
    Paragraph linea = new Paragraph(""
            + "--------------------------------------------------------------"
            + "--------------------------------------------------------------");
    linea.setAlignment(Element.ALIGN_CENTER);
    documento.add(linea);

    documento.add(new Paragraph(" "));

            /*documento.add(new Paragraph("REPORTE PERSONAS"));
            documento.add(new Paragraph(" "));*/
            
            Paragraph reporte = new Paragraph(
        "REPORTE DE CLIENTES",
        titulo);

    reporte.setAlignment(Element.ALIGN_CENTER);

    documento.add(reporte);

    documento.add(new Paragraph(" "));

            // TABLA
            PdfPTable tabla = new PdfPTable(3);

            tabla.addCell("ID");
            tabla.addCell("NOMBRE");
            tabla.addCell("CORREO");

            // DATOS MYSQL
            while (rs.next()) {

                tabla.addCell(rs.getString("id"));
                tabla.addCell(rs.getString("nombre"));
                tabla.addCell(rs.getString("correo"));
            }

            documento.add(tabla);
            
            //texto de politicas y derechos reservados en pie de pagina
            PdfContentByte cb = pw.getDirectContent();
            
            BaseColor colorGris = new BaseColor(150, 150, 150);
            Font fuenteNota = FontFactory.getFont(
                    FontFactory.HELVETICA_OBLIQUE, 9, colorGris);

            documento.add(new Paragraph(" ")); 

            Paragraph advertencia = new Paragraph(
                "La generacion de este reporte es solo con fines administrativos. "
                        + "Cualquier otro uso sera sancionado por la empresa.", 
                fuenteNota);

            Paragraph derechos = new Paragraph(
                "© 2026 SUAREZ TECH SOLUTIONS ENTERPRISE - "
                        + "Todos los derechos reservados.", 
                fuenteNota);

            //posicionar texto de politica y derechos
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, advertencia, 300, 60, 0);
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, derechos, 300, 35, 0);

            documento.close();

            rs.close();
            ps.close();
            cn.close();

            out.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
