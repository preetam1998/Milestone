package com.firstmilestone.first.validation;


import com.firstmilestone.first.entities.User;
import com.firstmilestone.first.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ValidateMobEml {

    @Autowired
    CrudRepository crudRepository;

    public boolean validateUser(User user){
        if(user.getFirstName() == null || user.getFirstName() == "")
            throw new UserValidationException("firstName","'First Name' can't be null or empty");

        if(user.getEmail() == null || user.getEmail() == "")
            throw new UserValidationException("email","'Email' can't be null or empty");

        if(user.getMobile() == null || user.getMobile() == "")
            throw new UserValidationException("mobile","property can't be null or empty");

        if(user.getUserName() == null || user.getUserName() == "")
            throw new UserValidationException("userName","property can't be null or empty");

        if(user.getCurrentAddress() == null || user.getCurrentAddress() == "")
            throw new UserValidationException("address1","property can't be null or empty");

        if(crudRepository.findByUserName(user.getUserName() ) != null)
            throw new UserValidationException("userName","already taken, should be unique");

        if(crudRepository.findByMobile(user.getMobile()) != null)
            throw new UserValidationException("mobile","already registered, should be unique");

        if(crudRepository.findByEmail(user.getEmail()) != null)
            throw new UserValidationException("email","already registered, should be unique");

        return true;
    }
    public boolean validateUpdateUser(User user){
        if(user.getFirstName() == null || user.getFirstName() == "")
            throw new UserValidationException("firstName","'First Name' can't be null or empty");

        if(user.getEmail() == null || user.getEmail() == "")
            throw new UserValidationException("email","'Email' can't be null or empty");

        if(user.getMobile() == null || user.getMobile() == "")
            throw new UserValidationException("mobile","property can't be null or empty");

        if(user.getUserName() == null || user.getUserName() == "")
            throw new UserValidationException("userName","property can't be null or empty");

        if(user.getCurrentAddress() == null || user.getCurrentAddress() == "")
            throw new UserValidationException("address1","property can't be null or empty");

        return true;
    }

}
