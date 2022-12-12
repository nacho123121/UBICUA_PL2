package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.Log;
import logic.Logica;


public class Getsalamasreservadausuario extends HttpServlet {
    
    public Getsalamasreservadausuario() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.log.info("--get sala mas reservada por un usuario--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
                
                String email = request.getParameter("email");
    
                String respuesta = String.valueOf(Logica.getSalaMasReservada(email));
                //si el email = % seran estadisticas generales, en cambio si es un correo concreto seran las estadisticas de ese usuario.       
                out.println(respuesta);
    
            } catch (Exception e) {
                out.println("error");
                Log.log.error("Exception: {}", e);
            } finally {
                out.close();
            }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
}