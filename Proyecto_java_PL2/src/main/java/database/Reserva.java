package database;

public class Reserva {

    private int id;
    private String horaInicio;
    private String horaFin;
    private int claveSala;
    private String emailCliente;
    private int idobjetoReserva;
    private int hasidocancelada;

    // constructores

    public Reserva(int id, String horaInicio, String horaFin, int claveSala, String emailCliente,
            int idobjetoReserva, int hasidocancelada) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.claveSala = claveSala;
        this.emailCliente = emailCliente;
        this.idobjetoReserva = idobjetoReserva;
        this.hasidocancelada = hasidocancelada;
    }
    
    public Reserva() {
        this.id = 0;
        this.horaInicio = null;
        this.horaFin = null;
        this.claveSala = 0;
        this.emailCliente = null;
        this.idobjetoReserva = 0;
        this.hasidocancelada = 0;
    }

    // getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public int getClaveSala() {
        return claveSala;
    }

    public void setClaveSala(int claveSala) {
        this.claveSala = claveSala;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public int getIdObjetoReserva() {
        return idobjetoReserva;
    }

    public void setIdObjetoReserva(int idobjetoReserva) {
        this.idobjetoReserva = idobjetoReserva;
    }

    public int getHaSidoCancelada() {
        return hasidocancelada;
    }

    public void setHaSidoCancelada(int hasidocancelada) {
        this.hasidocancelada = hasidocancelada;
    }

}
