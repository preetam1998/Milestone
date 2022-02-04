package com.example.second.exception;

import com.example.second.config.Validations;
import com.example.second.dao.UserRepo;
import com.example.second.models.UserSideResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class UserValidationException extends RuntimeException {

    @Autowired
    private Validations validations;

    @Autowired
    private UserRepo userRepo;

    private String property;
    private String message;

    public UserValidationException(String property, String message) {
        super("Error in field: " +property+ ", "+ message);
        this.property = property;
        this.message = message;
    }


    public boolean checkUserValidation(UserSideResponse user)
    {

        // Request Body Data Validation
        if(user.getUserName() == "" || user.getUserName() == null)
            throw new UserValidationException("UserName", "Enter User Name Correctly");

        if(user.getFirstName() == "" || user.getFirstName() == null)
            throw new UserValidationException("First Name", "Enter First Name Correctly");

        if(user.getPassword() == "" || user.getPassword() == null)
            throw new UserValidationException("Password", "Enter password Correctly");

        if(! this.validations.checkEmail(user.getEmail()))
            throw new UserValidationException("Email ", "Enter Valid Email");

        if(! this.validations.checkMobile(user.getMobileNumber()))
            throw new UserValidationException("Mobile Number", "Enter Valid Mobile Number");

        if(user.getcAddress() == "" || user.getcAddress() == null) {
            System.out.println(user);
            throw new UserValidationException("Current Address", "Enter Address 000Correctly");
        }


        // Is User's username, mobile, email already taken ??????????
        if(userRepo.findByUserName(user.getUserName()) != null) throw new UserValidationException("UserName","already taken, should be unique");
        if(userRepo.findByMobileNumber(user.getMobileNumber()) != null) throw new UserValidationException("Mobile Number","already registered, should be unique");
        if(userRepo.findByEmail(user.getEmail()) != null) throw new UserValidationException("Email","already registered, should be unique");

        return true;
    }

    public boolean checkUserUpdateValidation(UserSideResponse user) {
        // Request Body Data Validation
        if(user.getUserName() == "" || user.getUserName() == null)
            throw new UserValidationException("UserName", "Enter User Name Correctly");

        if(user.getFirstName() == "" || user.getFirstName() == null)
            throw new UserValidationException("First Name", "Enter First Name Correctly");

        if(user.getPassword() == "" || user.getPassword() == null)
            throw new UserValidationException("Password", "Enter password Correctly");

        if(! this.validations.checkEmail(user.getEmail()))
            throw new UserValidationException("Email ", "Enter Valid Email");

        if(! this.validations.checkMobile(user.getMobileNumber()))
            throw new UserValidationException("Mobile Number", "Enter Valid Mobile Number");

        if(user.getcAddress() == "" || user.getcAddress() == null)
            throw new UserValidationException("Current Address", "Enter Address Correctly");

        return true;
    }
}
