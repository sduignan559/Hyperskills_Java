package animals;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Set;

import static java.util.Collections.asLifoQueue;
import static java.util.Collections.sort;

public class Tree {
    private Node<String> root;
    private Node<String> current;

    public Tree() {
        root = null;
    }


    public static ArrayList<String> listAnimals(Node root) {
        ArrayList<String> animals= new ArrayList<>();
        Deque<Node> queue = new ArrayDeque<>();
        if (root == null) {
            return animals;
        }
        queue.add(root);
        //bfs find all roots
        while(queue.size()>0){
            Node current = queue.removeFirst();
            if(current.getRight() == null && current.getLeft() == null) {
                animals.add(current.getValue());
            }
            if(current.getRight() !=null){
                queue.addLast(current.getRight());
            }
            if(current.getLeft() != null){
                queue.addLast(current.getLeft());
            }
        }
        sort(animals);
        return animals;
    }

    public static boolean hasPath(Node root, ArrayList<String> parents, String animal)
    {
        if (root==null)
            return false;
        // push the node's value in 'arr'
        parents.add(root.getValue());
        if (root.getValue().equals(animal))
            return true;
        if (hasPath(root.getRight(), parents, animal) || hasPath(root.getLeft(), parents, animal))
            return true;
        parents.remove(parents.size()-1);
        return false;
    }

    public static void printPath(Node root, String animal)
    {
        // ArrayList to store the path
        ArrayList<String> arr= new ArrayList<>();
        // reverse and get to node
        if (hasPath(root, arr, animal)) {
            for (int i = 0; i < arr.size() - 1; i++) {

                if(root.getRight().getValue().equals(arr.get(i+1))){
                    System.out.println(Communication.convertQuestion(arr.get(i), true));
                    root = root.getRight();
                } else{
                    System.out.println(Communication.convertQuestion(arr.get(i), false));
                    root = root.getLeft();
                }
            }
        }
        // 'x' is not present in the binary tree
        else {
            System.out.print("No facts about the "+ animal);
        }
    }

    public static void statistics(Node root){
        // Print knowledge tree stats.
        System.out.println("The Knowledge Tree stats");
        System.out.println();
        System.out.printf("- root node                    %s\n", Communication.convertQuestion(root.getValue(), true));
        System.out.printf("- total number of nodes        %d\n",  getNoOfNodes(root));
        System.out.printf("- total number of animals      %d\n",  getNoOfLeafs(root));
        System.out.printf("- total number of statements   %d\n",  getNoOfParents(root));
        System.out.printf("- height of the tree           %d\n",  getMinimumAnimalDepth(root));
        System.out.printf("- minimum animal's depth       %d\n",   getMaximumAnimalDepth(root));
        System.out.printf("- average animal's depth       %.1f\n", getAverageAnimalDepth(root));
        System.out.println();
    }

    public static float getAverageAnimalDepth(Node root) {
        int[] sumAndCount = traverse(root, 0, new int[]{0, 0});
        return (float)sumAndCount[0] / sumAndCount[1];
    }

    private static int[] traverse(Node node, int depth, int[] sumAndCount) {
        if (node == null) {
            return sumAndCount;
        }

        sumAndCount[0] += depth;
        sumAndCount[1]++;

        traverse(node.getLeft(), depth + 1, sumAndCount);
        traverse(node.getRight(), depth + 1, sumAndCount);

        return sumAndCount;
    }

    public static int getMaximumAnimalDepth(Node root) {
        if (root == null) {
            return 0;
        }

        int maxDepth = Integer.MIN_VALUE;
        traversemax(root, 1, maxDepth);
        return maxDepth;
    }

    static void traversemax(Node node, int depth, int maxDepth) {
        if (node.getRight() == null && node.getLeft() == null) {
            maxDepth = Math.max(maxDepth, depth);
        }

        if (node.getLeft() != null) {
            traverse(node.getRight(), depth + 1, maxDepth);
        }

        if (node.getRight() != null) {
            traverse(node.getLeft(), depth + 1, maxDepth);
        }
    }

    static int height = 0;
    public static int getMinimumAnimalDepth(Node root) {
        if (root == null) {
            return 0;
        }

        int minDepth = Integer.MAX_VALUE;
        traverse(root, 1, minDepth);
        return minDepth;
    }

    static void traverse(Node node, int depth, int minDepth) {
        if (node.getRight() == null && node.getLeft() == null) {
            minDepth = Math.min(minDepth, depth);
        }

        if (node.getLeft() != null) {
            traverse(node.getRight(), depth + 1, minDepth);
        }

        if (node.getRight() != null) {
            traverse(node.getLeft(), depth + 1, minDepth);
        }
    }


    static int parents = 0;
    public static int getNoOfParents(Node root) {
        if(root == null){
            return parents;
        }
        if(root.getRight() != null && root.getRight() != null) {
            parents++;
        }
        getNoOfParents(root.getRight());
        getNoOfParents(root.getLeft());

        return parents;
    }
    static int leafs = 0;
    public static int getNoOfLeafs(Node root) {
        if(root == null){
            return leafs;
        }
        if(root.isLeaf()) {
            leafs++;
        }
        getNoOfLeafs(root.getRight());
        getNoOfLeafs(root.getLeft());
        return leafs;
    }
    static int nodes = 0;
    public static int getNoOfNodes(Node root) {
        if(root == null){
            return nodes;
        }
        nodes++;
        getNoOfNodes(root.getRight());
        getNoOfNodes(root.getLeft());
        return nodes;
    }
    public static void printTree(Node root){

    }


    public Node<String> getRoot() {
        return root;
    }
    public void setRoot(Node<String> root) {
        this.root = root;
    }
    public void setRoot(String value) {
           root = new Node<>(value);
            this.root = root;
    }
    public Node<String> getCurrent() {
        return current;
    }

    public void setCurrent(Node<String> current) {
        this.current = current;
    }
}
