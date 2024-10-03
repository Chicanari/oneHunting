package mail;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/signup")

public class OCIemail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		//signup内からメール値の取得
		HttpSession session = request.getSession();
		String userEmail = (String) session.getAttribute("userEmail");	
		
		 // メール送信メソッドを呼び出す
	    try {
	        sendEmail(userEmail);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}
 
	private void sendEmail(String recipientEmail) throws Exception {
		// Create a Properties object to contain connection configuration information.
	    Properties props = System.getProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.port", 587);
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.starttls.required", "true");
	    
	    //Create a Session object to represent a mail session with the specified properties.
	    Session session = Session.getDefaultInstance(props);
		
	    
	    // Create a message with the specified information.
	    MimeMessage msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress("oneHunting.teamB@gmail.com", "ひと狩り行こうぜ！"));
	    msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail)); // userEmailをここに使用
	    msg.setSubject("「ひと狩り行こうぜ！」にご登録ありがとうございます。");
	    msg.setContent( "<h1>ご登録いただきありがとうございます！</h1>", "text/html");
		
	    // Create a transport.
	    Transport transport = session.getTransport();
		
	 
	    
	        try
	        {
	 
	            System.out.println("Sending Email now...standby...");
	 
	 
	            // Connect to OCI Email Delivery using the SMTP credentials specified.
	            transport.connect("smtp.gmail.com","oneHunting.teamB@gmail.com", "l n p i m p a x u b c r t u f y");    
	 
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