/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.localbrokerclient.commands.arguments.ArgumentsContext;
import com.acidmanic.localbrokerclient.commands.utility.DirectoryScanner;
import com.acidmanic.pact.models.Pact;
import com.acidmanic.pactmodels.Contract;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author diego
 */
public abstract class UploaderCommandBase extends ApplicationCommandBase {

    protected Pact getAvailablePacts(ArgumentsContext argumentsContext) {
        List<Contract> contracts = new ArrayList<>();

        File root = argumentsContext.getRoot();

        new DirectoryScanner().scan(root, file -> scanForContract(file, contracts));

        Pact pact = new Pact();

        pact.setContracts(contracts);

        return pact;
    }

    @Override
    protected String getUsageDescription() {
        return "This command uploads given pact files into pact broker server.";
    }

    private void scanForContract(File file, List<Contract> contracts) {

        if (file.getName().toLowerCase().endsWith(".json")) {

            try {

                ObjectMapper mapper = new ObjectMapper();

                String json = new FileIOHelper().tryReadAllText(file);

                Contract contract = null;

                if (json != null && json.length() > 0) {

                    contract = mapper.readValue(file, Contract.class);
                }
                if (contract != null) {
                    contracts.add(contract);
                }
            } catch (Exception e) {
            }
        }
    }

}
