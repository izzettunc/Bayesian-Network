package com.company;

import java.util.ArrayList;
import java.util.Hashtable;

public class customIndexedMatrix<T> {
    private Object[][] matrix;
    private Hashtable<T,Integer> rows;
    private Hashtable<T,Integer> cols;

    public customIndexedMatrix(Object[][] matrix, ArrayList<T> rowIndices, ArrayList<T> colIndices)
    {
        this.matrix=matrix;
        rows = new Hashtable<>();
        cols = new Hashtable<>();

        for (int i = 0; i < rowIndices.size(); i++) {
            rows.put(rowIndices.get(i),i);
        }

        for (int i = 0; i < colIndices.size(); i++) {
            cols.put(colIndices.get(i),i);
        }
    }

    public Object get(T rowIndex, T colIndex)
    {
        return matrix[rows.get(rowIndex)][cols.get(colIndex)];
    }

    public void set(T rowIndex, T colIndex, Object value)
    {
        matrix[rows.get(rowIndex)][cols.get(colIndex)] = value;
    }
}
