package nl.syntouch.util;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
//import nl.syntouch.models.DMN;
//import nl.syntouch.models.Domain;
//import nl.syntouch.models.Environment;
//import nl.syntouch.models.DMNVersion;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.stream.Stream;

public class devInitializer {

    void onStartup(@Observes StartupEvent startupEvent) {
//        String[] environments = {"Test", "Acceptatie", "Productie"};
//        for (String environment : environments) {
//            Environment env = new Environment();
//            env.setName(environment);
//            env.persist();
//        }
//
//        String[] domains = {"Sociaal Domein", "Fysiek Domein", "Algemeen Domein"};
//        for (String domain : domains) {
//            Domain dom = new Domain();
//            dom.setName(domain);
//            dom.persist();
//        }
//
//        String[] dmns = { "Wet IIT", "Wet ABC", "Wet" };
//        String[] owners = { "Jan", "Mark", "Jorn"};
//        List<Domain> tmpdoms = Domain.listAll();
//        Iterator<Domain> doms = tmpdoms.iterator();
//        for (int i = 0; i < dmns.length; i++) {
//            DMN dmn = new DMN();
//            dmn.setName(dmns[i]);
//            dmn.setOwner(owners[i]);
//            dmn.setDomain( doms.next());
//            dmn.setVersions();
//            dmn.persist();
//        }
//
//        DMNVersion dmnVersion = new DMNVersion();
//        dmnVersion.setDmn();
//
//

    }

    //private static String dmn1;
}
