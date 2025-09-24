package nl.syntouch.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Deployment extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @OneToOne
    private DMNVersion dmnVersion;

    //Later connect to user?
    private String deployedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    private Environment deployedTo;

    private Instant deployedTime = Instant.now();
}
