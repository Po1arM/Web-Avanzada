package eitc.pucmm.practica4.Controladores;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import eitc.pucmm.practica4.MensajeRepository;
import eitc.pucmm.practica4.entidades.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class MessageController {
    @Autowired
    private MensajeRepository mensajeRepository;
    @Autowired
    private SimpMessagingTemplate webSocket;

    public static List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping(value = "/ServerSent", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(){
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        try{
            emitter.send(SseEmitter.event().name("INIT"));
        }catch (IOException e){
            e.printStackTrace();
        }
        emitter.onCompletion(()->emitters.remove(emitter));
        emitters.add(emitter);
        return emitter;
    }

    public void enviarDatos() throws Exception {
        List<Mensaje> sensor1 = mensajeRepository.findAllByIdDispositivo(1);
        List<Mensaje> sensor2 = mensajeRepository.findAllByIdDispositivo(2);
        JsonArray datos1 = new JsonArray();
        JsonArray datos2 = new JsonArray();
        JsonObject datos = new JsonObject();

        for (Mensaje aux : sensor1) {
            JsonArray aux1 = new JsonArray();
            aux1.add(aux.getFechaGeneracion());
            aux1.add(aux.getTemperatura());
            aux1.add(aux.getHumedad());
            datos1.add(aux1);
        }
        for (Mensaje aux : sensor2) {
            JsonArray aux1 = new JsonArray();
            aux1.add(aux.getFechaGeneracion());
            aux1.add(aux.getTemperatura());
            aux1.add(aux.getHumedad());
            datos2.add(aux1);
        }
        datos.add("sensor1", datos1);
        datos.add("sensor2", datos2);

        for (SseEmitter emitter: emitters){
            try{
                emitter.send(SseEmitter.event().name("Cliente").data(datos.toString()));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
