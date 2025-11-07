package nl.syntouch.rest.custom;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import nl.syntouch.models.DMN;
import nl.syntouch.models.DMNVersion;
import nl.syntouch.models.DTOs.*;
import nl.syntouch.models.Domain;

import java.util.ArrayList;
import java.util.List;

@Authenticated
@Path("/dmns")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class DMNResourceCustom {
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
        version.setCreatedBy("Mark Akkermans"); //TODO managed by Keycloak
        versions.add(version);
        dmn.setVersions(versions);
        dmn.persist();
        return dmn;
    }

    @Path("/{dmnId}/")
    @POST
    @Transactional
    public DMNVersion addVersion(@PathParam("dmnId") Integer dmnId, DMNVersionCreateDTO versionDTO) {
        DMN dmn = DMN.find("id", dmnId).firstResult();

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

    @Path("/{dmnId}/{version}/")
    @PUT
    @Transactional
    public Response updateVersion(@PathParam("dmnId") Integer dmnId, @PathParam("version") Integer versionId, DMNVersionUpdateDTO versionDTO) {
        DMNVersion dmn = DMNVersion.find("dmn.id = ?1 and version = ?2", dmnId, versionId).firstResult();

        if (dmn == null) {
            throw new NotFoundException("DMN not found");
        }

        if (dmn.getStatus() >= 4) {
            throw new BadRequestException("Cannot update a DMN version that is production or archived.");
        }

        dmn.setStatus(versionDTO.status);
        dmn.setModifiedBy(versionDTO.modifiedBy);
        dmn.persist();
        return Response.status(Response.Status.OK).entity(dmn).build();
    }

    @Path("/{dmnId}/{versionId}/file")
    @GET
    public DMNVersionDTO getFile(@PathParam("dmnId") Integer dmnId, @PathParam("versionId") Integer versionId) {
        DMNVersion entity = DMNVersion.getFile(dmnId, versionId);

        if (entity == null) {
            throw new NotFoundException("DMN version not found for dmnId " + dmnId + " and versionId " + versionId);
        }

        return new DMNVersionDTO(entity);
    }

    @Path("/{dmnId}/{versionId}/file")
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
