package ClosestSchools;
/*
* Author: Benjamin Allan-Rahill
* Hours: =< 3
* Collaborators: Charlotte Gray 
* Implements the closest pair of points recursive algorithm
* on locations of K-12 schools in Vermont obtained from http://geodata.vermont.gov/datasets/vt-school-locations-k-12

*/

import java.io.File;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import java.io.File;
import java.util.HashMap;


public class Main {

	public static void main(String[] args) throws IOException{

		//Creates an ArrayList containing School objects from the .csv file
		// Based on https://stackoverflow.com/questions/49599194/reading-csv-file-into-an-arrayliststudent-java
		String line = null;
		ArrayList<School> schoolList = new ArrayList <School>();
		// You may have to adjust the file address in the following line to your computer
		BufferedReader br = new BufferedReader(new FileReader("ClosestSchools/Data/VT_School_Locations__K12(1).csv"));
		if ((line=br.readLine())==null){
			return;
		}
		while ((line = br.readLine())!=null) {
			String[] temp = line.split(",");
			schoolList.add(new School(temp[4],Double.parseDouble(temp[0]),Double.parseDouble(temp[1])));
		}


		//Preprocess the data to create two sorted arrayLists (one by X-coordinate and one by Y-coordinate):
		ArrayList<School> Xsorted = new ArrayList <School>();
		ArrayList<School> Ysorted = new ArrayList <School>();
		Collections.sort(schoolList, new SortbyX());
		Xsorted.addAll(schoolList);
		Collections.sort(schoolList, new SortbyY());
		Ysorted.addAll(schoolList);

		//convert to arrays 
		School[] sortedXSchools = Xsorted.toArray(new School[Xsorted.size()]);
		School[] sortedYSchools = Ysorted.toArray(new School[Ysorted.size()]);

		//Run the Recursive Algorithm
		School[] cp = new School[2];
		cp = ClosestPoints(sortedXSchools, sortedYSchools);
		if(cp[0]!=null)
			System.out.println("The two closest schools are "+ cp[0].name + " and " + cp[1].name +".");
	}

	public static School[] ClosestPoints(School[] X_arr, School[] Y_arr) {
		// Will be the true recursive function that finds the closest points
		School[] closestPair = new School[2];

		if (X_arr.length <= 3) {
			closestPair = CalcMinPair(X_arr, 3);
		} else {
			// generate right and left arrays
			School[][] div_schools = DivideLeftRight(X_arr, Y_arr);
			School[] sLx = div_schools[0];
			School[] sRx = div_schools[1];
			School[] sLy = div_schools[2];
			School[] sRy = div_schools[3];

			// find closest on the left and right
			School[] cLeft = ClosestPoints(sLx, sLy);
			School[] cRight = ClosestPoints(sRx, sRy);

			// calc distance between closest pairs
			double cDLeft = CalcDistance(cLeft[0], cLeft[1]);
			double cDRight = CalcDistance(cRight[0], cRight[1]);

			//establish delta 
			if (cDLeft > cDRight) {
				closestPair = cRight;
			} else {
				closestPair = cLeft;
			}
			double delta = CalcDistance(closestPair[0], closestPair[1]);

			//get midline value
			double mid = GetMidline(sLx[sLx.length - 1], sRx[0]);

			// generate array of values within delta of midline
			School[] yDelta = GenerateYDelta(mid, delta, Y_arr);

			// find closest pair within delta of the midline
			if (yDelta.length > 1) {
				School[] delta_pair = CalcMinPair(yDelta, 7);

				double d_prime = CalcDistance(delta_pair[0], delta_pair[1]);

				if (d_prime < delta) {
					return delta_pair;
				}
			}
		}
		return closestPair;
	}
	
	// UTILITY FUNCTIONS // 

	// function to divide lists of schools into left and right sides
	public static School[][] DivideLeftRight(School[] sortedXSchools, School[] sortedYSchools) {
		//create HashMap to store name and bool 
		HashMap<School, Boolean> isLeft = new HashMap<>();

		// x //
		School[] sXLeft = Arrays.copyOfRange(sortedXSchools, 0, (sortedXSchools.length + 1) / 2);
		School[] sXRight = Arrays.copyOfRange(sortedXSchools, (sortedXSchools.length + 1) / 2, sortedXSchools.length);

		// Add values to hash map
		for (int i = 0; i < sXLeft.length; i++) {
			isLeft.put(sXLeft[i], true);
		}
		for (int i = 0; i < sXRight.length; i++) {
			isLeft.put(sXRight[i], false);
		}

		// y //
		
		ArrayList<School> sYLeft = new ArrayList<School>();
		ArrayList<School> sYRight = new ArrayList<School>();
		
		// use hashmap to check if y should be on right or left side
		for (int i = 0; i < sortedYSchools.length; i++) {
			if (isLeft.get(sortedYSchools[i])) {
				sYLeft.add(sortedYSchools[i]);
			} else {
				sYRight.add(sortedYSchools[i]);
			}
		}

		//convert back to arrays 
		School[] sYleft = sYLeft.toArray(new School[sYLeft.size()]);
		School[] sYright = sYRight.toArray(new School[sYRight.size()]);


		School[][] schools = { sXLeft, sXRight, sYleft, sYright };

		return schools;
	}
	
	// function for finding schools within delta of the midline
	public static School[] GenerateYDelta(double midline, double delta, School[] ySschools) {
		ArrayList<School> temp = new ArrayList<School>();
		for (School school : ySschools) {
			double midline_dist = Math.abs((school.getX() - midline));
			if (midline_dist < delta) {
				temp.add(school);
			}
		}
		//convert to array 
		School[] yDelta = temp.toArray(new School[temp.size()]);

		return yDelta;

	}

	// simple function to get midline between schools
	public static double GetMidline(School a, School b) {
		return ((a.getX() + b.getX()) / 2);
	}
	
	// function that calculates the closest pair of schools for a smaller array
	public static School[] CalcMinPair(School[] schoolArr, Integer n) {
		School[] minPair = new School[2]; 
		// Function to calculate the smallest pair of schools
		double minDist = Double.POSITIVE_INFINITY;

		for (int i = 0; i < schoolArr.length - 1; i++) {
			int j = 1;
			// only look at next seven (in case of midline checking)
			while (j <= 7 && ((i+j) < (schoolArr.length))){
				double d = CalcDistance(schoolArr[i], schoolArr[i+j]);
				if (d < minDist) {
					minDist = d;
					minPair[0] = schoolArr[i];
					minPair[1] = schoolArr[i+j];
				}
				j++;
			}
		}
		return minPair;
	}
	
	// calculate euclidean distance between schools
	public static double CalcDistance(School a, School b){
		// find difference in coords
		double xDist = a.getX() - b.getX();
		double yDist = a.getY() - b.getY();
		
		// square quantities
		double xS = Math.pow(xDist, 2);
		double yS = Math.pow(yDist, 2);

		// calc distance
		double distance = Math.sqrt((xS + yS));

		return distance;
	}
	

}