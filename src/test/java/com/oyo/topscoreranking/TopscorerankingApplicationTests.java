package com.oyo.topscoreranking;

import com.oyo.topscoreranking.TopscorerankingApplication;
import com.oyo.topscoreranking.dto.PlayerDto;
import com.oyo.topscoreranking.entity.Player;
import com.oyo.topscoreranking.repository.PlayerRepository;
import com.oyo.topscoreranking.service.ScroreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import com.oyo.topscoreranking.utility.TestUtility;

import javax.persistence.EntityManager;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.json.JSONObject;

import static com.oyo.topscoreranking.helper.Converter.convertToPlayerDTO;
import static com.oyo.topscoreranking.helper.Converter.convertToPlayerEntity;
import static org.hamcrest.Matchers.hasItem;
import  static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TopscorerankingApplication.class )
@AutoConfigureMockMvc
@WithMockUser
class TopscorerankingApplicationTests {

	private static final Integer DEFAULT_ID    = 1;
    private static final Integer DEFAULT_SCORE = 100;
    private static final Integer UPDATED_SCORE = 200;

//    private static final Integer SMALLER_SCORE = 1 - 1;

    private static final DateTimeFormatter FORMATE_DATE_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter FORMATE_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final LocalDateTime DEFAULT_TIME = LocalDateTime.now();
    //LocalDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final LocalDateTime UPDATED_TIME = LocalDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final LocalDateTime SMALLER_TIME = LocalDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_PLAYER = "PlayerX";
    private static final String UPDATED_PLAYER = "PlayerY";

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScroreServiceImpl playerService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MockMvc mockMvc;

    private Player player;


	 /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerDto createDto(EntityManager entityManager) {
        PlayerDto playerDto = new PlayerDto(DEFAULT_PLAYER, DEFAULT_SCORE, DEFAULT_TIME);
        return playerDto;
    }

	 /**
     * Create  an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerDto createUpdatedDto(EntityManager em) {
        PlayerDto playerDto = new PlayerDto(UPDATED_PLAYER, UPDATED_SCORE, UPDATED_TIME);
        return playerDto;
    }

	@BeforeEach
    public void initTest() {
        player =convertToPlayerEntity(createDto(entityManager));
    }

	@Test
    @Transactional
    public void addPlayer() throws Exception {
        int numberOfRecordBefore = playerRepository.findAll().size();
        // Create the Player
        mockMvc.perform(post("/api/oyo/player")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(TestUtility.convertObjectToJsonBytes(player)))
                .andExpect(status().isOk());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(numberOfRecordBefore + 1);
    }

	@Test
    @Transactional
    public void addPlayerWithExistingId() throws Exception {

        int numberOfRecordBefore = playerRepository.findAll().size();

        // Create the player with an existing ID
        player.setId(1);

        // An entity with an existing ID cannot be created, so this API call must fail
        mockMvc.perform(post("/api/oyo/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtility.convertObjectToJsonBytes(player)));

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(numberOfRecordBefore);
    } 

    @Test
    @Transactional
    public void addPlayerWithInValidPlayerName() throws Exception {

        int numberOfRecordBefore = playerRepository.findAll().size();
        player.setPlayer("Player1");

        // An entity with an existing ID cannot be created, so this API call must fail
        mockMvc.perform(post("/api/oyo/player")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtility.convertObjectToJsonBytes(player)))
                .andExpect(status().isBadRequest());

        // Validate the Player in the database
        List<Player> playerList = playerRepository.findAll();
        assertThat(playerList).hasSize(numberOfRecordBefore);
    } 

    @Test
    @Transactional
     void getPlayer() throws Exception {

        String dateTime = "2019-08-20 11:00:55";
        // Get the Player
        mockMvc.perform(get("/api/oyo/player/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
                .andExpect(jsonPath("$.time").value(dateTime))
                .andExpect(jsonPath("$.player").value("PlayerA"));
    }

    @Test
    @Transactional
     void getHistoryByPlayerName() throws Exception {

        String player = "PlayerA";
        // Get the Player
        mockMvc.perform(get("/api/oyo/playerHistory/{player}", player))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @Transactional
     void getAllPlayersListWithOutPlayer() throws Exception {
        
        int testPlayerId = 1;
        String dateTime = "2019-08-20 11:00:55";

        mockMvc.perform(get("/api/oyo/playerList?" + 
        "fromDate="+LocalDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC).format(FORMATE_DATE)
        +"&"+"toDate="+UPDATED_TIME.format(FORMATE_DATE)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.[*].id").value(hasItem(testPlayerId)))
        .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
        .andExpect(jsonPath("$.[*].time").value(hasItem(dateTime)))
        .andExpect(jsonPath("$.[*].player").value(hasItem("PlayerA")));
    }

    @Test
    @Transactional
     void getAllPlayersListWithPlayer() throws Exception {
        
        int testPlayerId = 1;
        String dateTime = "2019-08-20 11:00:55";

        mockMvc.perform(get("/api/oyo/playerList?name=PlayerA&"+
        "fromDate="+LocalDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC).format(FORMATE_DATE)
        +"&"+"toDate="+UPDATED_TIME.format(FORMATE_DATE)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.[*].id").value(hasItem(testPlayerId)))
        .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
        .andExpect(jsonPath("$.[*].time").value(hasItem(dateTime)))
        .andExpect(jsonPath("$.[*].player").value(hasItem("PlayerA")));
    }

    @Test
    @Transactional
     void getAllPlayersListWithPlayerName() throws Exception {
        
        int testPlayerId = 1;
        String dateTime = "2019-08-20 11:00:55";

         mockMvc.perform(get("/api/oyo/playerList?name=PlayerA,PlayerB&"+
        "fromDate="+LocalDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC).format(FORMATE_DATE)
        +"&"+"toDate="+UPDATED_TIME.format(FORMATE_DATE)))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.[*].id").value(hasItem(testPlayerId)))
        .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
        .andExpect(jsonPath("$.[*].time").value(hasItem(dateTime)))
        .andExpect(jsonPath("$.[*].player").value(hasItem("PlayerB")));


    }

    @Test
    @Transactional
     void getAllPlayersListWithInValidDate() throws Exception {

         mockMvc.perform(get("/api/oyo/playerList?name=PlayerA,PlayerB&"+
        "fromDate="+UPDATED_TIME.format(FORMATE_DATE)
        +"&"+"toDate="+LocalDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC).format(FORMATE_DATE)))
        .andExpect(status().isBadRequest());
    }
    
}
