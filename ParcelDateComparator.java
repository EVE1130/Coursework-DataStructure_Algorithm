package Group3;

import java.util.Comparator;

public class ParcelDateComparator implements Comparator<Parcel> {
	public int compare(Parcel p1, Parcel p2) {
		if (p1.getDate().isBefore(p2.getDate()))
			return -1;
		else if(p1.getDate().isEqual(p2.getDate()))
			return 0;
		else
			return 1;
	}
}
