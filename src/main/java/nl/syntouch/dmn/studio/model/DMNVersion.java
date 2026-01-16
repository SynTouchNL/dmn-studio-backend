package nl.syntouch.dmn.studio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.syntouch.dmn.studio.model.composites.DMNVersionId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;

@Getter
@Setter
@Entity
@IdClass(DMNVersionId.class)
@Table(name = "versions")
public class DMNVersion extends PanacheEntityBase {
    @Id
    @ManyToOne
    @JoinColumn(name = "dmn_id", nullable = false)
    @JsonBackReference
    private DMN dmn;

    @Id
    @Column(name = "version", nullable = false)
    private Long version = 1L;

    @Column(name = "status", nullable = false)
    private Long status = 1L;

    //@Lob //@Basic(fetch = FetchType.LAZY) // TODO revisit use of @Lob, because it causes Long error.
    @Column(name = "file_blob", nullable = false, columnDefinition = "BYTEA")
    @JsonIgnore
    private byte[] fileBlob;

    @Column(name = "modified_by")
    private String modifiedBy; // TODO Later connect to user?
    @UpdateTimestamp
    @Column(name = "modified_date")
    private Instant modifiedDate;

    @Column(name = "created_by", nullable = false)
    private String createdBy; // TODO Later connect to user?
    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private Instant createdDate = Instant.now();
}

