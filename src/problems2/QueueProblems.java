package problems2;

import java.util.LinkedList;

public class QueueProblems {


}

abstract class Animal {
    int order;
    public boolean isOlderThan(int otherOrder) {
        return this.order < otherOrder;
    }

}

class Dog extends Animal {}
class Cat extends Animal {}

class AnimalQueue {
    LinkedList<Dog> dogs = new LinkedList<Dog>();
    LinkedList<Cat> cats = new LinkedList<Cat>();
    int order = 0;

    public void enque(Animal a) {
        order++;
        a.order = order;
        if(a instanceof Cat) cats.add((Cat)a);
        if(a instanceof Dog) dogs.add((Dog)a);
    }

    public Animal dequeAny() {
        if(dogs.isEmpty()) return dequeCat();
        if(cats.isEmpty()) return dequeDog();
        if(dogs.peek().order < cats.peek().order)
            return dequeDog();
        return dequeCat();
    }

    public Cat dequeCat() {
        return cats.poll();
    }

    public Dog dequeDog() {
        return dogs.poll();
    }

}
