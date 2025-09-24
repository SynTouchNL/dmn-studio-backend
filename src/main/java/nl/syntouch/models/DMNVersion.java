package nl.syntouch.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.Instant;


@Entity
public class DMNVersion extends PanacheEntityBase {

    public enum STATUS {
        CONCEPT, TESTING, APPROVAL, READY
    }

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private DMN dmn;

    private STATUS status;
    @Lob @Basic(fetch = FetchType.LAZY)
    private byte[] file_blob;

    // TODO Later connect to user?
    private String modifiedBy;
    private Instant modifiedDate = Instant.now();

    // TODO Later connect to user?
    private String createdBy;
    private Instant createdDate = Instant.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DMN getDmn() {
        return dmn;
    }

    public void setDmn(DMN dmn) {
        this.dmn = dmn;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
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
