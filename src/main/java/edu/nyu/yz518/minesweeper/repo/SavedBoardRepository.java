package edu.nyu.yz518.minesweeper.repo;

import java.util.List;

public interface SavedBoardRepository {
    int saveBoardState(String json);

    List<Integer> getBoardIds();

    String getBoard(int bid);
}
