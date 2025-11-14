package nl.syntouch.dmn.studio.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "unittests")
@Entity
public class Test extends PanacheEntityBase {
    @Id
    public String decisionName;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "dmn_id", referencedColumnName = "dmn_id"),
            @JoinColumn(name = "version", referencedColumnName = "version")
    })
    @JsonManagedReference
    public DMNVersion dmnVersion;

    public String title;

    public Boolean passed;
}
