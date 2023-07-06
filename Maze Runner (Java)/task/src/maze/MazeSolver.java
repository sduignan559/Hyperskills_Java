package maze;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class MazeSolver {

    static Deque<Position> path = new ArrayDeque<>();
    static ArrayList<Position> visited = new ArrayList<>();

    public int[][]  solveMaze(int[][] maze) {

        //starting position
        Position p = new Position(1,1);
        maze [1][0] = 3;
        maze [1][1] = 3;
        path.push(p);
        visited.add(p);

        while(true)	{

            int y = path.peek().y;
            int x = path.peek().x;


            //down
            if(isValid(maze, y+1 , x)) {
                if(maze[y+1][x] == 2) {
                    maze[y][x] = 3;
                    maze[y + 1][x] = 3;
                    System.out.println("win");
                    break;
                } else if(maze[y+1][x] == 1) {
                    Position newPsotion = new Position(y+1, x);
                    path.push(newPsotion);

                    if(visited.contains(newPsotion)){
                        maze[y][x] = 1;
                    } else {
                        maze[y][x] = 3;
                    }
                    visited.add(newPsotion);
                    continue;
                }
            }

            //right
            if(isValid(maze,y,x+1)) {
                if(maze[y][x+1] == 2 ) {
                    maze[y][x] = 3;
                    maze[y][x+1] = 3;
                    System.out.println("win");
                    break;
                } else if(maze[y][x+1] == 1 && isValid(maze,y,x+1)) {
                    Position newPsotion = new Position(y, x+1);
                    path.push(newPsotion);

                    if(visited.contains(newPsotion)){
                        maze[y][x] = 1;
                    } else {
                        maze[y][x] = 3;
                    }
                    visited.add(newPsotion);
                    continue;
                }
            }

            //left
            if(isValid(maze,y,x+1)) {
                if(maze[y][x-1] == 2 ) {
                    maze[y][x] = 3;
                    maze[y][x-1] = 3;
                    System.out.println("win");
                    break;
                } else if(maze[y][x-1] == 1 && isValid(maze, y,x-1)) {
                    Position newPsotion = new Position(y, x-1);
                    path.push(newPsotion);

                    if(visited.contains(newPsotion)){
                        maze[y][x] = 1;
                    } else {
                        maze[y][x] = 3;
                    }
                    System.out.println("left");
                    visited.add(newPsotion);
                    continue;
                }
            }

            //up
            if(isValid(maze ,y-1,x)) {
                if(maze[y-1][x] == 2 ) {
                    maze[y][x] = 3;
                    maze[y-1][x] = 3;
                    System.out.println("win");
                    break;
                } else if(maze[y-1][x] == 1) {
                    Position newPsotion = new Position(y-1, x);
                    path.push(new Position(y-1, x));
                    path.push(newPsotion);

                    if(visited.contains(newPsotion)){
                        maze[y][x] = 1;
                    } else {
                        maze[y][x] = 3;
                    }
                    System.out.println("up");
                    continue;
                }
            }

            //backtrack
            //remove the first element
            path.pop();

            // if path size is zero
            if(path.size() <= 0) {
                System.out.println("no path");
                break;
            }
        }
        return maze;
    }
    public static boolean isValid (int[][] maze , int y, int x) {
        if(y< 0 || y >= maze.length ||x < 0 ||x >= maze[y].length ) {
            return false;
        } else {
            return true;
        }
    }

}
