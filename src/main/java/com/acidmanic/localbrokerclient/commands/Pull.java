/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.localbrokerclient.commands.arguments.ArgumentsContext;
import com.acidmanic.localbrokerclient.models.Dto;
import com.acidmanic.localbrokerclient.models.Pact;
import com.acidmanic.localbrokerclient.models.PactDto;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 *
 * @author diego
 */
public class Pull extends ApplicationCommandBase {

    @Override
    protected void execute(ApplicationContext applicationContext, ArgumentsContext argumentsContext) {

        String errorPostFix = "";
        HttpResponse<String> response = null;

        try {
            response = Unirest
                    .get(argumentsContext.getServer() + "/pull")
                    .header("token", argumentsContext.getToken())
                    .asString();
        } catch (Exception e) {
            errorPostFix = ": " + e.getClass().getSimpleName();
        }

        if (response != null && response.getStatus() == 200) {

            String json = response.getBody();

            PactDto dto = toObject(json, PactDto.class);

            if (dto != null && !dto.isFailure() && dto.getModel() != null) {
                info("Pacts are received from broker");

                Pact pact = dto.getModel();
            }

        }

        error("Problem receiving pacts from broker" + errorPostFix);
        applicationContext.fail();

    }

    @Override
    protected String getUsageDescription() {
        return "This command uploads given pact files into pact broker server.";
    }

}
