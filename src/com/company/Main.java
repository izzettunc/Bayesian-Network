package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        network bayesian = new network();
        /*bayesian.add_node(strings.play);
        bayesian.add_node(strings.temp);
        bayesian.add_node(strings.humidity);
        bayesian.add_node(strings.windy);
        bayesian.add_node(strings.outlook);
        bayesian.set_root(strings.play);
        bayesian.add_connection(strings.play,strings.temp);
        bayesian.add_connection(strings.play,strings.humidity);
        bayesian.add_connection(strings.play,strings.windy);
        bayesian.add_connection(strings.play,strings.outlook);
        bayesian.add_connection(strings.humidity,strings.temp);
        bayesian.add_connection(strings.humidity,strings.windy);*/
        bayesian.add_node(strings.attr_class);
        bayesian.add_node(strings.attr_purpose);
        bayesian.add_node(strings.attr_housing);
        bayesian.add_node(strings.attr_personal_status);
        bayesian.add_node(strings.attr_property_magnitude);
        bayesian.add_node(strings.attr_job);
        bayesian.add_node(strings.attr_employment);
        bayesian.add_node(strings.attr_own_telephone);
        bayesian.add_node(strings.attr_credit_history);
        bayesian.set_root(strings.attr_class);
        bayesian.add_connection(strings.attr_class,strings.attr_purpose);
        bayesian.add_connection(strings.attr_class,strings.attr_housing);
        bayesian.add_connection(strings.attr_class,strings.attr_property_magnitude);
        bayesian.add_connection(strings.attr_class,strings.attr_personal_status);
        bayesian.add_connection(strings.attr_class,strings.attr_employment);
        bayesian.add_connection(strings.attr_class,strings.attr_job);
        bayesian.add_connection(strings.attr_class,strings.attr_own_telephone);
        bayesian.add_connection(strings.attr_class,strings.attr_credit_history);
        bayesian.add_connection(strings.attr_property_magnitude,strings.attr_housing);
        bayesian.add_connection(strings.attr_property_magnitude,strings.attr_job);
        bayesian.add_connection(strings.attr_housing,strings.attr_purpose);
        bayesian.add_connection(strings.attr_housing,strings.attr_personal_status);
        bayesian.add_connection(strings.attr_job,strings.attr_employment);
        bayesian.add_connection(strings.attr_job,strings.attr_own_telephone);
        bayesian.add_connection(strings.attr_own_telephone,strings.attr_credit_history);

        data my_train_data = new data("D:\\Git\\Bayesian-Network\\data\\train.csv",true);
        data my_test_data = new data("D:\\Git\\Bayesian-Network\\data\\test.csv",true);

        bayesian_network my_bayes = new bayesian_network(bayesian,my_train_data,my_test_data);
        my_bayes.train();
        my_bayes.test("class");
    }
}
