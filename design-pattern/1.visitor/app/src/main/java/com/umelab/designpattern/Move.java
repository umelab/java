package com.umelab.designpattern;

public class Move implements AnimalOperation{
    
    public void visitMonkey(Monkey monkey) {
        monkey.walk();
    }
    
    public void visitLion(Lion lion) {
        lion.run();
    }
}
