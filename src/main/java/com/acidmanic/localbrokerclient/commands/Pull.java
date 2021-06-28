/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.localbrokerclient.commands.arguments.ArgumentsContext;
import com.acidmanic.localbrokerclient.commands.utility.ContractHelper;
import com.acidmanic.localbrokerclient.models.PactDto;
import com.acidmanic.pact.models.Pact;
import com.acidmanic.pactmodels.Contract;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import kong.unirest.HttpResponse;
import kong.unirest.JsonObjectMapper;
import kong.unirest.Unirest;
import kong.unirest.jackson.JacksonObjectMapper;

/**
 *
 * @author diego
 */
public class Pull extends ApplicationCommandBase {

    @Override
    protected void execute(ApplicationContext applicationContext, ArgumentsContext argumentsContext) {

        String errorPostFix = "";
        HttpResponse<PactDto> response = null;

        Unirest.config().setObjectMapper(new JacksonObjectMapper());
        
        try {
            response = Unirest
                    .get(argumentsContext.getServer() + "/pull")
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
        return "This command uploads given pact files into pact broker server.";
    }

    private boolean saveAllContracts(List<Contract> contracts, File root) {

        HashMap<String, Contract> mergedContracts = new ContractHelper(getLogger())
                .mergeByProvider(contracts);

        root.mkdirs();

        boolean success = true;

        for (String version : mergedContracts.keySet()) {

            File file = root.toPath().resolve(version + ".json").toFile();

            Contract contract = mergedContracts.get(version);

            success = success & save(contract, file);

        }
        return success;
    }

    private boolean save(Contract contract, File file) {
        try {

            if (file.exists()) {
                file.delete();
            }
            ObjectMapper mapper = new ObjectMapper();

            String json = mapper.writeValueAsString(contract);

            new FileIOHelper().tryWriteAll(file, json);

            return true;

        } catch (Exception e) {
            
        }
        return false;
    }

}
