package com.songnguyen.example05.Service;

import com.songnguyen.example05.payloads.UserDTO;
import com.songnguyen.example05.payloads.UserResponse;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    UserResponse getAllUsers(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);
    UserDTO getUserById(Long userId);
    UserDTO updateUser(Long userId,UserDTO userDTO);
    String deleteUser(Long userId);
}
