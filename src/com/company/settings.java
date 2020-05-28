package com.company;

public class settings {
    static String train_path;
    static String network_path;
    static String test_path;

    static String target_feature;

    static boolean print_cpt=false;
    static boolean print_test_acc=false;
    static boolean print_train_acc=false;


    static void run()
    {
        //Prepares the data

        data my_train_data = new data(train_path,true);
        data my_test_data = new data(test_path,true);

        network my_network = new network(network_path);

        bayesian_network my_bayes_train = new bayesian_network(my_network,my_train_data,my_train_data);
        bayesian_network my_bayes_test = new bayesian_network(my_network,my_train_data,my_test_data);
        //Trains
        my_bayes_train.train();
        my_bayes_test.train();

        //Predict
        my_bayes_train.test(target_feature);
        my_bayes_test.test(target_feature);

        //Reporting
        if(print_train_acc)
            my_bayes_train.print_stats_from_confusion_matrix("train");
        if(print_test_acc)
            my_bayes_test.print_stats_from_confusion_matrix("test");
        if(print_cpt)
            my_bayes_train.print_cpts();

        gui.show_message("Process successfuly finished","Info");

    }
}
