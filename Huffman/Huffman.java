/*
* Author: Benjamin Allan-Rahill
* Implements Huffman's binary encoding algorithm
*/



import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

// import sun.launcher.resources.launcher;


public class Huffman {


	public static void main(String[] args) throws IOException{

		//Runs encode
        System.out.println(encode("Hello World!"));
        //counts("kjdjs.d d jdkewial .k");
		

	}

    public static String encode(String message) {
        // Uses Huffman's algorithm to encode message into binary
        // Also prints a dictionary.
        ArrayList<Letter> chars = weights(message);

        Comparator<Letter> letterSorter = Comparator.comparing(c -> c.getWeight());

        PriorityQueue<Letter> priorityQueue = new PriorityQueue<>(letterSorter);

        //add chars to priority queue 
        chars.forEach((c) -> priorityQueue.add(c));

        // print out priority queue 
        while(true) 
        {
            Letter l = priorityQueue.poll();
            if(l == null) break;
            System.out.println(l);
            
            
        }

        return "You should probably insert some code";

    }
    
    // public static HashMap<Character, Double> counts(String message) {
    //     ;
    //     for (int i = 0; i < message.length(); i++) {
    //         if (counts.containsKey(message.charAt(i))) {
    //             Double val = counts.get(message.charAt(i));
    //             val++;
    //             counts.put(message.charAt(i), val);
    //             //System.out.printf("%c: %d \n", message.charAt(i), val);
    //         } else {
    //             counts.put(message.charAt(i), 1.0);
    //             ;
    //         }
    //     }

    //     return counts;
    // }
    
    public static ArrayList<Letter> weights(String message) {
        Double len = new Double(message.length());
        HashMap<Character, Double> counts = new HashMap<>();

        for (int i = 0; i < message.length(); i++) {
            if (counts.containsKey(message.charAt(i))) {
                Double val = counts.get(message.charAt(i));
                val++;
                counts.put(message.charAt(i), val);
            } else {
                counts.put(message.charAt(i), 1.0);
                ;
            }
        }
        ArrayList<Letter> characters = new ArrayList<>();
        for (Character c : counts.keySet()) {
            characters.add(new Letter(c, (counts.get(c) / len)));
        }

        return characters;

        // System.out.println("WEIGHTS:");
        // weights.forEach((key, tab) -> System.out.printf("%c: %f \n", key, tab));


        
    }

	

}