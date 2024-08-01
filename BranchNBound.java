package Group3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;


public class BranchNBound extends AbstractSolver{
	protected TreeNode FinalLB;            //Store the details of the best combinations
	
	//Setter and Getter
	public TreeNode getFinal() {
		return FinalLB;
	}
	public void setFinal(TreeNode node) {
		FinalLB = node;
	}
	
	//Constructor
	@SuppressWarnings("unchecked")
	public BranchNBound(ArrayList<Parcel> parcels, int maxCapacity) {
		super((ArrayList<Parcel>) parcels.clone(), maxCapacity);
	}

	//Branch and Bound Method
	@Override
	public void solve() {
		//Creates empty PriorityQueue sort by Lower bound
		PriorityQueue <TreeNode> nodesQueue= new PriorityQueue<TreeNode>(new SortByLB());
		
		//Initial path show all parcel not included 
		int [] initialPath = new int [parcels.size()];     
		Arrays.fill(initialPath, 0);
		
		//Create a root node with combination starts from the first parcel (parcel[0])
		TreeNode root = new TreeNode(0,0,0,0,initialPath);   
	    computeBound(root,root.getLvl());                    
	    
	    //Assign initial max bound 
	    FinalLB = root;          

		//Enqueue root node into the nodesQueue
	    nodesQueue.add(root);    
		
		//Loop to find best combination until the PriorityQueue is empty
		while (!nodesQueue.isEmpty()) { 
			
			//remove first node from the queue and assign as parent node
			TreeNode parent = nodesQueue.remove();   
			int [] parentPath = parent.getSelect();
			int level = parent.getLvl();
			
			if(level+1<parcels.size()) {
				//Create left child (Combinations that included parcel[level+1].
				TreeNode left= new TreeNode(0,0,0,0,initialPath);  
				if(parentPath[level+1] == 1) {       //At n+1 level,if (n+1) element is selected
					left = parent;                   //Then clone parent 
					left.setLvl(level+1);
				}
				
				//Create right child (parcel[level+1] not included) and compute bound.
				TreeNode right= new TreeNode();
				right.setLvl(level+1);
				right.setSelect(copyPath(parentPath,right.getLvl())); 
				computeBound(right,right.getLvl());                  
				
				//Compare bound, if > FinalLB,then enqueue it and find the child again and again.
				if(compareLB(right) == 1) 
					nodesQueue.add(right);  
				if(compareLB(left) == 1) 
					nodesQueue.add(left);
			}
		}
	}
	
	//Method for root node and right child node to compute bound and record path
	public void computeBound(TreeNode n, int level) {
		double ub = 0 ,lb = 0;
		int tw = 0, lvl=level;
		int [] path = n.getSelect();
	    
		if(lvl==0)
			lvl=-1;
		else{
			for(int i=0; i<lvl; i++) {   //Check parent's selection before this level
				if(path[i]==1) {                   
					lb += parcels.get(i).getCom();
					tw += parcels.get(i).getWeight();
				}
			}
		}
		int idx = 0;                                  // To record where it stop
		for (int i=lvl+1; i < parcels.size(); i++) {   //try to add more parcels, not included parcel[child.level].
			if(tw + parcels.get(i).getWeight() <= maxCapacity) {
				lb += parcels.get(i).getCom();
				tw += parcels.get(i).getWeight();
				path[i] = 1;
			}
			else { 
				idx = i;
				break;
			}
		}
		ub = lb + parcels.get(idx).getCom()/parcels.get(idx).getWeight()*(maxCapacity-tw) ; //compute upper bound
		
		n.setLB(lb);   n.setUB(ub);  n.setTW(tw);  n.setSelect(path);                 //assign value to corresponding node
	}
	
	//Method for right child node to copy parent's path until this level.
	public int[] copyPath(int [] path, int lvl) {
		int[] p = new int [path.length];
		for (int i=0; i<lvl; i++) {
			p[i]=path[i];
		}
		return p;
	}

	//Method for child node to compare its upper bound and lower bound with final lower bound.
	public int compareLB(TreeNode n) {
		if(FinalLB.getLB()< n.getLB())   //if child.LB > final.LB , then final = child.
			FinalLB = n;
			
		if(n.getUB()< FinalLB.getLB())   //if child.UB < final.LB, then it will not enqueue back to the queue
			return -1;
		else
			return 1;
	}

	@Override
	public void result() {
		System.out.print("  --------------------Branch and Bound Solution--------------------"
				       + "\n Total Commission: " + calculateTotalCom()
				       + "\n Total Weight: " + calculateTotalWeight()
				       + "\n Percel Selected: \n ID  Weight  Commission  Registered Date \n");
		
		int[]path = FinalLB.getSelect();
		for (int i=0; i<path.length; i++)
			if (path[i]==1)
				System.out.println(" " + parcels.get(i).getID()+ "\t" + 
						     	   parcels.get(i).getWeight()+ "\t" + 
						     	   parcels.get(i).getCom()+ "\t" +
						     	   parcels.get(i).getDate()+ "\t" );	
		System.out.println("\n");
	}
	
	public int calculateTotalWeight() {
		return FinalLB.getTW();
	}
	
	public double calculateTotalCom() {
		return FinalLB.getLB();
	}

}
