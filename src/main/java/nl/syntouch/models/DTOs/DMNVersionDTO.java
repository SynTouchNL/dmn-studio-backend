package nl.syntouch.models.DTOs;

import nl.syntouch.models.DMNVersion;

public class DMNVersionDTO {
    public Integer id;
    public Integer version;
    public byte[] fileBlob; // Base64 encoded
    public Integer status;

    public DMNVersionDTO(DMNVersion entity) {
        this.id = entity.getId();
        this.version = entity.getVersion();
        this.fileBlob = entity.getFileBlob();
        this.status = entity.getStatus();
    }
}
