package nl.syntouch.dmn.studio.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "environments")
@Entity
public class Environment extends PanacheEntityBase {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @JsonManagedReference("deployments")
    @OneToMany(mappedBy = "deployedTo", fetch = FetchType.LAZY)
    private List<Deployment> deployments;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name;}
    public void setName(String name) { this.name = name; }

    public List<Deployment> getDeployments() { return deployments; }
    public void setDeployments(List<Deployment> deployments) { this.deployments = deployments; }
}
