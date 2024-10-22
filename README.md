# Maze Lab


# Purpose

“In this lab you will use the power of a stack and a queue to explore and find your way through a maze. The purpose of this lab is to:



* Have you implement a stack and a queue,
* Build a simple linked list of nodes,
* Use your stack and queue to solve a maze,
* Explore the differences between the two solution strategies.
* Apply software team development best practices


# Requirements



* Complete the [CSCI 151 - Stacks and Queues Simply A-Maze-ing! lab](https://www.cs.oberlin.edu/~rhoyle/16s-cs151/lab03/index.html) from Oberlin.
* Changes from the lab as written for Oberlin:
    * The code is in the MazeApp folder in your current repository. You can skip all the steps under “starting point code”
    * You will use VS Code and not Eclipse.
    * Consider questions 2, 4, and 5 in Part 5. We will discuss them as a class.
* You will work with a partner on this summative lab and demonstrate your understanding of using GitHub as a team
    * Commit to GitHub frequently, at least daily, with a meaningful commit message
    * You and your partner must create and use branches for your development as specified in the daily milestones
    * You and your partner must issue pull requests to incorporate changes in your branch into `main`
* Edit README.md in your repository to Include a reflection in which you reflect on you and your partners implementation of the GitHub best practices for team development. **In addition**, reflect on your teamwork. What did you and your partner do well? What do you and your partner need to improve next time? What would you do differently next time?
* This lab is recorded as two labs in the gradebook. One score is based on satisfying the requirements for the Maze lab. The other score is based on demonstrating GitHub best practices.


# Daily Milestones (will be checked daily by instructor)



1. (**Teammate A & B**) create `mazeSkeleton` branch from `main`; implement `Maze` class skeleton in `mazeSkeleton` branch; pull into `main` (**pair programming**) \
At the same time:
    * (**Teammate B**) create `mazeTest` branch from `main`; implement `Maze` unit test in `mazeTest` branch[^1]; run test (verify fails); pull into `main`


    * (**Teammate A**) create `maze` branch from `main`; implement `Maze` class in `maze` branch

    (**Teammate A & B**) merge `main` into `maze` branch; run tests (probably fails); debug `Maze` class; run tests (verify passes); pull into `main` (**pair programming**)

2. At the same time:
    * (**Teammate A**) create `stack` branch from `main`; implement `MyStack` and `MyStackTest` in `stack` branch; run tests (verify passes); pull into `main`
    * (**Teammate B**) create `queue` branch from `main`; implement `MyQueue` and `MyQueueTest`[^2] in `queue` branch; run tests (verify passes); pull into `main`


3. (**Teammate A & B**) create `solver` branch from `main`; implement `MazeSolver` in `solver` branch (**pair programming**)
4. At the same time:
    * (**Teammate A**) create `stackSolver` branch from `solver` (not `main`); implement `MazeSolverStack` in `stackSolver` branch
    * (**Teammate B**) create `queueSolver` branch from `solver` (not `main`); implement `MazeSolverQueue` in `queueSolver` branch
5. (**Teammate A & B**) tie up loose ends; pull both branches into `solver`; run tests (verify passes); uncomment all of the lines in MazeApp.java (refer to comment at the top of the file); test MazeApp by hand (verify functionality); pull into `main`

Your GitHub branching history should look similar to the following:



![visualization of GitHub branching reflecting best practices](image_0.png)



# Extensions



* Try the bonus mazes.
* Add more awesome.


# Submission



* Submit a link to your GitHub repository with this assignment.

<!-- Footnotes themselves at the bottom. -->
## Notes

[^1]:

     There is a maze-invalid file that contains an improperly formatted maze and should be used in the test to ensure exceptions do not go unhandled.

[^2]:

     To avoid future headaches, ensure that the test thoroughly tests the Queue class. Specifically, make sure to test that the last element in the queue is successfully dequeued.
