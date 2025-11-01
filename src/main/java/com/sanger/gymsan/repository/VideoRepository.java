package com.sanger.gymsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {

}
