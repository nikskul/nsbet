package com.project.nsbet.repository;

import com.project.nsbet.model.Avatar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, String> {
    
    public Avatar findByName(String name);
    
}