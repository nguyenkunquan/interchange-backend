package com.interchange.service;

import com.interchange.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.voice.Sms;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;
@Service
public class TwilioOTPService {

    @Autowired
    private TwilioConfig twilioConfig;

    private String generateOTP;
    public void sendOTPForPasswordReset(String phoneNumber) {

        PhoneNumber to = new PhoneNumber(phoneNumber);
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
        generateOTP = generateOTP();
        Message message = Message.creator(to,
                from, "Your OTP is " + generateOTP).create();
    }

    public boolean verifyOTP(String enteredOTP) {
        return generateOTP.equals(enteredOTP);
    }
    private String generateOTP() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
