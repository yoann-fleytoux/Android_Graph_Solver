package db;


public class Edge_tmp {
	public int CURR_VERTEX_ID;
	public int ASSOCIATED_VERTEX_ID;
	public int edge_lenght;
	public String description;
	public String audio;
	
	public Edge_tmp(int CURR_VERTEX_ID, int ASSOCIATED_VERTEX_ID, int edge_lenght, String description, String audio){
		this.CURR_VERTEX_ID = CURR_VERTEX_ID;
		this.ASSOCIATED_VERTEX_ID = ASSOCIATED_VERTEX_ID;
		this.edge_lenght = edge_lenght;
		this.description = description;
		this.audio = audio;
	}
}
