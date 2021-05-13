package edu.nyu.yz518.minesweeper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.nyu.yz518.minesweeper.api.PlayerRecord;
import edu.nyu.yz518.minesweeper.repo.SavedBoardRepository;
import edu.nyu.yz518.minesweeper.repo.ScoreRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@RestController
public class MinesweeperRestController {
    private final Logger LOG = LoggerFactory.getLogger(MinesweeperRestController.class);
    private final ScoreRecordRepository scoreRecordRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SavedBoardRepository savedBoardRepository;

    public MinesweeperRestController(
            SavedBoardRepository savedBoardRepository,
            ScoreRecordRepository scoreRecordRepository){
        this.savedBoardRepository = savedBoardRepository;
        this.scoreRecordRepository = scoreRecordRepository;
    }

    @GetMapping("/board")
    public List<Integer> getBoardIds(){
        LOG.info("GET /board");
        return savedBoardRepository.getBoardIds();
    }

    @GetMapping("/board/{bid}")
    public String getBoardState(@PathVariable int bid){
        LOG.info("GET /board/" + bid );
        return savedBoardRepository.getBoard(bid);
    }

    @PostMapping("/board")
    public int saveBoardState(@RequestBody String boardState){
        LOG.info("POST /board");
        return savedBoardRepository.saveBoardState(boardState);
    }

    @PostMapping("/score")
    public void recordScore(@RequestBody String body){
        LOG.info("POST /score");
        try {
            PlayerRecord record = objectMapper.readValue(body, PlayerRecord.class);
            scoreRecordRepository.recordScore(record);
        } catch (JsonProcessingException e) {
            LOG.error("Failed to parse json", e);
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/score")
    public List<PlayerRecord> getTopPlayer(){
        LOG.info("GET /score");
        return scoreRecordRepository.getTopRecords();
    }
}
