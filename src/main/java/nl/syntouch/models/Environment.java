package nl.syntouch.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "environments")
@Entity
public class Environment extends PanacheEntityBase {
    // TODO Connectivity info of Camunda instances

    @Id @GeneratedValue
    private Long id;
    private String name;

    @OneToMany( mappedBy = "deployedTo" )
    private List<Deployment> deployments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Deployment> getDeployments() {
        return deployments;
    }

    public void setDeployments(List<Deployment> deployments) {
        this.deployments = deployments;
    }
}
