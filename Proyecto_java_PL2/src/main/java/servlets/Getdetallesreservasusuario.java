package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.Log;
import logic.Logica;

public class Getdetallesreservasusuario extends HttpServlet {

    public Getdetallesreservasusuario() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.log.info("--get detalles reserva usuario--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
                
                String email_Cliente = request.getParameter("email_Cliente");
    
                String respuesta = String.valueOf(Logica.getDetallesReservasUsuario(email_Cliente));
    
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
