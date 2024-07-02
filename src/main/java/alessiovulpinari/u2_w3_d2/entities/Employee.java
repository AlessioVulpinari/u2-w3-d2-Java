package alessiovulpinari.u2_w3_d2.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dipendenti")
public class Employee {

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

    public Employee(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.avatarUrl = "https://ui-avatars.com/api/?name=" + this.getFirstName() + "+" + this.getLastName();
    }
}
