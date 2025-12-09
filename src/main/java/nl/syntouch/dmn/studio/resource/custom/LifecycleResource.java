package nl.syntouch.dmn.studio.resource.custom;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import nl.syntouch.dmn.studio.model.dto.ReviewDTO;
import nl.syntouch.dmn.studio.model.dto.SubmissionDTO;
import nl.syntouch.dmn.studio.service.LifecycleService;
import nl.syntouch.dmn.studio.service.UnitTestService;

import java.io.IOException;

@Authenticated
@Path("/lifecycle")
@ApplicationScoped
@AllArgsConstructor
@Produces("application/json")
@Consumes("application/json")
public class LifecycleResource {
    private final LifecycleService lifecycleService;

    @GET
    @Path("/{dmnId}/{version}/review")
    public Response getPendingReview(@PathParam("dmnId") Long dmnId, @PathParam("version") Long version) {
        return Response.ok(lifecycleService.getPendingReview(dmnId, version)).build();
    }

    @POST
    @Path("/{dmnId}/{version}/submit")
    public Response submitForReview(@PathParam("dmnId") Long dmnId, @PathParam("version") Long version, SubmissionDTO submissionDTO) { // TODO: define proper DTO\
        if (lifecycleService.getPendingReview(dmnId, version) == null) {
            return Response.ok(lifecycleService.handleSubmission(dmnId, version, submissionDTO)).build();
        } else {
            return Response.status(Response.Status.CONFLICT).entity("There is already a pending review for this DMN version.").build();
        }
    }

    @DELETE
    @Path("/{dmnId}/{version}/{changeId}/cancel")
    public Response cancelSubmission(@PathParam("dmnId") Long dmnId, @PathParam("version") Long version, @PathParam("changeId") Long changeId) {
        lifecycleService.cancelSubmission(dmnId, version, changeId);
        return Response.noContent().build();
    }

    @POST
    @Path("/{dmnId}/{version}/{changeId}/review")
    public Response reviewComplete(@PathParam("dmnId") Long dmnId, @PathParam("version") Long version, @PathParam("changeId") Long changeId, ReviewDTO reviewDTO) { // TODO: define proper DTO
        return Response.ok(lifecycleService.handleReview(dmnId, version, changeId, reviewDTO)).build();
    }
}
