package com.example.lessoneight;

interface ErrorHandler {
	enum ErrorType {
		BUFFER_CREATION_ERROR
	}
	
	void handleError(ErrorType errorType, String cause);
}