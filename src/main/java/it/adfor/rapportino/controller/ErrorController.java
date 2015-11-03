/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.adfor.rapportino.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController 
@RequestMapping("err/{code}")
public class ErrorController {
    
    @RequestMapping(method=RequestMethod.GET)
    public String showError(@PathVariable String code){
        System.out.println("IM IN ERRCONTROLLER");
        
        switch (code){
            case "0" : return "{\"msg\": \" Wrong username or password\"}";
            case "1" : return "{\"msg\": \" No access\"}";
        }
        System.out.println("((((");
        return null;
    }
}

class RestError{
    String message;
    public RestError(String msg){
        this.message = msg;
    }
}