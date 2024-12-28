package com.songnguyen.example05.Service.Impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.songnguyen.example05.Entity.Address;
import com.songnguyen.example05.Entity.Role;
import com.songnguyen.example05.Entity.User;
import com.songnguyen.example05.Repository.AddressRepo;
import com.songnguyen.example05.Repository.RoleRepo;
import com.songnguyen.example05.Repository.UserRepo;
import com.songnguyen.example05.Service.UserService;
import com.songnguyen.example05.config.AppConstants;
import com.songnguyen.example05.exceptions.APIException;
import com.songnguyen.example05.exceptions.ResourceNotFoundException;
import com.songnguyen.example05.payloads.AddressDTO;
import com.songnguyen.example05.payloads.UserDTO;
import com.songnguyen.example05.payloads.UserResponse;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private AddressRepo addressRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDTO registerUser(UserDTO userDTO){
        try{
            User user=modelMapper.map(userDTO,User.class);
            Role role=roleRepo.findById(AppConstants.USER_ID).get();
            user.getRoles().add(role);
            String country=userDTO.getAddress().getCountry();
            String state=userDTO.getAddress().getState();
            String city=userDTO.getAddress().getCity();
            String pincode=userDTO.getAddress().getPincode();
            String street=userDTO.getAddress().getStreet();
            String buildingName=userDTO.getAddress().getBuildingName();
            Address address= addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country, state, city, pincode, street, buildingName);
            if(address==null){
                address=new Address(country, state, city, pincode, street, buildingName);
                address=addressRepo.save(address);
            }
            user.setAddresses(List.of(address));
            User registerUser=userRepo.save(user);
            userDTO = modelMapper.map(registerUser,UserDTO.class);
            userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(),AddressDTO.class));
            return userDTO;
        }catch(DataIntegrityViolationException e){
            throw new APIException("User already exists with email:"+userDTO.getEmail());
        }
    }
    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
       Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending()
            :Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<User> pageUsers=userRepo.findAll(pageDetails);
        List<User> users=pageUsers.getContent();
        if(users.size()==0){
            throw new APIException("No User exists");

        }
        List<UserDTO> userDTOs=users.stream().map(user->{
            UserDTO dto=modelMapper.map(user,UserDTO.class);
            if(user.getAddresses().size()!=0){
                dto.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(),AddressDTO.class));
            }
            return dto;
        }).collect(Collectors.toList());
        UserResponse userResponse= new UserResponse();
        userResponse.setContent(userDTOs);
        userResponse.setPageNumber(pageUsers.getNumber());
        userResponse.setPageSize(pageUsers.getSize());
        userResponse.setTotalElements(pageUsers.getTotalElements());
        userResponse.setTotalPages(pageUsers.getTotalPages());
        userResponse.setLastPage(pageUsers.isLast());
        return userResponse;
    }
    @Override
    public UserDTO getUserById(Long userId) {
       User user=userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
        UserDTO userDTO=modelMapper.map(user,UserDTO.class);
        userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(),AddressDTO.class));
        return userDTO;
    }
    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
       User user=userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
        String encodePass=passwordEncoder.encode(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encodePass);
        if(userDTO.getAddress()!=null){
            String country=userDTO.getAddress().getCountry();
            String state=userDTO.getAddress().getState();
            String city=userDTO.getAddress().getCity();
            String pincode=userDTO.getAddress().getPincode();
            String street=userDTO.getAddress().getStreet();
            String buildingName=userDTO.getAddress().getBuildingName();
            Address address=addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country, state, city, pincode, street, buildingName);
            if(address==null){
                address= new Address(country, state, city, pincode, street, buildingName);
                addressRepo.save(address);
                user.setAddresses(List.of(address));
            }
        }
        userDTO=modelMapper.map(user,UserDTO.class);
        userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(),AddressDTO.class));
        return userDTO;
    }
    @Override
    public String deleteUser(Long userId) {
        User user=userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
        userRepo.delete(user);
        return "User with userId" +userId+"deleted successfully";
    }
    
}
