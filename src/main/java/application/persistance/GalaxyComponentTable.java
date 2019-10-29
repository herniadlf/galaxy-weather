package application.persistance;

import javax.persistence.*;

@Entity
@Table(name = "GALAXY_COMPONENT")
public class GalaxyComponentTable {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "NAME", nullable = false)
    private String name;
}
