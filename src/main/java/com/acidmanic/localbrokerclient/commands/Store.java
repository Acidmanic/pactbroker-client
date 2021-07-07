/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.localbrokerclient.commands.arguments.ArgumentsContext;
import com.acidmanic.localbrokerclient.commands.arguments.Tag;
import com.acidmanic.localbrokerclient.models.ResultDto;
import com.acidmanic.pact.models.Pact;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 *
 * @author diego
 */
public class Store extends PactIOCommandBase {

    @Override
    protected void addArgumentClasses(TypeRegistery reg) {
        
        super.addArgumentClasses(reg); 
        
        reg.registerClass(Tag.class);
    }
    
    @Override
    protected void execute(ApplicationContext applicationContext, ArgumentsContext argumentsContext) {

        Pact pact = getAvailablePacts(argumentsContext);
        
        
        if (pact.getContracts().isEmpty()) {
            
            warning("No contracts has been found. command exited without contacting broker server.");
            return;
        }
        
        String errorString = "";
        
        
        try {
            HttpResponse<ResultDto> response = 
                    Unirest.post(argumentsContext.getServer() + "/store/" + argumentsContext.getTag())
                    .header("token", argumentsContext.getToken())
                    .header("Content-Type", "application/json")
                    .body(pact)
                    .asEmpty();

            if (response != null && response.isSuccess()) {

                info(pact.getContracts().size() + " Contracts stored to pact "
                        + "broker server successfully tagged as "+argumentsContext.getTag()+".");

                return;
            }
        } catch (Exception e) {
            errorString = "\nError: " + e.getClass().getSimpleName();
        }

        error("Unable to upload pact contracts to broker server." + errorString);
        applicationContext.fail();
        Unirest.shutDown();
    }

    @Override
    protected String getUsageDescription() {
        return "This command uploads given pact files with a tag, into pact broker server as a candidate.";
    }

}
