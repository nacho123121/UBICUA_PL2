package servlets;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import logic.Log;
import logic.Logica;

public class Getpuestoespecifico extends HttpServlet{

    public Getpuestoespecifico() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.log.info("--get puesto especifico base datos--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            int id_objetoreserva = Integer.parseInt(request.getParameter("id_objetoreserva_ObjetoReserva"));
            String respuesta = String.valueOf(Logica.getPuestoEspecifico(id_objetoreserva));
            
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
