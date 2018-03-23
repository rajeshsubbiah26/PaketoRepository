package com.vmware.devopsApplications.exception;

import com.vmware.devopsApplications.utility.ResponseUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

        @Autowired
        private ResponseUtility responseUtility;

        @ExceptionHandler
        public ResponseEntity<Object> entityNotFoundException(EntityNotFoundException e) throws Exception{
            log.error(e.getMessage(),e);
            Map responseMap;
            responseMap = responseUtility.getResponseJson("fail", e.getMessage());
            return new ResponseEntity<Object>(responseMap, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler
        public ResponseEntity<?> connectionException(JenkinsExceptions e) throws Exception{
            log.error(e.getMessage(),e);
            Map responseMap;
            responseMap=responseUtility.getResponseJson("fail",e.getMessage());
            return new ResponseEntity<Object>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler
        public  ResponseEntity<?> invalidInput(MethodArgumentTypeMismatchException e) throws Exception{
            log.error(e.getMessage(), e);
            Map responseMap;
            responseMap = responseUtility.getResponseJson("fail", "Invalid Input");
            return new ResponseEntity<Object>(responseMap, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler
        public ResponseEntity<?> unhandledExceptions(Exception e) throws Exception{
            log.error(e.getMessage(),e);
            Map responseMap;
            responseMap = responseUtility.getResponseJson("fail","Internal Server Error");
            return new ResponseEntity<Object>(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
