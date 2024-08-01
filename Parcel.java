package Group3;

import java.time.LocalDate;
import java.util.ArrayList;

public class Parcel {
	private int id, weight, commission;
	private LocalDate regDate; 
	
	//Getter and setter
	public int getID() { 
		return id;
	}
	public void setID(int parcelID) {
		id = parcelID;
	}
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		if (weight>0)
			this.weight = weight;
		else 
			throw new IllegalArgumentException ("Weight must be larger than 0");
	}
	
	public int getCom() {
		return commission;
	}
	public void setCom(int value) {
		if (value>0)
			commission = value;
		else
			throw new IllegalArgumentException ("Commission must be larger than 0");
	}
	
	public LocalDate getDate() {
		return regDate;
	}
	public void setDate(LocalDate date) {
		regDate = date;
	}
	
	//Constructor
	public Parcel (int id, int weight, int value, LocalDate date) {
		this.id = id;
		setWeight(weight);
		setCom(value);
		regDate = date;
	}
	
	public boolean checkDuplicate(ArrayList<Parcel> parcels) {
		boolean duplicate = false;
		
		int i = 0;
		while (i < parcels.size() && !duplicate) {
			if(parcels.get(i).getID()== this.getID()) {
				duplicate = true;
				break;
			}
			else
				i++;
		}
		return duplicate;	
	}

}
