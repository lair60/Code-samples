package enterprise.rest.jersey;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import enterprise.db.DBSong;

@Path("/songwebservice")
public class SongWebService1 {

	private DBSong db;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/create")
    public Response create( @QueryParam("title") String title, @QueryParam("id") String id,@QueryParam("artist") String artist,@QueryParam("genre") String genre) {
		db = DBSong.getDbCon();
		try {
			ResultSet rs = db.query("select id from Song where id=" + Integer.parseInt(id));
			if (!rs.next()){
				Song song = new Song(Integer.parseInt(id),title,artist,genre);
				//songList.put(Integer.parseInt(id), song);
				try {
					db.insert("INSERT INTO Song VALUES ("+Integer.parseInt(id)+",'"+title+"','"+artist+"','"+genre+"')");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return Response.status(400).entity("Error database connection!!").build();
				}
				return Response.status(200).entity(song).header("Access-Control-Allow-Origin","*").build();
			}
			else{
				Response.status(400).entity("Song is already created or there are some bad parameters values").build();
			}
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return Response.status(400).entity("Bad format number").build();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return Response.status(400).entity("Error in DB").build();
		}
		return Response.status(400).entity("Unknown error").build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/searchAll")
    public Response searchAll(){
		db = DBSong.getDbCon();
		ArrayList<Song> list = new ArrayList<Song>();
		ResultSet rs;
		try {
			rs = db.query("select * from Song");
			while (rs.next()) 
			{ 
				int i =rs.getInt(1);
				System.out.println(i);
				String title=rs.getString(2);
				System.out.println(title);
				String artist=rs.getString(3);
				System.out.println(artist);
				String genre=rs.getString(4);
				System.out.println(genre);
				list.add(new Song(i,title,artist,genre));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(400).entity("Error SQL").build();
		}
		return Response.status(200).entity(list).header("Access-Control-Allow-Origin","*").build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/search")
    public Response search(@QueryParam("title") String title,@QueryParam("artist") String artist,@QueryParam("genre") String genre){
		db = DBSong.getDbCon();
		ArrayList<Song> list = new ArrayList<Song>();
		try {
			ResultSet rs;
			String query = "select * from Song where ";
			String t1 = null;
			String t2 = null;
			String t3 = null;
			if (title!=null && !title.equals(""))
				t1 = "lower(title) like lower('%" + title + "%')";
			if (artist!=null && !artist.equals(""))
				t2 = "lower(artist) like lower('%" + artist+"%')";
			if (genre!=null && !genre.equals(""))
				t3 = "lower(genre) like lower('%" + genre+"%')";
			if (t1!=null){
				query = query + t1;
				if (t2!=null){
					query = query + " and " + t2;
					if (t3!=null)
						query = query + " and " + t3;
				}
				else{
					if (t3!=null)
						query = query + " and " + t3;
				}
			}
			else{
				if (t2!=null){
					query = query + t2;
					if (t3!=null)
						query = query + " and " + t3;
				}
				else{
					if (t3!=null)
						query = query + t3;
				}
			}
			rs = db.query(query);
			while (rs.next()) 
			{
				list.add(new Song(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
			}
		}catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return Response.status(400).entity("Bad format number").build();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return Response.status(400).entity("Error in DB").build();
		}
	
		if (list.size()>0){
			return Response.status(200).entity(list).build();
		}
		else{
			return Response.status(400).entity("No song found with these values.").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/update")
    public Response update(@QueryParam("title") String title, @QueryParam("id") String id,@QueryParam("artist") String artist,@QueryParam("genre") String genre){
		db = DBSong.getDbCon();
		try {
			ResultSet rs = db.query("select id from Song where id=" + Integer.parseInt(id));
			if (rs.next()){
				Song song = new Song(Integer.parseInt(id),title,artist,genre);
				//songList.put(Integer.parseInt(id), song);
				try {
					db.insert("UPDATE Song SET id="+Integer.parseInt(id)+",title='"+title+"',artist='"+artist+"',genre='"+genre+"' WHERE id="+Integer.parseInt(id));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return Response.status(400).entity("Error database connection!!").build();
				}
				return Response.status(200).entity(song).header("Access-Control-Allow-Origin","*").build();
			}
			else{
				return Response.status(400).entity("Song with id="+Integer.parseInt(id)+ "doesnt exist!!").build();
			}
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(400).entity("Error!!").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public Response delete( @QueryParam("id") String id){
			db = DBSong.getDbCon();
			try {
				int res= db.delete("delete from Song where id="+Integer.parseInt(id));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Response.status(400).entity("Bad number format").build();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Response.status(400).entity("Error in DB").build();
			}
			return Response.status(200).entity("Done. Song removed!").header("Access-Control-Allow-Origin","*").build();
	}
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/album/{id}")
    public Response responseMsg1( @PathParam("id") String id){
    	db = DBSong.getDbCon();
		try {
			ResultSet rs = db.query("select id from Song where id=" + Integer.parseInt(id));
			if (rs.next()){
				return Response.status(200).entity(new Song(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4))).build();
			}
			else{
				return Response.status(400).entity("No song found").build();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(400).entity("Bad number format").build();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(400).entity("Error in DB").build();
		}
    }
}

