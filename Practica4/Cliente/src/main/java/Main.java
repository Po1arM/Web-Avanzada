import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws JMSException, InterruptedException {
        if(args.length > 0){
            Date fechaGeneracion = new Date();
            float temperatura = (float) (Math.random()*15) +1;
            float humedad = (float) (Math.random()*15) +1;
            Mensaje aux = new Mensaje(fechaGeneracion.toString(),Integer.parseInt(args[0]),temperatura,humedad);
            Gson gson = new Gson();

            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://app-web:61616");
            Connection connection = factory.createConnection("admin","admin");
            connection.start();

            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            MessageProducer messageProducer = null;
            Topic topic = session.createTopic("notificacion_sensores");
            messageProducer = session.createProducer(topic);
            TextMessage message;
            while (true) {
                aux = modificarMensaje(aux);
                message = session.createTextMessage(gson.toJson(aux));
                //message = session.createTextMessage("Hola mundo");
                //System.out.println(gson.toJson(aux));
                messageProducer.send(message);
                Thread.sleep(60000);
            }
        }
    }
    public static Mensaje modificarMensaje(Mensaje mensaje){
        float humedad = mensaje.getHumedad();
        float temperatura = mensaje.getTemperatura();
        mensaje.setHumedad((float) Math.random()*(humedad+5)+(humedad-5));
        mensaje.setTemperatura((float) Math.random()*(temperatura+5)+(temperatura-5));
        mensaje.setFechaGeneracion(new Date().toString());
        return mensaje;
    }
}
