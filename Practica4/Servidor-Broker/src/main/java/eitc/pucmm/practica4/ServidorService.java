package eitc.pucmm.practica4;

import com.google.gson.Gson;
import eitc.pucmm.practica4.entidades.Mensaje;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.util.List;
import java.util.Map;


public class ServidorService {
    private MensajeRepository mensajeRepository;
    private ActiveMQConnectionFactory factory;
    private Connection connection;
    private Session session;
    private Topic topic;
    private MessageConsumer messageConsumer;

    public ServidorService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    public void start() throws JMSException {
        System.out.println("\n\n Inicializando Cliente para recibir mensajes\n\n");
        factory = new ActiveMQConnectionFactory("admin","admin","failover:tcp://localhost:61616");
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

        topic = session.createTopic("notificacion_sensores");
        messageConsumer = session.createConsumer(topic);
        Gson gson = new Gson();

        messageConsumer.setMessageListener(message -> {
            try {
                TextMessage textMessage = (TextMessage) message;
                System.out.println(textMessage.getText());
                Mensaje aux = gson.fromJson(textMessage.getText(),Mensaje.class);
                mensajeRepository.save(aux);
                hola();
                //Agregar llamada a la funcion que ejecuta el websocket

            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private void hola() {
        List<Mensaje> mensajes = mensajeRepository.findAll();
        System.out.println(mensajes.size());
    }

}
