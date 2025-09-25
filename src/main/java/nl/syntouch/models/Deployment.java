package nl.syntouch.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.Instant;

@Table(name = "deployments")
@Entity
public class Deployment extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private DMNVersion dmnVersion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Environment deployedTo;
    private String deployedBy; //TODO Later connect to user?
    private Instant deployedTime = Instant.now();
}
