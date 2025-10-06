package nl.syntouch.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "dmns")
@Entity
public class DMN extends PanacheEntityBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45, nullable = false)
    private String owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_id", nullable = false)
    private Domain domain;

    @OneToMany (mappedBy = "dmn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DMNVersion> versions;

    public Integer getId() { return id;}
    public void setId(Integer id) { this.id = id; }

    public List<DMNVersion> getVersions() { return versions; }
    public void setVersions(List<DMNVersion> versions) { this.versions = versions; }

    public Domain getDomain() { return domain; }
    public void setDomain(Domain domain) { this.domain = domain; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
}
