package eitc.pucmm.eventosmicroservice.Controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import eitc.pucmm.eventosmicroservice.Repository.EventosRepository;
import eitc.pucmm.eventosmicroservice.entidades.Compra;

@RestController
public class EventosApiController {
    @Autowired
    EventosRepository eventosRepository;

    @GetMapping("/compras/general/{filter}")
    List<Compra> getCompras(@PathVariable String filter){
        List<Compra> compras = eventosRepository.findAll();
        switch (filter) {
            case "hoy":
                return getTodayCompras(compras);
            case "pasadas":
                return getComprasPasadas(compras);          
            default:
                return compras;
        }
    }

    @GetMapping("/compras/{id}")
    List<Compra> getAllComprasById(@PathVariable long id){
        return eventosRepository.findAllByIdUsuario(id);
    }

    @PostMapping("/compra")
    Compra realizaCompra(@RequestBody String body) throws IOException{
        Compra compra = new Gson().fromJson(body, Compra.class);
        compra.setFecha(new Date());
        eventosRepository.save(compra);

        URL url = new URL("http://mail-microservice:8080/notificacion/compra");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("body", new ObjectMapper().writeValueAsString(compra));
        con.connect();

        return compra;
    }

    @GetMapping("/compra/{id}")
    Compra getCompraById(@PathVariable long id){
        return eventosRepository.getById(id);
    }

    @PutMapping("/compra/{idEvento}/{idEmpleado}")
    Compra updateCompra(@PathVariable long idEvento, @PathVariable long idEmpleado){
        Compra compra = eventosRepository.getById(idEvento);
        compra.setIdEmpleado(idEmpleado);
        eventosRepository.save(compra);
        return null;
    }
    
    private List<Compra> getTodayCompras(List<Compra> compras) {
        List<Compra> aux = new ArrayList<>();
        Date hoy = new Date();
        for (Compra compra : compras) {
            if( hoy.equals(compra.getFecha())){
                aux.add(compra);
            }
        }
        return aux;
    }

    private List<Compra> getComprasPasadas(List<Compra> compras) {
        List<Compra> aux = new ArrayList<>();
        Date hoy = new Date();
        for (Compra compra : compras) {
            if( hoy.after(compra.getFecha())){
                aux.add(compra);
            }
        }
        return aux;    
    }
}
