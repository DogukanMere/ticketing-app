package com.ticketing.service;

import com.ticketing.dto.ProjectDTO;

import java.util.List;

public interface ProjectService {

    ProjectDTO getByProjectCode(String code);

    List<ProjectDTO> listAllProjects();

    void save(ProjectDTO dto);

    void update(ProjectDTO dto);

    void delete(String code);


}
