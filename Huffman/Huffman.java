/*
* Author: Benjamin Allan-Rahill
* Implements Huffman's binary encoding algorithm
* hours: 2
*/



import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Huffman {


    public static void main(String[] args) throws IOException {

        //Runs encode
        String message = "Hello this is my word!";

        HashMap<Character, String> codeDict = encode(message);

        System.out.printf("Encypted Message:\t%s\n", encrypt(message, codeDict));

        printCodeDict(codeDict);

    }
    

    /**
     * Functiom to return a HashMap including all of the encodings for each character
     * @param message
     * @return
     */
    public static HashMap<Character, String> encode(String message) {

        // calculate weights
        ArrayList<BinaryTree> chars = weights(message);

        // make comparator based off Binary Tree weights
        Comparator<BinaryTree> treeSorter = Comparator.comparing(c -> c.getWeight());
        PriorityQueue<BinaryTree> priorityQueue = new PriorityQueue<>(treeSorter);

        //add chars to priority queue 
        chars.forEach((c) -> priorityQueue.add(c));

        // merge the trees
        while (priorityQueue.size() > 1) {
            mergeBinaryTree(priorityQueue);
        }

        makeEncoding(priorityQueue.peek(), "");

        HashMap<Character, String> codes = creatCodeDict(chars);

        return codes;

    }
    

    /**
     * Prints the codes for each character in the message
     * @param codes
     */
    public static void printCodeDict(HashMap<Character, String> codes) {
        // print the dictionary
        System.out.println("The alphabet dictionary:");
        codes.keySet().forEach((codeKey) -> System.out.printf("%c -> %s \n", codeKey, codes.get(codeKey)));

    }
    /**
     * Returns the encrypted message 
     * @param message : the original message
     * @param codes : the HashMap that contains the code encodings
     * @return String code
     */
    public static String encrypt(String message, HashMap<Character, String> codes){
        String code = "";
        for (int i = 0; i < message.length(); i++) {
            code += codes.get(message.charAt(i));
        }
        return code;
    }
    /**
     * Returns the HashMap that contains the encodings for each character
     * @param letters
     * @return
     */
    public static HashMap<Character, String> creatCodeDict(ArrayList<BinaryTree> letters) {
        HashMap<Character, String> codes = new HashMap<>();
        for (BinaryTree letter : letters) {
            if (codes.containsKey(letter.root.letter._char)) {
                continue;
            } else {
                codes.put(letter.root.letter._char, letter.root.letter.enconding);
                ;
            }

        }
        return codes;
    }

    
    /**
     * Calculates the weights for each character and creates a Binary Tree for each
     * @param message
     * @return ArrayList of binary trees for PriorityQueue comparison
     */
    public static ArrayList<BinaryTree> weights(String message) {
        Double len = new Double(message.length());
        HashMap<Character, Double> counts = new HashMap<>();

        // calculate occurances
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
        // make and add binary tree objs to list
        ArrayList<BinaryTree> characters = new ArrayList<>();
        for (Character c : counts.keySet()) {
            characters.add(new BinaryTree((counts.get(c) / len), (new Letter(c, (counts.get(c) / len)))));
        }

        return characters;        
    }
    /**
     * merge the two min weight trees in the priority queue and return the queue
     * @param queue
     * @return modified priority queue
     */
    public static PriorityQueue<BinaryTree> mergeBinaryTree(PriorityQueue<BinaryTree> queue) {
        // get the two smallest values 
        BinaryTree a = queue.poll();
        BinaryTree b = queue.poll();

        // calculate new weight
        Double combinedWeight = a.root.key + b.root.key;

        // make new tree
        BinaryTree newTree = new BinaryTree(combinedWeight, a, b);

        // add back to priority queue 
        queue.add(newTree);

        return queue;
    }

    /**
     * Assign the encodings for each of the Letters at the leaves of the binary trees. 
     * @param tree
     * @param encoding
     */
    public static void makeEncoding(BinaryTree tree, String encoding) {
        if (tree.isLeaf()) {
            tree.root.letter.enconding = encoding;
            return;
        }
        makeEncoding(tree.left, encoding + "0");
        makeEncoding(tree.right, encoding + "1");
    }

	

}