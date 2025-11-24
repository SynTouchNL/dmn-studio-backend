package nl.syntouch.dmn.studio.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Table(name = "environments")
@Entity
public class Environment extends PanacheEntityBase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonManagedReference("deployments")
    @OneToMany(mappedBy = "deployedTo", fetch = FetchType.LAZY)
    private List<Deployment> deployments;
}
