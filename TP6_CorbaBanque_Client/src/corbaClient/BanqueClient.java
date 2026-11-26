
package corbaClient;

import corbaBanque.Compte;
import corbaBanque.IBanqueRemote;
import corbaBanque.IBanqueRemoteHelper;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

public class BanqueClient {

    public static void main(String[] args) {
        try {
            Properties props = new Properties();

            props.put(Context.INITIAL_CONTEXT_FACTORY,
                    "com.sun.jndi.cosnaming.CNCtxFactory");

            props.put(Context.PROVIDER_URL,
                    "iiop://localhost:900");

            Context context = new InitialContext(props);

            org.omg.CORBA.Object obj =
                    (org.omg.CORBA.Object) context.lookup("Banque");

            IBanqueRemote banque =
                    IBanqueRemoteHelper.narrow(obj);

            System.out.println("Connexion au serveur réussie !");

            Compte c1 = new Compte(1001, 1000.0f);
            banque.creeCompte(c1);
            System.out.println("Compte créé.");

            banque.verser(500.0f, 1001);
            System.out.println("Versement effectué.");

            banque.retirer(200.0f, 1001);
            System.out.println("Retrait effectué.");

            Compte compte = banque.getCompte(1001);

            if (compte != null) {
                System.out.println("Code : " + compte.code);
                System.out.println("Solde : " + compte.solde + " DT");
            }
            Compte[] comptes = banque.getComptes();

            System.out.println("\nListe des comptes :");

            for (Compte cpte : comptes) {
                System.out.println("Code : " + cpte.code
                        + " | Solde : " + cpte.solde + " DT");
            }
            double resultat = banque.conversion(100);

            System.out.println("\n100 Euros = " + resultat + " DT");

            context.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
