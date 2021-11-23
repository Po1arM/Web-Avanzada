package eitc.pucmm.practica4;

import eitc.pucmm.practica4.Controladores.MessageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Practica4Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = SpringApplication.run(Practica4Application.class, args);
        Main.main(args);
        MensajeRepository mensajeRepository = (MensajeRepository) applicationContext.getBean("mensajeRepository");
        MessageController messageController = (MessageController) applicationContext.getBean("messageController");
        ServidorService servidorService = new ServidorService(mensajeRepository, messageController);
        servidorService.start();
    }

}
