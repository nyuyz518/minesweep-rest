package edu.nyu.yz518.minesweeper.repo;

import edu.nyu.yz518.minesweeper.api.PlayerRecord;

import java.util.List;

public interface ScoreRecordRepository {
    void recordScore(PlayerRecord record);

    List<PlayerRecord> getTopRecords();
}
