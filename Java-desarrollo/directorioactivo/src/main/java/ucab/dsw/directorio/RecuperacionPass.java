package ucab.dsw.directorio;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

/**
 * Clase para realizar envios de correos electronicos con contraseñas generadas aleatoriamente
 * @author Jesus Requena
 */
public class RecuperacionPass{

    /**
     * Método para enviar un correo electronico con una nueva contraseña a un usuario
     * @author Jesus Requena
     * @param email con la direccion de correo electronico a enviar
     * @param newPass con la nueva contraseña que se establecera en el usuario
     */
    public void recuperar(String email, String newPass) {

        try {
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.auth", "true");

            Session session = Session.getDefaultInstance(props);

            String correoRemitente = "mercadeoucab.recuperacion@gmail.com";
            String passwordRemitente = "Ucab2020!";
            String correoReceptor = email;
            String asunto = "Recuperacion de contraseña - MercadeoUCAB";
            String mensaje = "<br>Ha solicitado una recuperación de su contraseña para MercadeoUCAB<br>Inicie sesión " +
                    "con la siguiente contraseña y modifique su contraseña actual <br><br>   Nueva contraseña temporal: "
                    + newPass +
                    "<br><br><b>MercadeoUCAB</b>";

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoRemitente));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
            message.setSubject(asunto);
            message.setText(mensaje, "ISO-8859-1", "html");

            Transport t = session.getTransport("smtp");
            t.connect(correoRemitente, passwordRemitente);
            t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            t.close();

        } catch (AddressException ex) {
            System.out.println(ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método para generar una contraseña alfanumerica aleatoria
     * @author Jesus Requena
     * @return String con el la nueva contraseña generada
     */
    public String newPass(){
        String caracteres = "ABCDEFGHIJKLNMOPKRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789001234567890";
        String newpass= "";
        Integer MAX = 12;
        char[] text = new char[MAX];
        Random random = new Random();

        for( int i = 0; i<MAX ;i++ ){
            text[i] = caracteres.charAt(random.nextInt(caracteres.length()));
            newpass += text[i];
        }

        return newpass;
    }
}
