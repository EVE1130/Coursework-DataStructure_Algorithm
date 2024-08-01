package roup3;

public class TreeNode {
	private double upperB, lowerB;
	private int totalWeight, level, select[];
	
	public double getUB(){
		return upperB;
	}
	public void setUB(double ub) {
		upperB = ub;
	}
	
	public double getLB(){
		return lowerB;
	}
	public void setLB(double lb) {
		lowerB = lb;
	}
	
	public int getTW(){
		return totalWeight;
	}
	public void setTW(int tw) {
		totalWeight = tw;
	}
	
	public int getLvl(){
		return level;
	}
	public void setLvl(int lvl) {
		level = lvl;
	}
	
	public int[] getSelect(){
		return select;
	}
	public void setSelect(int [] s) {
		select = s;
	}
	
	public TreeNode() {}
	
	public TreeNode (double ub, double lb, int tw, int lvl, int[] s) {
		upperB = ub;
		lowerB = lb;
		totalWeight = tw;
		level = lvl;
		select = s;
	}

}
