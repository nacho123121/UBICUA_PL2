package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import logic.Log;

public class Conexionbbdd {

    public Connection obtainConnection(boolean autoCommit) throws NullPointerException {
        Connection con = null;
        
        String url = "jdbc:postgresql://79.116.51.30:5432/ibiblyddbb";
        String user = "admin";
        String pwd = "admin";
        
        for (int i = 0; i < 10; i++) {
            try{
                con = DriverManager.getConnection(url,user,pwd);
                
                con.setAutoCommit(autoCommit);
                i = 10;
            
            } catch(SQLException ex){
                Logger.getLogger(Conexionbbdd.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
//        int intentos = 5;
//        for (int i = 0; i < intentos; i++) {
//            Log.logdb.info("Attempt " + i + " to connect to the database");
//            try {
//                Context ctx = new InitialContext();
//                //Get the connection factory configured in Tomcat
//                //DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ubicomp");
//
//                //DataSource ds = (DataSource) ctx.lookup("jdbc:postgresql://79.116.51.30:5432/ibiblyddbb");
//                DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/ibiblyddbb");
//
//                //Obtiene una conexion
//                con = ds.getConnection();
//                Calendar calendar = Calendar.getInstance();
//                java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
//                Log.logdb.debug(
//                        "Connection creation. Bd connection identifier: " + con.toString() + " obtained in " + date.toString());
//                con.setAutoCommit(autoCommit);
//                Log.logdb.info("Conection obtained in the attempt: " + i);
//                i = intentos;
//            } catch (NamingException ex) {
//                Log.logdb.error("Error getting connection while trying: " + i + " = " + ex);
//            } catch (SQLException ex) {
//                Log.logdb.error("ERROR sql getting connection while trying: " + i + " = " + ex);
//                throw (new NullPointerException("SQL connection is null"));
//            }
//        }
            
        return con;
    }

    public void closeTransaction(Connection con) {
        try {
            con.commit();
            Log.logdb.debug("Transaction closed");
        } catch (SQLException ex) {
            Log.logdb.error("Error closing the transaction: " + ex);
        }
    }

    public void cancelTransaction(Connection con) {
        try {
            con.rollback();
            Log.logdb.debug("Transaction canceled");
        } catch (SQLException ex) {
            Log.logdb.error("ERROR sql when canceling the transation: " + ex);
        }
    }

    public void closeConnection(Connection con) {
        try {
            Log.logdb.info("Closing the connection");
            if (null != con) {
                Calendar calendar = Calendar.getInstance();
                java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
                Log.logdb.debug(
                        "Connection closed. Bd connection identifier: " + con.toString() + " obtained in " + date.toString());
                con.close();
            }

            Log.logdb.info("The connection has been closed");
        } catch (SQLException e) {
            Log.logdb.error("ERROR sql closing the connection: " + e);
            e.printStackTrace();
        }
    }

    public static PreparedStatement getStatement(Connection con, String sql) {
        PreparedStatement ps = null;
        try {
            if (con != null) {
                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            }
        } catch (SQLException ex) {
            Log.logdb.warn("ERROR sql creating PreparedStatement:{} ", ex);
        }

        return ps;
    }

    //*****************************   CALLS TO THE DATABASE   ********************************//
    //----registrar usuario----//
    public static PreparedStatement InsertNuevoCliente(Connection con) {
        return getStatement(con, "INSERT INTO CLIENTE (email, nombre, apellidos, contrasena) VALUES (?,?,?,?) ON duplicate key update email=?, nombre=?, apellidos=?, contrasena=?;");
    }

    //----insertar nueva reserva----//
    public static PreparedStatement InsertNuevaReserva(Connection con) {
        return getStatement(con, "INSERT INTO RESERVA (id_reserva, horaInicio, horaFin, email_Cliente, id_objetoreserva_ObjetoReserva, ha_sido_cancelada) VALUES (?,?,?,?,?,?) ON duplicate key update id_reserva=?, horaInicio=?, horaFin=?, email_Cliente=?, id_objetoreserva_ObjetoReserva=?, ha_sido_cancelada=?;");
    }

    //----update ha_sido_cancelada (esta a 0 y se pone a 1)----//
    public static PreparedStatement UpdateHaSidoCancelada(Connection con) {
        return getStatement(con, "update reserva set ha_sido_cancelada = 1 where horaInicio < now() and horaFin > now() and Reserva.id_objetoreserva_ObjetoReserva=?");
    }

    /*
    //----update ha_sido_cancelada de una reserva----//

    public static PreparedStatement UpdateHaSidoCancelada(Connection con)
    {
    	return getStatement(con,"UPDATE RESERVA SET HA_SIDO_CANCELADA = ? WHERE ID = ?;");  	
    }
     */
    //----delete reserva----// (segun id_reserva)
    public static PreparedStatement DeleteReservaEspecifica(Connection con) {
        return getStatement(con, "delete from reserva where id_reserva=?;");
    }

    //----update horaFin de la reserva----// (segun id_reserva) 
    /* Sse utilizara si un usuario quiere irse antes de su sitio y finalizar la reserva
    *   entonces la horaFin se actualizará a la hora actual.
     */
    public static PreparedStatement UpdateFinalizar(Connection con) {
        return getStatement(con, "update reserva set horafin=now() where id_reserva=?;");
    }

    public static PreparedStatement GetIdObjetoReserva_objres(Connection con) {
        return getStatement(con, "select id_objetoreserva_ObjetoReserva from reserva where id_reserva=?;");
    }

    //----get all----//
    public static PreparedStatement GetClientes(Connection con) {
        return getStatement(con, "select * from cliente;");
    }

    public static PreparedStatement GetReservas(Connection con) {
        return getStatement(con, "select * from reserva;");
    }

    public static PreparedStatement GetPuestos(Connection con) {
        return getStatement(con, "select * from puesto;");
    }

    public static PreparedStatement GetSalas(Connection con) {
        return getStatement(con, "select * from sala;");
    }

    public static PreparedStatement GetReservaEspecifica(Connection con) {
        return getStatement(con, "select * from reserva where id_objetoreserva_ObjetoReserva = ?;");
    }

    public static PreparedStatement GetPuestoEspecifico(Connection con) {
        return getStatement(con, "select * from puesto where id_objetoreserva_ObjetoReserva = ?;");
    }

    public static PreparedStatement GetSalaEspecifica(Connection con) {
        return getStatement(con, "select * from sala where id_objetoreserva_ObjetoReserva = ?;");
    }

    //----estadisticas generales----//
    public static PreparedStatement GetHoraInicioMasReservada(Connection con) {
        return getStatement(con, "select to_char(horaInicio, 'HH24:MI') as horas from reserva where (ha_sido_cancelada =0 and email_Cliente = ?) group by horas order by count(EXTRACT(HOUR FROM horaInicio)) desc limit 1;");
    }

    public static PreparedStatement GetPuestoMasReservado(Connection con) {
        return getStatement(con,
                "select id_objetoreserva_ObjetoReserva as puesto from (select Reserva.id_objetoreserva_ObjetoReserva, count(Reserva.id_objetoreserva_ObjetoReserva) from Reserva where id_objetoreserva_ObjetoReserva in (select ObjetoReserva.id_ObjetoReserva from ObjetoReserva INNER JOIN Puesto ON Puesto.id_objetoreserva_ObjetoReserva = ObjetoReserva.id_ObjetoReserva) and ha_sido_cancelada != 1 and email_Cliente=? group by Reserva.id_objetoreserva_ObjetoReserva order by count(Reserva.id_objetoreserva_ObjetoReserva) DESC LIMIT 1) as tabla;");
    }

    public static PreparedStatement GetSalaMasReservada(Connection con) {
        return getStatement(con, "select id_objetoreserva_ObjetoReserva *(-1) as sala from (select Reserva.id_objetoreserva_ObjetoReserva, count(Reserva.id_objetoreserva_ObjetoReserva) from Reserva where id_objetoreserva_ObjetoReserva in (select ObjetoReserva.id_ObjetoReserva from ObjetoReserva INNER JOIN Sala ON Sala.id_objetoreserva_ObjetoReserva = ObjetoReserva.id_ObjetoReserva) and ha_sido_cancelada != 1 and email_Cliente=? group by Reserva.id_objetoreserva_ObjetoReserva order by count(Reserva.id_objetoreserva_ObjetoReserva) DESC LIMIT 1) as tabla;");
    }

    public static PreparedStatement GetPorcentajeCancelacionPorAusencia(Connection con) {
        return getStatement(con, "select concat(round(count(*)*100.0/(select count(*) from reserva)),'%') as porcentaje from reserva where ha_sido_cancelada = 1 and email_Cliente=? group by ha_sido_cancelada;");
    }

    //----reservas----//
    public static PreparedStatement GetPuestosSinReservar(Connection con) {
        return getStatement(con, "select id_objetoreserva_ObjetoReserva from puesto INNER JOIN reserva on reserva.id_objetoreserva_ObjetoReserva = puesto.id_objetoreserva_ObjetoReserva where puesto.planta = ? and puesto.enchufe = ? and puesto.ventana = ? and id_objetoreserva_ObjetoReserva not in select id_objetoreserva_ObjetoReserva from reserva where (horaInicio=? between horaInicio and horaFin) or (horaFin=? between horaInicio and horaFin) or (horaInicio=? < horaInicio and horaFin=? > horaFin) limit 1");
    }

    public static PreparedStatement GetSalasSinReservar(Connection con) {
        return getStatement(con, "select id_objetoreserva_ObjetoReserva from sala INNER JOIN reserva on reserva.id_objetoreserva_ObjetoReserva = sala.id_objetoreserva_ObjetoReserva where sala.personas = ? and id_objetoreserva_ObjetoReserva not in select id_objetoreserva_ObjetoReserva from reserva where (horaInicio=? between horaInicio and horaFin) or (horaFin=? between horaInicio and horaFin) or (horaInicio=? < horaInicio and horaFin=? > horaFin) limit 1");

    }

    //----detalles reservas----//
    public static PreparedStatement GetDetallesReservasUsuario(Connection con) {
        return getStatement(con, "Select * from reserva where email_Cliente=?;");
    }

    //----reserva disponibles ahora para el esp----// 
    public static PreparedStatement GetReservaDisponiblesAhora(Connection con) {
        return getStatement(con, "select id_reserva, claveSala from reserva where (horainicio<now() and horafin > now() and id_objetoreserva_ObjetoReserva = ?) order by horaInicio desc limit 1;");
    }

    public static PreparedStatement GetNumeroTotalDeReservas(Connection con) {
        return getStatement(con, "select count(*) from reserva;");
    }

    /*
    public static PreparedStatement getHoraInicioMenosReservada(Connection con) {
        return getStatement(con, "select to_char(horainicio, 'HH12:MI') as horas from reserva where ha_sido_cancelada != 1 group by horas order by count(EXTRACT(HOUR FROM horainicio)) DESC LIMIT 1");
    }

    public static PreparedStatement getHoraMenosReservada(Connection con) {
        return getStatement(con,
                "select to_char(horainicio, 'HH12:MI') as horas from reserva where ha_sido_cancelada != 1 group by horas order by count(EXTRACT(HOUR FROM horainicio)) ASC LIMIT 1");
    }

    public static PreparedStatement getHoraMasReservadaDia(Connection con) {
        return getStatement(con,
                "select to_char(horainicio, 'HH12:MI') as horas from reserva where ha_sido_cancelada != 1 and fecha = current_date group by horas order by count(EXTRACT(HOUR FROM horainicio)) DESC LIMIT 1");
    }*/
}
