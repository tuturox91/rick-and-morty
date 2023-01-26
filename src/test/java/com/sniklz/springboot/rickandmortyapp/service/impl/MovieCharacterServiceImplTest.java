package com.sniklz.springboot.rickandmortyapp.service.impl;

import com.sniklz.springboot.rickandmortyapp.model.Gender;
import com.sniklz.springboot.rickandmortyapp.model.MovieCharacter;
import com.sniklz.springboot.rickandmortyapp.model.Status;
import com.sniklz.springboot.rickandmortyapp.repository.MovieCharacterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class MovieCharacterServiceImplTest {
    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;
    @Mock
    private MovieCharacterRepository characterRepository;

    private final static MovieCharacter rickCharacter = new MovieCharacter();
    private final static MovieCharacter mortyCharacter = new MovieCharacter();

    @BeforeAll
    static void setUp() {
        rickCharacter.setId(1L);
        rickCharacter.setExternalId(1L);
        rickCharacter.setName("Rick Sanchez");
        rickCharacter.setStatus(Status.ALIVE);
        rickCharacter.setGender(Gender.MALE);

        mortyCharacter.setId(2L);
        mortyCharacter.setExternalId(2L);
        mortyCharacter.setName("Morty Smith");
        mortyCharacter.setStatus(Status.ALIVE);
        mortyCharacter.setGender(Gender.MALE);
    }

    @Test
    void removeExistingCharactersByExternalIdWithOneOfTwoExist() {
        Set<Long> externalIds = new HashSet<>();
        externalIds.add(rickCharacter.getExternalId());
        externalIds.add(mortyCharacter.getExternalId());
        int expectedIdSetSize = 1;
        List<MovieCharacter> characters = List.of(rickCharacter);
        Mockito.doReturn(characters).when(characterRepository).findAllByExternalIdIn(Mockito.any());
        int actualIdsSetSize = movieCharacterService.removeExistingCharacterById(externalIds).size();
        Assertions.assertEquals(expectedIdSetSize, actualIdsSetSize, "Size of id set must be 1");
    }

    @Test
    void removeExistingCharactersByExternalIdWithEmptyDB() {
        Set<Long> externalIds = new HashSet<>();
        externalIds.add(rickCharacter.getExternalId());
        externalIds.add(mortyCharacter.getExternalId());
        int expectedIdSetSize = 2;
        Mockito.doReturn(Collections.emptyList()).when(characterRepository).findAllByExternalIdIn(Mockito.any());
        int actualIdsSetSize = movieCharacterService.removeExistingCharacterById(externalIds).size();
        Assertions.assertEquals(expectedIdSetSize, actualIdsSetSize, "Size of id set must be 2");
    }
}