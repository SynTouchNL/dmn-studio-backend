package nl.syntouch.dmn.studio.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Table(name = "change_log")
@Entity
public class Change extends PanacheEntityBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "dmn_id", referencedColumnName = "dmn_id"),
            @JoinColumn(name = "version", referencedColumnName = "version")
    })
    private DMNVersion version;

    @Column(name = "description", nullable = false)
    private String changeDescription;

    @Column(name = "submitted_by", nullable = false)
    private String submittedBy;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "timestamp", nullable = false)
    private Instant submittedAt = Instant.now();

}
