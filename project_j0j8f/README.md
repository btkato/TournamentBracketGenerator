# **CPSC 210 Personal Project - Tournament Bracket Builder**

## Project Proposal 

### *What* will the application do?
Tournament Bracket Builder will create a single-elimination tournament bracket tree for a minimum of five and up to eight competitors. A tournament bracket tree will produce an appropriate sized tree with all competitors placed on the end branches in a randomized order.

### *Who* will use it?
 - Karate tournaments
 - Soccer tournaments
 - Basketball tournaments
 - Tournaments that require single-elimination tournament brackets
 - Tournaments that use a round-robin format

### *Why* is the project of interest to you?
I formerly competed in karate and help run the club I used to train at. I recently helped organize and run a tournament in March of 2023. We paid a company to help run our tournament registration and scorekeeping systems. This company created all tournament brackets from the online database of registered competitors. They also used this data to keep track of who won each match, what scores each competitors received and the winners of each division.

I noticed many inefficiencies and bugs with the programmed system that was used. As a first step, I would like to take this opportunity in my studies to work towards creating this larger project of a tournament scorekeeping and registration system. Karate in BC does not have a **robust** tournament tracking system and I think it would be a good opportunity to create this. 

I understand this project would be too complex for the scope of this course and time I have, so I thought a **simple** component of this program would be the match making system. This means creating tournament brackets from a list of competitors and the ability to recreate and update a bracket if competitors were added or removed from a division.

## User Stories

- As a user, I want to create a single-elimination tournament bracket from a list of minimum five to a maximum of eight registered competitors
- As a user, I want to replace a bracket after adding a new competitor to the list
- As a user, I want to replace a bracket after removing a new competitor to the list
- As a user, I want to view the created single-elimination tournament bracket
- As a user, I want to add "Bye" matches if there are not enough competitors for a divison
- As a user, I want my bracket tree to never have a match with "Bye" versus "Bye"
- As a user, I want to have the option to save or discard my newly completed and generated bracket
- As a user I want to have the option to load previously saved and generated brackets

# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by clicking the "Add Competitor" button or by clicking the "Show Registered" button and from the newly generated screen press "Add Competitor"
- You can generate the second required action related to adding Xs to a Y by clicking the "Remove Competitor" button or by clicking the "Show Registered" and from the newly generated screen press "Remove Competitor"
- You can locate my visual component by adding a minimum of 5 competitors by the "Add Competitor" button, then clicking the "Save Completed Division & Add New Division" button, then finally clicking the "Print" button or by clicking the "Quit" button
- You can save the state of my application by clicking the "Save All Progress" button
- You can reload the state of my application by clicking the "Load" button at the start of the application

# Phase 4: Task 2
Mon Aug 07 14:34:39 PDT 2023
Event log cleared.
Mon Aug 07 14:34:46 PDT 2023
Brandon added
Mon Aug 07 14:35:10 PDT 2023
Kyle added
Mon Aug 07 14:35:15 PDT 2023
John added
Mon Aug 07 14:35:19 PDT 2023
Anne added
Mon Aug 07 14:35:20 PDT 2023
Anne removed
Mon Aug 07 14:35:26 PDT 2023
Alyssa added
Mon Aug 07 14:35:33 PDT 2023
Ryan added
Mon Aug 07 14:35:37 PDT 2023
Quinn added
Mon Aug 07 14:35:39 PDT 2023
Quinn removed
Mon Aug 07 14:35:43 PDT 2023
Iris added
Mon Aug 07 14:35:47 PDT 2023
Owen added
Mon Aug 07 14:35:49 PDT 2023
Gary added
Mon Aug 07 14:35:52 PDT 2023
Matchmaking done
Mon Aug 07 14:35:52 PDT 2023
Division 1 added

# Phase 4: Task 3

If I were to refactor this project, I would start at the GeneratedTreeGUI class and refactor all methods within this class. Specifically I would have found a way to reduce the redundant code in drawFirstRound(), drawSecondRound(), drawThirdRound() and printCompetitors() methods. I can see there is a large amount of redundant coding in the creation of these methods. I would try to find ways to create loops and constants for the coordinates of each shape and string.

I would have also looked into splitting the BracketGeneratorGUI class into separate classes for all the buttons. I notice there is multiple purposes for a single class and I would try to look into refactoring each button into a single class with an interface for the button action methods.