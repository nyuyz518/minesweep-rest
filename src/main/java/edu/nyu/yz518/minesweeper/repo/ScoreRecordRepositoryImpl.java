package edu.nyu.yz518.minesweeper.repo;

import edu.nyu.yz518.minesweeper.api.PlayerRecord;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ScoreRecordRepositoryImpl implements ScoreRecordRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    ScoreRecordRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public void recordScore(PlayerRecord record){
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("uid", record.getUid()).addValue("score", record.getScore());
        jdbcTemplate.update("insert into scores (uid, score) values (:uid, :score)", parameters);
    }

    @Override
    public List<PlayerRecord> getTopRecords() {
        return jdbcTemplate.query("select uid, score from scores order by score desc limit 5",
                (rs, num) ->  new PlayerRecord(rs.getString("uid"), rs.getInt("score")));
    }
}
