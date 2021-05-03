/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.localbrokerclient.commands.arguments.ArgumentsContext;
import com.acidmanic.localbrokerclient.commands.utility.DirectoryScanner;
import com.acidmanic.localbrokerclient.models.ResultDto;
import com.acidmanic.pact.models.Pact;
import com.acidmanic.pactmodels.Contract;
import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

/**
 *
 * @author diego
 */
public class Push extends ApplicationCommandBase {

    @Override
    protected void execute(ApplicationContext applicationContext, ArgumentsContext argumentsContext) {

        List<Contract> contracts = new ArrayList<>();

        File root = argumentsContext.getRoot();

        new DirectoryScanner().scan(root, file -> scanForContract(file, contracts));

        if (contracts.isEmpty()) {
            warning("No contracts has been found. command exited without contacting broker server.");
            return;
        }

        Pact pact = new Pact();

        pact.setContracts(contracts);

        String errorString = "";
        try {
            HttpResponse<ResultDto> response = Unirest.post(argumentsContext.getServer() + "/push")
                    .header("token", argumentsContext.getToken())
                    .header("Content-Type", "application/json")
                    .body(pact)
                    .asEmpty();

            if (response != null && response.isSuccess()) {

                info(contracts.size() + " Contracts uploaded to pact broker server successfully.");

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
        return "This command downloads available pact files from pact broker server.";
    }

    private void scanForContract(File file, List<Contract> contracts) {

        if (file.getName().toLowerCase().endsWith(".json")) {

            try {
                Gson gson = new Gson();

                String json = new FileIOHelper().tryReadAllText(file);

                Contract contract = null;

                if (json != null && json.length() > 0) {
                    contract = gson.fromJson(json, Contract.class);
                }
                if (contract != null) {
                    contracts.add(contract);
                }
            } catch (Exception e) {
            }
        }
    }

}
