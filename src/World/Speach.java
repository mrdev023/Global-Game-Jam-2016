package World;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Tools.Coord;

public class Speach {
	
	private ArrayList<Replique> listReplique = new ArrayList<Replique>();
	
	public Speach(String speachName){
		try{
			File fichier = new File("res/donnees/" + speachName + ".txt");
			Scanner fEntree = new Scanner(new BufferedInputStream(new FileInputStream(fichier)));
			String line, tab[];
			ArrayList<String> list;
			
			while(fEntree.hasNext()){
				list = new ArrayList<String>();
				list.clear();
				line = fEntree.nextLine();
				tab = line.split("/#/");
				
				for(int i=1; i<tab.length; i++){
					list.add(tab[i]);
				}
				
				listReplique.add(new Replique(tab[0], list));
			}
			fEntree.close();
		}
		catch(IOException e){
			System.out.println("ERREUR DE LECTURE du fichier : " + speachName + ".txt");
		}
	}
	
	public ArrayList<Replique> getListReplique(){
		return listReplique;
	}
}
