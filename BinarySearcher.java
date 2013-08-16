//  Author: Khanh Duy Huynh
//  Date: 20/09/2011
//  File Name: BinarySearcher.java
import java.util.*;
public class BinarySearcher
{
	private ArrayList<Patient> pList;
	/**
		Constructs a BinarySearcher.
		@param anArray a sorted array list of Patient
	*/

	public BinarySearcher(ArrayList<Patient> anArray)
	{
		pList = anArray;
	}

	/**
		Finds a value in a sorted array list, using the binary
		search algorithm.
		@param strId the value to search
		@return the index at which the value occurs, or -1
		if it does not occur in the array list
	*/


	public int search(String strId)
	{
		int low = 0;
		int high = pList.size() - 1;
		while (low <= high)
		{
			int mid = (low + high) / 2;
			String midId = pList.get(mid).getPatientId();

			if (midId.compareTo(strId) == 0)
				return mid;
			else if (midId.compareTo(strId) < 0)
				low = mid + 1;
			else
				high = mid - 1;
		}
		return -1;
	}

}

