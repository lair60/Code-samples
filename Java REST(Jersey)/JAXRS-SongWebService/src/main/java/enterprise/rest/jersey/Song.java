package enterprise.rest.jersey;

import com.owlike.genson.annotation.JsonProperty;

public class Song{
	
	@JsonProperty("id")
	private int id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("artist")
	private String artist;
	@JsonProperty("genre")
	private String genre;
	
	public Song(){}
	
	public Song(int id,String title,String artist,String genre) {
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.genre = genre;		
	}
	public int getId(){
		return id;
	}
	public String getTitle(){
		return title;
	}
	public String getArtist(){
		return artist;
	}
	public String getGenre(){
		return genre;
	}
	@Override
    public String toString() {
        return "Song [id=" + id +", title=" + title + ", artist=" + artist + ", genre=" + genre + "]";
    }

}
