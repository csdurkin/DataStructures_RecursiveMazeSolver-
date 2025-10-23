# Data Structures: Recursive Maze Solver 

Implements a recursive, backtracking maze solver in Java using a 2D button grid. Supports: (1) boolean path existence (`findMazePath`), (2) enumeration of all paths as coordinate lists (`findAllMazePaths` via `findMazePathStackBased`), and (3) shortest-path extraction (`findMazePathMin`). Includes a Swing GUI (`MazeTest`) for interactive editing and solving.

---

## Overview
This project demonstrates recursion and backtracking on a two-dimensional grid. Cells toggle between BACKGROUND and NON_BACKGROUND; a solver explores neighbors, marks PATH for successful traversal and TEMPORARY for dead ends, and backtracks when necessary. A stack-based trace records coordinates for all valid paths, which can then be analyzed to produce the shortest path.

Color legend (from `GridColors`):
- PATH = green
- BACKGROUND = white
- NON_BACKGROUND = red
- TEMPORARY = black
- ABNORMAL = NON_BACKGROUND

---

## File Layout
```
DataStructures_RecursiveMazeSolver/
  Maze/
    GridColors.java
    Maze.java
    MazeTest.java
    PairInt.java
    TwoDimGrid.java
  README.md
```

Each source uses `package Maze;`, so place the `.java` files under a `Maze/` directory relative to your project root.

---

## Build and Run
From the project root:
```bash
# Compile (creates ./out directory and compiles package Maze)
javac -d out Maze/*.java

# Run the GUI (prompts for rows/cols if no file is given)
java -cp out Maze.MazeTest
```

### Loading a bitmap file
`MazeTest` also accepts an optional text file argument to initialize the grid:
```bash
# Example: run with a bitmap file (lines of '0' and '1', same row length)
java -cp out Maze.MazeTest path/to/bitmap.txt
```
- Use `'1'` for cells initialized as NON_BACKGROUND (candidate path cells), `'0'` for BACKGROUND.
- The program sets cells with `'1'` to NON_BACKGROUND (red) on load.

---

## Usage Notes (GUI)
1. Start the app and enter the number of rows and columns, or pass a bitmap file.
2. Click cells to toggle between BACKGROUND (white) and NON_BACKGROUND (red).
3. Click **SOLVE** to run a solver variant (uncomment the desired block in `MazeTest.solve()`):
   - `findMazePath()` shows existence by coloring PATH and TEMPORARY cells on the grid.
   - `findAllMazePaths(0, 0)` returns all solutions as ordered coordinate lists.
   - `findMazePathMin(0, 0)` returns the shortest path as an ordered coordinate list.
4. Click **RESET** to restore the grid colors.
5. The start is `(0,0)`; the goal is `(getNCols()-1, getNRows()-1)`.

---

## Public API

### `Maze`
```java
public class Maze implements GridColors {
  public Maze(TwoDimGrid m);

  // Problem 1: path existence with coloring and backtracking
  public boolean findMazePath();          // wrapper; calls (0,0)
  public boolean findMazePath(int x, int y);

  // Problem 2: all paths via stack-based backtracking
  public ArrayList<ArrayList<PairInt>> findAllMazePaths(int x, int y);
  public void findMazePathStackBased(int x, int y,
      ArrayList<ArrayList<PairInt>> result, Stack<PairInt> trace);

  // Problem 3: shortest path among all paths
  public ArrayList<PairInt> findMazePathMin(int x, int y);

  // Utilities
  public void resetTemp(); // TEMPORARY -> BACKGROUND
  public void restore();   // PATH/NON_BACKGROUND -> BACKGROUND
}
```

### `PairInt`
```java
public class PairInt {
  public PairInt(int x, int y);
  public int getX();
  public int getY();
  public void setX(int x);
  public void setY(int y);
  public boolean equals(Object p);
  public String toString();
  public PairInt copy();
}
```

### `TwoDimGrid`
- Swing `JPanel` grid of `JButton`s with color-aware helpers:
  - `getNCols()`, `getNRows()`
  - `getColor(int x, int y)`
  - `recolor(int x, int y, Color c)`
  - `recolor(char[][] bitMap, Color aColor)`
  - `recolor(Color tempColor, Color newColor)`

### `GridColors`
- Declares color constants used by the solver and grid.

### `MazeTest`
- `main` launches the GUI and optionally loads a bitmap file.
- `solve()` contains commented sections you can toggle for the variant you want to demo.

---

## Algorithm Notes
- `findMazePath(int x, int y)`:
  - Rejects out-of-bounds or non-NON_BACKGROUND cells.
  - If at the exit, paints PATH and returns true.
  - Otherwise, paint PATH, recursively try four neighbors; if none succeed, paint TEMPORARY and backtrack.
- `findAllMazePaths` / `findMazePathStackBased`:
  - Use a stack (`trace`) to record the current path.
  - On reaching the exit, copy the trace into `result`.
  - Mark visited cells as PATH before recursion and restore to NON_BACKGROUND after exploring to allow alternative routes.
- `findMazePathMin`:
  - Builds on `findAllMazePaths` to return the shortest coordinate sequence.

---

## Complexity
Let `R = getNRows()`, `C = getNCols()`, and `N = R*C`.
- Path existence: worst-case O(N) visits with constant-time neighbor checks per cell.
- Enumerating all paths: exponential in branching factor and path length in the worst case.
- Shortest path selection over `k` solutions: O(k) comparisons after enumeration.

---

## Example Bitmap
```
01000
01110
00010
01110
00001
```
This initializes the grid with a potential corridor along the 1s (NON_BACKGROUND).

---

## Notes
- Start is top-left `(0,0)`; goal is bottom-right `(C-1,R-1)`.
- Color semantics must remain consistent across solvers to keep GUI state meaningful.
