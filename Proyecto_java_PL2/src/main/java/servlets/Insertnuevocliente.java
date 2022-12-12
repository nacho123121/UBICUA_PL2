package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.Log;
import logic.Logica;

public class Insertnuevocliente extends HttpServlet {

    public Insertnuevocliente() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //doGet(request, response);
        
        Log.log.info("--insert nuevo cliente en base de datos--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            String email = request.getParameter("email");
            String nombre = request.getParameter("nombre");
            String apellidos = request.getParameter("apellidos");
            String contraseña = request.getParameter("contrasena");

            String respuesta = String.valueOf(Logica.insertNuevoCliente(nombre, apellidos, email, contraseña));

            out.println(respuesta);

        } catch (Exception e) {
            out.println("error");
            Log.log.error("Exception: {}", e);
        } finally {
            out.close();
        }
    }

}
