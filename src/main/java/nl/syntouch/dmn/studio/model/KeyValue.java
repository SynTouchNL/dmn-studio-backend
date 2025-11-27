package nl.syntouch.dmn.studio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "key_value_pairs")
@Entity
public class KeyValue extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonBackReference
    private Test test;

    @Column(name = "is_input")
    private Boolean isInput;

    @Column(name = "key_name")
    private String key;

    @Column(name = "value_text")
    private String value;

    @Column(name = "value_type")
    private String valueType = "string";

}
