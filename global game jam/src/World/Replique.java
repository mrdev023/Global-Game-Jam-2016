package World;

import java.util.ArrayList;

public class Replique {

	private String speakerName;
	private String lignesReplique[] = new String[4];
	
	public Replique(String speakerName, ArrayList<String> lignesReplique){
		this.speakerName = speakerName;
		this.lignesReplique[0] = "";
		this.lignesReplique[1] = "";
		this.lignesReplique[2] = "";
		this.lignesReplique[3] = "";
		for(int i=0; i<lignesReplique.size(); i++){
			this.lignesReplique[i] = lignesReplique.get(i);
		}
	}
	
	public String getSpeakerName(){
		return speakerName;
	}
	
	public String[] getLignesReplique(){
		return lignesReplique;
	}
}
