package logic;

import database.Cliente;
import database.Conexionbbdd;
import database.Puesto;
import database.Sala;
import database.Reserva;



import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import mqtt.MQTTBroker;
import mqtt.MQTTPublisher;


public class Logica {
    
    

    // registrar nuevo usuario en la base de datoss
    public static boolean insertNuevoCliente(String nombre, String apellidos, String email, String contrasenna) {

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        boolean respuesta = true;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.InsertNuevoCliente(con);
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, email);
            ps.setString(4, contrasenna);
            ps.setString(5, nombre);
            ps.setString(6, apellidos);
            ps.setString(7, email);
            ps.setString(8, contrasenna);

            // Log.log.info("Query to store Measurement=> {}", ps.toString());
            ps.executeUpdate();

            respuesta = true; // se inserto correctamente

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            respuesta = false;
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            respuesta = false;
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            respuesta = false;
        } finally {
            conector.closeConnection(con);
        }
        
        return respuesta;

    }

    // insertar nueva reserva en la base de datos
    public static boolean insertNuevaReserva(int id, String horaInicio, String horaFin, int clavesala, String email,
            int idobjetoreserva, int ha_sido_cancelada) {

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        boolean respuesta = true;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.InsertNuevaReserva(con);
            ps.setInt(1, id);
            ps.setString(2, horaInicio);
            ps.setString(3, horaFin);
            ps.setInt(4, clavesala);
            ps.setString(5, email);
            ps.setInt(6, idobjetoreserva);
            ps.setInt(7, ha_sido_cancelada);

            // Log.log.info("Query to store Measurement=> {}", ps.toString());
            ps.executeUpdate();

            respuesta = true; // se inserto correctamente

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            respuesta = false;
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            respuesta = false;
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            respuesta = false;
        } finally {
            conector.closeConnection(con);
        }
        return respuesta;

    }

    // update ha_sido_cancelada en la base de datos
    public static boolean updateHaSidoCancelada(int id) {

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        boolean respuesta = true;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.UpdateHaSidoCancelada(con);
            ps.setInt(1, id);

            ps.executeUpdate();

            respuesta = true; // update correcto

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            respuesta = false;
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            respuesta = false;
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            respuesta = false;
        } finally {
            conector.closeConnection(con);
        }
        return respuesta;

    }

    //get reservas disponibles ahora para el esp
    public static Reserva getReservaDisponibleAhora(int idobjres) {
        
        Reserva reserva = new Reserva();

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetReservaDisponiblesAhora(con);
            ps.setInt(1, idobjres);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {

                reserva.setId(rs.getInt("id_reserva"));
                reserva.setHoraInicio(rs.getString("horaInicio"));
                reserva.setHoraFin(rs.getString("horaFin"));
                reserva.setEmailCliente(rs.getString("email_Cliente"));
                reserva.setIdObjetoReserva(rs.getInt("id_objetoreserva_ObjetoReserva"));
                reserva.setHaSidoCancelada(rs.getInt("ha_sido_cancelada"));
            }

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
       
        } finally {
            conector.closeConnection(con);
        }
        return reserva;
    }

    // get all clientes de la base de datos
    public static ArrayList<Cliente> getClientes() {
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetClientes(con);
            // Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setEmail(rs.getString("email"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellidos(rs.getString("apellidos"));           
                cliente.setContrasenna(rs.getString("contrasena"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            clientes = new ArrayList<Cliente>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            clientes = new ArrayList<Cliente>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            clientes = new ArrayList<Cliente>();
        } finally {
            conector.closeConnection(con);
        }
        return clientes;
    }

    // get all reservas de la base de datos
    public static ArrayList<Reserva> getReservas() {
        ArrayList<Reserva> reservas = new ArrayList<Reserva>();

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetReservas(con);
            // Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id_reserva"));
                reserva.setHoraInicio(rs.getString("horaInicio"));
                reserva.setHoraFin(rs.getString("horafin"));
                reserva.setClaveSala(rs.getInt("claveSala"));
                reserva.setEmailCliente(rs.getString("email_Cliente"));
                reserva.setIdObjetoReserva(rs.getInt("id_objetoreserva_ObjetoReserva"));
                reserva.setHaSidoCancelada(rs.getInt("ha_sido_cancelada"));
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            reservas = new ArrayList<Reserva>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            reservas = new ArrayList<Reserva>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            reservas = new ArrayList<Reserva>();
        } finally {
            conector.closeConnection(con);
        }
        return reservas;
    }

    // get all salas de la base de datos
    public static ArrayList<Sala> getSalas() {
        ArrayList<Sala> salas = new ArrayList<Sala>();

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetSalas(con);
            // Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sala sala = new Sala();
                sala.setId(rs.getInt("id_sala"));
                sala.setPersonas(rs.getInt("personas"));
                sala.setIdObjetoReserva(rs.getInt("id_objetoreserva_ObjetoReserva"));
                salas.add(sala);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            salas = new ArrayList<Sala>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            salas = new ArrayList<Sala>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            salas = new ArrayList<Sala>();
        } finally {
            conector.closeConnection(con);
        }
        return salas;
    }

    // get all puestos de la base de datos
    public static ArrayList<Puesto> getPuestos() {
        ArrayList<Puesto> puestos = new ArrayList<Puesto>();

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetPuestos(con);
            //Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Puesto puesto = new Puesto();
                puesto.setId(rs.getInt("id_puesto"));
                puesto.setPlanta(rs.getInt("planta"));
                puesto.setEnchufe(rs.getBoolean("enchufe"));
                puesto.setVentana(rs.getBoolean("ventana"));
                puesto.setIdObjetoReserva(rs.getInt("id_objetoreserva_ObjetoReserva"));

                puestos.add(puesto);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            puestos = new ArrayList<Puesto>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            puestos = new ArrayList<Puesto>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            puestos = new ArrayList<Puesto>();
        } finally {
            conector.closeConnection(con);
        }
        return puestos;
    }

    // Hora inicio más reservada de la biblioteca por los usuarios
    public static String getHoraInicioMasReservada(String email) {

        String horaInicio = null;
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetHoraInicioMasReservada(con);
            ps.setString(1, email);
            Log.log.info("Query=> " + ps.toString());

            ResultSet rs = ps.executeQuery();
            horaInicio = rs.getString("horas"); // nombre de columna que sale como resultado en pgadmin

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return horaInicio;
    }
    
    // Puesto mas reservado (estadistica usuario)
    public static int getPuestoMasReservado(String email) {
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        int puesto = 0;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetPuestoMasReservado(con);
            ps.setString(1, email);
            Log.log.info("Query=> " + ps.toString());
            
            ResultSet rs = ps.executeQuery();
            
            puesto = rs.getInt("puesto");
            
            
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return puesto;
    }

    // Sala mas reservado (estadistica usuario)
    public static int getSalaMasReservada(String email) {
        
        int sala = 0;
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetSalaMasReservada(con);
            ps.setString(1, email);
            
            Log.log.info("Query=> " + ps.toString());

            ResultSet rs = ps.executeQuery();
            sala = rs.getInt("sala"); // nombre de columna que sale como resultado en pgadmin

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return sala;
    }

    // Porcentaje de reservas canceladas
    public static String getPorcentajeCancelacionPorAusencia(String email) {
        String porcentaje = null;
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetPorcentajeCancelacionPorAusencia(con);
            ps.setString(1, email);
            Log.log.info("Query=> " + ps.toString());

            ResultSet rs = ps.executeQuery();
            porcentaje = rs.getString("porcentaje"); // nombre de columna que sale como resultado en pgadmin

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return porcentaje;
    }

    // Lista de todas las reservas de un usuario en concreto almacenadas en la base
    // de datos
    public static ArrayList<Reserva> getDetallesReservasUsuario(String usuario_email) {

        ArrayList<Reserva> reservas = new ArrayList<Reserva>();

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetDetallesReservasUsuario(con);
            ps.setString(1, usuario_email);

            // Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id_reserva"));
                reserva.setHoraInicio(rs.getString("horaInicio"));
                reserva.setHoraFin(rs.getString("horaFin"));
                reserva.setEmailCliente(rs.getString("email_Cliente"));
                reserva.setIdObjetoReserva(rs.getInt("id_objetoreserva_ObjetoReserva"));
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            reservas = new ArrayList<Reserva>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            reservas = new ArrayList<Reserva>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            reservas = new ArrayList<Reserva>();
        } finally {
            conector.closeConnection(con);
        }
        return reservas;
    }

    // Lista de todas las reservas almacenadas en la base de datos
    public static ArrayList<Reserva> getDetallesReservas() {

        ArrayList<Reserva> reservas = new ArrayList<Reserva>();

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetDetallesReservasUsuario(con);
            ps.setString(1, "%");   //con el % seleccionamos todos los emails en la query.

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id_reserva"));
                reserva.setHoraInicio(rs.getString("horaInicio"));
                reserva.setHoraFin(rs.getString("horaFin"));
                reserva.setEmailCliente(rs.getString("email_Cliente"));
                reserva.setIdObjetoReserva(rs.getInt("id_objetoreserva_ObjetoReserva"));
                
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            reservas = new ArrayList<Reserva>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            reservas = new ArrayList<Reserva>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            reservas = new ArrayList<Reserva>();
        } finally {
            conector.closeConnection(con);
        }
        return reservas;
    }

    //Puesto sin reservar 
    public static int getPuestoSinReservar(int planta, boolean enchufe, boolean ventana, String horainicio, String horafin) {

        int puesto = 0;

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetPuestosSinReservar(con);
            ps.setInt(1,planta);
            ps.setBoolean(2, enchufe);
            ps.setBoolean(3, ventana);
            ps.setString(4, horainicio);
            ps.setString(5, horafin);
            ps.setString(6, horainicio);
            ps.setString(7, horafin);

            ResultSet rs = ps.executeQuery();
            puesto = rs.getInt("id_objetoreserva_ObjetoReserva");

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return puesto;
    }
    
    //Sala sin reservar.
    public static int getSalaSinReservar(int personas, String horainicio, String horafin) {

        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        int sala = 0;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.GetSalasSinReservar(con);
            ps.setInt(1,personas);
            ps.setString(2, horainicio);
            ps.setString(3, horafin);
            ps.setString(4, horainicio);
            ps.setString(5, horafin);

            ResultSet rs = ps.executeQuery();
            
            sala = rs.getInt("id_objetoreserva_ObjetoReserva");

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        
        return sala;
    }
    
    
    //numero total de reservas
    public static int getNumeroTotalDeReservas() {
        
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        int num = 0;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = Conexionbbdd.GetPuestoMasReservado(con);
            Log.log.info("Query=> " + ps.toString());
            
            ResultSet rs = ps.executeQuery();
            
            num = rs.getInt("count");
            
            
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return num;
        
        
    }
    
    //reserva especifica
    public static ArrayList<Reserva> getReservaEspecifica(int id_objetoreserva_ObjetoReserva) {
          
        ArrayList<Reserva> reservas = new ArrayList<Reserva>();
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            
            PreparedStatement ps = Conexionbbdd.GetReservaEspecifica(con);
            ps.setInt(1, id_objetoreserva_ObjetoReserva);
            Log.log.info("Query=> " + ps.toString());
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Reserva reserva = new Reserva();
                reserva.setId(rs.getInt("id_reserva"));
                reserva.setHoraInicio(rs.getString("horaInicio"));
                reserva.setHoraFin(rs.getString("horaFin"));
                reserva.setClaveSala(rs.getInt("claveSala"));
                reserva.setEmailCliente(rs.getString("email_Cliente"));
                reserva.setIdObjetoReserva(rs.getInt("id_objetoreserva_ObjetoReserva"));
                reserva.setHaSidoCancelada(rs.getInt("ha_sido_cancelada"));

                reservas.add(reserva);
            }
            
            
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return reservas;
            
    }
    
    //puesto especifico
    public static ArrayList<Puesto> getPuestoEspecifico(int id_objetoreserva_ObjetoReserva) {
        
        ArrayList<Puesto> puestos = new ArrayList<Puesto>();
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            
            PreparedStatement ps = Conexionbbdd.GetPuestoEspecifico(con);
            ps.setInt(1, id_objetoreserva_ObjetoReserva);
            
            Log.log.info("Query=> " + ps.toString());
                        
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Puesto puesto = new Puesto();
                puesto.setId(rs.getInt("id_puesto"));
                puesto.setPlanta(rs.getInt("planta"));
                puesto.setEnchufe(rs.getBoolean("enchufe"));
                puesto.setVentana(rs.getBoolean("ventana"));
                puesto.setIdObjetoReserva(rs.getInt("id_objetoreserva_ObjetoReserva"));

                puestos.add(puesto);
            }
            
            
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return puestos;
            
    }
    
    //sala especifica
    public static ArrayList<Sala> getSalaEspecifica(int id_objetoreserva_ObjetoReserva) {
        
        ArrayList<Sala> salas = new ArrayList<Sala>();
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            
            PreparedStatement ps = Conexionbbdd.GetSalaEspecifica(con);
            ps.setInt(1, id_objetoreserva_ObjetoReserva);
            Log.log.info("Query=> " + ps.toString());
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sala sala = new Sala();
                sala.setId(rs.getInt("id_sala"));
                sala.setPersonas(rs.getInt("personas"));
                sala.setIdObjetoReserva(rs.getInt("id_objetoreserva_ObjetoReserva"));
                salas.add(sala);
            }
            
            
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return salas;
            
    }
    
    //delete reserva especifica
    public static boolean deleteReserva(int id_reserva) {
        
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        boolean respuesta = true;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            
            PreparedStatement ps = Conexionbbdd.DeleteReservaEspecifica(con);
            ps.setInt(1, id_reserva);
            Log.log.info("Query=> " + ps.toString());
            
            ps.executeUpdate();

            respuesta = true; // se inserto correctamente
            
            
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return respuesta;
            
    }
    
    //delete reserva especifica
    public static boolean updateFinalizar(int id_reserva) {
        
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        boolean respuesta = true;
        int id = 0;
        
        MQTTBroker broker = new MQTTBroker();
        MQTTPublisher publisher = new MQTTPublisher();
        
        
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            
            PreparedStatement ps = Conexionbbdd.UpdateFinalizar(con);
            ps.setInt(1, id_reserva);
            Log.log.info("Query=> " + ps.toString());
            ps.executeUpdate();
            
            PreparedStatement ps2 = Conexionbbdd.GetIdObjetoReserva_objres(con);
            ps2.setInt(1, id_reserva);
            Log.log.info("Query=> " + ps2.toString());
            
            ResultSet rs = ps2.executeQuery();
            
            id = rs.getInt("id_objetoreserva_ObjetoReserva");
            
            ps2.getResultSet();
            respuesta = true; // se inserto correctamente
            
            publisher.publish(broker,"IBibly/esp/finalizar",""+id); //hacemos un publish al esp y le enviamos el topic 
             
            
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return respuesta;
            
    }
    
    
    
    

        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    /*
    // update atributo ocupado de objetoreserva
    public static boolean updateOcupado(int id_objetoReserva, int ocupado) {
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = Conexionbbdd.UpdateOcupado(con);
            ps.setInt(1, ocupado);
            ps.setInt(2, id_objetoReserva);

            // Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            return false;
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            return false;
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            return false;
        } finally {
            conector.closeConnection(con);
        }
        return true;
    }*/

}
