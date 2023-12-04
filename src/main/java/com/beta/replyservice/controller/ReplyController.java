package com.beta.replyservice.controller;

import com.beta.replyservice.service.IReplyV2Service;
import com.beta.replyservice.service.ReplyMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.NoSuchAlgorithmException;

@RestController
public class ReplyController {
	private final IReplyV2Service replyV2Service;

	public ReplyController(IReplyV2Service replyV2Service) {
		this.replyV2Service = replyV2Service;
	}

	@GetMapping("/reply")
	public ReplyMessage replying() {
		return new ReplyMessage("Message is empty");
	}

	@GetMapping("/reply/{message}")
	public ReplyMessage replying(@PathVariable String message) {
		return new ReplyMessage(message);
	}

	@GetMapping("/v2/reply/{message}")
	public ResponseEntity<ReplyMessage> replyingV2(@PathVariable String message) throws NoSuchAlgorithmException {
		ReplyMessage errorMessage = new ReplyMessage("invalid input");
		if (!message.matches("\\d{1,2}-[a-z0-9]*")) {
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		ReplyMessage replyMessage = replyV2Service.result(message);
		if (replyMessage == null || replyMessage.getMessage() == null) {
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(replyMessage, HttpStatus.OK);
	}
}