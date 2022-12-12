package mqtt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import database.Conexionbbdd;
import database.Reserva;
import database.Topic;
import java.time.Duration;
import java.time.LocalDateTime;
import logic.Log;
import logic.Logica;

public class MQTTSuscriber implements MqttCallback {
    
    
    MQTTBroker broker = new MQTTBroker();
    MQTTPublisher publisher = new MQTTPublisher();

    public void searchTopicsToSuscribe(MQTTBroker broker) {
        Conexionbbdd conector = new Conexionbbdd();
        Connection con = null;
        ArrayList<String> topics = new ArrayList<>();
        try {
            con = conector.obtainConnection(true);
            Log.logmqtt.debug("Database Connected");
            //suscribirme al topic IBibly/sensores/# para recibir los datos de los sensore
            //suscribirme al topic IBibly/
            topics.add("IBibly/sensores/#");
            /*
            PreparedStatement psCity = Conexionbbdd.GetClientes(con);
            Log.logmqtt.debug("Query to search cities=> {}", psCity.toString());
            ResultSet rsCity = psCity.executeQuery();
            while (rsCity.next()) {
                topics.add("" + rsCity.getInt("IBibly") + "/#");
            }*/
            suscribeTopic(broker, topics);
        } catch (NullPointerException e) {
            Log.logmqtt.error("Error: {}", e);
        } catch (Exception e) {
            Log.logmqtt.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
    }

    public void suscribeTopic(MQTTBroker broker, ArrayList<String> topics) {
        Log.logmqtt.debug("Suscribe to topics");
        MemoryPersistence persistence = new MemoryPersistence();
        try {
            MqttClient sampleClient = new MqttClient(MQTTBroker.getBroker(), MQTTBroker.getClientId(), persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            Log.logmqtt.debug("Mqtt Connecting to broker: " + MQTTBroker.getBroker());
            sampleClient.connect(connOpts);
            Log.logmqtt.debug("Mqtt Connected");
            sampleClient.setCallback(this);
            for (int i = 0; i < topics.size(); i++) {
                sampleClient.subscribe(topics.get(i));
                Log.logmqtt.info("Subscribed to " + topics.get(i));
            }

        } catch (MqttException me) {
            Log.logmqtt.error("Error suscribing topic: {}", me);
        } catch (Exception e) {
            Log.logmqtt.error("Error suscribing topic: {}", e);
        }
    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.logmqtt.info(topic + ": " + message.toString());
        String[] topics = topic.split("/");
        Topic newTopic = new Topic();
        newTopic.setValue(message.toString());

        /*if (topic.contains("finalizar")) {

            //???¿¿¿??¿ borrar?

        }else*/
        if (topic.contains("anular")) {
            //llamar a la clase logic con la funcion updatehasidocancelada y pasarle el id de la reserva

            Logica.updateHaSidoCancelada(Integer.parseInt(message.toString())); //el mensaje es el id de la reserva

        } else if (topic.contains("consultar")) {

            //select idreserva y codigo sala de las reservas disponibles ahora (hora inicio menor que now) la primera del final
            Reserva reserva = Logica.getReservaDisponibleAhora(Integer.parseInt(message.toString())); //el mensaje es el idobjetoreserva
            int anoI = Integer.parseInt(reserva.getHoraInicio().substring(0, 4));
            int mesI = Integer.parseInt(reserva.getHoraInicio().substring(5, 7));
            int diaI = Integer.parseInt(reserva.getHoraInicio().substring(8, 10));
            int horaI = Integer.parseInt(reserva.getHoraInicio().substring(11, 13));
            int minutoI = Integer.parseInt(reserva.getHoraInicio().substring(14, 16));
            int anoF = Integer.parseInt(reserva.getHoraFin().substring(0, 4));
            int mesF = Integer.parseInt(reserva.getHoraFin().substring(5, 7));
            int diaF = Integer.parseInt(reserva.getHoraFin().substring(8, 10));
            int horaF = Integer.parseInt(reserva.getHoraFin().substring(11, 13));
            int minutoF = Integer.parseInt(reserva.getHoraFin().substring(14, 16));
            LocalDateTime horaInicio = LocalDateTime.of(anoI, mesI, diaI, horaI, minutoI);
            LocalDateTime horaFin = LocalDateTime.of(anoF, mesF, diaF, horaF, minutoF);
        
            Duration duracion = Duration.between(horaInicio, horaFin);
            long segundos = duracion.getSeconds();
            
            if (reserva.getIdObjetoReserva() == 1) {
                
                publisher.publish(broker, "IBibly/esp/puesto1",""+reserva.getId()+","+segundos);
                
            } else if (reserva.getIdObjetoReserva() == 2) {
            
                publisher.publish(broker, "IBibly/esp/puesto2",""+reserva.getId()+","+segundos);
                
            } else if (reserva.getIdObjetoReserva() == -1) {
            
                publisher.publish(broker, "IBibly/esp/sala",""+reserva.getId()+","+segundos+":"+reserva.getClaveSala());
            }
            
        }
        
    }

    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
