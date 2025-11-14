package nl.syntouch.dmn.studio.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;


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


    public String getDecisionName() {
        return decisionName;
    }

    public void setDecisionName(String decisionName) {
        this.decisionName = decisionName;
    }

    public DMNVersion getDmnVersion() {
        return dmnVersion;
    }

    public void setDmnVersion(DMNVersion dmnVersion) {
        this.dmnVersion = dmnVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }
}
