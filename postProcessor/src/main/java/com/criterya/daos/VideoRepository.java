package com.criterya.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.criterya.model.Video;

public interface VideoRepository extends JpaRepository<Video, Integer> {

}
