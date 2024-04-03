package com.interchange.repository;

import com.interchange.entities.ImageRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRoomRepository extends JpaRepository<ImageRoom, Integer> {
    ImageRoom findByRoom_RoomId(int roomId);
}
