package database;

public class Sala {
    
    private int id;
    private int personas; //numero de personas que ocupara la sala
    private int idobjetoReserva;

    
    
    //constructores

    public Sala(int id, int personas, int idobjetoReserva) {
        this.id = id;
        this.personas = personas;
        this.idobjetoReserva = idobjetoReserva;
    }
    
    public Sala() {
        this.id = 0;
        this.personas = 0;
        this.idobjetoReserva = 0;
    }
    
    //getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonas() {
        return personas;
    }

    public void setPersonas(int personas) {
        this.personas = personas;
    }

    public int getIdObjetoReserva() {
        return idobjetoReserva;
    }

    public void setIdObjetoReserva(int idobjetoReserva) {
        this.idobjetoReserva = idobjetoReserva;
    }
    
    
    
}
