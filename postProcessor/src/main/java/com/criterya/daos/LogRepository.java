package com.criterya.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.criterya.model.Log;

public interface LogRepository extends JpaRepository<Log, Integer>{
	List<Log> findByVideoRgb(String video_rgb);
}
