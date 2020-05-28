package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class data {

    public ArrayList<String> labels;
    private boolean contains_label;
    ArrayList<ArrayList<Object>> rawData = new ArrayList<>();
    ArrayList<ArrayList<String>> uniques = new ArrayList<>();
    String[][] dataMatrix;

    public data(String path, boolean contains_label) {

        labels = new ArrayList<>();
        this.contains_label = contains_label;
        read_csv(path);
    }

    public void rawDataToMatrix() {
        dataMatrix = new String[rawData.size()][rawData.get(0).size()];
        for (int i = 0; i < dataMatrix.length; i++) {
            for (int j = 0; j < dataMatrix[0].length; j++) {
                dataMatrix[i][j] = (String) rawData.get(i).get(j);
            }
        }
    }

    private void read_csv(String path) {
        String row = null;
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
            //gui.show_error_message(e.getMessage(),"FileNotFoundException");
        }
        boolean f = true;
        while (true) {
            try {
                row = csvReader.readLine();
                if (f && contains_label && row != null) {
                    String[] elements = row.split(",");
                    for (int i = 0; i < elements.length; i++) {
                        labels.add(elements[i]);
                    }
                    f = false;
                    row = csvReader.readLine();
                }
            } catch (IOException e) {
                System.out.print(e.getMessage());
                gui.show_error_message(e.getMessage(),"IOException");
            }
            if (row == null) break;
            Object[] elements = row.split(",");
            rawData.add(new ArrayList<>(Arrays.asList(elements)));
        }
        try {
            csvReader.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
            gui.show_error_message(e.getMessage(),"IOException");
        }
    }

    public void find_uniques() {
        for (int i = 0; i < rawData.get(0).size(); i++) {
            uniques.add(new ArrayList<>());
        }
        for (int i = 0; i < rawData.size(); i++) {
            for (int j = 0; j < rawData.get(0).size(); j++) {
                String element = (String) rawData.get(i).get(j);

                if (!(uniques.get(j).contains(element))) {
                    uniques.get(j).add(element);
                }
            }
        }
    }

}
