# TilePuzzle-Uniformed-and-Informed-search

Tile puzzle is a combination puzzle that challenges a player to slide (frequently flat) pieces along certain routes (usually on a board) to establish a certain end-configuration.

<img src="https://user-images.githubusercontent.com/57639675/88153170-b74adf80-cc0d-11ea-9a64-a57a04b36b8f.jpg" alt="TilePuzzle" width="250"/>

Through this game we will solve different combinations of game board, by using the following algorithms:
Uniformed search - BFS, DFID
Informed search - A*, IDA*, DFBnB

The pieces on the board consist number and color, the number range is from 1 to the size of the range and the color can be green, red or black.
When a black piece cannot be moved, the cost of green piece is 1 and red piece is 30.
When we use uniformed search we want to find the shortest path, and when we use informed search we want to find the cheapest path.

SearchAlgorithm is an interface that contains the methods: 
* doAlgo() that Performs the search according to the algorithm.
* getCountVertices() that returns number of vertices that generated to solve the problem.
* printOpenL(HashMap<String, vertex> h) that prints the open list to the screen.

The classes: BFS, DFID, ASTAR, IDA, DFBnB implements SearchAlgorithm.
Every class that implements SearchAlgorithm has vertex, the class vertex represents a node in a graph,every vertex has the attributes: Board, cost, costH, cost, lastStep, path, out, timestamp.
The class Board represents a board game with the attributes matrix ,row,col. When row and col represent the place on the matrix of the empty cell. 


![uml](https://user-images.githubusercontent.com/57639675/93845716-91dc5f80-fcaa-11ea-9b0b-4bec213adb3a.jpg)

