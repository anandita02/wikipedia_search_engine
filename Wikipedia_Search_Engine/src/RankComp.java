package ire;

import java.util.Comparator;

public class RankComp implements Comparator<ListRank> {

	@Override
	public int compare(ListRank o1, ListRank o2) {
		 return (o2.rank-o1.rank);
		
	}

}
