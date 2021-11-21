package eitc.pucmm.practica4.Controladores;

import eitc.pucmm.practica4.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class httpController {

    @Autowired
    private MensajeRepository mensajeRepository;

    @RequestMapping("/")
    public String index(Model model){
        return "index.html";
    }

}
