package com.circ.quickchat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.circ.quickchat.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>{

}
