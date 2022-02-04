package com.example.second.config;


import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class Validations {

    public boolean checkMobile(String mob)
    {
        if(mob.length() != 10 || mob.charAt(0) == '0')
            return false;
        else
            return true;
    }


    public boolean checkEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    public boolean checkAmount(double amt) {
        if(amt < 0)
            return false;
        else
            return true;
    }
}
