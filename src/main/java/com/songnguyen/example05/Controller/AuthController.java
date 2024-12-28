package com.songnguyen.example05.Controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.songnguyen.example05.Service.UserService;
import com.songnguyen.example05.exceptions.UserNotFoundException;
import com.songnguyen.example05.payloads.LoginCrendentials;
import com.songnguyen.example05.payloads.UserDTO;
import com.songnguyen.example05.Security.JWTUtil;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "E-commerce Application")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerHandler(@Valid @RequestBody UserDTO user) throws
    UserNotFoundException{
        String encodePass=passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePass);
        UserDTO userDTO=userService.registerUser(user);
        String token=jwtUtil.generateToken(userDTO.getEmail());
        return new ResponseEntity<Map<String, Object>>(Collections.singletonMap("jwt-token", token),HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public Map<String,Object> loginHandler(@Valid @RequestBody LoginCrendentials crendentials){
        UsernamePasswordAuthenticationToken authCrendentials= new UsernamePasswordAuthenticationToken(crendentials.getEmail(), crendentials.getPassword());
        authenticationManager.authenticate(authCrendentials);
        String token=jwtUtil.generateToken(crendentials.getEmail());
        return Collections.singletonMap("jwt-token", token);
    }
}
