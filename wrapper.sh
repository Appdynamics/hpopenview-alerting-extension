#!/bin/bash -e

DATE=`date`
# log file when the script is executed
OUTFILE="ibm-tbsm-alert.log"

. ./appd_alert.sh  "$@"
echo "$SEVERITY"
echo "$APP_NAME"
echo "$HEALTH_RULE_NAME"
echo "$AFFECTED_ENTITY_NAME"
echo "$SUMMARY_MESSAGE"
echo "HRV= $IS_HRV"
echo "POLICY_TYPE= $POLICY_TYPE"


if [ "$IS_HRV" -eq 1 ] && ([[ $POLICY_TYPE == POLICY_CLOSE* ]] || [[ $POLICY_TYPE == POLICY_CANCEL* ]]);
then
	SEVERITY="NORMAL"
elif [ "$SEVERITY" == "WARN" ];
then
	SEVERITY="WARNING"
elif [ "$SEVERITY" == "ERROR" ];
then
	SEVERITY="CRITICAL"
elif [ "$SEVERITY" == "INFO" ];
then
	SEVERITY="MINOR"
fi

if [ "$IS_HRV" -eq 1 ];
then	
	echo "$DATE : /opt/OV/bin/opcmsg severity="$SEVERITY" application="$APP_NAME" object="$APP_NAME":"$HEALTH_RULE_NAME" msg_grp=AppDynamics msg_text="Health rule "$SUMMARY_MESSAGE""" >> "$OUTFILE"
	/opt/OV/bin/opcmsg severity="$SEVERITY" application="$APP_NAME" object="$APP_NAME":"$HEALTH_RULE_NAME" msg_grp=AppDynamics msg_text="Health rule "$SUMMARY_MESSAGE""
else
	echo "$DATE : /opt/OV/bin/opcmsg severity="$SEVERITY" application="$APP_NAME" object="$APP_NAME":"$EN_NAME" msg_grp=AppDynamics msg_text="$EVENT_DETAIL"" >> "$OUTFILE"
	/opt/OV/bin/opcmsg severity="$SEVERITY" application="$APP_NAME" object="$APP_NAME":"$EN_NAME" msg_grp=AppDynamics msg_text="$EVENT_DETAIL"
fi
