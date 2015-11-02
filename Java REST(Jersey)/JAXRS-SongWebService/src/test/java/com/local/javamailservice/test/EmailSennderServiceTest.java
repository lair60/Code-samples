package com.local.javamailservice.test;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class EmailSennderServiceTest extends JerseyTest {
	
	private static final String URL_RESOURCE = "http://localhost:8080/JAXRS-WebServices/emailWebService";
	private static final String send_mail_service = "/emailSenderService/sendMail";

	@Override
	protected AppDescriptor configure(){
		return new WebAppDescriptor.Builder().build();
	}
	@Test
	public void test() {
		MultivaluedMap<String, String> formData = new MultivaluedMapImpl();
		formData.add("name", "Daniel");
		formData.add("email", "daniel@yahoo.es");
		formData.add("message", "hello!!!!");
		WebResource webResource = client().resource(URL_RESOURCE);
		ClientResponse responseMsg = webResource.path(send_mail_service).type(MediaType.
                APPLICATION_FORM_URLENCODED_TYPE)
                .post(ClientResponse.class, formData);
		assertEquals( 200, responseMsg.getStatus());
	}

}
