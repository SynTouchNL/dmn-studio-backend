package nl.syntouch.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Environment extends PanacheEntityBase {
    /** TODO
     *  Connectivity info of Camunda instances
     */

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;

    @OneToMany
    private List<Deployment> deployments;

    public List<Deployment> getDeployments() {
        return deployments;
    }

    public void setDeployments(List<Deployment> deployments) {
        this.deployments = deployments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
