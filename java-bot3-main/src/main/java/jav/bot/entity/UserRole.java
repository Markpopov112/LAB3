package jav.bot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(
        name = "user_roles"
)
public class UserRole {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_id_seq"
    )
    @SequenceGenerator(
            sequenceName = "role_id_seq",
            name = "role_id_seq",
            allocationSize = 1
    )
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToOne
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    public UserRole() {
    }

    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(this.role.name());
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
