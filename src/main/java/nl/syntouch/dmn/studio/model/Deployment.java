package nl.syntouch.dmn.studio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.syntouch.dmn.studio.model.composites.DeploymentId;
import org.hibernate.annotations.CreationTimestamp;
import java.time.Instant;

@Getter
@Setter
@Table(name = "deployments")
@Entity
@IdClass(DeploymentId.class)
public class Deployment extends PanacheEntityBase {
    @Id
    private Long id;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "dmn_id", referencedColumnName = "dmn_id"),
            @JoinColumn(name = "dmn_version", referencedColumnName = "version")
    })
    private DMNVersion version;

    @JsonBackReference("deployments")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "environment_id", nullable = false)
    private Environment deployedTo;

    @Column(name = "deployed_by", nullable = false)
    private String deployedBy;

    @CreationTimestamp
    @Column(name = "deployed_date", nullable = false)
    private Instant deployedTime = Instant.now();

    @Column(name = "deployment_ref", nullable = false)
    private String deploymentRef;

}