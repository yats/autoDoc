/* Formatted on 12/04/2016 11:07:25 (QP5 v5.215.12089.38647) */
SET SERVEROUTPUT ON

DECLARE
   L_SANITY          VARCHAR2 (10) := 'OK';
   L_SANITY_GLOBAL   VARCHAR2 (10) := 'OK';
BEGIN
  
   
   /*-------------------------------------------------------------------------------------------------------------------------------------------*/
   /*-------------   FIN SANITY TEST   ---------------------------------------------------------------------------------------------------------*/
   /*-------------------------------------------------------------------------------------------------------------------------------------------*/

   DBMS_OUTPUT.PUT_LINE ('*********************************');

   IF L_SANITY_GLOBAL = 'OK'
   THEN
      DBMS_OUTPUT.PUT_LINE ('SANITY TEST OK');
   ELSE
      DBMS_OUTPUT.PUT_LINE ('SANITY TEST KO');
   END IF;

   DBMS_OUTPUT.PUT_LINE ('*********************************');
END;
/

EXIT;