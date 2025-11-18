package nl.syntouch.dmn.studio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name = "key_value_pairs")
@Entity
public class KeyValue extends PanacheEntityBase {

    @Id
    @ManyToOne
    @JsonBackReference
    private Test test;

    private String key;

    private String value;

    private String type = "string";

}
