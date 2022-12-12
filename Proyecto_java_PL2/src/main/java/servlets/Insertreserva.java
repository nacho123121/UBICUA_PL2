package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import logic.Log;
import logic.Logica;


public class Insertreserva extends HttpServlet {

    public Insertreserva() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //doGet(request, response);
        
        Log.log.info("--insert nueva reserva en base de datos--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {

            int id = Integer.parseInt(request.getParameter("id_reserva"));
            String horainicio = request.getParameter("horaInicio");
            String horafin = request.getParameter("horaFin");
            int clavesala = Integer.parseInt(request.getParameter("claveSala"));
            String email = request.getParameter("email_Cliente");
            int idobjetoreserva = Integer.parseInt(request.getParameter("id_objetoreserva_ObjetoReserva"));
            int ha_sido_cancelada = Integer.parseInt(request.getParameter("ha_sido_cancelada"));

            String respuesta = String.valueOf(Logica.insertNuevaReserva(id, horainicio, horafin, clavesala, email, idobjetoreserva, ha_sido_cancelada));

            out.println(respuesta);

        } catch (Exception e) {
            out.println("error");
            Log.log.error("Exception: {}", e);
        } finally {
            out.close();
        }
        
    }
    
}
