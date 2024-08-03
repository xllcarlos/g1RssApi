package com.g1RssApi.repositories;

import com.g1RssApi.models.NoticeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeModel, Long> {

    Optional<NoticeModel> findById(Long id);
    Optional<NoticeModel> findByTitleAndPubDate(String name, Date pubDate);

}
