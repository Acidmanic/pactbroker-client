
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands.utility;

import com.acidmanic.lightweight.logger.Logger;
import com.acidmanic.lightweight.logger.SilentLogger;
import com.acidmanic.pactmodels.Consumer;
import com.acidmanic.pactmodels.Contract;
import com.acidmanic.pactmodels.Interaction;
import com.acidmanic.pactmodels.Metadata;
import com.acidmanic.pactmodels.PactSpecification;
import com.acidmanic.pactmodels.Provider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author diego
 */
public class ContractHelper {

    private final Logger logger;

    public ContractHelper() {
        this.logger = new SilentLogger();
    }

    public ContractHelper(Logger logger) {
        this.logger = logger;
    }

    public HashMap<String, Contract> mergeByProvider(List<Contract> contracts) {

        HashMap<String, List<Contract>> groups = new HashMap<>();

        for (Contract c : contracts) {

            String provider = tryGetProvider(c);

            if (provider == null) {
                
                this.logger.warning("Contract without version has been ignored.");
            } else {
                
                provider = provider.toLowerCase();

                if (!groups.containsKey(provider)) {
                    
                    groups.put(provider, new ArrayList<>());
                }

                List<Contract> groupContracts = groups.get(provider);

                groupContracts.add(c);
            }
        }

        HashMap<String, Contract> mergedContracts = new HashMap<>();

        groups.forEach((vr, cts) -> mergedContracts.put(vr, merge(vr, cts)));

        return mergedContracts;
    }

    private String tryGetProvider(Contract c) {
        Provider provider = c.getProvider();

        if (provider != null) {
            return provider.getName();
        }
        return null;
    }

    private String tryGetPactVersion(Contract c) {
        if (c.getMetadata() != null) {
            if (c.getMetadata().getPactSpecification() != null) {
                String version = c.getMetadata().getPactSpecification().getVersion();
                if (version != null && version.length() > 0) {
                    return version;
                }
            }
        }
        return null;
    }

    private Contract merge(String provider, List<Contract> contracts) {

        Contract contract = newMergedContract(provider);

        ArrayList<Interaction> interactions = new ArrayList<>();

        contracts.forEach(c -> interactions.addAll(c.getInteractions()));

        contract.setInteractions(interactions);

        return contract;
    }

    private Contract newMergedContract(String providerName) {
        Contract contract = new Contract();

        Consumer consumer = new Consumer();

        consumer.setName("Clinet");

        contract.setConsumer(consumer);

        PactSpecification pactSpecification = new PactSpecification();

        pactSpecification.setVersion("3.0.0");

        Metadata metadata = new Metadata();

        metadata.setPactSpecification(pactSpecification);

        contract.setMetadata(metadata);

        Provider provider = new Provider();

        provider.setName(providerName);

        contract.setProvider(provider);

        return contract;
    }
}
