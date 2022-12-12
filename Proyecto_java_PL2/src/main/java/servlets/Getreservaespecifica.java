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



public class Getreservaespecifica extends HttpServlet{

    public Getreservaespecifica() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.log.info("--get reserva especifica base datos--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            int id_objetoreserva_ObjetoReserva = Integer.parseInt(request.getParameter("id_objetoreserva_ObjetoReserva"));
            
            String respuesta = String.valueOf(Logica.getReservaEspecifica(id_objetoreserva_ObjetoReserva));
            
            String jsonStations = new Gson().toJson(respuesta);
            out.println(jsonStations);

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

