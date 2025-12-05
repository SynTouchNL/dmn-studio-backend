package nl.syntouch.dmn.studio.service;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import nl.syntouch.dmn.studio.model.Change;
import nl.syntouch.dmn.studio.model.Comment;
import nl.syntouch.dmn.studio.model.DMNVersion;
import nl.syntouch.dmn.studio.model.dto.ReviewDTO;
import nl.syntouch.dmn.studio.model.dto.SubmissionDTO;
import nl.syntouch.dmn.studio.repository.ChangeRepository;
import nl.syntouch.dmn.studio.repository.CommentRepository;
import nl.syntouch.dmn.studio.repository.DmnVersionRepository;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Transactional
@RequiredArgsConstructor
@ApplicationScoped
public class LifecycleService {
    private final SecurityIdentity identity;
    private final DmnVersionRepository dmnVersionRepository;
    private final ChangeRepository changeRepository;
    private final CommentRepository commentRepository;

    public Change getPendingReview(Long dmnId, Long versionNumber) {
        return changeRepository.find(
                "select c from Change c " +
                        "join fetch c.version v " +
                        "where v.dmn.id = ?1 and v.version = ?2 and c.approved = false",
                dmnId, versionNumber
        ).firstResult();
    }

    public Change handleSubmission(Long dmnId, Long version, SubmissionDTO submissionDTO) {
        DMNVersion dmnVersion = dmnVersionRepository.find("dmn.id = ?1 and version = ?2", dmnId, version).firstResult();
        Change newChange = createChange(submissionDTO, dmnVersion);
        dmnVersion.setStatus(3); // Set status to "Under Review"
        dmnVersion.setModifiedBy(identity.getPrincipal().getName());
        dmnVersion.persist();
        return newChange;
    }

    public void cancelSubmission(Long dmnId, Long version, Long changeId) {
        DMNVersion dmnVersion = dmnVersionRepository.find("dmn.id = ?1 and version = ?2", dmnId, version).firstResult();
        Change change = changeRepository.find("id = ?1", changeId).firstResult();
        if (dmnVersion != null) {
            if(change != null){
                change.delete();
                dmnVersion.setStatus(1); // Set status back to Draft
                dmnVersion.persist();
            }
        }
        return;
    }

    public Change handleReview(Long dmnId, Long version, Long changeId, ReviewDTO reviewDTO) throws EntityNotFoundException {
        Change modifiedChange = modifyChange(reviewDTO, changeId);
        if(reviewDTO.approved()) {
            DMNVersion approvedVersion = dmnVersionRepository.find("dmn.id = ?1 and version = ?2", dmnId, version).firstResult();
            DMNVersion previousVersion = dmnVersionRepository.find("dmn.id = ?1 and status = 4 and version != ?2", dmnId, version).firstResult();
            if(approvedVersion != null){
                approvedVersion.setStatus(4); // Approved
                approvedVersion.persist();
            } else {
                throw new EntityNotFoundException("Approved version does not exist.");
            }
            if(previousVersion != null){
                previousVersion.setStatus(5); // Archived
                previousVersion.persist();
            }
        }
        return modifiedChange;
    }

    private Change createChange(SubmissionDTO submissionDTO, DMNVersion dmnVersion) {
        Change change = new Change();
        change.setVersion(dmnVersion);
        change.setChangeDescription(submissionDTO.changeDescription());
        change.setAssignedTo(submissionDTO.assignedTo());
        change.setSubmittedBy(identity.getPrincipal().getName());
        change.setApproved(false);
        change.persist();
        return change;
    }
    private Change modifyChange(ReviewDTO reviewDTO, Long changeId) {
        Change reviewedChange = changeRepository.find("id = ?1", changeId).firstResult();
        reviewedChange.setApproved(reviewDTO.approved());
        reviewedChange.setSubmittedAt(Instant.now());
        reviewedChange.persist();
        return reviewedChange;
    }

}
