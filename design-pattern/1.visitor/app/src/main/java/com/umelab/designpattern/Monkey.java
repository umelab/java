package com.umelab.designpattern;

public class Monkey implements Animal {
    
    public void shout() {
        System.out.println("Ooh oo aa aa!");
    }

    public void walk() {
        System.out.println("Monkey walk");
    }

    public void accept(AnimalOperation operation) {
        operation.visitMonkey(this);
    }
}

class Lion implements Animal {
    
    public void roar() {
        System.out.println("Roaaar!");
    }

    public void run(){
        System.out.println("Lion run");
    }

    public void accept(AnimalOperation operation) {
        operation.visitLion(this);
    }
}
