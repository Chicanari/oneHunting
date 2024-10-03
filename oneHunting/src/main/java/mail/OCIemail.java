package mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class OCIemail {

	// Replace FROM with your "From" address.
    // This address must be added to Approved Senders in the console.
    static final String FROM = "oneHunting.teamB@gmail.com";
    static final String FROMNAME = "ひと狩り行こうぜ！";
 
    // Replace TO with a recipient address.
    static final String TO = "yamasaki.hrk97@gmail.com";
 
    // Replace smtp_username with your Oracle Cloud Infrastructure SMTP username generated in console.
    static final String SMTP_USERNAME = FROM ;
 
    // Replace smtp_password with your Oracle Cloud Infrastructure SMTP password generated in console.
    static final String SMTP_PASSWORD = "l n p i m p a x u b c r t u f y";
 
    // Oracle Cloud Infrastructure Email Delivery hostname.
    static final String HOST = "smtp.gmail.com";
 
    // The port you will connect to on the SMTP endpoint. Port 25 or 587 is allowed.
    static final int PORT = 587;
    
    public void sendMail(String to, String subject, String body) throws Exception {
        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.auth.login.disable", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM, FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        msg.setSubject(subject);
        msg.setContent(body, "text/html");

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try {
            System.out.println("Sending Email now...standby...");
            // Connect to OCI Email Delivery using the SMTP credentials specified.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
            // Send email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        } finally {
            // Close & terminate the connection.
            transport.close();
        }
    }

    
    
    public static void main(String[] args) {
        OCIemail ociEmail = new OCIemail();
        String to = "yamasaki.hrk97@gmail.com";
        String subject = "「ひと狩り行こうぜ！」にご登録ありがとうございます。";
        String body = String.join(
            System.getProperty("line.separator"),
            "<!DOCTYPE html>",
            "<html>",
            "<head>",
            "<meta charset=\"UTF-8\" />",
            "</head>",
            "<body>",
            "<p>ご登録いただきありがとうございます！</p>",
            "</body>",
            "</html>"
        );

        try {
            ociEmail.sendMail(to, subject, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    
    
    
    
    
 
     

}	
	
	