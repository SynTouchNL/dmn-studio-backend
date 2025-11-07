package nl.syntouch.rest;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import nl.syntouch.models.Comment;

public interface CommentResource extends PanacheEntityResource<Comment, Integer> {
}
