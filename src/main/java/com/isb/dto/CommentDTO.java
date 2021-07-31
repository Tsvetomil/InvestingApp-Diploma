package com.isb.dto;

import com.isb.model.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class CommentDTO {
    private String owner;
    private String message;
    private String timestamp;


    public static CommentDTO toDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setMessage(comment.getMessage());
        commentDTO.setOwner(comment.getOwner());

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        commentDTO.setTimestamp(comment.getTimestamp().format(format));

        return commentDTO;
    }
}
