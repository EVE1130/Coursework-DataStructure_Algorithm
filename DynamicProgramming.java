package Group3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DynamicProgramming extends AbstractSolver {
	
    List<Parcel> selectedParcels = new ArrayList<>();
    
    // maximum commission 
    protected double maxCom;
    public double getMaxCom() { 
		return maxCom;
	}
	public void setMaxCom(double maxCom) {
		this.maxCom = maxCom;
	}

	// quantity of parcels
	protected int qty;
	public int getQty() { 
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	// Constructor
	public DynamicProgramming(ArrayList<Parcel> parcels, int maxCapacity) {
		super(parcels, maxCapacity);
		setQty(parcels.size());
	}
	
	public void solve() {
		int[][] dp = new int[qty + 1][maxCapacity + 1];
		
		boolean[][] selected = new boolean[qty + 1][maxCapacity + 1];


		Parcel[] parcelArray = parcels.toArray(new Parcel[0]);
	    
	    // Initialize the first item
        for (int w = 1; w <= maxCapacity; w++) {
            if (parcelArray[0].getWeight() <= w) {
                dp[1][w] = parcelArray[0].getCom();
                selected[1][w] = true;
            }
        }
	    
	    for (int i = 2; i <= qty; i++) {
	        for (int w = 1; w <= maxCapacity; w++) {
	            if (parcelArray[i - 1].getWeight() <= w) {
	                int include = dp[i - 1][w - parcelArray[i - 1].getWeight()] + parcelArray[i - 1].getCom();
	                int exclude = dp[i - 1][w];
	                dp[i][w] = Math.max(include, exclude);

	                // Mark the item as selected if it contributes to the maximum value
	                if (include > exclude) {
	                    selected[i][w] = true;	           
	                }
	          }
	          else {
	        	  dp[i][w] = dp[i - 1][w];
	          }
	        }
	    }
	    
	    // get selected items
	    int currentItem = qty;
        int remainingCapacity = maxCapacity;
        
        while (currentItem > 0 && remainingCapacity > 0) {
            if (selected[currentItem][remainingCapacity]) {
            	selectedParcels.add(parcelArray[currentItem - 1]);
                remainingCapacity -= parcelArray[currentItem - 1].getWeight();
            }
            currentItem--;
        }	    
		
        // get maximum commission
        setMaxCom(dp[qty][maxCapacity]);
	}
	
	public void result() {
		Collections.sort(selectedParcels, new ParcelDateComparator());

	    
		System.out.print("  --------------------Dynamic Programming Solution--------------------"
			       + "\n Total Commission: " + calculateTotalCom()
			       + "\n Total Weight: " + calculateTotalWeight()
			       + "\n Parcel Selected: \n ID  Weight  Commission  Registered Date \n");
	
		for (Parcel parcel:selectedParcels) {
			System.out.println(" " + parcel.getID()+ "\t" + 
			     	   parcel.getWeight()+ "\t" + 
			     	   parcel.getCom()+ "\t" +
			     	   parcel.getDate()+ "\t" );
		}
		System.out.println("\n");
	}
	
	public int calculateTotalWeight() {
        int totalWeight = 0;
        for (Parcel parcel : selectedParcels) {
            totalWeight += parcel.getWeight();
        }
        return totalWeight;
    }
	
	public double calculateTotalCom() {
		return maxCom;
	}


}
