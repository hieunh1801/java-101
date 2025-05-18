package org.hieunh1801.element;

public class Element {

    public String name;
    public String symbol;
    public int position;

    public Element(String name, String symbol, int position) {
        this.name = name;
        this.symbol = symbol;
        this.position = position;
    }

    public Element() {
        // Use by JSON mappers
    }
}