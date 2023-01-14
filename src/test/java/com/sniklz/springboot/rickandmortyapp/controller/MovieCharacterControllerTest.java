package com.sniklz.springboot.rickandmortyapp.controller;

import com.sniklz.springboot.rickandmortyapp.model.Gender;
import com.sniklz.springboot.rickandmortyapp.model.MovieCharacter;
import com.sniklz.springboot.rickandmortyapp.model.Status;
import com.sniklz.springboot.rickandmortyapp.service.MovieCharacterService;
import java.util.List;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MovieCharacterControllerTest {

    @MockBean
    private MovieCharacterService characterService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }


    @Test
    public void getRandom() {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setId(1L);
        movieCharacter.setExternalId(1L);
        movieCharacter.setName("Rick Sanchez");
        movieCharacter.setGender(Gender.MALE);
        movieCharacter.setStatus(Status.ALIVE);
        Mockito.when(characterService.getRandomCharacter()).thenReturn(movieCharacter);

        RestAssuredMockMvc.when()
                .get("/movie-characters/random")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("externalId", Matchers.equalTo(1))
                .body("name", Matchers.equalTo("Rick Sanchez"))
                .body("status", Matchers.equalTo(Status.ALIVE.toString()))
                .body("gender", Matchers.equalTo(Gender.MALE.toString()));

    }

    @Test
    void findAllByName() {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setId(1L);
        movieCharacter.setExternalId(1L);
        movieCharacter.setName("Rick Sanchez");
        movieCharacter.setGender(Gender.MALE);
        movieCharacter.setStatus(Status.ALIVE);

        MovieCharacter movieCharacterSecond = new MovieCharacter();
        movieCharacterSecond.setId(8L);
        movieCharacterSecond.setExternalId(8L);
        movieCharacterSecond.setName("Adjudicator Rick");
        movieCharacterSecond.setGender(Gender.MALE);
        movieCharacterSecond.setStatus(Status.DEAD);

        MovieCharacter movieCharacterThird= new MovieCharacter();
        movieCharacterThird.setId(15L);
        movieCharacterThird.setExternalId(15L);
        movieCharacterThird.setName("Alien Rick");
        movieCharacterThird.setGender(Gender.MALE);
        movieCharacterThird.setStatus(Status.UNKNOWN);

        List<MovieCharacter> movieCharacterList = List.of(movieCharacter,
                movieCharacterSecond, movieCharacterThird);

        String name = "Rick";

       Mockito.when(characterService.findAllByNameContains(name)).thenReturn(movieCharacterList);

       RestAssuredMockMvc
               .given()
               .queryParam("name", name)
               .when()
               .get("/movie-characters/by-name")
               .then()
               .statusCode(200)
               .body("[0].id", Matchers.equalTo(1))
               .body("[1].id", Matchers.equalTo(8))
               .body("[2].id", Matchers.equalTo(15))
               .body("[0].externalId", Matchers.equalTo(1))
               .body("[1].externalId", Matchers.equalTo(8))
               .body("[2].externalId", Matchers.equalTo(15))
               .body("[0].name", Matchers.equalTo("Rick Sanchez"))
               .body("[1].name", Matchers.equalTo("Adjudicator Rick"))
               .body("[2].name", Matchers.equalTo("Alien Rick"))
               .body("[0].status", Matchers.equalTo(Status.ALIVE.toString()))
               .body("[1].status", Matchers.equalTo(Status.DEAD.toString()))
               .body("[2].status", Matchers.equalTo(Status.UNKNOWN.toString()))
               .body("[0].gender", Matchers.equalTo(Gender.MALE.toString()))
               .body("[1].gender", Matchers.equalTo(Gender.MALE.toString()))
               .body("[2].gender", Matchers.equalTo(Gender.MALE.toString()));
    }
}