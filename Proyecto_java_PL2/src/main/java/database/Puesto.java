package database;

public class Puesto {

    private int id;
    private int planta;
    private boolean enchufe; // true si tiene enchufe, false sino
    private boolean ventana; // true si tiene ventana, false sino
    private int id_objetoReserva;

    public Puesto(int id, int planta, boolean enchufe, boolean ventana, int id_objetoReserva) {
        this.id = id;
        this.planta = planta;
        this.enchufe = enchufe;
        this.ventana = ventana;
        this.id_objetoReserva = id_objetoReserva;
    }
    
    public Puesto() {
        this.id = 0;
        this.planta = 0;
        this.enchufe = false;
        this.ventana = false;
        this.id_objetoReserva = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlanta() {
        return planta;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }

    public boolean isEnchufe() {
        return enchufe;
    }

    public void setEnchufe(boolean enchufe) {
        this.enchufe = enchufe;
    }

    public boolean isVentana() {
        return ventana;
    }

    public void setVentana(boolean ventana) {
        this.ventana = ventana;
    }

    public int getIdObjetoReserva() {
        return id_objetoReserva;
    }

    public void setIdObjetoReserva(int id_objetoReserva) {
        this.id_objetoReserva = id_objetoReserva;
    }
}