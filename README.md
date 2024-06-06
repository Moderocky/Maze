Maze
=====

This is a little library for generating (and handling) mazes in Java.

I wanted to create mazes for a project and found no suitable maze-generating libraries online.
The ones I did find were either:

- Unsuitable for converting into the format I needed
- Impossible to customise or control in any meaningful way
- Ridiculously greedy for memory

This was designed with the following abilities in mind:

1. To represent mazes in a wall-and-path pixel layout. \
   For any (x,y) coordinate in the maze, you can test whether that position is a path or a wall,
   and metadata about the position (e.g. is it on the correct path? Is it an intersection?)
2. To allow fine-grain control over the maze generation. \
   The ability to set the start and end points, to create multiple exits, etc.
3. To minimise the memory footprint, and allow very large mazes to be generated.
   > Note to fellow maze library creators: \
   Please, for goodness' sake, don't create a table of individual `Cell` objects :(
