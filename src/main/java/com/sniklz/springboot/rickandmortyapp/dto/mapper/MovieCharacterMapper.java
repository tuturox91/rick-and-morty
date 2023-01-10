package com.sniklz.springboot.rickandmortyapp.dto.mapper;

import com.sniklz.springboot.rickandmortyapp.dto.CharacterResponseDto;
import com.sniklz.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import com.sniklz.springboot.rickandmortyapp.model.Gender;
import com.sniklz.springboot.rickandmortyapp.model.MovieCharacter;
import com.sniklz.springboot.rickandmortyapp.model.Status;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper {

    public MovieCharacter parseApiCharacterResponseDto(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setName(dto.getName());
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setExternalId(dto.getId());

        return movieCharacter;
    }

    public CharacterResponseDto toResponseDto(MovieCharacter movieCharacter) {
        CharacterResponseDto responseDto = new CharacterResponseDto();
        responseDto.setId(movieCharacter.getId());
        responseDto.setExternalId(movieCharacter.getExternalId());
        responseDto.setName(movieCharacter.getName());
        responseDto.setStatus(movieCharacter.getStatus());
        responseDto.setGender(movieCharacter.getGender());

        return responseDto;
    }

}
