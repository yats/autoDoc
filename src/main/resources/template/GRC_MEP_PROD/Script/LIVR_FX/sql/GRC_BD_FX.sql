/*-------------------------------------------------------------------------------------------*/
/*
/*  Type             : Requete SQL
/*  Nom              : {REF_LIVRAISON}
/*  Auteur           : {CP_PRESTATAIRE}
/*  Création         : {DATE}
/*  Description      : 
/*  Cadre            : 
/*
/*  BD Cibles   : Appliquer sur la base GRC FIXE 
/*  Paramètres  : 
/*
/* 
/*
/*-------------------------------------------------------------------------------------------*/

SET DEFINE OFF;


/* Règles à respecter (Supprimer ce commentaire)  */
/* =====================================================
-  Toujours ajouter le bloc ci-dessous en fin de fichier 
           /
           exit;  
- Encodage UTF-8 
- Retour a la ligne format UNIX 
- Verifier que toutes les tables sont préfixées par grc.
- Vérifier qu'il n y a de commit qu'à la fin du fichier
- Ne jamais ajouter de commentaire de type '--' après les commandes sql (Le script risque de ne pas etre lancé. Utiliser plutôt les commentaires de type /* *\/

- Lancer les traitements dml et ddl sous sqlplus pour validation (Tester sur les bases TIG et INFOCENTRE)
- Vérifier que les demandes DBA (droits sur de nouvelles tables) ont bien été faites
- Vérifier qu'il n y a pas de demande de grant à ajouter 
- Vérifier que toutes les permissions ont été rajoutées (Voir les spéc et le code)
- Verifier qu'il n y a pas de tables ou colonnes déclarées dans les DMLs mais qui n'existe pas encore dans la prod 
 
- Pour exécuter un bloc pl/sql sous sqlPlus, il faut le compléter par un / sur une ligne séparée à la fin pour lancer la compilation et l'exécution. 
- Mettre les blocs pl sql à la fin 
- Eviter de mettre plusieurs bloc pl/sql sur un seul fichier. Mettre plutôt dans des fichiers séparer à lancer ensuite comme suit : @monscript.sql

/* =====================================================

/* Ajouter votre script ICI */


COMMIT;

/

EXIT;