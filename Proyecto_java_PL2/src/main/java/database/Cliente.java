package database;

public class Cliente {
    
    private String nombre;
    private String apellidos;
    private String email;
    private String contrasenna;
    


    public Cliente(String nombre, String apellidos, String email, String contrasenna) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.contrasenna = contrasenna;
    }
    
    public Cliente() {
        this.nombre = null;
        this.apellidos = null;
        this.email = null;
        this.contrasenna = null;
    }
    
    
    //getters y setters
     
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }
    
}
