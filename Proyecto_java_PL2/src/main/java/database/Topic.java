package database;


public class Topic 
{
    private String idTopic;
    private String idReserva;
    private String idCliente;
    private String idPuesto;
    private String idSala;
    private String value;
    
    
    // constructors
    public Topic() 
    {
    	this.idTopic = null;
    	this.idReserva = null;
    	this.idCliente = null;
    	this.idPuesto = null;
    	this.idSala = null;
        this.setValue(null);
    }
    public Topic(String idTopic, String idReserva, String idCliente, String idPuesto, String idSala, String value) 
    {
    	this.idTopic = idTopic;
    	this.idReserva = idReserva;
    	this.idCliente = idCliente;
    	this.idPuesto = idPuesto;
    	this.idSala = idSala;
        this.setValue(value);
    }
    
    public String getIdTopic() {
		
        return idTopic;

    }

    public void setIdTopic(String idTopic) {
		
        this.idTopic = idTopic;
	
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdPuesto() {
        return idPuesto;
    }

    public void setIdPuesto(String idPuesto) {
        this.idPuesto = idPuesto;
    }

    public String getIdSala() {
        return idSala;
    }

    public void setIdSala(String idSala) {
        this.idSala = idSala;
    }
    
    public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
        
        
    
 }
