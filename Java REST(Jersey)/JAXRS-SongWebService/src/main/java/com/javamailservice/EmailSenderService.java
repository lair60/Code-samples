package com.javamailservice;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/emailSenderService")
public class EmailSenderService {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/sendMail")
	public Response send( @FormParam("name") String name, @FormParam("email") String email,@FormParam("message") String messageText) {
		if (sendEmail(name,email ,messageText)){
			return Response.status(200).entity("Thank you for taking the time to contact me. I will be in touch shortly!").header("Access-Control-Allow-Origin","*").build(); 
		}
		else{
			return Response.status(400).entity("Sorry, connection failed. Please try again later.").build();
		}
	}
	
	private Session session;
	
	final String username = "laircode@gmail.com";
	final String password = "laircode925120840";

	private Properties properties = new Properties();
 
	private void init() {

		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
 
		session = Session.getInstance(properties,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				  });
	}
 
	public boolean sendEmail(String name, String email ,String messageText){
 
		init();
		try{
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("lair60@yahoo.es"));
			message.setSubject("Visitor Message from LairCode.com");
			message.setContent("Hi Luis,<br><br> <strong>" + name + "</strong> is sending the following message: <br><br><strong><i>"+messageText+"</i></strong><br><br> Please reply as soon as possible to <strong>"+email+ "</strong><br><br> Thank you!!!","text/html; charset=utf-8");

			Transport.send(message);
			return true;
		}catch (MessagingException me){
                        //Aqui se deberia o mostrar un mensaje de error o en lugar
                        //de no hacer nada con la excepcion, lanzarla para que el modulo
                        //superior la capture y avise al usuario con un popup, por ejemplo.
			System.out.println("Exception:"+ me.getMessage());
			return false;
		}
		
	}
 
}
