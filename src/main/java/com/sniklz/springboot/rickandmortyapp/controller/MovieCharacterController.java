package com.sniklz.springboot.rickandmortyapp.controller;

import com.sniklz.springboot.rickandmortyapp.dto.CharacterResponseDto;
import com.sniklz.springboot.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.sniklz.springboot.rickandmortyapp.service.MovieCharacterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {

    private final MovieCharacterService characterService;
    private final MovieCharacterMapper mapper;

    public MovieCharacterController(MovieCharacterService characterService,
                                    MovieCharacterMapper mapper) {
        this.characterService = characterService;
        this.mapper = mapper;
    }

    @GetMapping("/random")
    @ApiOperation("Get random movie character")
    public CharacterResponseDto getRandom() {
        return mapper.toResponseDto(characterService.getRandomCharacter());
    }

    @GetMapping("/by-name")
    @ApiOperation("Get all movie characters with a query by their name")
    public List<CharacterResponseDto> findAllByName(
            @RequestParam("name")
            @ApiParam(value = "Name or part of name") String namePart) {
        return characterService.findAllByNameContains(namePart)
                .stream()
                .map(mapper::toResponseDto)
                .collect(Collectors.toList());
    }
}
