package nl.syntouch.dmn.studio.repository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import nl.syntouch.dmn.studio.model.Comment;

@ApplicationScoped
public class CommentRepository implements PanacheRepository<Comment> {

}
