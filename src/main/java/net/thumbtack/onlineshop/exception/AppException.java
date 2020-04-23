package net.thumbtack.onlineshop.exception;

import java.util.ArrayList;
import java.util.List;

public class AppException extends RuntimeException{

    private List<ServiceException> serviceExceptions ;

    public AppException() {
        this.serviceExceptions = new ArrayList<>();
    }

    public List<ServiceException> getServiceExceptions() {
        return serviceExceptions;
    }

    public boolean addException(ServiceException ex){
        if(serviceExceptions == null){
            serviceExceptions=new ArrayList<>();
        }
        return serviceExceptions.add(ex);
    }
}
