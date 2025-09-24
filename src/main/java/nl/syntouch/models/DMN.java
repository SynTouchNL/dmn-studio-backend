package nl.syntouch.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class DMN extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String name;
    private String owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private Domain domain;

    @OneToMany ( mappedBy = "dmn")
    private List<DMNVersion> versions;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public List<DMNVersion> getVersions() {
        return versions;
    }

    public void setVersions() {
        this.versions = versions;
    }
}
