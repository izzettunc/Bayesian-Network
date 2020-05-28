package com.company;

import java.util.ArrayList;
import java.util.Hashtable;

public class customIndexedMatrix<T> {
    public Object[][] matrix;
    public Hashtable<T,Integer> rows;
    public Hashtable<T,Integer> cols;
    public ArrayList<T> row_indices;
    public ArrayList<T> col_indices;

    public customIndexedMatrix(Object[][] matrix, ArrayList<T> rowIndices, ArrayList<T> colIndices)
    {
        this.matrix=matrix;
        rows = new Hashtable<>();
        cols = new Hashtable<>();
        row_indices = new ArrayList<>(rowIndices);
        col_indices = new ArrayList<>(colIndices);
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
