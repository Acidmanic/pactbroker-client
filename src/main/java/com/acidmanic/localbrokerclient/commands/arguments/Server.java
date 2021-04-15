/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands.arguments;

/**
 *
 * @author diego
 */
public class Server extends ArgumentBase {

    @Override
    protected void execute(ArgumentsContext context, String argument) {
        context.setServer(argument);
    }

    @Override
    protected String getUsageDescription() {
        return "This argument sets the address of broker server.";
    }

}
