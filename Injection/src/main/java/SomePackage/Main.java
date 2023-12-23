package SomePackage;

public class Main {
    public static void main(String[] args) {
        System.out.println("First test:");
        SomeBean bc = (new Injector()).inject(new SomeBean(), false);
        bc.foo();
        System.out.println("Second test:");
        SomeBean ac = (new Injector().inject(new SomeBean(), true));
        ac.foo();
    }
}