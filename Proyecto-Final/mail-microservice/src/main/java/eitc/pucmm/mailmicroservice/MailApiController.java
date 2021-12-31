package eitc.pucmm.mailmicroservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class MailApiController {

    public void main(){
        enviarCorreo("", "", "");
    }
    @PostMapping("/notificacion/registro")
    public boolean notificarRegistro(@RequestBody String body){
        JsonObject json = new Gson().fromJson(body, JsonObject.class);
        String correo = json.get("correo").getAsString();
        //Definir el cuerpo del correo
        //Recordar que se tiene que enviar la contraseña
        String html = "";
        enviarCorreo(correo, "Confirmacion de registro - PUCMM Eventos", html);
        return true;
    }

    @PostMapping("/notificacion/compra")
    public boolean notificarCompra(@RequestBody String body) throws IOException{
        //Se deben hacer dos llamadas, una para el cliente y otra que sea para notificar a todos los empleados
        notificarEmpleados();
        return enviarCorreo(body, "Notificacion de compra", "");
    }

    public void notificarEmpleados() throws IOException {
        //Hacer llamada al microservicio de usuarios para obtener una lista de correos
        //Hacer un foreach llamando a la funcion enviar correo
        URL url = new URL("http://user-microservice:8080/usuarios/tipo/empleado");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.connect();
        System.out.println(con.getResponseMessage());
    }
    public boolean enviarCorreo(String destinatario,String subject,String html){
        try {
            Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.sendgrid.net", 587, "apikey", "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")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withSessionTimeout(10 * 1000)
                .clearEmailAddressCriteria() // turns off email validation
                .withDebugLogging(true)
                .buildMailer();

            /*Cargando la información
            byte[] imagen = Main.class.getResourceAsStream("/logoPucmm.png").readAllBytes();
            byte[] anexo = Main.class.getResourceAsStream("/isc1b.pdf").readAllBytes();*/

            
            Email email = EmailBuilder.startingBlank()
                    .from("noreply@fguzman.codes")
                    .to(destinatario)
                    .withReplyTo("Soporte", "soporte@fguzman.codes")
                    .withSubject(subject)
                    .withHTMLText(html)
                    .withPlainText("No visualiza la información en formato html")

                    .withReturnReceiptTo()
                    .withBounceTo("bounce@fguzman.codes")
                    .buildEmail();

            //Enviando el mensaje:
            mailer.sendMail(email);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
