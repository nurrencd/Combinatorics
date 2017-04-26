# Combinatorics

Repository for coding projects for Combinatoric Optimization Course

Project 1 Due 4-25-17

User’s Guide
The user interface of this code can be found by running MainInterface.java. Initially, you will be prompted for the filepath of a directory that contains all of the desired .gr and .ss files. Then, you will be prompted for the name of a graph file, which will be processed immediately. The next prompt will be for a name for the graph, which can be used later to refer to the same results. After this, it prompts you for a general command, which can be one of the following:

•	ss graph file N  imposes a .ss file problem onto a graph, then solves the problem. “graph” and “file” must be existing graphs and .ss files. N is the desired algorithm to be run on the problem, denoted as follows:

o	1—Generic algorithm

o	2—FIFO implementation

o	3—Stack implementation

o	4—Dequeue implementation

o	5—Priority queue implementation

•	res graph n  returns the resulting GraphResult object and prints the results from the nth .ss problem line from the file imposed on the graph. Zero-indexed.

•	print  prints out available graphs

•	ng  returns to prompt you for another graph file, then return to general commands.

•	exit  exits the whole program

Error checking for commands is minimal, so be meticulous while entering commands. It may also be helpful to have the filepath copied to the clipboard. All file names are assumed to be formatted properly. Inputting the wrong filename will cause an error and will require restarting.




