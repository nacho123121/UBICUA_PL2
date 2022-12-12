package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import logic.Log;
import logic.Logica;

public class Getnumeroreservastotales extends HttpServlet {
    
        public Getnumeroreservastotales() {
            super();
        }
    
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Log.log.info("--get numero total de reservas de la base de datos--");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
    
            try {
    
                int value = Logica.getNumeroTotalDeReservas();
                String totalreservas = new Gson().toJson(value);
                Log.log.info("JSON numero total de reservas=> "+totalreservas);
                out.println(totalreservas);
    
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
