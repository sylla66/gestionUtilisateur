package perso.free.time.userManagement.entities;


import jakarta.persistence.*;
import lombok.*;
import perso.free.time.userManagement.TypeDeRole;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "libelle", nullable  = false)
    @Enumerated(EnumType.STRING)
    private TypeDeRole libelle;


}
