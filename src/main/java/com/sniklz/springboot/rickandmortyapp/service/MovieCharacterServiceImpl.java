package com.sniklz.springboot.rickandmortyapp.service;

import com.sniklz.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import com.sniklz.springboot.rickandmortyapp.dto.external.ApiResponseDto;
import com.sniklz.springboot.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.sniklz.springboot.rickandmortyapp.model.MovieCharacter;
import com.sniklz.springboot.rickandmortyapp.repository.MovieCharacterRepository;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {

    private final HttpClient httpClient;
    private final MovieCharacterRepository characterRepository;
    private final MovieCharacterMapper mapper;

    public MovieCharacterServiceImpl(HttpClient httpClient,
                                     MovieCharacterRepository characterRepository,
                                     MovieCharacterMapper mapper) {
        this.httpClient = httpClient;
        this.characterRepository = characterRepository;
        this.mapper = mapper;
    }

    @PostConstruct
    @Scheduled(cron = "0 8 * * * ?")
    @Override
    public void syncExternalCharacters() {
        log.info("SyncExternalCharacters method was invoked at" + LocalDateTime.now());
        ApiResponseDto apiResponseDto = httpClient.get("https://rickandmortyapi.com/api/character",
                ApiResponseDto.class);

        saveDtosToDB(apiResponseDto);

        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtosToDB(apiResponseDto);
        }

    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long count = characterRepository.count();

        long randomId = (long) (Math.random() * count);
        return characterRepository.getReferenceById(randomId);
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return characterRepository.findAllByNameContains(namePart);
    }

    void saveDtosToDB(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingCharacters =
                characterRepository.findAllByExternalIdIn(externalIds);

        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters
                .stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));

        Set<Long> existingIds = existingCharactersWithIds.keySet();

        externalIds.removeAll(existingIds);

        List<MovieCharacter> charactersToSave = externalIds
                .stream()
                .map(i -> mapper.parseApiCharacterResponseDto(externalDtos.get(i)))
                .collect(Collectors.toList());

        characterRepository.saveAll(charactersToSave);
    }
}
