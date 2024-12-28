package com.songnguyen.example05.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.songnguyen.example05.Entity.Role;
@Repository
public interface RoleRepo extends JpaRepository<Role,Long>   {
    
}
