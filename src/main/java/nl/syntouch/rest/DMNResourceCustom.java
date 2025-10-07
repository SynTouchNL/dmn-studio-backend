package nl.syntouch.rest;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import nl.syntouch.models.DMN;
import nl.syntouch.models.DMNVersion;
import nl.syntouch.models.DTOs.DMNCreateDTO;
import nl.syntouch.models.DTOs.DMNUpdateFileDTO;
import nl.syntouch.models.DTOs.DMNVersionCreateDTO;
import nl.syntouch.models.DTOs.DMNVersionDTO;
import nl.syntouch.models.Domain;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

@Path("/dmns")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DMNResourceCustom {
    @Authenticated
    @POST
    @Transactional
    public DMN createDMN(DMNCreateDTO dmnDTO) {
        DMN dmn = new DMN();
        dmn.setName(dmnDTO.name);
        dmn.setOwner(dmnDTO.owner);
        Domain domain = Domain.findById(dmnDTO.domainId);
        if (domain == null) {
            throw new NotFoundException("Domain not found");
        }
        dmn.setDomain(domain);
        List<DMNVersion> versions = new ArrayList<>();
        DMNVersion version = new DMNVersion();
        version.setDmn(dmn);
        version.setFileBlob(dmnDTO.fileBlob); // Add fileBlob to DMNCreateDTO
        version.setCreatedBy("Mark Akkermans");
        versions.add(version);
        dmn.setVersions(versions);
        dmn.persist();
        return dmn;
    }

    @Path("/{dmnId}/")
    @Authenticated
    @POST
    @Transactional
    public DMNVersion addVersion(@PathParam("dmnId") Integer dmnId, DMNVersionCreateDTO versionDTO) {
        DMN dmn = DMN.find("id", dmnId).firstResult();
        System.out.println("DMN ID: " + dmnId);
        System.out.println("DMN : " + dmn.toString());
        if (dmn == null) {
            throw new NotFoundException("DMN not found");
        }
        Integer nextVersion = ((Number) DMNVersion.getEntityManager()
                .createQuery("select max(v.version) from DMNVersion v where v.dmn.id = :dmnId")
                .setParameter("dmnId", dmnId)
                .getSingleResult()) == null ? 1 :
                ((Number) DMNVersion.getEntityManager()
                        .createQuery("select max(v.version) from DMNVersion v where v.dmn.id = :dmnId")
                        .setParameter("dmnId", dmnId)
                        .getSingleResult()).intValue() + 1;

        DMNVersion version = new DMNVersion();
        version.setVersion(nextVersion);
        version.setDmn(dmn);
        version.setFileBlob(versionDTO.fileBlob);
        version.setCreatedBy(versionDTO.createdBy);
        version.persist();
        return version;
    }

    @Path("/{dmnId}/{versionId}/file")
    @Authenticated
    @GET
    public DMNVersionDTO getFile(@PathParam("dmnId") Integer dmnId, @PathParam("versionId") Integer versionId) {
        DMNVersion entity = DMNVersion.getFile(dmnId, versionId);
        if (entity == null) {
            throw new NotFoundException("DMN version not found for dmnId " + dmnId + " and versionId " + versionId);
        }
        return new DMNVersionDTO(entity);
    }

    @Path("/{dmnId}/{versionId}/file")
    @Authenticated
    @PUT
    @Transactional
    public DMNUpdateFileDTO updateFile(@PathParam("dmnId") Integer dmnId, @PathParam("versionId") Integer versionId, DMNUpdateFileDTO versionDTO) {
        DMNVersion entity = DMNVersion.getFile(dmnId, versionId);
        if (entity == null) {
            throw new NotFoundException("DMN version not found for dmnId " + dmnId + " and versionId " + versionId);
        }
        entity.setFileBlob(versionDTO.fileBlob);
        entity.setModifiedBy(versionDTO.updatedBy);
        return new DMNUpdateFileDTO(entity.getFileBlob(), entity.getModifiedBy());
    }
}
