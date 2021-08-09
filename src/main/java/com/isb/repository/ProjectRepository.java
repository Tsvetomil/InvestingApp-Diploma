package com.isb.repository;

import com.isb.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project getProjectByImgName(String imgName);
    List<Project> getProjectsByUserID(long id);
    List<Project> findAllByOrderByUploadedDateDesc();
    List<Project> findAllByOrderByToRaiseAsc();
    List<Project> findAllByOrderByToRaiseDesc();
    List<Project> findAllByCaptionLikeIgnoreCase(String like);
}
