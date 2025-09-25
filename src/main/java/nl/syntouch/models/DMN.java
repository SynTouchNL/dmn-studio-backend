package nl.syntouch.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "dmns")
@Entity
public class DMN extends PanacheEntityBase {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    private Domain domain;

    @OneToMany (mappedBy = "dmn")
    @JsonManagedReference
    private List<DMNVersion> versions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DMNVersion> getVersions() {
        return versions;
    }

    public void setVersions(List<DMNVersion> versions) {
        this.versions = versions;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
