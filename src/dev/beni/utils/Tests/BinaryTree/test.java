package dev.beni.utils.Tests.BinaryTree;

import java.util.ArrayList;

public class test {

    public static void main(String[] args){
        //Tree tree = new Tree();
        //Tree.Nodenotused root = new Tree.Nodenotused(5);
        //System.out.println("Binary Tree example");
        //System.out.println("building Tree with root value " + root.value);

        //tree.insert(root, 2);
        //tree.insert(root, 4);
        //tree.insert(root, 8);
        //tree.insert(root, 6);
        //tree.insert(root, 7);
        //tree.insert(root, 3);
        //tree.insert(root, 9);
        //tree.insert(root, 2);
        //System.out.println("Traversing Tree in Order");
        //tree.traverseInOrder(root);
        //Tree.Nodenotused found = tree.find(3, root);
        //if(found != null)
            //System.out.println("found: " + found.value);
        //else
            //System.out.println("not found");


        ThisIsTheBossOfTheSocketDictTrees tree2 = new ThisIsTheBossOfTheSocketDictTrees("b", "b");
        tree2.insert("a", "a");
        tree2.insert("c", "c");
        tree2.insert("Y", "Y");
        tree2.insert("q", "q");
        tree2.insert("k", "k");
        tree2.insert("Z", "Z");
        System.out.println("normal traversal");
        tree2.traverseInOrder();
        System.out.println();
        System.out.println("finding c");
        ThisIsTheBossOfTheSocketDictTrees.Node lul = tree2.find_Node_with_key("c");
        System.out.println(lul.key);
        System.out.println("With List: ");
        ArrayList<ThisIsTheBossOfTheSocketDictTrees.Node> nodes = new ArrayList<ThisIsTheBossOfTheSocketDictTrees.Node>();
        ArrayList<ThisIsTheBossOfTheSocketDictTrees.Node> node = tree2.traverseInOrderWithPuttingEverythingInAnArraylist(nodes);
        for(int i = 0; i < node.size(); i++) {
            System.out.print("(key:");
            System.out.print(node.get(i).key);
            System.out.print(", value:");
            System.out.print(node.get(i).value + ") ");
        }
        System.out.println("\ndeleting: Z");
        System.out.println(tree2.find_Predecessor_Node_of_Node_with_key("z").key);
        //tree2.deleteNode("Z");
        tree2.traverseInOrder();
        System.out.println();
        //tree2.insert("b", "a");
        //tree2.traverseInOrder();
        System.out.println();
        System.out.println(tree2.toString());
        tree2.fromString(tree2.toString());
    }

}
