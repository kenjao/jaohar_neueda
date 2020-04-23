package com.jaohar.neueda.repository;

import com.jaohar.neueda.entity.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Mapper, Long> {

    Mapper findByShortUrl(String shortUrl);
    Mapper findByLongUrl(String longUrl);
}
