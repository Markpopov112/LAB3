package jav.bot.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(
        name = "users"
)
public class User implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_seq"
    )
    @SequenceGenerator(
            sequenceName = "user_id_seq",
            name = "user_id_seq",
            allocationSize = 1
    )
    private Long id;
    private String username;
    private String password;
    private boolean expired;
    private boolean locked;
    private boolean enabled;
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER
    )
    private List<UserRole> userRoles;

    public User() {
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection)this.userRoles.stream().map(UserRole::getAuthority).collect(Collectors.toList());
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return !this.expired;
    }

    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    public boolean isCredentialsNonExpired() {
        return !this.expired;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<UserRole> getUserRoles() {
        return this.userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
