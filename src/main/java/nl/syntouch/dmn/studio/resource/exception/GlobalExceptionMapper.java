package nl.syntouch.dmn.studio.resource.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.syntouch.dmn.studio.model.dto.ApiErrorResponse;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.time.Instant;
import java.util.List;

public class GlobalExceptionMapper {
    private static final Logger LOG = Logger.getLogger(GlobalExceptionMapper.class);

    @ServerExceptionMapper
    public Response mapWebApplicationException(WebApplicationException exception,
                                               ContainerRequestContext requestContext) {
        int status = exception.getResponse().getStatus();
        return response(status, reasonPhrase(status), exception.getMessage(), requestContext, List.of());
    }

    @ServerExceptionMapper
    public Response mapConstraintViolationException(ConstraintViolationException exception,
                                                    ContainerRequestContext requestContext) {
        List<ApiErrorResponse.Violation> violations = exception.getConstraintViolations().stream()
                .map(violation -> new ApiErrorResponse.Violation(
                        fieldPath(violation.getPropertyPath().toString()),
                        violation.getMessage()
                ))
                .toList();
        return response(
                Response.Status.BAD_REQUEST.getStatusCode(),
                Response.Status.BAD_REQUEST.getReasonPhrase(),
                "Validation failed",
                requestContext,
                violations
        );
    }

    @ServerExceptionMapper
    public Response mapUnhandledException(Exception exception, ContainerRequestContext requestContext) {
        LOG.error("Unhandled request exception", exception);
        return response(
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected error occurred",
                requestContext,
                List.of()
        );
    }

    private Response response(int status,
                              String error,
                              String message,
                              ContainerRequestContext requestContext,
                              List<ApiErrorResponse.Violation> violations) {
        ApiErrorResponse body = new ApiErrorResponse(
                Instant.now(),
                status,
                error,
                message,
                requestContext.getUriInfo().getRequestUri().getPath(),
                violations
        );
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(body)
                .build();
    }

    private String reasonPhrase(int status) {
        Response.Status knownStatus = Response.Status.fromStatusCode(status);
        return knownStatus == null ? "HTTP " + status : knownStatus.getReasonPhrase();
    }

    private String fieldPath(String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
    }
}
