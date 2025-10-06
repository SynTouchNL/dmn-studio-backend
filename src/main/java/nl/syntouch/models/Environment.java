package nl.syntouch.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "environments")
@Entity
public class Environment extends PanacheEntityBase {

    @Id @GeneratedValue
    private Integer id;
    private String name;

    @OneToMany( mappedBy = "deployedTo" )
    @JsonManagedReference
    private List<Deployment> deployments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
