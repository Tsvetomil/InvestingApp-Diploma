package com.isb.dto;

import com.isb.model.SubComment;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class SubCommentDTO {
    private long id;
    private String owner;
    private String message;
    private String timestamp;

    public static SubCommentDTO toDTO(SubComment subComment) {
        SubCommentDTO subCommentDTO = new SubCommentDTO();
        subCommentDTO.setId(subComment.getId());
        subCommentDTO.setMessage(subComment.getMessage());
        subCommentDTO.setOwner(subComment.getOwner());


        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        subCommentDTO.setTimestamp(subComment.getTimestamp().format(format));

        return subCommentDTO;
    }
}
