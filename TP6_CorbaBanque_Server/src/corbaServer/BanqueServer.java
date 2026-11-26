
package corbaServer;

import service.BanqueImpl;

import org.omg.CORBA.ORB;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class BanqueServer {

    public static void main(String[] args) {

        try {
            ORB orb = ORB.init(args, null);

            POA rootPOA = POAHelper.narrow(
                    orb.resolve_initial_references("RootPOA")
            );

            rootPOA.the_POAManager().activate();

            BanqueImpl banque = new BanqueImpl();
            org.omg.CORBA.Object ref =
                    rootPOA.servant_to_reference(banque);

            Properties props = new Properties();
            props.put(
                    Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.cosnaming.CNCtxFactory"
            );
            props.put(
                    Context.PROVIDER_URL,
                    "iiop://localhost:900"
            );

            Context context = new InitialContext(props);

            context.rebind("Banque", ref);

            System.out.println("Serveur Banque démarré !");
            System.out.println("Service enregistré sous le nom : Banque");

            orb.run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
