<br />
<p align="center">

  <h3 align="center">Bayesian Network</h3>

  <p align="center">
    A binary classification application that uses bayesian network as algorithm.
    <br />
    <br />
    <a href="https://github.com/izzettunc/Bayesian-Network/issues">Report Bug</a>
    ·
    <a href="https://github.com/izzettunc/Bayesian-Network/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
* [Getting Started](#getting-started)
  * [Installation](#installation)
* [Usage](#usage)
* [Input structure](#about-input-structure)
* [Roadmap](#roadmap)
* [License](#license)
* [Contact](#contact)



<!-- ABOUT THE PROJECT -->
## About The Project

![Product Name Screen Shot][product-screenshot]

This project made as a class assignment. Its purpose is basically classifying given test data by learned information from train data using bayesian networks and bayesian inference.

**Features:**

* You can classify any binary classification data
* You can print conditional probability table
* You can print test and train results in a txt file

### What is Bayesian Networks ?

A Bayesian network, Bayes network, belief network, decision network, Bayes(ian) model or probabilistic directed acyclic graphical model is a probabilistic graphical model (a type of statistical model) that represents a set of variables and their conditional dependencies via a directed acyclic graph (DAG). Bayesian networks are ideal for taking an event that occurred and predicting the likelihood that any one of several possible known causes was the contributing factor. For example, a Bayesian network could represent the probabilistic relationships between diseases and symptoms. Given symptoms, the network can be used to compute the probabilities of the presence of various diseases.

Efficient algorithms can perform inference and learning in Bayesian networks. Bayesian networks that model sequences of variables (e.g. speech signals or protein sequences) are called dynamic Bayesian networks. Generalizations of Bayesian networks that can represent and solve decision problems under uncertainty are called influence diagrams.

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Installation

1.  Clone the repo
```sh
git clone https://github.com/izzettunc/Bayesian-Network.git
```

2. Open src folder in a Java Editor

3. Make changes, run it, use it whatever you like :smile:

<!-- USAGE EXAMPLES -->
## Usage

Here is some unneeded screenshots for how to use it

* Select training file **Don't forget! Input should look like [Input structure](#about-input-structure)**
* Select test file **Don't forget! Input should look like [Input structure](#about-input-structure)**
* Select network structure file **Don't forget! Input should look like [Input structure](#about-input-structure)**
* Select target feature and reporting settings
* Press run

![Application Screen Shot][app-screenshot]
**Stats**
![Application Screen Shot][stats]
**Conditional probability table**
![Application Screen Shot][cpt]

## About Input Structure

While reading input files(train and test) I basically use DFS like algorithm to get each and every input file in every folder. So if you change input app won't going to work.

**Train and Test Structure:**
```
feature1,feature2,feature3,...,featureN
a       ,b       ,c       ,...,n        <- record 1
.
.
.
an      ,bn      ,cn      ,...,nn       <- record n   
```

**Network Structure**

```
    feature1,feature2,....,featureN
    from,to1,to2,to3,...,toN
    ..
    ..
    ..
```

**Example:**

![Network Example][network-example]

```
    T,Y,R
    T,R
    Y,R
```

<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/izzettunc/Bayesian-Network/issues) for a list of proposed features (and known issues).

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.

<!-- CONTACT -->
## Contact

İzzet Tunç - izzet.tunc1997@gmail.com
<br>
[![LinkedIn][linkedin-shield]][linkedin-url]

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=flat-square&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/izzettunc
[product-screenshot]: data/ss/header.png
[app-screenshot]: data/ss/bn_main.png
[stats]: data/ss/bn_stats.png
[cpt]: data/ss/bn_cpt.png
[network-example]: data/ss/bn_network.png
