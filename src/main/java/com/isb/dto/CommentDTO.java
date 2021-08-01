package com.isb.dto;

import com.isb.model.Comment;
import com.isb.model.SubComment;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CommentDTO {
    private long id;
    private String owner;
    private String message;
    private String timestamp;
    private List<SubCommentDTO> replies;


    public static CommentDTO toDTO(Comment comment){
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setMessage(comment.getMessage());
        commentDTO.setOwner(comment.getOwner());


        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        commentDTO.setTimestamp(comment.getTimestamp().format(format));

        List<SubCommentDTO> result = new ArrayList<>();
        for(SubComment subComment : comment.getReplies()){
            result.add(SubCommentDTO.toDTO(subComment));
        }
        commentDTO.setReplies(result);

        return commentDTO;
    }
}
