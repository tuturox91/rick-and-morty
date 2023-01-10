package com.sniklz.springboot.rickandmortyapp.repository;

import com.sniklz.springboot.rickandmortyapp.model.MovieCharacter;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
    List<MovieCharacter> findAllByExternalIdIn(Set<Long> externalId);

    List<MovieCharacter> findAllByNameContains(String namePart);
}
