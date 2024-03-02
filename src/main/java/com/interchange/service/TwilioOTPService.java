package com.interchange.service;

import com.interchange.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;
@Service
@Data
public class TwilioOTPService {

    @Autowired
    private TwilioConfig twilioConfig;

    private String generateOTP;

    private Random random = new Random();
    public ResponseEntity<?> sendOTP(String phoneNumber) {

        PhoneNumber to = new PhoneNumber(phoneNumber);
        PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
        generateOTP = generateOTP();
        Message message = Message.creator(to,
                from, "Your OTP is " + generateOTP).create();
        return new ResponseEntity<>("Send OTP successfully", HttpStatus.OK);
    }

    public boolean verifyOTP(String enteredOTP) {
        return  this.getGenerateOTP().equals(enteredOTP);
    }
    private String generateOTP() {
        return new DecimalFormat("000000").format(random.nextInt(999999));
    }
}