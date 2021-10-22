package com.circ.quickchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.circ.quickchat.entity.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

}
