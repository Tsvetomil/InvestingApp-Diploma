package com.isb.controllers;

import com.isb.dto.UserDTO;
import com.isb.exception.NotDeletedException;
import com.isb.exception.UserException;
import com.isb.model.Project;
import com.isb.repository.ProjectRepository;
import com.isb.utils.UserUtils;
import com.isb.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
public class ProjectController implements IController{

    @Autowired
    private ProjectRepository projectRepository;

    //TODO should return Response with everything
    @PostMapping("/add")
    public Project add(@RequestBody Project project, HttpSession session) throws UserException {
        project.setUserID(UserUtils.getUser(session).getId());
        projectRepository.save(project);

        return project;
    }

    @DeleteMapping("/remove")
    public void delete(@PathVariable long id, HttpSession session) throws UserException, NotDeletedException {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()){
            UserDTO user = UserUtils.getUser(session);
            if(user.getId() == project.get().getUserID()){
                projectRepository.delete(project.get());
                return;
            }
        }
        throw new NotDeletedException("Not deleted");
    }

    @GetMapping("/")
    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile){
        try {
            UtilsService.saveImage(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return "";
    }

}
