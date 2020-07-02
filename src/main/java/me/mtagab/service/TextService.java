package me.mtagab.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class TextService {

    private static String ACCOUNT_SID = "AC0a6918edfa719dde488c26e2fe737bdd";
    private static String AUTH_TOKEN = "6a877c33f7626779225d3cf429b98753";
    private static String ownNumber = "+12058507414";
    
    public void sendText( String to, String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber("+639155140113"),
                new PhoneNumber(ownNumber),
                msg).create();

        System.out.println(message.getSid());
    }
}
