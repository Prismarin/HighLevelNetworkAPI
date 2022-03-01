package dev.beni.utils;

public class Demo {

    /**test method I wrote you can see how to use the SocketDict in this file**/
    public static void main(String[] args) {
        //creates new SocketDict called dict
        //in this example the dict stores the letters of the alphabet with animals starting with the correspondent letter
        SocketDict dict = new SocketDict(new Node("m", "Mouse"));

        //adds all the other letters
        dict.insert(new Node("f", "Fish"));
        dict.insert(new Node("b", "Bear"));
        dict.insert(new Node("d", "Dodo"));
        dict.insert(new Node("a", "Alpaca"));
        dict.insert(new Node("c", "Camel"));
        dict.insert(new Node("e", "Eagle"));
        dict.insert(new Node("i", "Ibis"));
        dict.insert(new Node("g", "Grizzly"));
        dict.insert(new Node("h", "Hedgehog"));
        dict.insert(new Node("j", "Jaguar"));
        dict.insert(new Node("l", "Lemur"));
        dict.insert(new Node("k", "Kangaroo"));
        dict.insert(new Node("t", "Tapir"));
        dict.insert(new Node("p", "Penguin"));
        dict.insert(new Node("n", "Narwhal"));
        dict.insert(new Node("o", "Octopus"));
        dict.insert(new Node("q", "Queen Snake"));
        dict.insert(new Node("s", "Snail"));
        dict.insert(new Node("r", "Rabbit"));
        dict.insert(new Node("w", "Wallaby"));
        dict.insert(new Node("u", "Umbrellabird"));
        dict.insert(new Node("v", "Vulture"));
        dict.insert(new Node("x", "X-Ray Tetra")); //Honestly I have never heard of this animal, but I found nothing else with x
        dict.insert(new Node("z", "Zebra"));
        dict.insert(new Node("y", "Yak"));

        //prints the animals in alphabetical order to the console
        dict.output();
        System.out.println("start dict:");
        //deletes the animal with the letter z
        dict.delete("z");
        //shows the result
        System.out.println("after deleting:");
        dict.output();
        System.out.println();
        //finds a Node
        Node found = dict.find("c");
        //sets the value of found (from above) to Chameleon
        found.setValue("Chameleon");
        //shows the result
        System.out.println("after changing Camel to Chameleon");
        dict.output();
    }

}
