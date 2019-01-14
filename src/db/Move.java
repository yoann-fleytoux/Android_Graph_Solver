package db;

import java.util.LinkedList;

public class Move {
	 public static String name_start;
	 public String name_end;
	 public LinkedList<Move_sub> move;

	 public Move(String argname_start, String argname_end){
		 name_start = argname_start;
		 name_end = argname_end;
		 move = new LinkedList<Move_sub>();
	 }
}
