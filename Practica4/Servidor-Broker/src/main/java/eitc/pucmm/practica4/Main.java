package eitc.pucmm.practica4;

import eitc.pucmm.practica4.entidades.Mensaje;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("Inicializando Servidor JMS");
        BrokerService brokerService = new BrokerService();
        brokerService.addConnector("tcp://0.0.0.0:61616");
        brokerService.start();

    }
}
