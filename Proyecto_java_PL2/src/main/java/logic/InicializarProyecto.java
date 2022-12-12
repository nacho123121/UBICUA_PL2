package logic;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import mqtt.MQTTBroker;
import mqtt.MQTTSuscriber;

/**
 * Clase encargada de inicializar el sistema
 */
public class InicializarProyecto implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Log.log.info("-->Suscribe Topics<--");
        MQTTBroker broker = new MQTTBroker();
        MQTTSuscriber suscriber = new MQTTSuscriber();
        suscriber.searchTopicsToSuscribe(broker);
        
    }
    

}
