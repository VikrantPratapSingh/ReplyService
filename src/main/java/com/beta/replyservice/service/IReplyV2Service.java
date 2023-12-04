package com.beta.replyservice.service;

import java.security.NoSuchAlgorithmException;

public interface IReplyV2Service {
    ReplyMessage result(String message) throws NoSuchAlgorithmException;
}
