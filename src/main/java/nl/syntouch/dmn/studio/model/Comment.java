package nl.syntouch.dmn.studio.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@Table(name = "comments")
@Entity
public class Comment extends PanacheEntityBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "change_id")
    private Change change;

    private String commenter;

    private String comment;

    private Instant timestamp = Instant.now();
    
}
