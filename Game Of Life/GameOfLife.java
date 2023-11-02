import java.awt.*;

public class GameOfLife {
    public static void main(String[] args) {
        // Finding how big the board is
        int numCells = Integer.parseInt(args[0]);
        int numCopies = Integer.parseInt(args[1]);

        if (numCells <= 0 || numCopies <= 0 || args.length > 2) {
            System.err.println("There can only be 2 positive arguments");
            System.exit(1);
        }

        // Creating The Board
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, numCells);
        StdDraw.setYscale(0, numCells);
        StdDraw.filledSquare((numCells - 1) / 2,(numCells - 1) / 2, (numCells + 4) / 2);

        int[][] state = new int[numCells][numCells];

        for (int i = 0; i < numCopies; i++) {
            state = blinker(state, ((int) (Math.random() * numCells)), ((int) (Math.random() * numCells)), numCells);
            state = beacon(state, ((int) (Math.random() * numCells)), ((int) (Math.random() * numCells)), numCells);
            state = glider(state, ((int) (Math.random() * numCells)), ((int) (Math.random() * numCells)), numCells);
        }

        draw(state, numCells);

        while (true) {
            int[][] next = newState(state);
            StdDraw.enableDoubleBuffering();
            StdDraw.clear();
            StdDraw.setPenColor(Color.black);
            StdDraw.filledSquare((numCells - 1) / 2,(numCells - 1) / 2, (numCells + 4) / 2);
            draw(next, numCells);
            StdDraw.show();
            StdDraw.pause(100);
            state = next;
        }
    }

    public static int[][] newState(int[][] state) {
        int[][] next = new int[state.length][state.length];
        int rightUp;
        int right;
        int rightDown;
        int leftUp;
        int left;
        int leftDown;
        int up;
        int down;
        int sum;
        for (int y = 0; y < state.length; y++) {
            for (int x = 0; x < state.length; x++) {
                up = state[x][(y + 1) % state.length];
                rightUp = state[(x + 1) % state.length][(y + 1) % state.length];
                right = state[(x + 1) % state.length][y];
                rightDown = state[(x + 1) % state.length][((y - 1) + state.length) % state.length];
                down = state[x][((y - 1) + state.length) % state.length];
                leftDown = state[((x - 1) + state.length) % state.length][((y - 1) + state.length) % state.length];
                left = state[((x - 1) + state.length) % state.length][y];
                leftUp = state[((x - 1) + state.length) % state.length][(y + 1) % state.length];
                sum = up + rightUp + right + rightDown + down + leftDown + left + leftUp;

                if (sum < 2 && state[x][y] == 1){
                    next[x][y] = 0;
                }

                if ((sum == 2 || sum == 3) && state[x][y] == 1){
                    next[x][y] = 1;
                }

                if (state[x][y] == 1 && sum > 3) {
                    next[x][y] = 0;
                }

                if (state[x][y] == 0 && sum == 3) {
                    next[x][y] = 1;
                }
            }
        }
        return next;
    }


    public static int[][] blinker(int[][] cells, int c, int r, int x) {
        cells[c][r] = 1;
        cells[(c + 1) % x][r] = 1;
        cells[((c - 1) + x) % x][r] = 1;
        return cells;
    }

    public static int[][] beacon(int[][] cells, int c, int r, int x) {
        cells[(c + x) % x][r] = 1;
        cells[((c + 1) + x) % x] [r] = 1;
        cells[(c + x) % x] [((r - 1) + x) % x] = 1;

        cells[(c + 2) % x][((r - 3) + x) % x] = 1;
        cells[(c + 3) % x][((r - 3) + x) % x] = 1;
        cells[(c + 3) % x][((r - 2) + x) % x] = 1;
        return cells;
    }

    public static int[][] glider(int[][] cells, int c, int r, int x) {
        cells[c][r] = 1;
        cells[(c + 1) % x][r] = 1;
        cells[(c + 2) % x][r] = 1;
        cells[(c + 2) % x][((r + 1) + x) % x] = 1;
        cells[(c + 1) % x][((r + 2) + x) % x] = 1;
        return cells;
    }

    public static void draw(int[][] cells, int x) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < x; j++) {
                if (cells[i][j] == 1) {
                    StdDraw.setPenColor(Color.CYAN);
                    StdDraw.filledCircle(i + 0.5, j + 0.5, 0.5);
                }
            }
        }
    }
}

