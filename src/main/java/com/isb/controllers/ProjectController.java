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
import java.io.File;
import java.time.LocalDateTime;
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
        project.setUploadedDate(LocalDateTime.now());
        projectRepository.save(project);

        return new Response(HttpStatus.OK.value(), project);
    }

    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public void delete(@PathVariable(value="id") long id, HttpSession session) throws UserException, NotDeletedException {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if(projectOpt.isPresent()){
            UserDTO user = UserUtils.getUser(session);
            Project project = projectOpt.get();
            if(user.getId() == project.getUserID() || user.isAdmin()){
                ImageUtils.deleteImage(project.getImgName());
                new File(project.getImgName()).delete();
                projectRepository.delete(project);
                return;
            }
        }
        throw new NotDeletedException("Not deleted");
    }

    @GetMapping("/{id}")
    public Project get(@PathVariable(value="id") long id){
        return projectRepository.findById(id).get();
    }

    @GetMapping(value={"/filter/{filter}"})
    public List<Project> getAll(@PathVariable(value="filter")  String filter){
        if(filter.equals("mostRecent")){
            return projectRepository.findAllByOrderByUploadedDateDesc();
        }
        else if(filter.equals("invPriceAsc")){
            return projectRepository.findAllByOrderByToRaiseAsc();
        }
        else if(filter.equals("invPriceDesc")){
            return projectRepository.findAllByOrderByToRaiseDesc();
        }
        return projectRepository.findAll();
    }

    @GetMapping(value={"/search"})
    public List<Project> search(@RequestParam(name = "q") String query){
        return projectRepository.findAllByCaptionLikeIgnoreCase("%" + query + "%");
    }

    @GetMapping(value={"/"})
    public List<Project> getAll(){
        return projectRepository.findAll();
    }

    @GetMapping("/user")
    public List<Project> getAllByID(HttpSession session) throws UserException {
        return projectRepository.getProjectsByUserID(UserUtils.getUser(session).getId());
    }
    @GetMapping("/admin/user/{id}")
    public List<Project> getAllByID(@PathVariable(value="id") long id, HttpSession session) throws UserException {
        if(UserUtils.getUser(session).isAdmin()) {
            return projectRepository.getProjectsByUserID(id);
        }
        return null;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/uploadImage")
    public Response uploadImage(@RequestParam("imageFile") MultipartFile imageFile){
        if(!imageFile.getContentType().toLowerCase().contains("image")){
            return new Response(HttpStatus.BAD_REQUEST.value(), "Please upload an image");
        }
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

    @PostMapping("/edit/{id}")
    public Response edit(@RequestBody Project projectUI, @PathVariable(value="id") long id, HttpSession session) throws UserException {
        Optional<Project> projectOpt = projectRepository.findById(id);
        UserDTO user = UserUtils.getUser(session);
        if(projectOpt.isPresent()){
            Project project = projectOpt.get();
            if(project.getUserID() != user.getId()){
                return new Response(HttpStatus.UNAUTHORIZED.value());
            }
            project.merge(projectUI);
            project.setId(id);
            project.setUserID(user.getId());
            projectRepository.save(project);
        }
        return new Response(HttpStatus.OK.value());
    }

}
