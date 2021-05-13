package edu.nyu.yz518.minesweeper.repo;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Repository
public class SavedBoardRepositoryImpl implements SavedBoardRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public SavedBoardRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int saveBoardState(String json){
        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("json", json);
        jdbcTemplate.update("insert into boards (json) values (:json)", parameters, holder);
        return Objects.requireNonNull(holder.getKey()).intValue();
    }

    @Override
    public List<Integer> getBoardIds(){
        return jdbcTemplate.query("select bid from boards", (rs, num) -> rs.getInt("bid"));
    }

    @Override
    public String getBoard(int bid){
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("bid", bid);
        return jdbcTemplate.queryForObject("select json from boards where bid = :bid", parameters, String.class);
    }
}
