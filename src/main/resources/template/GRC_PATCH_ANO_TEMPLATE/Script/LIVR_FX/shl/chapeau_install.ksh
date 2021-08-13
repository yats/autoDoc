#!/bin/ksh


l_date="`date '+%d%m%Y%H%M%S'`"

. ../cfg/config.cfg 

Print_time()
{
        L_DATE="`date '+%d/%m/%Y - %H:%M:%S'`"
        echo " Timing : $1 -- [$$]  ($L_DATE) " >> Timing.verif
}

##########################################################################################################
##### Install FC 485
##########################################################################################################

	ksh ${REP_SHL}/lance_sql_chapeau.ksh > ${REP_LOG}/${l_date}lance_sql_chapeau.log 2>> ${REP_LOG}/${l_date}lance_sql_chapeau.log

	FILE_LOG=${REP_LOG}/${l_date}lance_sql_chapeau.log

	all_ora=`fgrep "ORA-" $FILE_LOG | wc -l`

	result2=`fgrep "ORA-00001" $FILE_LOG | wc -l` #ORA-00001: unique constraint 
	
	result3=`fgrep "ORA-00942" $FILE_LOG | wc -l` #ORA-00942: table or view does not exist
	
	result4=`fgrep "ORA-01432" $FILE_LOG | wc -l` #ORA-01432: public synonym to be dropped does not exist
	
	result5=`fgrep "ORA-00955" $FILE_LOG | wc -l` #ORA-00955: name is already used by an existing object
	
	result6=`fgrep "ORA-01430" $FILE_LOG | wc -l` #ORA-01430: column being added already exists in table
	
	let sum_ora=$(($result2+$result3+$result4+$result5+$result6))

	if (( $sum_ora != $all_ora ))
	then  
			echo "Batch terminé avec erreurs !!!!" 
			echo "veuillez consulter $FILE_LOG"
			exit 1   
	fi
	
	warn=`fgrep Warning $FILE_LOG`
	
	if [ "$warn" ]
	then
			echo "Batch terminé avec erreurs !!!!" 
			echo "veuillez consulter $FILE_LOG"
			exit 1 
	fi
	
	sanity=`fgrep "SANITY TEST KO" $FILE_LOG | wc -l` #SANITY TEST KO
	
	if [ $sanity -gt 0 ]
	then
			echo "Sanity tests en erreur !!!!" 
			echo "veuillez consulter $FILE_LOG"
			exit 1 
	fi
	

echo "Fin du batch avec succès ..."
exit 0