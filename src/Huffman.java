import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Huffman {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        encode("test.txt");
    }

    public static void encode(String filename) throws IOException {
        try {
            HashMap<Character, Double> letterCounts = countLetters(filename);
            Node[] nodes = CreateNodes(letterCounts);
            Node huffman = CalculateTree(nodes);
        } catch(Exception e) {
            System.out.println(e);
        }

    }

    public static Node CalculateTree(Node[] nodes) {
        List<Node> NodeList = new LinkedList<>(Arrays.asList(nodes));
        while(NodeList.size() > 1) {
            double min = 1;
            Node minNode = null;
            int mindex = 0;
            for(int i = 0; i < NodeList.size(); i++){
                if(NodeList.get(i).prob < min) {
                    min = NodeList.get(i).prob;
                    minNode = NodeList.get(i);
                    mindex = i;
                }
            }


            double min2 = 1;
            Node minNode2 = null;
            int mindex2 = 0;
            for(int j = 0; j < NodeList.size(); j++){
                if(NodeList.get(j).prob < min2 && mindex != j) {
                    min2 = NodeList.get(j).prob;
                    minNode2 = NodeList.get(j);
                    mindex2 = j;
                }
            }
            System.out.println(minNode.value);
            System.out.println(minNode2.value);
            Node newNode = new Node(min + min2, minNode, minNode2);
            NodeList.remove(minNode);
            NodeList.remove(minNode2);
            System.out.println(newNode.prob);
            NodeList.add(newNode);
        }
        return nodes[0];
    }

    public static Node[] CreateNodes(HashMap<Character, Double> letterCounts) {
        Node[] nodes = new Node[letterCounts.keySet().size()];
        Object[] keys = letterCounts.keySet().toArray();

        for(int i = 0; i < keys.length; i++) {
            Node newNode = new Node((Character)keys[i], letterCounts.get(keys[i]), Node.Empty(), Node.Empty());
            nodes[i] = newNode;
        }
        return nodes;
    }

    // BOILERPLATE: TODO
    public static HashMap<Character, Double> countLetters(String filename) throws IOException{
        HashMap<Character, Integer> counts = new HashMap<>();
        int charCount = 0;
        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = in.readLine()) != null){
                for(char ch:line.toCharArray()){
                    if(Character.isLetter(ch)){
                        charCount++;
                        int old = counts.getOrDefault(ch, 0);
                        counts.put(ch, old+1);
                    }
                }
            }
        }
        HashMap<Character, Double> freqs = new HashMap<>();
        Object[] countKeys = counts.keySet().toArray();
        for(int i = 0; i < countKeys.length; i++){
            freqs.put((Character)countKeys[i], counts.get(countKeys[i]) / (double)charCount);
        }
        return freqs;
    }
}

class Node {

    public Node left;
    public Node right;
    public char value;
    public double prob;

    public Node(char val, double prob, Node lt, Node rt) {
        this.left = lt;
        this.right = rt;
        this.value = val;
        this.prob = prob;
    }

    public Node(double prob, Node lt, Node rt) {
        this.left = lt;
        this.right = rt;
        this.prob = prob;
    }

    public static Node Empty() {
        return null;
    }

    public Boolean IsLeaf() {
        return this.left == null && this.right == null;
    }
}