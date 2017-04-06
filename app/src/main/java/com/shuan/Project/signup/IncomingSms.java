package com.shuan.Project.signup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by Android on 4/3/2017.
 */

public class IncomingSms extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null)
            {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj .length; i++)
                {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[])pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber ;
                    String message = currentMessage .getDisplayMessageBody();
                    String num = senderNum.substring(3);
                    String m = message.substring(0,6);
                    try
                    {
                        if (num.equals("PHNVER"))
                        {
                             MailVerify Sms = new MailVerify();
                            Sms.recivedsms(m);

                        }
                    }
                    catch(Exception e){}

                }
            }

        } catch (Exception e)
        {

        }
    }
}
