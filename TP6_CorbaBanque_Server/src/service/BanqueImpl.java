
package service;

import corbaBanque.Compte;
import corbaBanque.IBanqueRemotePOA;

import java.util.ArrayList;
import java.util.List;

public class BanqueImpl extends IBanqueRemotePOA {

    private List<Compte> comptes = new ArrayList<>();

    private static final double TAUX_EURO_DT = 3.35;

    @Override
    public void creeCompte(Compte cpte) {

        if (cpte == null || cpte.solde < 0) {
            System.out.println("Compte invalide !");
            return;
        }

        if (getCompte(cpte.code) != null) {
            System.out.println("Ce compte existe déjà !");
            return;
        }

        comptes.add(cpte);

        System.out.println("Compte créé avec succès !");
    }
    @Override
    public void verser(float mt, int code) {

        Compte cpte = getCompte(code);

        if (cpte == null) {
            System.out.println("Compte introuvable !");
            return;
        }

        if (mt <= 0) {
            System.out.println("Montant invalide !");
            return;
        }

        cpte.solde += mt;

        System.out.println("Versement effectué avec succès !");
    }
    @Override
    public void retirer(float mt, int code) {

        Compte cpte = getCompte(code);

        if (cpte == null) {
            System.out.println("Compte introuvable !");
            return;
        }

        if (mt <= 0 || mt > cpte.solde) {
            System.out.println("Retrait impossible !");
            return;
        }

        cpte.solde -= mt;

        System.out.println("Retrait effectué avec succès !");
    }
    @Override
    public Compte getCompte(int code) {

        for (Compte cpte : comptes) {
            if (cpte.code == code) {
                return cpte;
            }
        }

        return null;
    }
    @Override
    public Compte[] getComptes() {

        return comptes.toArray(new Compte[comptes.size()]);
    }

    @Override
    public double conversion(double mt) {

        if (mt < 0) {
            return -1;
        }

        return mt * TAUX_EURO_DT;
    }
}
