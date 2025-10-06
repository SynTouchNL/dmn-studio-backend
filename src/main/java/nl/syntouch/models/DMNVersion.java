package nl.syntouch.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import nl.syntouch.models.composites.DMNVersionId;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;

@Entity
@IdClass(DMNVersionId.class)
@Table(name = "versions")
public class DMNVersion extends PanacheEntityBase {

    //    public enum STATUS {
//        CONCEPT, TESTING, APPROVAL, READY, ARCHIVED
//    }
    @Id
    @ManyToOne
    @JoinColumn(name = "dmn_id", nullable = false)
    @JsonBackReference
    public DMN dmn;

    @Id
    public Integer version = 1;

    @Column(name = "status", nullable = false)
    private Integer status = 1;

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

    public static DMNVersion getFile(Integer id, Integer version) {
        DMNVersion result = DMNVersion.find("dmn.id = ?1 and version = ?2", id, version).firstResult();
        if (result == null) {
            throw new IllegalArgumentException("No DMN version found for id " + id + " and version " + version);
        }
        return result;
    }

    public Integer getId() { return dmn.getId(); }

    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }

    public DMN getDmn() { return dmn; }
    public void setDmn(DMN dmn) { this.dmn = dmn; }

    public Integer getStatus() { return status;}
    public void setStatus(Integer status) { this.status = status; }

    public byte[] getFileBlob() { return fileBlob; }
    public void setFileBlob(byte[] fileBlob) { this.fileBlob = fileBlob;}

    public String getModifiedBy() { return modifiedBy; }
    public void setModifiedBy(String modifiedBy) { this.modifiedBy = modifiedBy; }

    public Instant getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(Instant modifiedDate) { this.modifiedDate = modifiedDate; }

    public String getCreatedBy() { return createdBy;}
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public Instant getCreatedDate() { return createdDate; }
    public void setCreatedDate(Instant createdDate) { this.createdDate = createdDate; }
}
