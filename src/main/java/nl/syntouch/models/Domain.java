package nl.syntouch.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "domains")
@Entity
public class Domain extends PanacheEntityBase {
    @Id @GeneratedValue
    private Integer id;

    @Column(length = 45, nullable = false)
    public String name;

    @OneToMany(mappedBy = "domain", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    public List<DMN> dmns;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<DMN> getDmns() { return dmns; }
    public void setDmns(List<DMN> dmns) { this.dmns = dmns; }
}
