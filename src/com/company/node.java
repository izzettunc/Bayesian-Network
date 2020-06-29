package com.company;

import java.util.ArrayList;

/* Basic graph component */
public class node {
    public String name;
    public ArrayList<node> connectedTo;
    public ArrayList<node> connectedFrom;

    public node(String name)
    {
        connectedTo= new ArrayList<>();
        connectedFrom = new ArrayList<>();
        this.name = name;
    }

    public void connect(node to)
    {
        this.connectedTo.add(to);
        to.connectedFrom.add(this);
    }
}
