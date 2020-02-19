package ClosestSchools;
/*
* Author: 
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

		//Run the Recursive Algorithm
		School[] cp = new School[2];
		cp = ClosestPoints(Xsorted,Ysorted);
		if(cp[0]!=null)
			System.out.println("The two closest schools are "+ cp[0].name + " and " + cp[1].name +".");
		

	}

	public static School[] ClosestPoints(ArrayList<School> sLx, ArrayList<School> sLy){
		// Recursive divide and conquer algorithm for closest points
		// sLx should be sorted by x coordinate and sLy should be sorted by y coordinate
		// Returns an array containing the two closest School objects

		School[] closestPair = new School[2];

		///////////////////
		//Your code here!//
		///////////////////


		return closestPair;

	}

	public static School ClosestPointsRecursive(ArrayList<School> sLx, ArrayList<School> sLy) {
		// Will be the true recursive function that finds the closest points 
		if (sLx.length() <= 3) {
			double minDist == Double.POSITIVE_INFINITY;
			for (int i = 0; i <sLx.length(); i++) {
				for(int j = 0; j <sLx.length(); j++){
					sArr = School[];
					dist = CalcDistance([school, sLx[]])
				}
			}
			
		} else {

		}
	}
	
	public static Integer CalcDistance(School[] sArr) {
		// Function to calculate the distance between schools
		
		// find difference in coords
		double xDist = sArr[0].getX() - sArr[1].getX();
		double yDist = sArr[0].getY() - sArr[1].getY();
		
		// square quantities
		double xS = Math.pow(xDist, 2);
		double yS = Math.pow(yDist, 2);

		// calc distance
		double distance = Math.sqrt((xS + yS));

		return distance;

	}
	

}