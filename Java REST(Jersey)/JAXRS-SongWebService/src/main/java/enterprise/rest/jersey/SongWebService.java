package enterprise.rest.jersey;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/songwebservice")
public class SongWebService {
	
	private static Hashtable<Integer,Song> songList = new Hashtable<Integer,Song> (); 

	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response create( @QueryParam("title") String title, @QueryParam("id") String id,@QueryParam("artist") String artist,@QueryParam("genre") String genre) {
		if (!songList.containsKey(Integer.parseInt(id)) && title.length()>0 && artist.length()>0 && genre.length()>0){
				Song song = new Song(Integer.parseInt(id),title,artist,genre);
    			songList.put(Integer.parseInt(id), song);
    			return Response.status(200).entity(song).build();
    		}
    		else{//Error
    			return Response.status(400).entity("Song is already created or there are some bad parameters values").build();
    		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/search")
    public Response search(@QueryParam("title") String title,@QueryParam("artist") String artist){
		if (title!=null && artist!=null){
			return Response.status(400).entity("Only 1 filter accepted").build();
		}
		else{
			Enumeration<Integer> e = songList.keys();
			Integer key = null;
			ArrayList<Song> list = new ArrayList<Song>();  
			while (e.hasMoreElements()) {
				key = e.nextElement();
				if (title!=null){
					if (songList.get( key).getTitle().toLowerCase().contains(title.toLowerCase()))
						list.add(songList.get( key));
				}
				else{
						if (songList.get( key).getArtist().toLowerCase().contains(artist.toLowerCase()))
							list.add(songList.get( key));
					}
			}
			if (list.size()>0){
				return Response.status(200).entity(list).build();
			}
			else{
				return Response.status(400).entity("No song found with these values.").build();
			}
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response update(@QueryParam("title") String title, @QueryParam("id") String id,@QueryParam("artist") String artist,@QueryParam("genre") String genre){
		if (id!=null && songList.get(Integer.parseInt(id))!=null){
			
			Song song = songList.get(Integer.parseInt(id)); 
			Song newsong = new Song(Integer.parseInt(id),title!=null?title:song.getTitle(),artist!=null?artist:song.getArtist(),genre!=null?genre:song.getGenre());
			songList.put(Integer.parseInt(id), newsong);
			return Response.status(200).entity(newsong).build();
		}
		else{
			return Response.status(400).entity("Id is not specified or not exist").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public Response delete( @QueryParam("id") String id){
		if (id!=null && songList.get(Integer.parseInt(id))!=null){
			songList.remove(Integer.parseInt(id));
			return Response.status(200).entity("Done. Song removed!").build();
		}
		else{
			return Response.status(400).entity("Id is not specified or not exist").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/list")
    public Response list(@QueryParam("artist") String artist,@QueryParam("genre") String genre){
		if (genre!=null && artist!=null){
			return Response.status(400).entity("Only 1 filter accepted").build();
		}
		else{
			Enumeration<Integer> e = songList.keys();
			ArrayList<Song> list = new ArrayList<Song>();
			Integer key = null;
			while (e.hasMoreElements()) {
				key = e.nextElement();
				if (genre!=null){
					if (songList.get( key).getGenre().toLowerCase().equals(genre.toLowerCase()))
						list.add(songList.get( key));
				}
				else{
					if (songList.get( key).getArtist().toLowerCase().equals(artist.toLowerCase()))
						list.add(songList.get(key));
				}
			}
			if (list.size()>0){
				return Response.status(200).entity(list).build();
			}
			else{
				return Response.status(400).entity("No song found with these values").build();
			}
		}
	}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/album/{id}")
    public Response responseMsg1( @PathParam("id") String id) {
    	if (songList.get(Integer.parseInt(id))!=null){
    		return Response.status(200).entity(songList.get(Integer.parseInt(id))).build();
    	}
    	else{
    		return Response.status(400).entity("No song found").build();
    	}
    }
}

