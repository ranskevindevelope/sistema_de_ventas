package com.miempresa.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Inicio")
public class Inicio extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Connection conectarBD() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/sistema_ventas"; // Cambia el puerto si es necesario
        String usuario = "root"; // Cambia tu usuario de MySQL
        String clave = "root"; // Cambia tu contraseña de MySQL si tienes
        return DriverManager.getConnection(url, usuario, clave);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Sistema de Ventas</title></head><body>");
            out.println("<h1>Bienvenido al Sistema de Ventas</h1>");

            try (Connection conn = conectarBD(); Statement stmt = conn.createStatement()) {
                out.println("<p>Conexión a la base de datos establecida correctamente.</p>");

                // Consultar lista de productos
                out.println("<h2>Lista de Productos</h2>");
                out.println("<table border='1'><tr><th>ID</th><th>Nombre</th><th>Precio</th></tr>");
                ResultSet rs = stmt.executeQuery("SELECT id, nombre, precio FROM productos");
                while (rs.next()) {
                    out.println("<tr><td>" + rs.getInt("id") + "</td><td>" + rs.getString("nombre") + "</td><td>" + rs.getDouble("precio") + "</td></tr>");
                }
                out.println("</table>");
            } catch (SQLException e) {
                out.println("<p>Error de conexión: " + e.getMessage() + "</p>");
            }

            out.println("</body></html>");
        }
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String nombre = request.getParameter("nombre");
            double precio = Double.parseDouble(request.getParameter("precio"));

            try (Connection conn = conectarBD();
                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO productos (nombre, precio) VALUES (?, ?)")) {
                stmt.setString(1, nombre);
                stmt.setDouble(2, precio);
                stmt.executeUpdate();
                response.sendRedirect("Inicio"); // Redirige para actualizar la lista
            } catch (SQLException e) {
                response.getWriter().println("Error al guardar: " + e.getMessage());
            }
        }
    }
}
