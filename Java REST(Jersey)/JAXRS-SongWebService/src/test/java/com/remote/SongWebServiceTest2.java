package com.remote;
import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONException;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

import enterprise.rest.jersey.Song;

public class SongWebServiceTest2 extends JerseyTest {
	
	private static final String URL_RESOURCE = "http://ec2-52-28-238-199.eu-central-1.compute.amazonaws.com:8080/JAXRS-WebServices/musicWebService/";
	private static final String create_service = "/songwebservice2/create";
	private static final String delete_service = "/songwebservice2/delete";
	private static final String update_service = "/songwebservice2/update";
	private static final String search_service = "/songwebservice2/search";
	private static final String searchAll_service = "/songwebservice2/searchAll";
	private static final String getAlbumId_service = "/songwebservice2/album/";

	@Override
	protected AppDescriptor configure(){
		return new WebAppDescriptor.Builder().build();
	}
 
	@Test
	public void testCreate() throws JSONException,
			URISyntaxException {
		WebResource webResource = client().resource(URL_RESOURCE);
		// CREATE SONG 1
		ClientResponse responseMsg = webResource.path(create_service)
        .queryParam("id", "1")
        .queryParam("title", "London Calling")
        .queryParam("artist", "The Clash")
        .queryParam("genre", "Punk Rock")
        .get(ClientResponse.class);
		
		assertEquals( 200, responseMsg.getStatus());
		
		Song  song = responseMsg.getEntity(Song.class);
		assertEquals(1, song.getId());
		assertEquals("London Calling", song.getTitle());
		assertEquals("The Clash", song.getArtist());
		assertEquals("Punk Rock", song.getGenre());
		
		//Remove the elements for the next tests
		responseMsg = webResource.path(delete_service)
        .queryParam("id", "1")
        .get(ClientResponse.class);
	}
	
	@Test
	public void testUpdate() throws JSONException,
			URISyntaxException {
		WebResource webResource = client().resource(URL_RESOURCE);
		
		// CREATE SONG 1
		ClientResponse responseMsg = webResource.path(create_service)
		.queryParam("id", "1")
        .queryParam("title", "London Calling")
        .queryParam("artist", "The Clash")
        .queryParam("genre", "Punk Rock")
        .get(ClientResponse.class);

		
		responseMsg = webResource.path(update_service)
		.queryParam("id", "1")
        .queryParam("title", "London Calling 2")
        .queryParam("artist", "The Clash")
        .queryParam("genre", "Punk Rock")
        .get(ClientResponse.class);
		
		assertEquals( 200, responseMsg.getStatus());
		
		Song  song = responseMsg.getEntity(Song.class);
		assertEquals(1, song.getId());
		assertEquals("London Calling 2", song.getTitle());
		assertEquals("The Clash", song.getArtist());
		assertEquals("Punk Rock", song.getGenre());
		
		//Remove the elements for the next tests
		responseMsg = webResource.path(delete_service)
        .queryParam("id", "1")
        .get(ClientResponse.class);
	}
	@Test
	public void testList() throws JSONException,
			URISyntaxException {
		WebResource webResource = client().resource(URL_RESOURCE);
		// CREATE SONG 1
		ClientResponse responseMsg = webResource.path(create_service)
		.queryParam("id", "1")
        .queryParam("title", "London Calling")
        .queryParam("artist", "The Clash")
        .queryParam("genre", "Punk Rock")
        .get(ClientResponse.class);
		
		// CREATE SONG 2
		responseMsg = webResource.path(create_service)
	    .queryParam("id", "2")
        .queryParam("title", "London Calling 2")
        .queryParam("artist", "The Clash")
        .queryParam("genre", "Punk Rock 2")
	    .get(ClientResponse.class);
        
		responseMsg = webResource.path(search_service)
        .queryParam("artist", "The Clash")
        .get(ClientResponse.class);
		
		assertEquals( 200, responseMsg.getStatus());
		List<Song>  songlist = responseMsg.getEntity(new GenericType<List<Song>>() {});
		assertEquals(2, songlist.size());//2 Songs
		if (songlist.get(0).getId()==1){
			assertEquals(2, songlist.get(1).getId());
		}
		else{
			assertEquals(2, songlist.get(0).getId());
			assertEquals(1, songlist.get(1).getId());
		}
		
		if (songlist.get(0).getTitle().equals("London Calling")){
			assertEquals("London Calling 2", songlist.get(1).getTitle());
		}
		else{
			assertEquals("London Calling 2", songlist.get(0).getTitle());
			assertEquals("London Calling", songlist.get(1).getTitle());
		}
		
		assertEquals("The Clash", songlist.get(0).getArtist());
		assertEquals("The Clash", songlist.get(1).getArtist());
		
		
		if (songlist.get(0).getGenre().equals("Punk Rock")){
			assertEquals("Punk Rock 2", songlist.get(1).getGenre());
		}
		else{
			assertEquals("Punk Rock 2", songlist.get(0).getGenre());
			assertEquals("Punk Rock", songlist.get(1).getGenre());
		}
		
		//Remove the elements for the next tests
		responseMsg = webResource.path(delete_service)
        .queryParam("id", "1")
        .get(ClientResponse.class);
		
		responseMsg = webResource.path(delete_service)
        .queryParam("id", "2")
        .get(ClientResponse.class);
	}
	
	@Test
	public void testSearch() throws JSONException,
			URISyntaxException {
		WebResource webResource = client().resource(URL_RESOURCE);
		// CREATE SONG 1
		ClientResponse responseMsg = webResource.path(create_service)
		.queryParam("id", "1")
        .queryParam("title", "Digital Love")
        .queryParam("artist", "Daft Punk")
        .queryParam("genre", "Electronic")
        .get(ClientResponse.class);
		
		// CREATE SONG 2
		responseMsg = webResource.path(create_service)
	    .queryParam("id", "2")
        .queryParam("title", "She Will Be Love")
        .queryParam("artist", "Maroon 5")
        .queryParam("genre", "Pop")
	    .get(ClientResponse.class);
		
		// CREATE SONG 3
		responseMsg = webResource.path(create_service)
	    .queryParam("id", "3")
        .queryParam("title", "You’ve Got The Love")
        .queryParam("artist", "Florence + The Machine")
        .queryParam("genre", "Indie")
	    .get(ClientResponse.class);
		
		
		 responseMsg = webResource.path(search_service)
        .queryParam("title", "love")
        .get(ClientResponse.class);
		
		assertEquals( 200, responseMsg.getStatus());
		
		List<Song>  songlist = responseMsg.getEntity(new GenericType<List<Song>>() {});
		assertEquals(3, songlist.size());//3 Songs
		String t0,a0,g0,t1,a1,g1,t2,a2,g2;

		switch (songlist.get(0).getId()) {
			case 1:
				t0="Digital Love";
				a0="Daft Punk";
				g0="Electronic";
				if (songlist.get(1).getId()==2){
					t1="She Will Be Love";
					a1="Maroon 5";
					g1="Pop";
					
					t2="You’ve Got The Love";
					a2="Florence + The Machine";
					g2="Indie";
				}
				else{
					t1="You’ve Got The Love";
					a1="Florence + The Machine";
					g1="Indie";
					
					t2="She Will Be Love";
					a2="Maroon 5";
					g2="Pop";
				}
				break;
			case 2:
				t0="She Will Be Love";
				a0="Maroon 5";
				g0="Pop";
				if (songlist.get(1).getId()==1){
					t1="Digital Love";
					a1="Daft Punk";
					g1="Electronic";
					
					t2="You’ve Got The Love";
					a2="Florence + The Machine";
					g2="Indie";
				}
				else{
					t1="You’ve Got The Love";
					a1="Florence + The Machine";
					g1="Indie";
					
					t2="Digital Love";
					a2="Daft Punk";
					g2="Electronic";
				}
				
				break;
			case 3:
				t0="You’ve Got The Love";
				a0="Florence + The Machine";
				g0="Indie";
				if (songlist.get(1).getId()==1){
					t1="Digital Love";
					a1="Daft Punk";
					g1="Electronic";
					
					t2="She Will Be Love";
					a2="Maroon 5";
					g2="Pop";
				}
				else{
					t1="She Will Be Love";
					a1="Maroon 5";
					g1="Pop";
					
					t2="Digital Love";
					a2="Daft Punk";
					g2="Electronic";
				}
				break;

			default:
				t0 ="";
				a0 ="";
				g0 ="";
				t1 ="";
				a1 ="";
				g1 ="";
				t2 ="";
				a2 ="";
				g2 ="";
				break;
		}
		
		assertEquals(t0, songlist.get(0).getTitle());
		assertEquals(a0, songlist.get(0).getArtist());
		assertEquals(g0, songlist.get(0).getGenre());
		assertEquals(t1, songlist.get(1).getTitle());
		assertEquals(a1, songlist.get(1).getArtist());
		assertEquals(g1, songlist.get(1).getGenre());
		assertEquals(t2, songlist.get(2).getTitle());
		assertEquals(a2, songlist.get(2).getArtist());
		assertEquals(g2, songlist.get(2).getGenre());
		
		//Remove the elements for the next tests
		responseMsg = webResource.path(delete_service)
        .queryParam("id", "1")
        .get(ClientResponse.class);

		responseMsg = webResource.path(delete_service)
        .queryParam("id", "2")
        .get(ClientResponse.class);
		
		responseMsg = webResource.path(delete_service)
        .queryParam("id", "3")
        .get(ClientResponse.class);
	}
	
	@Test
	public void testRemove() throws JSONException,
			URISyntaxException {
		WebResource webResource = client().resource(URL_RESOURCE);
		// CREATE SONG 1
		ClientResponse responseMsg = webResource.path(create_service)
		.queryParam("id", "1")
        .queryParam("title", "London Calling")
        .queryParam("artist", "The Clash")
        .queryParam("genre", "Punk Rock")
        .get(ClientResponse.class);
		
		responseMsg = webResource.path(delete_service)
        .queryParam("id", "1")
        .get(ClientResponse.class);
		
		assertEquals( 200, responseMsg.getStatus());
		
		String  message = responseMsg.getEntity(String.class);
		assertEquals( "Done. Song removed!",message);
	}
 
	@Test(expected = UniformInterfaceException.class)
	public void testSongNotFound() {
		WebResource webResource = client().resource(URL_RESOURCE);
		webResource.path(getAlbumId_service + "10").get(Song.class);
	}
}