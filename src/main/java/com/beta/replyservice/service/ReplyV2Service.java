package com.beta.replyservice.service;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@Service
public class ReplyV2Service implements IReplyV2Service{

    private final static String[] RULE_LIST={"1","2","12"};

    @Override
    public ReplyMessage result(String message) throws NoSuchAlgorithmException {
        //splitted the message first based on "-"
        String[] splitMessage=message.split("-");
        List<String> ruleSet = Arrays.asList(RULE_LIST);
        String finalMessage=null;
        //at least have two parts
        if(splitMessage.length>=2){
            //specific rule
            String rule=splitMessage[0];
            String data=splitMessage[1];
            //defined a RULE_LIST to accommodate future rule implementations
            if(ruleSet.contains(rule)){
                 finalMessage= processMessage(rule,data);
            }else if(rule.matches("11")){
                finalMessage=data;
            }else if(rule.matches("22")){
                finalMessage=md5HashEncoding(md5HashEncoding(message));
            }
        }
        return new ReplyMessage(finalMessage);
    }

    private String processMessage(String rule, String message) throws NoSuchAlgorithmException {
        String result=null;
        StringBuilder reversedMessage = new StringBuilder(message).reverse();
        if (rule.matches("1")) {
            result=reversedMessage.toString();
        } else if (rule.matches("2")) {
            result= md5HashEncoding(message);
        }
        else if(rule.matches("12")){
            String reversedMessageString=reversedMessage.toString();
            result=md5HashEncoding(reversedMessageString);
        }
        return result;
    }

    private String md5HashEncoding(String message) throws NoSuchAlgorithmException {
        try{
            //MD5 algorithm instance
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getBytes());
            byte[] digest = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                //0xff hexadecimal representation of 255 as in Java,bytes range (-128,127)
                String hex = Integer.toHexString(0xff & b);
                hexString.append(hex);
            }
            return hexString.toString();

        }catch (NoSuchAlgorithmException e){
            return ("MD5 encoding failed");
        }
    }
}
