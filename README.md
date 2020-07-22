# TilePuzzle-Uniformed-and-Informed-search

Tile puzzle is a combination puzzle that challenges a player to slide (frequently flat) pieces along certain routes (usually on a board) to establish a certain end-configuration.

<img src="https://user-images.githubusercontent.com/57639675/88153170-b74adf80-cc0d-11ea-9a64-a57a04b36b8f.jpg" alt="TilePuzzle" width="250"/>

Through this game we will solve different combinations of game board, by using the following algorithms:
Uniformed search - BFS, DFID
Informed search - A*, IDA*, DFBnB

The pieces to be moved consist number and color, the number range is from 1 to the size of the range and the color can be green, red or black.
When a black piece cannot be moved, the cost of green piece is 1 and red piece is 30.
When we use uniformed search we want to find the shortest path, and when we use informed search we want to find the cheapest path.
