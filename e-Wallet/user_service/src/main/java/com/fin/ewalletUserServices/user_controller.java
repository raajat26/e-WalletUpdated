package com.fin.ewalletUserServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
@RestController
public class user_controller{

    @Autowired
    user_service user_servic;

    @PostMapping("/createUser")
    public void createUser(@RequestBody  user_impl user_impl_Obj) throws JsonProcessingException {

        user_servic.createUser(user_impl_Obj);

        // json processing exception added to use Object Mapper in UserService  class

    }

    @GetMapping("/user/{user_id}")
    public user_impl returnUserDetails(@PathVariable("user_id") String user_id ){


        return user_servic.getUserId(user_id); // TYPO, this is getting user details

    }


}