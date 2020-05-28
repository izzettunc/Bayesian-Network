package com.company;

import java.awt.desktop.SystemSleepEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class bayesian_network {
    network conditions;
    data train_data;
    data test_data;

    private int[][] cm;

    Hashtable<String, customIndexedMatrix<String>> cpTables;
    Hashtable<String, Boolean> isVisited;

    public bayesian_network(network conditions, data train, data test) {
        this.conditions = conditions;
        train_data = train;
        test_data = test;
    }

    public void train() {
        train_data.rawDataToMatrix();
        train_data.find_uniques();
        calculate_cpts();
    }

    public void test(String target_col) {
        test_data.rawDataToMatrix();
        test_data.find_uniques();

        int target_col_index = test_data.labels.indexOf(target_col);
        int row_count = test_data.dataMatrix.length;
        int col_count = test_data.dataMatrix[0].length;
        String[] truth = new String[row_count];
        String[] prediction = new String[row_count];

        for (int i = 0; i < row_count; i++) {
            truth[i] = test_data.dataMatrix[i][target_col_index];
        }

        ArrayList<String> classes = test_data.uniques.get(target_col_index);
        Hashtable<String, BigDecimal> probabilities = new Hashtable<>();
        Hashtable<String, BigDecimal> probabilitiesWOZ = new Hashtable<>();
        for (int i = 0; i < row_count; i++) {
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal totalWOZ = BigDecimal.ZERO;
            BigDecimal max = BigDecimal.valueOf(Double.MIN_VALUE);
            for (String c : classes) {
                BigDecimal cProb = BigDecimal.ONE;
                BigDecimal cProbWOZ = BigDecimal.ONE;
                for (int j = 0; j < col_count; j++) {
                    if (j != target_col_index) {
                        customIndexedMatrix<String> table = cpTables.get(test_data.labels.get(j));
                        String row = "";
                        node col_node = conditions.get_node(test_data.labels.get(j));
                        for (node connection : col_node.connectedFrom) {
                            if (connection.name.equals(target_col))
                                row += "|" + c;
                            else {
                                String con_col_val = test_data.dataMatrix[i][test_data.labels.indexOf(connection.name)];
                                row += "|" + con_col_val;
                            }
                        }
                        row = row.substring(1);
                        BigDecimal prob = BigDecimal.valueOf((double) table.get(row, test_data.dataMatrix[i][j]));
                        cProb = cProb.multiply(prob);
                        if (prob.compareTo(BigDecimal.ZERO) != 0)
                            cProbWOZ = cProbWOZ.multiply(prob);

                    }
                }
                customIndexedMatrix<String> table = cpTables.get(target_col);
                cProb = cProb.multiply(BigDecimal.valueOf((double) table.get("", c)));
                cProbWOZ = cProbWOZ.multiply(BigDecimal.valueOf((double) table.get("", c)));
                probabilities.put(c, cProb);
                probabilitiesWOZ.put(c, cProbWOZ);
                total = total.add(cProb);
                totalWOZ = totalWOZ.add(cProbWOZ);
            }
            for (String c : classes) {
                BigDecimal t;
                if (total.compareTo(BigDecimal.ZERO) != 0)
                    t = probabilities.get(c).divide(total, RoundingMode.HALF_UP);
                else
                    t = probabilitiesWOZ.get(c).divide(totalWOZ, RoundingMode.HALF_UP);
                if (t.compareTo(max) > 0) {
                    max = t;
                    prediction[i] = c;
                }
            }
        }
        cm = calc_confusion_matrix(truth, prediction);
    }

    private int[][] calc_confusion_matrix(Object[] truth, Object[] pred) {
        int[][] cm = new int[2][2];
        for (int i = 0; i < truth.length; i++) {
            if (truth[i].equals(pred[i]) && truth[i].equals("bad"))
                cm[0][0]++;//TP
            else if (truth[i].equals(pred[i]) && truth[i].equals("good"))
                cm[1][1]++;//TN
            else if (!truth[i].equals(pred[i]) && truth[i].equals("bad"))
                cm[1][0]++;//FN
            else
                cm[0][1]++;//FP
        }

        return cm;
    }

    public void print_stats_from_confusion_matrix(String name) {
        try {
            FileWriter fw=null;
            File f = new File("results\\");
            if(!f.exists())
                f.mkdir();
            fw = new FileWriter("results\\stats_"+name+".csv");
            double acc = (double) (cm[0][0] + cm[1][1]) / (double) (cm[1][1] + cm[1][0] + cm[0][1] + cm[0][0]);
            fw.write("Accuracy : " + acc+"\n");
            fw.write("True Positive Count : " + cm[0][0]+"\n");
            double tpr = (double) cm[0][0] / (double) (cm[0][0] + cm[1][0]);
            fw.write("True Positive Ratio : " + tpr+"\n");
            fw.write("True Negative Count : " + cm[1][1]+"\n");
            double tnr = (double) cm[1][1] / (double) (cm[1][1] + cm[0][1]);
            fw.write("True Negative Ratio : " + tnr+"\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void print_cpts()
    {
        try {
                FileWriter fw = null;
                File f = new File("results\\");
                if (!f.exists())
                    f.mkdir();
                Set<String> keys = cpTables.keySet();
                for (String key : keys) {
                    fw = new FileWriter("results\\cpt_"+key+".csv");
                    customIndexedMatrix<String> cpt = cpTables.get(key);
                    Object[][] table = cpt.matrix;
                    fw.write("rows\\cols,");
                    for(int j = 0; j < table[0].length; j++)
                        if(j==table[0].length-1)
                            fw.write(cpt.col_indices.get(j)+"\n");
                        else
                            fw.write(cpt.col_indices.get(j)+",");
                    for (int i = 0; i < table.length; i++) {
                        fw.write(cpt.row_indices.get(i)+",");
                        for (int j = 0;j < table[0].length; j++) {
                            if(j==table[0].length-1)
                                fw.write(((double)table[i][j])+"\n");
                            else
                                fw.write(((double)table[i][j])+",");
                        }
                    }
                    fw.flush();
                    fw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void calculate_cpts() {
        isVisited = new Hashtable<>();
        cpTables = new Hashtable<>();
        dfs(conditions.root);
    }

    private void dfs(node current) {
        if (isVisited.get(current.name) == null) {
            isVisited.put(current.name, true);//DFS

            //Find all unique values of parents for conditions
            ArrayList<ArrayList<String>> conditions = new ArrayList<>();
            ArrayList<Integer> col_indices = new ArrayList<>();
            ArrayList<Integer> s_indices = new ArrayList<>();
            for (int i = 0; i < current.connectedFrom.size(); i++) {
                conditions.add(new ArrayList<>());
                int col_index = train_data.labels.indexOf(current.connectedFrom.get(i).name);
                col_indices.add(col_index);
                for (int j = 0; j < train_data.uniques.get(col_index).size(); j++) {
                    conditions.get(i).add(train_data.uniques.get(col_index).get(j));
                }
            }
            //Find all unique values of current node/column for possible values
            for (int i = 0; i < conditions.size(); i++) {
                s_indices.add(0);
            }
            int cur_index = train_data.labels.indexOf(current.name);

            //Calculate cpt
            customIndexedMatrix<String> cpt = create_cpt(conditions, s_indices, train_data.uniques.get(cur_index), col_indices, cur_index);

            cpTables.put(train_data.labels.get(cur_index), cpt);

            for (node connection : current.connectedTo) {
                dfs(connection);
            }
        }
    }

    private customIndexedMatrix<String> create_cpt(ArrayList<ArrayList<String>> condition_list, ArrayList<Integer> condition_indices, ArrayList<String> cur_unique, ArrayList<Integer> col_indices, int cur_index) {
        int row = 1;
        int col = cur_unique.size();
        for (ArrayList<String> c : condition_list) {
            row *= c.size();
        }
        Object[][] raw_cpt = new Object[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                raw_cpt[i][j] = (double) 0;
            }
        }
        ArrayList<String> row_indices = new ArrayList<>();
        for (int iterator = 0; iterator < row; iterator++) {

            double total = 0;
            for (int i = 0; i < train_data.dataMatrix.length; i++) {
                int flag_c = 0;
                for (int j = 0; j < cur_unique.size(); j++) {
                    if (train_data.dataMatrix[i][cur_index].equals(cur_unique.get(j))) {
                        for (int k = 0; k < condition_indices.size(); k++) {
                            if (train_data.dataMatrix[i][col_indices.get(k)].equals(condition_list.get(k).get(condition_indices.get(k))))
                                flag_c++;
                        }
                        if (flag_c == condition_indices.size()) {
                            raw_cpt[iterator][j] = ((double) raw_cpt[iterator][j]) + 1;
                            total++;
                        }
                    }
                }
            }
            for (int i = 0; i < col; i++) {
                raw_cpt[iterator][i] = ((double) raw_cpt[iterator][i]) / total;
            }
            String row_index = "";
            for (int i = 0; i < condition_indices.size(); i++) {
                row_index += "|" + condition_list.get(i).get(condition_indices.get(i));
            }
            if (!row_index.equals(""))
                row_index = row_index.substring(1);
            row_indices.add(row_index);
            //Index increaser
            if (condition_list.size() == 0) break;
            condition_indices.set(condition_indices.size() - 1, condition_indices.get(condition_indices.size() - 1) + 1);
            for (int i = condition_indices.size() - 1; i >= 0; i--) {
                if (i != 0 && condition_indices.get(i) >= condition_list.get(i).size()) {
                    condition_indices.set(i, 0);
                    condition_indices.set(i - 1, condition_indices.get(i - 1) + 1);
                } else if (condition_indices.get(i) >= condition_list.get(i).size()) {
                    break;
                }
            }
        }
        return new customIndexedMatrix<>(raw_cpt, row_indices, cur_unique);
    }
}
