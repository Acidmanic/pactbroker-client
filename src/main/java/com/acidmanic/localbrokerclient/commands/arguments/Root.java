/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands.arguments;

import java.io.File;

/**
 *
 * @author diego
 */
public class Root extends ArgumentBase {

    @Override
    protected void execute(ArgumentsContext context, String argument) {
        File file = new File(argument);

        if (!file.exists() || !file.isDirectory()) {
            error(this.getClass().getSimpleName() + " takes a directory path.");
            return;
        }

        file = file.toPath().toAbsolutePath().normalize().toFile();

        context.setRoot(file);

    }

    @Override
    protected String getUsageDescription() {
        return "This argument sets the root directory to for pact files.";
    }

}
