package alessiovulpinari.u2_w3_d2.entities;

import alessiovulpinari.u2_w3_d2.enums.EmployeeRole;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dipendenti")
@JsonIgnoreProperties({"password", "role", "authorities", "enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
public class Employee implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "id_dipendente", nullable = false)
    private UUID employeeId;

    @Column(nullable = false)
    private String username;

    @Column(name = "nome", nullable = false)
    private String firstName;

    @Column(name = "cognome", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(name = "url_avatar", nullable = false)
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    private EmployeeRole role;

    public Employee(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = EmployeeRole.USER;
        this.avatarUrl = "https://ui-avatars.com/api/?name=" + this.getFirstName() + "+" + this.getLastName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
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
        return true;
    }
}
