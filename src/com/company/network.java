package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/* Basic graph structure */
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

    //reads and creates the graph
    public network(String path)
    {
        String row = null;
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
            gui.show_error_message(e.getMessage(),"FileNotFoundException");
        }
        boolean f = true;
        while (true) {
            try {
                row = csvReader.readLine();
                if (f && row != null) {
                    String[] elements = row.split(",");
                    for (int i = 0; i < elements.length; i++) {
                        this.add_node(elements[i]);
                        if(i==0)
                            this.set_root(elements[i]);
                    }
                    f = false;
                    row = csvReader.readLine();
                }
            } catch (IOException e) {
                System.out.print(e.getMessage());
                gui.show_error_message(e.getMessage(),"IOException");
            }
            if (row == null) break;
            String[] elements = row.split(",");
            for (int i = 1; i < elements.length; i++) {
                this.add_connection(elements[0],elements[i]);
            }
        }
        try {
            csvReader.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
            gui.show_error_message(e.getMessage(),"IOException");
        }
    }
}
