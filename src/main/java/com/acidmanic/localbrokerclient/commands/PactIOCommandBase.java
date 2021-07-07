/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.commandline.commands.Help;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.io.file.FileIOHelper;
import com.acidmanic.localbrokerclient.commands.arguments.ArgumentsContext;
import com.acidmanic.localbrokerclient.commands.arguments.Root;
import com.acidmanic.localbrokerclient.commands.arguments.Server;
import com.acidmanic.localbrokerclient.commands.utility.ContractHelper;
import com.acidmanic.localbrokerclient.commands.utility.DirectoryScanner;
import com.acidmanic.pact.models.Pact;
import com.acidmanic.pactmodels.Contract;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author diego
 */
public abstract class PactIOCommandBase extends ApplicationCommandBase {
    
    @Override
    protected void addArgumentClasses(TypeRegistery reg) {

        super.addArgumentClasses(reg);
        
        reg.registerClass(Root.class);

        reg.registerClass(Server.class);

        reg.registerClass(Help.class);
    }

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

    protected boolean saveAllContracts(List<Contract> contracts, File root) {

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
