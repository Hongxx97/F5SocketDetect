ps -ef|grep weblogic|grep weblogic.Server|grep Dweblogic.Name=AdminServer|grep weblogic.policy |awk '{print $1,$2,$3,$8}'