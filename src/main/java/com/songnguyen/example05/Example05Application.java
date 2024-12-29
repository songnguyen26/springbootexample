package com.songnguyen.example05;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.songnguyen.example05.Entity.Role;
import com.songnguyen.example05.Repository.RoleRepo;
import com.songnguyen.example05.config.AppConstants;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@SecurityScheme(name = "E-commerce Application",scheme = "Bearer",type = SecuritySchemeType.HTTP,
in = SecuritySchemeIn.HEADER)
public class Example05Application implements CommandLineRunner {
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(Example05Application.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	@Override
	public void run(String... args) throws Exception {
		try{
			Role adminRole= new Role();
			adminRole.setRoleId(AppConstants.ADMIN_ID);
			adminRole.setRoleName("ADMIN");
			Role userRole=new Role();
			userRole.setRoleId(AppConstants.USER_ID);
			userRole.setRoleName("USER");
			List<Role> roles=List.of(adminRole,userRole);
			List<Role> savedRoless=roleRepo.saveAll(roles);
			savedRoless.forEach(System.out::println);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
