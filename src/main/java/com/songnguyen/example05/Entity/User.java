package com.songnguyen.example05.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    @Size(min=5,max=20,message="First name must be between 5 and 30 characters long")
    @Pattern(regexp="^[a-zA-Z]*$",message="Firts name must contain numbers or special characters")
    private String firstName;
    @Size(min=5,max=20,message="Last name must be between 5 and 30 characters long")
    @Pattern(regexp="^[a-zA-Z]*$",message="Last name must contain numbers or special characters")
    private String lastName;
    @Size(min=10,max=10,message="Mobile number must be exactly 10 digits long")
    @Pattern(regexp="^\\d{10}$",message="Mobile number must contain only Numbers")
    private String mobileNumber;
    @Email
    @Column(unique=true,nullable=false)
    private String email;
    private String password;
    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch=FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles=new HashSet<>();
    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinTable(name="user_address",joinColumns=@JoinColumn(name="user_id"),inverseJoinColumns=@JoinColumn(name="address_id"))
    private List<Address> addresses= new ArrayList<>();

}
