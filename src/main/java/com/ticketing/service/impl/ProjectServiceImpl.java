package com.ticketing.service.impl;

import com.ticketing.dto.ProjectDTO;
import com.ticketing.dto.UserDTO;
import com.ticketing.entity.Project;
import com.ticketing.entity.User;
import com.ticketing.enums.Status;
import com.ticketing.mapper.ProjectMapper;
import com.ticketing.mapper.UserMapper;
import com.ticketing.repository.ProjectRepository;
import com.ticketing.service.ProjectService;
import com.ticketing.service.TaskService;
import com.ticketing.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserService userService;
    private final ProjectMapper projectMapper;
    private final TaskService taskService;
    private final UserMapper userMapper;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserService userService, ProjectMapper projectMapper, TaskService taskService, UserMapper userMapper) {
        this.projectRepository = projectRepository;
        this.userService = userService;
        this.projectMapper = projectMapper;
        this.taskService = taskService;
        this.userMapper = userMapper;
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        return projectRepository.findAll().stream().map(projectMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {

        UserDTO currentUserDTO = userService.findByUserName("jack@gmail.com");
        User user = userMapper.convertToEntity(currentUserDTO);
        List<Project> list = projectRepository.findAllByAssignedManager(user);

        return list.stream().map(project -> {
            ProjectDTO obj = projectMapper.convertToDto(project);
            obj.setUnfinishedTaskCounts(taskService.totalNonCompletedTask(project.getProjectCode()));
            obj.setCompleteTaskCounts(taskService.totalCompletedTask(project.getProjectCode()));
            return obj;
        }).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO dto) {
        dto.setProjectStatus(Status.OPEN);
        Project project = projectMapper.convertToEntity(dto);
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());
        Project updatedProject = projectMapper.convertToEntity(dto);

        updatedProject.setId(project.getId());
        updatedProject.setProjectStatus(project.getProjectStatus());

        projectRepository.save(updatedProject);

    }

    @Override
    public void delete(String code) {
        Project project = projectRepository.findByProjectCode(code);

        project.setIsDeleted(true);
        project.setProjectCode(project.getProjectCode() + "-" + project.getId());
        projectRepository.save(project);

        taskService.deleteByProject(projectMapper.convertToDto(project));

    }

    @Override
    public void complete(String code) {

        Project projectData = projectRepository.findByProjectCode(code);

        projectData.setProjectStatus(Status.COMPLETE);
        projectRepository.save(projectData);

        taskService.completeByProject(projectMapper.convertToDto(projectData));

    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
        Project project = projectRepository.findByProjectCode(code);
        return projectMapper.convertToDto(project);
    }
}





















