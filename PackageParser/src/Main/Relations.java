package Main;

import java.util.ArrayList;

public class Relations {
	public String Nom;
	public ArrayList<String> interfaces;
	public ArrayList<String> heritages;
	
	public Relations(String Nom) {
		this.Nom = Nom;
		this.interfaces = new ArrayList<String>();
		this.heritages = new ArrayList<String>();
	}
	
	public void addInterface(String nom) {
		this.interfaces.add(nom);
	}
	
	public void addHeritage(String nom) {
		this.heritages.add(nom);
	}
	
	public String toString() {
		return "Nom Relation : " + this.Nom;
	}
	
	public ArrayList<String> getInterfaces() {
		return this.interfaces;
	}
	
	public ArrayList<String> getHeritages() {
		return this.heritages;
	}
	
	public String getNom() {
		return this.Nom;
	}
}
