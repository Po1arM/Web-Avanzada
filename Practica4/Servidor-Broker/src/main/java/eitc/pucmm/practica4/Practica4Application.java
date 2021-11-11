package eitc.pucmm.practica4;

import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Practica4Application {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = SpringApplication.run(Practica4Application.class, args);
        Main.main(args);
        MensajeRepository mensajeRepository = (MensajeRepository) applicationContext.getBean("mensajeRepository");
        ServidorService servidorService = new ServidorService(mensajeRepository);
        servidorService.start();
    }

}
