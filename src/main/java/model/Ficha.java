package model;

public class Ficha {

	private String nombre;
	private String url;
	private String imagen;
	private String torrent;
	private String details;
	
	public Ficha() {
		this.nombre = null;
		this.url = null;
		this.imagen = null;
		this.torrent = null;
		this.setDetails(null);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getImagen() {
		return imagen;
	}
	
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getTorrent() {
		return torrent;
	}

	public void setTorrent(String torrent) {
		this.torrent = torrent;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}	
	
}
