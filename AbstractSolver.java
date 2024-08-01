package Group3;

import java.util.ArrayList;


public abstract class AbstractSolver implements Knapsack {
	
	protected ArrayList<Parcel> parcels;
    protected int maxCapacity;
    
	public AbstractSolver(ArrayList<Parcel> parcels, int capacity){
		 this.parcels = parcels;
	     this.maxCapacity = capacity;
	};
	

	public int calculateTotalWeight() {
        int totalWeight = 0;
        for (Parcel parcel : parcels) {
            totalWeight += parcel.getWeight();
        }
        return totalWeight;
    }

	
	public abstract void solve();
	
	public abstract void result();
	

}
