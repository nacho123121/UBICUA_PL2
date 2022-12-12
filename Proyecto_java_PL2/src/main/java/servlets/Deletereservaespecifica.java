package servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import logic.Log;
import logic.Logica;

public class Deletereservaespecifica extends HttpServlet{
    
    public Deletereservaespecifica() {
        super();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Log.log.info("--delete reserva base datos--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            int id_reserva = Integer.parseInt(request.getParameter("id_reserva"));
            
            String value = String.valueOf(Logica.deleteReserva(id_reserva));
            
            String jsonStation = new Gson().toJson(value);
            
            out.println(jsonStation);

        } catch (Exception e) {
            out.println("error");
            Log.log.error("Exception: {}", e);
        } finally {
            out.close();
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        

    }
    
}
