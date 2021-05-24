Belief Fusion Plugin
======
[![License Badge](https://img.shields.io/badge/license-EPL%202.0-brightgreen.svg)](https://opensource.org/licenses/EPL-2.0)

## About

beliefFusionPlugin is a MagicDraw plugin that enables the the fusion the opinions added to UML diagrams using our UML Belief Profile.

## User guide

### Requirements

- MagicDraw 19.0
- Java >= 1.8


### Installing 

1. To install our plugin, as any MagicDraw plugin, the compiled files needs to be added to the **plugins** folder of your **local MagicDraw installation folder**, i.e. in <path to your MagicDraw installation folder>/MagicDraw/plugins/. You will find our compiled files in our latest release (v1.0) and under the folder named **release**. After placing them in the plugins folder, the folder structure should be:

    - /MagicDraw/plugins/fusionPlugin/fusionPlugin.jar
    - /MagicDraw/plugins/fusionPlugin/plugin.xml
    - /MagicDraw/plugins/fusionPlugin/logoFusion.png

2. Open MagicDraw and select a project with at least one **Object Diagram**. _Note that for other types of diagrams the icon won't be visible._
3. Open the Object Diagram and you should be able to see the following icon in the toolbar:

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118785217-f038fc80-b890-11eb-870d-452a527cafcb.png" width="80%"/></div>

4. If the icon is available, the installation was completed successfully.

### Usage

1. Import the example project with our profile from the following link. _Link not available yet, to be released soon._
2. Once the project is loaded, create the classes needed for your project and instanciate them in an **Object Diagram**. 

    In this tutorial, we are using the example of two friends, Ada and Bob, who are trying to agree on which film won an Oscar to make a guess in a group competition.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118788630-422f5180-b894-11eb-8acb-aaf37e28d552.png"/></div>

4. Now, we will assign the **Agent** stereotype to those instances which will comment on the films, in this case, Ada and Bob, instances of **FilmFan**. Additionally, we will assign the stereotype **UncertainElementFusion** to the films so that the fans can assign their beliefs and fuse them afterward.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118797220-a3f3b980-b89c-11eb-8e00-9bd9d164a0d0.png"/></div>

5. In this step, we create the **Belief** instances, so that the agents can assign their opinions to each of the movies.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118797526-f8973480-b89c-11eb-81a2-b41e45251e17.png"/></div>

6. Then, we assign the beliefs as tag values for each of the films.

    Ada is pretty sure that the film that won an Oscar was Parasite, so she assigns the most confident opinion. In contrast, she decides to discard the other two films since she is pretty sure they did not win, so she assigns a high level of disbelief. 

    Bob loves superhero films, and he thinks Joker won the Oscar, although he is not entirely sure. Bob is also not into other types of films, so he does not know what to say about the others, assigning a vacuous opinion to them.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118797921-5a579e80-b89d-11eb-93da-e21817ead0a2.png"/></div>

7. Finally, we click the fusion plugin button, and the opinions will be fused.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118798233-b6222780-b89d-11eb-947a-aa89916be134.png" width="80%"/></div>

Depending on the fusion operator, the projection of the opinion will have a different value. However, the highest ones are mostly in the Parasite film, which is also the correct answer.

Optionally, you can show the tag values in a note so that your diagram looks more organized.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/119328157-1a6d2e80-bc84-11eb-8051-13667dc3cfd8.png" width="80%"/></div>


## Developer guide

### Requirements

- MagicDraw 19.0
- Java compiler 1.8 (JDK must be version 1.8 too - MagicDraw 19.0 does not support a higher version)
- Eclipse IDE for Eclipse Committers >= 2021-03

### Configure Eclipse for development

Before starting this tutorial, make sure to have installed the software mentioned in the requirements section.

1. Clone our repository in Eclipse, using the option **Import Projects from Git**.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118799434-0483f600-b89f-11eb-8481-dcd6e8ee32f3.png" width="70%"/></div>

   Make sure that the Directory in which you clone the plugin is your current workspace.
   
   <div align="center"><img src="https://user-images.githubusercontent.com/26405870/119344581-2105a100-bc98-11eb-9516-c69a0ded9318.png" width="70%"/></div>
   
2. Following the tutorial available in the following URL, import the sample projects from the MagicDraw intallation folder.

    https://docs.nomagic.com/display/MD190SP4/Development+in+Eclipse
    
3. Aftet the previous steps, your workspace should look like this:

    <div align="center"><img src="https://user-images.githubusercontent.com/26405870/119345074-b86af400-bc98-11eb-90e3-718d3a273f6f.png"/></div>
    
4. Add the **BeliefFusionPlugin** project to the **MagicDraw with all plugins Build Path**, selecting on the _Project Properties > Java Build Path > Projects > Add_. _(Right-click on the project to access to the Properties option)_

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/119345374-14357d00-bc99-11eb-9bf6-8ebcbf9cf264.png" width="80%"/></div>

5. Modify the file **MagicDraw with all plugins.launch** inside MagicDraw with all plugins project, so that the stringAttribute named **org.eclipse.jdt.launching.VM_ARGUMENTS**, contains an attribute named  **-Dmd.plugins.dir** with the following content:

```
    -Dmd.plugins.dir=&quot;${resource_loc:MagicDraw/MAGIC_DRAW_INSTALL_DIRECTORY}/plugins;${workspace_loc};${workspace_loc}/beliefFusionPlugin/plugin;"
```
6. Right-click on the file **MagicDraw with all plugins.launch**, select _Run As... > MagicDraw with all plugins_ and a new instance of MagicDraw should open. If this icon is available, it means that the deployment was completed successfully.

<div align="center"><img src="https://user-images.githubusercontent.com/26405870/118785217-f038fc80-b890-11eb-870d-452a527cafcb.png" width="80%"/></div>

## Citations
