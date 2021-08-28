#!/bin/ksh


l_date="`date '+%d%m%Y%H%M%S'`"

. ../cfg/config.cfg 

Print_time()
{
        L_DATE="`date '+%d/%m/%Y - %H:%M:%S'`"
        echo " Timing : $1 -- [$$]  ($L_DATE) " >> Timing.verif
}

Lancer_execution()
{
        sqlplus -s ${USER}/${PASS}@${ORACLE_SID} @${REP_SQL}/sql_chapeau.sql
		sqlplus -s ${USER}/${PASS}@${ORACLE_SID} @${REP_SQL}/sql_sanity.sql
}
Print_time "--------------------------------------------------------------------"
Print_time "--------------------------------------------------------------------"
Print_time "--------------------------------------------------------------------"
Print_time "--------------------------------------------------------------------"
Print_time "--------------------------------------------------------------------"
echo "Lancement sql_chapeau.sql ..."
Print_time "Lancement sql_chapeau.sql..."
Print_time "-----------------------------------------"

Lancer_execution

Print_time "Fin de sql_chapeau.sql.."
echo "Fin de sql_chapeau.sql"
Print_time "-----------------------------------------"