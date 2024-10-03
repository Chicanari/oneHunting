package mail;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
 
public class PresentEmail {
 
    // Replace FROM with your "From" address.
    // This address must be added to Approved Senders in the console.
	private final String FROM = "oneHunting.teamB@gmail.com";
	private final String FROMNAME = "ひと狩り行こうぜ！";
	
    // Replace TO with a recipient address.
    private String TO;
 
    // Replace smtp_username with your Oracle Cloud Infrastructure SMTP username generated in console.
    private final String SMTP_USERNAME = FROM ;
 
    // Replace smtp_password with your Oracle Cloud Infrastructure SMTP password generated in console.
    private final String SMTP_PASSWORD = "l n p i m p a x u b c r t u f y";
 
    // Oracle Cloud Infrastructure Email Delivery hostname.
    private final String HOST = "smtp.gmail.com";
 
    // The port you will connect to on the SMTP endpoint. Port 25 or 587 is allowed.
    private final int PORT = 587;
 
    private final String SUBJECT = "【ひと狩り行こうぜ！】クーポンメール";
    
 
    public void sendCouponMail(String id,String mailaddress) throws Exception {
    	
    	//メールアドレスの代入
    	TO = mailaddress;
    	
    	//System.out.println(this.TO + " " + this.name + " " + this.id + " " + this.pw);
    	//System.out.println(FROM+" "+FROMNAME);
    	
    	final String BODY = String.join(
    			 
    	           System.getProperty("line.separator"),
    	           "<h2>狩猟アイテムのお得なクーポン</h2>",
    	           "<p>***************┗┻( ・(ｘ)・ )┻┛**************** <br>",
    	           "　↓罠アイテム１０％OFFコード↓<br>",
    	           "　",id,"-wana-coupon202410<br>",
    	           "　※一度のみご利用可能です。<br>",
    	           "***************┗┻( ・(ｘ)・ )┻┛**************** <br>",
    	          "ひと狩り行こうぜ！：http://localhost:8080/oneHunting/</p>"
    	 
    	);
 
        // Create a Properties object to contain connection configuration information.
 
       Properties props = System.getProperties();
       props.put("mail.transport.protocol", "smtp");
       props.put("mail.smtp.port", PORT);
 
       //props.put("mail.smtp.ssl.enable", "true"); //the default value is false if not set
       props.put("mail.smtp.auth", "true");
       props.put("mail.smtp.auth.login.disable", "true");  //the default authorization order is "LOGIN PLAIN DIGEST-MD5 NTLM". 'LOGIN' must be disabled since Email Delivery authorizes as 'PLAIN'
       props.put("mail.smtp.starttls.enable", "true");   //TLSv1.2 is required
       props.put("mail.smtp.starttls.required", "true");  //Oracle Cloud Infrastructure required
 
        // Create a Session object to represent a mail session with the specified properties.
       Session session = Session.getDefaultInstance(props);
 
        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM,FROMNAME));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(MimeUtility.encodeText(SUBJECT, "UTF-8", "B"));
        msg.setContent(BODY, "text/html; charset=UTF-8");
 
        // Create a transport.
        Transport transport = session.getTransport();
           
 
        // Send the message.
 
        try
        {
 
            System.out.println("Sending Email now...standby...");
 
 
            // Connect to OCI Email Delivery using the SMTP credentials specified.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);    
 
            // Send email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
 
        }
 
        catch (Exception ex) {
 
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
 
        }
 
        finally
 
        {
 
            // Close & terminate the connection.
            transport.close();
 
        }
 
    }
 
}