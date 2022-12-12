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

public class Getpuestomasreservadousuario extends HttpServlet {

    public Getpuestomasreservadousuario() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.log.info("--get puesto mas reservado por un usuario--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            String email = request.getParameter("email_Cliente");

            String respuesta = String.valueOf(Logica.getPuestoMasReservado(email));

            String jsonPorcentaje = new Gson().toJson(respuesta);

            Log.log.info("JSON Puesto mas reservado=> " + jsonPorcentaje);
            out.println(jsonPorcentaje);

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
