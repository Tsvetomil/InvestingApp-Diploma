package com.isb.controllers;

import com.isb.dto.CommentDTO;
import com.isb.dto.UserDTO;
import com.isb.exception.UserException;
import com.isb.model.Comment;
import com.isb.model.SubComment;
import com.isb.repository.CommentRepository;
import com.isb.rest.utils.Response;
import com.isb.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController implements IController{

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/add")
    public Response addComment(@RequestBody Comment comment, HttpSession session) throws UserException {
        UserDTO user = UserUtils.getUser(session);
        comment.setUserID(user.getId());
        comment.setOwner(user.getFirstName() + " " + user.getLastName());

        assert comment.getMessage() != null;
        assert !comment.getMessage().isEmpty();
        comment.setTimestamp(LocalDateTime.now());
        commentRepository.save(comment);

        return new Response(HttpStatus.OK.value());
    }

    @GetMapping("/all/{id}")
    public Response getAllCommentForProject(@PathVariable(value="id") long projectID, HttpSession session) throws UserException {
        UserUtils.getUser(session);
        List<Comment> allByProjectID = commentRepository.getAllByProjectIDOrderByTimestampDesc(projectID);
        List<CommentDTO> result = new ArrayList<>();
        for (Comment comment : allByProjectID){
            result.add(CommentDTO.toDTO(comment));
        }

        return new Response(HttpStatus.OK.value(), result);
    }

    @PostMapping("/add/reply/{id}")
    public Response addReply(@RequestBody SubComment reply, @PathVariable(value="id") long parentID, HttpSession session) throws UserException {
        UserDTO user = UserUtils.getUser(session);
        reply.setUserID(user.getId());
        reply.setOwner(user.getFirstName() + " " + user.getLastName());

        assert reply.getMessage() != null;
        assert !reply.getMessage().isEmpty();
        reply.setTimestamp(LocalDateTime.now());

        Comment one = commentRepository.getById(parentID);

        one.getReplies().add(reply);

        commentRepository.save(one);

        return new Response(HttpStatus.OK.value());
    }
}
