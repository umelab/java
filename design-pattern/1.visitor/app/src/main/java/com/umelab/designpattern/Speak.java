package com.umelab.designpattern;

public class Speak implements AnimalOperation{
    public void visitMonkey(Monkey monkey) {
        monkey.shout();
    }

    public void visitLion(Lion lion) {
        lion.roar();
    }
}
