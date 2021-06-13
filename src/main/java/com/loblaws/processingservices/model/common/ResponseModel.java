package com.loblaws.processingservices.model.common;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@Builder
public class ResponseModel {
	
	private HttpStatus StatusCode;	
	private int MessageTypeID;
	private String Message;
}
