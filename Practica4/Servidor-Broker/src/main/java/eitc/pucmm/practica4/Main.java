package eitc.pucmm.practica4;

import org.apache.activemq.broker.BrokerService;

public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("Inicializando Servidor JMS");
        BrokerService brokerService = new BrokerService();
        brokerService.addConnector("tcp://0.0.0.0:61616");
        brokerService.start();

        ServidorService instancia = new ServidorService();

    }
}
