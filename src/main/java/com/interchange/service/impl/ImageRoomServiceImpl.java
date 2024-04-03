package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.entities.ImageBlog;
import com.interchange.entities.ImageRoom;
import com.interchange.entities.Project;
import com.interchange.entities.Room;
import com.interchange.repository.ImageRoomRepository;
import com.interchange.repository.ProjectRepository;
import com.interchange.repository.RoomRepository;
import com.interchange.service.ImageRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageRoomServiceImpl extends BaseResponse  implements ImageRoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ImageRoomRepository imageRoomRepository;

    private ImageRoom convertFile(MultipartFile multipartFile) {
        ImageRoom imageRoom = new ImageRoom();
        try {
            imageRoom.setFileName(multipartFile.getOriginalFilename());
            imageRoom.setContentType(multipartFile.getContentType());
            imageRoom.setContent(multipartFile.getBytes());
            return imageRoom;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> save(Project project, MultipartFile[] multipartFiles) {
        for (int i = 0; i < multipartFiles.length; i++) {
            ImageRoom imageRoom = convertFile(multipartFiles[i]);
            Room room = project.getRooms().get(i);
            if(room.getImageRoom() != null) {
                int fileId = room.getImageRoom().getFileId();
                room.setImageRoom(null);
                imageRoomRepository.deleteById(fileId);
                roomRepository.flush();
                imageRoomRepository.flush();
                imageRoom.setRoom(room);
            }
            else {
                imageRoom.setRoom(room);
            }
            imageRoomRepository.save(imageRoom);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> getImageRoomByRoomId(int roomId) {
        return getResponseEntityRoomFile(roomRepository.findById(roomId).get().getImageRoom());
    }

    @Override
    public ResponseEntity<?> isRoomHasImage(int roomId) {
        boolean flag = imageRoomRepository.findByRoom_RoomId(roomId) != null;
        return getResponseEntity(flag);
    }

}
