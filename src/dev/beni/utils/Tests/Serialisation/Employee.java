package dev.beni.utils.Tests.Serialisation;

public class Employee implements java.io.Serializable {
    public String name;
    public String address;
    public transient int SSN = 3;
    public int number;

    public Employee() {}

    public Employee(String name, String address, int number) {
        this.name = name;
        this.address = address;
        this.number = number;
    }

    public void mailCheck() {
        System.out.println("Mailing a check to " + name + " " + address);
    }
}
