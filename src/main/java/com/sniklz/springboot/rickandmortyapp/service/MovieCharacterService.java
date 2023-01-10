package com.sniklz.springboot.rickandmortyapp.service;

import com.sniklz.springboot.rickandmortyapp.model.MovieCharacter;
import java.util.List;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandomCharacter();

    List<MovieCharacter> findAllByNameContains(String namePart);
}
