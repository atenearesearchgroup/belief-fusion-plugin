Belief Fusion Plugin
======
[![License Badge](https://img.shields.io/badge/license-EPL%202.0-brightgreen.svg)](https://opensource.org/licenses/EPL-2.0)

## About

beliefFusionPlugin is a MagicDraw plugin which allows the fusion of opinions in UML diagrams, using Subjective Logic and a UML Profile defined for this purpose.

## User guide

### Requirements

- MagicDraw 19.0
- Java >= 1.8


### Installing 

1. To install any MagicDraw plugin, you only have to add the corresponding compiled files in the folder **plugins** in your **local MagicDraw installation folder**, i.e. in /MagicDraw/plugins. In our case, you only have to add the three files available in the repository folder named **release**, so that they are in the following paths:

    - /MagicDraw/plugins/fusionPlugin/fusionPlugin.jar
    - /MagicDraw/plugins/fusionPlugin/plugin.xml
    - /MagicDraw/plugins/fusionPlugin/logoFusion.png

2. Open MagicDraw and select a project with at least one **Object Diagram**. _If it is not an Object Diagram, the icon won't be visible._
3. Open the Object Diagram and you should be able to see the following icon in the toolbar:

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118785217-f038fc80-b890-11eb-870d-452a527cafcb.png"/></div>

4. If the icon is available, the installation was completed successfully.

### Usage

1. Import the example project with our profile from the following link. _=> Link not available yet, to be released soon._
2. Once the project is loaded, create the classes needed for your project and instanciate them in an **Object Diagram**. 

In this tutorial, we will be using the example of two friends, Ada and Bob, who are deciding which film they think won an Oscar, so that they can make a guess in a group competition.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118788630-422f5180-b894-11eb-8acb-aaf37e28d552.png"/></div>

4. Now we will assign the **Agent** stereotype to those instances which will have an opinion, in this case, Ada and Bob, instances of **FilmFan**. Additionally, we will assign the stereotype **UncertainElementFusion** to the films, so that the fans can assign their beliefs, and fuse them afterwards.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118797220-a3f3b980-b89c-11eb-8e00-9bd9d164a0d0.png"/></div>

5. In this step, we create the **Belief** instances, so that the agents can assign their opinions to each of the movies.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118797526-f8973480-b89c-11eb-81a2-b41e45251e17.png"/></div>

6. Then, we assign the beliefs as tag values for each of the films.

Ada is pretty sure that the film which won an Oscar was Parasite, so she assigns the most confident opinion to it. In contrast, she decides to discard the other two films, since she is sure they did not win, so she assigns a high level of disbelief. 

Bob loves super hero films and he thinks Joker won the Oscar, although he is no entirely sure. Bob is also not into other type of films, so he does not know what to say about the others, assigning a vacuous opinion to them.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118797921-5a579e80-b89d-11eb-93da-e21817ead0a2.png"/></div>

7. Finally, we will click the fusion plugin button, and the opinions will be fused.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118798233-b6222780-b89d-11eb-947a-aa89916be134.png"/></div>

Depending on the fusion operator, the projection of the opinion will have a different value. However, the highest ones are mostly in the Parasite film, which is also the correct answer.

## Developer guide

### Requirements

- MagicDraw 19.0
- Java compiler 1.8 (JDK must be version 1.8 too - MagicDraw 19.0 does not support a higher version)
- Eclipse IDE for Eclipse Committers >= 2021-03

### Configure Eclipse for development

Before starting this tutorial, make sure to have installed the software mentioned in the requirements section.

1. Clone our repository in Eclipse, using the option **Import Projects from Git**.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118799434-0483f600-b89f-11eb-8481-dcd6e8ee32f3.png"/></div>

2. Following the tutorial available in the following URL, import the sample projects from the MagicDraw intallation folder.

https://docs.nomagic.com/display/MD182/Development+in+Eclipse

## Citations
