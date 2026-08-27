package com.example.spring_security_brief.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class RoleEntity implements GrantedAuthority {

    @Id
    private String authority;

    public RoleEntity() {
    }
    @Override
    public String getAuthority(){
        return this.authority;
    }
    public void setAuthority(String authority){
        this.authority = authority;
    }

}
