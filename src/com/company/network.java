package com.company;

import java.util.ArrayList;
import java.util.Hashtable;

public class network {
    node root;
    private Hashtable<String,node> nodes = new Hashtable<>();

    public void add_node(String name)
    {
        nodes.put(name,new node(name));
    }

    public void add_connection(String from,String to)
    {
        nodes.get(from).connect(nodes.get(to));
    }

    public void set_root(String name)
    {
        root = nodes.get(name);
    }

    public node get_node(String name)
    {
        return nodes.get(name);
    }
}
