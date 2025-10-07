package nl.syntouch.models.DTOs;

public class DMNCreateDTO {
    public String name;
    public String owner;
    public Integer domainId;
    public byte[] fileBlob; // Base64 encoded
    public String createdBy;

    public DMNCreateDTO(String name, String owner, Integer domainId, byte[] fileBlob, String createdBy) {
        this.name = name;
        this.owner = owner;
        this.domainId = domainId;
        this.fileBlob = fileBlob;
        this.createdBy = createdBy;
    }
}
