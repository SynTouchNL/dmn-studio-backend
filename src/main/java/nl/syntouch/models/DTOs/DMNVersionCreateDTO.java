package nl.syntouch.models.DTOs;

public class DMNVersionCreateDTO {
    public Integer dmnId;
    public byte[] fileBlob; // Base64 encoded
    public String createdBy;

    public DMNVersionCreateDTO(Integer dmnId, byte[] fileBlob, String createdBy) {
        this.dmnId = dmnId;
        this.fileBlob = fileBlob;
        this.createdBy = createdBy;
    }
}
