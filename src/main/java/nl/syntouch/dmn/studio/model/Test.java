package nl.syntouch.dmn.studio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Table(name = "unittests")
@Entity
public class Test extends PanacheEntityBase {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "decision_name", nullable = false)
    private String decisionName;

    @Column(name = "title", nullable = false, length = 256)
    private String title;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "dmn_id", referencedColumnName = "dmn_id"),
            @JoinColumn(name = "version", referencedColumnName = "version")
    })
    @JsonBackReference
    private DMNVersion dmnVersion;

    private Boolean passed;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<KeyValue> values;
}
