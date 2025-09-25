package nl.syntouch.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "domains")
@Entity
public class Domain extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "domain")
    @JsonBackReference
    List<DMN> dmns;

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

    public List<DMN> getDmns() {
        return dmns;
    }

    public void setDmns(List<DMN> dmns) {
        this.dmns = dmns;
    }
}
