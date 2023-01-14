package com.sniklz.springboot.rickandmortyapp.dto.mapper;

import com.sniklz.springboot.rickandmortyapp.dto.CharacterResponseDto;
import com.sniklz.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import com.sniklz.springboot.rickandmortyapp.model.Gender;
import com.sniklz.springboot.rickandmortyapp.model.MovieCharacter;
import com.sniklz.springboot.rickandmortyapp.model.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class MovieCharacterMapperTest {

    private static MovieCharacterMapper characterMapper;

    @BeforeAll
    public static void init() {
        characterMapper = new MovieCharacterMapper();
    }


    @Test
    public void DtoToCharacter_OK() {

        ApiCharacterDto apiCharacterDto = new ApiCharacterDto();
        apiCharacterDto.setName("Rick Sanchez");
        apiCharacterDto.setId(1L);
        apiCharacterDto.setStatus(Status.ALIVE.toString());
        apiCharacterDto.setGender(Gender.MALE.toString());

        MovieCharacter resCharacter = characterMapper.parseApiCharacterResponseDto(apiCharacterDto);
        assertEquals(null, resCharacter.getId());
        assertEquals(apiCharacterDto.getGender(), resCharacter.getGender().toString());
        assertEquals(apiCharacterDto.getName(), resCharacter.getName());
        assertEquals(apiCharacterDto.getStatus(), resCharacter.getStatus().toString());
    }

    @Test
    public void CharacterToDTO_OK() {

        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setName("Rick Sanchez");
        movieCharacter.setId(1L);
        movieCharacter.setStatus(Status.ALIVE);
        movieCharacter.setGender(Gender.MALE);

        CharacterResponseDto responseDto = characterMapper.toResponseDto(movieCharacter);
        assertEquals(movieCharacter.getId(), responseDto.getId());
        assertEquals(movieCharacter.getGender(), responseDto.getGender());
        assertEquals(movieCharacter.getName(), responseDto.getName());
        assertEquals(movieCharacter.getStatus(), responseDto.getStatus());
    }
}