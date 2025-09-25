package nl.syntouch.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import nl.syntouch.models.composites.DMNVersionId;

import java.time.Instant;

@Entity
@IdClass(DMNVersionId.class)
@Table(name = "versions")
public class DMNVersion extends PanacheEntityBase {

//    public enum STATUS {
//        CONCEPT, TESTING, APPROVAL, READY, ARCHIVED
//    }

    @Id
    @Column(name = "version", nullable = false)
    private Long version;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dmn_id", nullable = false)
    @JsonBackReference
    private DMN dmn;

    @Column(name = "status", nullable = false)
    private Long status = 1L;

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name = "file_blob", nullable = false, columnDefinition = "BYTEA")
    @JsonIgnore
    private byte[] file_blob;

    @Column(name = "modified_by")
    private String modifiedBy; // TODO Later connect to user?
    @Column(name = "modified_date")
    private Instant modifiedDate = Instant.now();

    @Column(name = "created_by", nullable = false)
    private String createdBy; // TODO Later connect to user?
    @Column(name = "created_date", nullable = false)
    private Instant createdDate = Instant.now();

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public DMN getDmn() {
        return dmn;
    }

    public void setDmn(DMN dmn) {
        this.dmn = dmn;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public byte[] getFile_blob() {
        return file_blob;
    }

    public void setFile_blob(byte[] file_blob) {
        this.file_blob = file_blob;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
