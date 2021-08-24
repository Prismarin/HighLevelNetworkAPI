package dev.beni.utils.Tests.BinaryTree;

public class test {

    public static void main(String[] args){
        Tree tree = new Tree();
        Tree.Nodenotused root = new Tree.Nodenotused(5);
        System.out.println("Binary Tree example");
        System.out.println("building Tree with root value " + root.value);

        tree.insert(root, 2);
        tree.insert(root, 4);
        tree.insert(root, 8);
        tree.insert(root, 6);
        tree.insert(root, 7);
        tree.insert(root, 3);
        tree.insert(root, 9);
        tree.insert(root, 2);
        System.out.println("Traversing Tree in Order");
        tree.traverseInOrder(root);
        Tree.Nodenotused found = tree.find(3, root);
        if(found != null)
            System.out.println("found: " + found.value);
        else
            System.out.println("not found");

        Tree.Savings savings = new Tree.Savings("name", "Tom");


        String lol = "Name";
        System.out.println(lol.compareToIgnoreCase("Name"));
        System.out.println(lol.compareToIgnoreCase("name"));
        System.out.println(lol.compareToIgnoreCase("lol"));
        System.out.println(lol.compareToIgnoreCase("out"));

        ThisIsTheBossOfTheSocketDictTrees tree2 = new ThisIsTheBossOfTheSocketDictTrees();
        ThisIsTheBossOfTheSocketDictTrees.Node rootnode = new ThisIsTheBossOfTheSocketDictTrees.Node("b", "b");
        tree2.insert(rootnode, "a", "a");
        tree2.insert(rootnode, "c", "c");
        tree2.insert(rootnode, "Z", "Z");
        tree2.insert(rootnode, "q", "q");
        tree2.insert(rootnode, "k", "k");
        tree2.traverseInOrder(rootnode);
        ThisIsTheBossOfTheSocketDictTrees.Node lul = tree2.find_Node_with_key("c", rootnode);
        System.out.println(lul.key);
        ThisIsTheBossOfTheSocketDictTrees.Node lal = tree2.find_Node_containing_Value("Z", rootnode);
        System.out.println(lal.key);
    }

}
