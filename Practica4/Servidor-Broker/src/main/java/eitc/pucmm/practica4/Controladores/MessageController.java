package eitc.pucmm.practica4.Controladores;

import com.google.gson.*;
import eitc.pucmm.practica4.MensajeRepository;
import eitc.pucmm.practica4.entidades.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class MessageController {
    @Autowired
    MensajeRepository mensajeRepository;
    @Autowired
    private SimpMessagingTemplate webSocket;

    @MessageMapping("/ws")
    @SendTo("/topic/grafico")
    public String enviarDatos() throws Exception {
        List<Mensaje> sensor1 = mensajeRepository.findAllByIdDispositivo(1);
        List<Mensaje> sensor2 = mensajeRepository.findAllByIdDispositivo(2);
        JsonArray datos1 = new JsonArray();
        JsonArray datos2 = new JsonArray();
        JsonObject datos = new JsonObject();

        for (Mensaje aux:sensor1) {
            JsonArray aux1 = new JsonArray();
            aux1.add(aux.getFechaGeneracion());
            aux1.add(aux.getTemperatura());
            aux1.add(aux.getHumedad());
            datos1.add(aux1);
        }
        for (Mensaje aux:sensor2) {
            JsonArray aux1 = new JsonArray();
            aux1.add(aux.getFechaGeneracion());
            aux1.add(aux.getTemperatura());
            aux1.add(aux.getHumedad());
            datos2.add(aux1);
        }
        datos.add("senson1",datos1);
        datos.add("sensor2",datos2);

        System.out.println(datos.toString());
        webSocket.convertAndSend(datos);
        return datos.toString();
    }
}
