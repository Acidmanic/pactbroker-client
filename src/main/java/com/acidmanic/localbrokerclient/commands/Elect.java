/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.localbrokerclient.commands.arguments.ArgumentsContext;
import com.acidmanic.localbrokerclient.commands.arguments.Tag;
import com.acidmanic.localbrokerclient.models.PactDto;
import com.acidmanic.pact.models.Pact;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;

/**
 *
 * @author diego
 */
public class Elect extends PactIOCommandBase {

    @Override
    protected void addArgumentClasses(TypeRegistery reg) {
        
        super.addArgumentClasses(reg); 
        
        reg.registerClass(Tag.class);
    }

    
    
    
    @Override
    protected void execute(ApplicationContext applicationContext, ArgumentsContext argumentsContext) {

        String errorPostFix = "";
        HttpResponse<PactDto> response = null;

        Unirest.config().setObjectMapper(new JacksonObjectMapper());

        try {
            response = Unirest
                    .get(argumentsContext.getServer() + "/elect/" + argumentsContext.getTag())
                    .header("token", argumentsContext.getToken())
                    .asObject(PactDto.class);
        } catch (Exception e) {
            errorPostFix = ": " + e.getClass().getSimpleName();

        }
        if (response != null && response.isSuccess()) {

            PactDto dto = response.getBody();

            if (dto != null && !dto.isFailure() && dto.getModel() != null) {

                info("Pacts are received from broker");

                Pact pact = dto.getModel();

                boolean success = saveAllContracts(pact.getContracts(), argumentsContext.getRoot());

                if (success) {
                    info("App Pact contracts successfully fetched from server.");
                    return;
                }
                errorPostFix += " - Problem saving pacts into disk.";
            }

        }

        error("Problem receiving pacts from broker" + errorPostFix);
        applicationContext.fail();
        Unirest.shutDown();
    }

    @Override
    protected String getUsageDescription() {
        return "This command chooses previousely uploaded (stored) pacts with given tag, "
                + "as current pact collection to be delivered from brocker. It also "
                + "downloads and saves selected pact collection at root directory.";
    }

}
