package com.hackerearth.connectfourgameapi.repository;

import com.hackerearth.connectfourgameapi.models.entity.GameData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDataRepository extends JpaRepository<GameData,String> {
}
