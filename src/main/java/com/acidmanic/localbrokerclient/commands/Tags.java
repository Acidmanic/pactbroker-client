/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.localbrokerclient.commands.arguments.ArgumentsContext;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;

/**
 *
 * @author diego
 */
public class Tags extends ApplicationCommandBase{

    @Override
    protected void execute(ApplicationContext applicationContext, ArgumentsContext argumentsContext) {
        
        
        HttpResponse<String[]> response = null;

        Unirest.config().setObjectMapper(new JacksonObjectMapper());        
        
        try {
            response = Unirest
                    .get(argumentsContext.getServer() + "/tags")
                    .header("token", argumentsContext.getToken())
                    .asObject(String[].class);
        } catch (Exception e) {

            
        }
        if (response != null && response.isSuccess()) {

            String[] tags = response.getBody();

            if (tags != null ) {
                                
                log("----------------");
                
                for(String tag:tags){
                    
                    log(tag);
                    
                }
                
                log("----------------");
            }

        }
    }

    @Override
    protected String getUsageDescription() {
        
        return "This commands gets all stored tags "
                + "from broker server and prints them on console.";
    }
}
