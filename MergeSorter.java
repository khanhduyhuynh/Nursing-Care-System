//  Author: Khanh Duy Huynh
//  Date: 20/09/2011
//  File Name: NursingCareSys.java
import java.util.*;
public class MergeSorter
{
	private ArrayList<Patient> pList;
	private String strType;
	/**
		Constructs a merge sorter.
		@param anArray the array list to sort
	*/
	public MergeSorter(ArrayList<Patient> anArray, String type)
	{
		pList = anArray;
		strType = type;
	}
	/**
		Sorts the array list managed by this merge sorter.
	*/
	public ArrayList<Patient> sort()
	{
		if (pList.size() <= 1)	return pList;
		ArrayList<Patient> first = new ArrayList<Patient>(pList.size()/2);
		ArrayList<Patient> second = new ArrayList<Patient>(pList.size() - first.size());

		for(int i = 0; i < pList.size()/2; i++)
		{
			Patient patient = pList.get(i);
			first.add(i, patient);
		}

		for(int j = 0; j < pList.size() - first.size(); j++)
		{
			Patient patient = pList.get(j + first.size());
			second.add(j, patient);
		}

		MergeSorter firstSorter = new MergeSorter(first, strType);
		MergeSorter secondSorter = new MergeSorter(second, strType);
		firstSorter.sort();
		secondSorter.sort();
		merge(first, second, strType);
		return pList;


	}
	/**
		Merges two sorted array lists into the array list managed by this
		merge sorter.
		@param first the first sorted array list
		@param second the second sorted array list
	*/
	private void merge(ArrayList<Patient> first, ArrayList<Patient> second, String strType)
	{
		// Merge both halves into the temporary array

		int iFirst = 0;
		// Next element to consider in the first array list
		int iSecond = 0;
		// Next element to consider in the second array list
		int j = 0;
		// Next open position in pList
		// As long as neither iFirst nor iSecond past the end, move
		// the smaller element into pList
		while (iFirst < first.size() && iSecond < second.size())
		{
			if(strType.equals("Id"))
			{
				if(first.get(iFirst).getPatientId().compareTo(second.get(iSecond).getPatientId()) < 0)
				{
					Patient patient = first.get(iFirst);
					pList.set(j, patient);
					iFirst++;
				}
				else
				{
					Patient patient = second.get(iSecond);
					pList.set(j, patient);
					iSecond++;
				}
			}
			else
			{
				boolean blFirst = false;
				boolean blSecond = false;
				boolean blThird = false;
				if(strType.equals("Name"))
				{
					blFirst = first.get(iFirst).getPatientName().compareTo(second.get(iSecond).getPatientName()) < 0;
					blSecond = first.get(iFirst).getPatientName().compareTo(second.get(iSecond).getPatientName()) == 0;
					blThird = first.get(iFirst).getPatientCategory() < second.get(iSecond).getPatientCategory();
				}
				if(strType.equals("Category"))
				{
					blFirst = first.get(iFirst).getPatientCategory() < second.get(iSecond).getPatientCategory();
					blSecond = first.get(iFirst).getPatientCategory() == second.get(iSecond).getPatientCategory();
					blThird = first.get(iFirst).getPatientName().compareTo(second.get(iSecond).getPatientName()) < 0;

				}
				if (blFirst)
				{
					Patient patient = first.get(iFirst);
					pList.set(j, patient);
					iFirst++;
				}
				else
				{
					if(blSecond)
					{
						if(blThird)
						{
							Patient patient = first.get(iFirst);
							pList.set(j, patient);
							iFirst++;
						}
						else
						{
							Patient patient = second.get(iSecond);
							pList.set(j, patient);
							iSecond++;
						}
					}
					else
					{
						Patient patient = second.get(iSecond);
						pList.set(j, patient);
						iSecond++;
					}
				}
			}

			j++;
		}

		// Copy any remaining entries of the first array list
		for(int f = iFirst; f < first.size(); f++)
		{
			Patient patient = first.get(f);
			pList.set(j, patient);
			j++;
		}

		// Copy any remaining entries of the second array list
		for(int s = iSecond; s < second.size(); s++)
		{
			Patient patient = second.get(s);
			pList.set(j, patient);
			j++;

		}

	}


}



