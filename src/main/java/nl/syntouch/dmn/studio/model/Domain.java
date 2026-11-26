package nl.syntouch.dmn.studio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Getter
@Setter
@Table(name = "domains")
@Entity
public class Domain extends PanacheEntityBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active = true;

    @Column(length = 45, nullable = false)
    private String owner;

    @Column(name = "created_by", length = 45, nullable = false)
    private String createdBy;

    @Column(name = "modified_by", length = 45, nullable = false)
    private String modifiedBy;

    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate = Instant.now();

    @OneToMany(mappedBy = "domain")
    @JsonBackReference
    private List<DMN> dmns;

}
