# Conway's Game of Life on a Torus
Welcome to our page where we implement Conway's Game of Life onto a torus. Our main goal was to visualize this game in a new perspective where instead of a infinite 2-D graph, we change it to a 3D shape. The purpose of this was to make the game interactive so that one can visualize how Conway's Game of Life would look like on a finite grid rather than an infinite plane. By using matrices and graph nodes, it allowed us to build our new data structure of a torus. 

<img width="438" alt="Screenshot 2024-05-14 at 10 58 23 AM" src="https://github.com/Frances-Lu/conway/assets/157903733/544a8578-ea70-4329-812b-98c203237dc5">

Built by Blayne Downer and Frances Lu
Williams College CSCI 136
Last updated 5/14/24

#Note on Setting up Alive and Dead Nodes
We did not have time to implement a system for the user to simply select which nodes they want alive/dead at the start as we said we would do. To work around this, we have found you can use methods in conwaygraph.java in the giveSquares() method to control which squares start alive (using makeNodeAlive() and makeNeighborsAlive() methods). We have commented a note and currently have node 1 alive along with its neighbors, resulting in a 3 by 3 square to be alive at the start.