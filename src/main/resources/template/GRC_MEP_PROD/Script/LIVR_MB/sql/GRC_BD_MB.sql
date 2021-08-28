/*-------------------------------------------------------------------------------------------*/
/*
/*  Type             : Requete SQL
/*  Nom              : {REF_LIVRAISON}
/*  Auteur           : {CP_PRESTATAIRE}
/*  Cr�ation         : {DATE}
/*  Description      : 
/*  Cadre            : 
/*
/*  BD Cibles   : Appliquer sur la base  MOBILE
/*  Param�tres  : 
/*
/* 
/*
/*-------------------------------------------------------------------------------------------*/

SET DEFINE OFF;

/* R�gles � respecter (Supprimer ce commentaire)  */
/* =====================================================
-  Toujours ajouter le bloc ci-dessous en fin de fichier 
           /
           exit;  
- Encodage UTF-8 
- Retour a la ligne format UNIX 
- Verifier que toutes les tables sont pr�fix�es par grc.
- V�rifier qu'il n y a de commit qu'� la fin du fichier
- Ne jamais ajouter de commentaire de type '--' apr�s les commandes sql (Le script risque de ne pas etre lanc�. Utiliser plut�t les commentaires de type /* *\/

- Lancer les traitements dml et ddl sous sqlplus pour validation (Tester sur les bases TIG et INFOCENTRE)
- V�rifier que les demandes DBA (droits sur de nouvelles tables) ont bien �t� faites
- V�rifier qu'il n y a pas de demande de grant � ajouter 
- V�rifier que toutes les permissions ont �t� rajout�es (Voir les sp�c et le code)
- Verifier qu'il n y a pas de tables ou colonnes d�clar�es dans les DMLs mais qui n'existe pas encore dans la prod 
 
- Pour ex�cuter un bloc pl/sql sous sqlPlus, il faut le compl�ter par un / sur une ligne s�par�e � la fin pour lancer la compilation et l'ex�cution. 
- Mettre les blocs pl sql � la fin 
- Eviter de mettre plusieurs bloc pl/sql sur un seul fichier. Mettre plut�t dans des fichiers s�parer � lancer ensuite comme suit : @monscript.sql

/* =====================================================

/* Ajouter votre script ICI */

COMMIT;

/

EXIT;