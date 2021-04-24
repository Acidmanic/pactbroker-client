
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

    public HashMap<String, Contract> mergeByVersion(List<Contract> contracts) {

        HashMap<String, List<Contract>> groups = new HashMap<>();

        for (Contract c : contracts) {
            String version = tryGetVersion(c);
            if (version == null) {
                this.logger.warning("Contract without version has been ignored.");
            } else {
                version = version.toLowerCase();

                if (!groups.containsKey(version)) {
                    groups.put(version, new ArrayList<>());
                }

                List<Contract> groupContracts = groups.get(version);

                groupContracts.add(c);
            }
        }

        HashMap<String, Contract> mergedContracts = new HashMap<>();

        groups.forEach((vr, cts) -> mergedContracts.put(vr, merge(vr, cts)));

        return mergedContracts;
    }

    private String tryGetVersion(Contract c) {
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

    private Contract merge(String version, List<Contract> contracts) {

        Contract contract = newMergedContract(version);

        ArrayList<Interaction> interactions = new ArrayList<>();

        contracts.forEach(c -> interactions.addAll(c.getInteractions()));

        contract.setInteractions(interactions);

        return contract;
    }

    private Contract newMergedContract(String version) {
        Contract contract = new Contract();

        Consumer consumer = new Consumer();

        consumer.setName("Clinet");

        contract.setConsumer(consumer);

        PactSpecification pactSpecification = new PactSpecification();

        pactSpecification.setVersion(version);

        Metadata metadata = new Metadata();

        metadata.setPactSpecification(pactSpecification);

        contract.setMetadata(metadata);

        Provider provider = new Provider();

        provider.setName("Server");

        contract.setProvider(provider);

        return contract;
    }
}
