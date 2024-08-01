package Group3;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class MainApplication {
		
	public static void main(String [] args) throws FileNotFoundException {
		int maxCapacity = 100;

		//Read and store data
		String filename = "src/Group3/parcelData.txt";
		Scanner reader = new Scanner(new File(filename));
		ArrayList <Parcel> parcels = new ArrayList <>();
		
		while (reader.hasNext()) {
			String par = reader.next();
			String[] split = par.split("[,]",0);
			
			int id = Integer.parseInt(split[0]); 
			int weight = Integer.parseInt(split[1]);
			int value = Integer.parseInt(split[2]);
			LocalDate date = LocalDate.parse(split[3]);
			
			Parcel temporary = new Parcel(id,weight,value,date);
			
			if(parcels.isEmpty() || !temporary.checkDuplicate(parcels)) //Check duplication of ID
				parcels.add(temporary);
		}
		
		//Sort parcel by date
		Collections.sort(parcels, new ParcelDateComparator());
		System.out.println("Parcel data after sort by date:");
		for (int i=0; i<parcels.size();i++) 
			System.out.println(" " + parcels.get(i).getID()+ "\t" + 
			     	   parcels.get(i).getWeight()+ "\t" + 
			     	   parcels.get(i).getCom()+ "\t" +
			     	   parcels.get(i).getDate());
		System.out.println("\n");
		
		//Start the Algorithm and print result
		//Branch and Bound
		BranchNBound testBNB = new BranchNBound(parcels,maxCapacity);
		testBNB.solve();
		testBNB.result();
		
		
		//Dynamic Programming
		DynamicProgramming testDP = new DynamicProgramming(parcels,maxCapacity);
		testDP.solve();
		testDP.result();
		
	}

}
