package nl.syntouch.dmn.studio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Table(name = "environments")
@Entity
public class Environment extends PanacheEntityBase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(length = 2048)
    private String url;

    private String username;

    @JsonIgnore
    @Column(name = "password_encrypted", length = 1024)
    private String passwordEncrypted;

    @Column(nullable = false)
    private boolean active = true;

    @JsonIgnore
    @Column(nullable = false)
    private boolean internal = false;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @JsonManagedReference("deployments")
    @OneToMany(mappedBy = "deployedTo", fetch = FetchType.LAZY)
    private List<Deployment> deployments;
}
