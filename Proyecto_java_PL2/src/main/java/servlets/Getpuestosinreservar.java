package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.Log;
import logic.Logica;

public class Getpuestosinreservar extends HttpServlet {

    public Getpuestosinreservar() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.log.info("--get puesto sin reservar--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            
            int planta = Integer.parseInt(request.getParameter("planta"));
            boolean enchufe = Boolean.parseBoolean(request.getParameter("enchufe"));
            boolean ventana = Boolean.parseBoolean(request.getParameter("ventana"));
            String horainicio = request.getParameter("horaInicio");
            String horafin = request.getParameter("horaFin");

            String respuesta = String.valueOf(Logica.getPuestoSinReservar(planta, enchufe, ventana, horainicio, horafin));
            //la hora fin habra que calcularla en la app sumandole a la horainicio el tiempo que dura la reserva

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