package eitc.pucmm.eventosmicroservice.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.*;

import org.joda.time.DateTimeComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import eitc.pucmm.eventosmicroservice.Repository.CompraRepository;
import eitc.pucmm.eventosmicroservice.Repository.EventosRepository;
import eitc.pucmm.eventosmicroservice.entidades.Compra;
import eitc.pucmm.eventosmicroservice.entidades.Evento;

@RestController
public class EventosApiController {
    @Autowired
    EventosRepository eventosRepository;

    @Autowired
    CompraRepository compraRepository;

    //Funciona
    @GetMapping("/compras/general/{filter}")
    List<Compra> getCompras(@PathVariable String filter){
        List<Compra> compras = compraRepository.findAll();
        switch (filter) {
            case "hoy":
                return getTodayCompras(compras);
            case "pasadas":
                return getComprasPasadas(compras);          
            default:
                return compras;
        }
    }

    //Funciona
    @GetMapping("/compras/{id}")
    List<Compra> getAllComprasById(@PathVariable String id){
        long aux = Long.parseLong(id);
        return compraRepository.findAllByIdUsuario(aux);
    }

    //Funciona
    @PostMapping("/compra/{id}")
    List<Evento> realizaCompra(@RequestBody String body,@PathVariable String id) throws IOException{
        
        Compra compra = new Compra();
        compra.setIdUsuario(Long.parseLong(id));
        compra.setFecha(new Date());
        compraRepository.save(compra);

        List<Evento> eventos = parseEventos(body, compra.getId());

        //Llamada a mail-microservice
        URL url = new URL("http://mail-microservice:8080/notificacion/compra");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = new Gson().toJson(body).getBytes("utf-8");
            os.write(input, 0, input.length);			
        }
        try(BufferedReader br = new BufferedReader(
            new InputStreamReader(con.getInputStream(), "utf-8"))) {
              StringBuilder response = new StringBuilder();
              String responseLine = null;
              while ((responseLine = br.readLine()) != null) {
                  response.append(responseLine.trim());
              }
              System.out.println(response.toString());
          }           

        return eventos;
    }

    //Funciona
    @GetMapping("/compra/{id}")
    Compra getCompraById(@PathVariable String id){
        long aux = Long.parseLong(id);
        return compraRepository.findById(aux).orElse(null);
    }

    @GetMapping("/compra/eventos/{id}")
    List<Evento> getEventos(@PathVariable String id){
        long idAux = Long.parseLong(id);
        return eventosRepository.findAllByIdCompra(idAux);
    }

    private List<Compra> getTodayCompras(List<Compra> compras) {
        List<Compra> aux = new ArrayList<>();
        Date hoy = new Date();
        DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
        for (Compra compra : compras) {
            if(dateTimeComparator.compare(hoy, compra.getFecha()) == 0){
                aux.add(compra);
            }
        }
        return aux;
    }

    private List<Compra> getComprasPasadas(List<Compra> compras) {
        List<Compra> aux = new ArrayList<>();
        Date hoy = new Date();
        DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
        for (Compra compra : compras) {
            if( dateTimeComparator.compare(hoy,compra.getFecha()) > 0){
                aux.add(compra);
            }
        }
        return aux;    
    }

    private List<Evento> parseEventos(String body, long l) {
        JsonObject object = new Gson().fromJson(body,JsonObject.class);

        JsonArray array = new Gson().fromJson(object.get("eventos"), JsonArray.class);
        List<Evento> eventos = new ArrayList<>();
        Evento aux;
        for(int i = 0; i < array.size();i++){
            aux = new Gson().fromJson(array.get(i), Evento.class);
            aux.setIdCompra(l);
            eventosRepository.save(aux);
            eventos.add(aux);
        }
        return eventos;
    }
}
