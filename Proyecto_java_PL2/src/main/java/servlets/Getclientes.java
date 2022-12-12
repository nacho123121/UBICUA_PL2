package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import logic.Log;
import logic.Logica;
import database.Cliente;


public class Getclientes extends HttpServlet{

    public Getclientes() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Log.log.info("--get clientes base datos--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            ArrayList<Cliente> values =Logica.getClientes();
            String jsonStations = new Gson().toJson(values);
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
