package com.gundom.Exception;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 7793296502162655579L;

   public ServiceException(){
       super();
   }
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable throwable){
       super(throwable);
    }
}
