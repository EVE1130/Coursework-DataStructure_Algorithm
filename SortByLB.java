package Group3;

import java.util.Comparator;

public class SortByLB implements Comparator<TreeNode>{
	public int compare(TreeNode n1, TreeNode n2) {
		if (n1.getLB()>n2.getLB())
			return 1;
		else if(n1.getLB() == n2.getLB())
			return 0;
		else
			return -1;
	}
}
