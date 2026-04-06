package com.galaxybck.model.entity;

import com.galaxybck.model.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "GLX_USER_ACCOUNT")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer id;

    @Column(name = "USER_NAME", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "USER_EMAIL", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "USER_PASSWORD", nullable = false, length = 255)
    private String password;

    @Column(name = "USER_ACTIVE")
    private Boolean active = true;

    @Column(name = "USER_DATE_CREATED")
    private LocalDateTime dateCreated;

    @Column(name = "DATE_UPDATED")
    private LocalDateTime dateUpdated;

    @Column(name = "CREATED_BY", length = 100)
    private String createdBy;

    @OneToOne(mappedBy = "user")
    private ClientEntity clientEntity;

    @OneToOne(mappedBy = "user")
    private Rider rider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    } // 👈 usa o campo active!
}
