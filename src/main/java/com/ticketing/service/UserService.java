package com.ticketing.service;

import com.ticketing.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> listAllUsers();

    UserDTO findByUserName(String username);

    void save(UserDTO dto);

    UserDTO update(UserDTO dto);

    void delete(String username);

    //    Find Manager
    List<UserDTO> listAllByRole(String role);

}
