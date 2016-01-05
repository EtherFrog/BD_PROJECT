create or replace FUNCTION nbEtu (curyear in varchar2, nbEtudiant out varchar2) return varchar2 
is 
 monException EXCEPTION;
 nbEtudiant NUMBER(10);
 BEGIN select count(*) into nbEtudiant from stage 
	where dateStage=curyear; 
	if nbFilms=0 THEN raise monException;
	END if;

return nbEtudiant;
 EXCEPTION WHEN monException
 THEN return 'Aucun etudiant a un stage cette annee';
 END;
 
 create or replace FUNCTION nbEtuAnn (annee in varchar2, nbEtudiant out varchar2) return varchar2 
is 
 monException EXCEPTION;
 nbEtudiant NUMBER(10);
 BEGIN select count(*) into nbEtudiant from stage 
	where  dateStage=annee; 
	if nbFilms=0 THEN raise monException;
	END if;

return nbEtudiant;
 EXCEPTION WHEN monException
 THEN return 'Aucun etudiant a un stage cette annee';
 END;