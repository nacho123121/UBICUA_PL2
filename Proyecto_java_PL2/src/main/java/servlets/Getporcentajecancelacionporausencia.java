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

/**
 * servlet porcentaje cancelacion por ausencia
 */
public class Getporcentajecancelacionporausencia extends HttpServlet {

    public Getporcentajecancelacionporausencia() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.log.info("-- Get porcentaje de cancelacion por ausencia from DB--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String email = request.getParameter("email_Cliente");
            
            String value = Logica.getPorcentajeCancelacionPorAusencia(email);

            String jsonPorcentaje = new Gson().toJson(value);

            Log.log.info("JSON Porcentaje=> " + jsonPorcentaje);
            out.println(jsonPorcentaje);

        } catch (Exception e) {
            out.println("error");
            Log.log.error("Exception: {}", e);
        } finally {
            out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
