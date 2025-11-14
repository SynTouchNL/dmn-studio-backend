package nl.syntouch.dmn.studio.model.dto;

public class DMNUpdateFileDTO {
    public byte[] fileBlob; // Base64 encoded
    public String updatedBy;

    public DMNUpdateFileDTO(byte[] fileBlob, String updatedBy) {
        this.fileBlob = fileBlob;
        this.updatedBy = updatedBy;
    }
}
