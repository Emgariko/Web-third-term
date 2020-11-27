package ru.itmo.wp.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private final int FIELDSIZE = 3;

    public enum Cell {
        X, O
    }

    public static class State {
        private final int size;
        private int filledCellsCount;
        private final Cell[][] cells;
        private String phase;
        private boolean crossesMove;

        public boolean isCrossesMove() {
            return crossesMove;
        }

        public int getSize() {
            return size;
        }

        public Cell[][] getCells() {
            return cells;
        }

        public String getPhase() {
            return phase;
        }

        public State(int size) {
            this.size = size;
            cells = new Cell[size][size];
            crossesMove = true;
            phase = "RUNNING";
            filledCellsCount = 0;
        }

        private void makeMove(int row, int col) {
            if (cells[row][col] != null) {
                return;
            }
            cells[row][col] = crossesMove ? Cell.X : Cell.O;
            filledCellsCount++;
            boolean isWinner = isWinner(row, col);
            if (isWinner) {
                phase = crossesMove ? "WON_X" : "WON_O";
            } else if (filledCellsCount == getSize() * getSize()) {
                phase = "DRAW";
            } else {
                crossesMove = !crossesMove;
            }
        }

        private boolean isWinner(int row, int col) {
            boolean isWinner = checkDirection(row - size + 1, col, 1, 0);
            isWinner |= checkDirection(row, col - size + 1, 0, 1);
            isWinner |= checkDirection(row - size + 1, col - size + 1, 1, 1);
            isWinner |= checkDirection(row - size + 1, col + size - 1, 1, -1);
            return isWinner;
        }

        private boolean checkDirection(int row, int col, int dr, int dc) {
            int curRow = row, curCol = col, equalsCellCount = 0;
            for (int i = 0; i < 2 * size - 1; i++) {
                if (isInField(curRow, curCol)) {
                    if (cells[curRow][curCol] != null && cells[curRow][curCol].equals(crossesMove ? Cell.X : Cell.O)) {
                        equalsCellCount++;
                    } else {
                        equalsCellCount = 0;
                    }
                    if (equalsCellCount == size) {
                        return true;
                    }
                }
                curRow += dr;
                curCol += dc;
            }
            return false;
        }

        private boolean isInField(int row, int col) {
            return (row >= 0 && col >= 0 && row <= size - 1 && col <= size - 1);
        }
    }

    public void action(HttpServletRequest request, Map<String, Object> view) {
        //request.getSession().setAttribute("state", new State());
        State state = null;
        if (request.getSession().getAttribute("state") != null) {
            state = (State) request.getSession().getAttribute("state");
        } else {
            state = new State(FIELDSIZE);
            request.getSession().setAttribute("state", state);
        }
        view.put("state", state);
    }
    public void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = (State) request.getSession().getAttribute("state");
        if (!state.phase.equals("RUNNING")) {
            view.put("state", state);
            return;
        }
        int row = -1, col = -1;
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            if (entry.getValue().length == 1 && entry.getKey().startsWith("cell_")) {
                row = Integer.parseInt(String.valueOf(entry.getKey().charAt(entry.getKey().lastIndexOf("_") + 1)));
                col = Integer.parseInt(String.valueOf(entry.getKey().charAt(entry.getKey().lastIndexOf("_") + 2)));
                break;
            }
        }
        if (row == -1 || col == -1) {
            throw new RuntimeException("Can't find request parameter");
        }
        state.makeMove(row, col);
        view.put("state", state);
    }
    public void newGame(HttpServletRequest request, Map<String, Object> view) {
        State state = new State(FIELDSIZE);
        request.getSession().setAttribute("state", state);
        view.put("state", state);
    }
}
