package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import com.google.gson.Gson;
import database.Reserva;
import logic.Log;
import logic.Logica;

public class Getreservas extends HttpServlet {
    
        public Getreservas() {
            super();
        }
    
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Log.log.info("--get reservas de la base de datos--");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
    
            try {
    
                ArrayList<Reserva> values =Logica.getReservas();
                String jsonreservas = new Gson().toJson(values);
                out.println(jsonreservas);
    
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
