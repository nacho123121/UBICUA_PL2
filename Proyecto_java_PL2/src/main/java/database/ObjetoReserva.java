package database;


public class ObjetoReserva {
    
    private int id;


       
    //constructor
    public ObjetoReserva(int id) {
        this.id = id;
        }
    
    public ObjetoReserva() {
        this.id = 0;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
