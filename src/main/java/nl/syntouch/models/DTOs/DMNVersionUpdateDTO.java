package nl.syntouch.models.DTOs;

public class DMNVersionUpdateDTO {
    public Integer status;
    public String modifiedBy;

    public DMNVersionUpdateDTO(Integer status, String modifiedBy, byte[] fileBlob) {
        this.status = status;
        this.modifiedBy = modifiedBy;
    }
}
