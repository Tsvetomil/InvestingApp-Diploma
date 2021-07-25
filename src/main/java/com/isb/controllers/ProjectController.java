package com.isb.controllers;

import com.isb.dto.UserDTO;
import com.isb.exception.NotDeletedException;
import com.isb.exception.UserException;
import com.isb.model.Project;
import com.isb.repository.ProjectRepository;
import com.isb.rest.utils.Response;
import com.isb.utils.UserUtils;
import com.isb.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/add")
    public Response add(@RequestBody Project project, HttpSession session) throws UserException {
        project.setUserID(UserUtils.getUser(session).getId());
        projectRepository.save(project);

        return new Response(HttpStatus.OK.value(), project);
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
    public Response uploadImage(@RequestParam("imageFile") MultipartFile imageFile){
        String uniqueImgName = ImageUtils.getUniqueImgName(imageFile.getOriginalFilename());
        try {
            ImageUtils.saveImage(imageFile, uniqueImgName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        Project project = projectRepository.getProjectByImgName(imageFile.getOriginalFilename());
        project.setImgName(uniqueImgName);
        projectRepository.save(project);

        return new Response(HttpStatus.OK.value());
    }

}
