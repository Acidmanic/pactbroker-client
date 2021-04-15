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
public class Token extends ArgumentBase {

    @Override
    protected void execute(ArgumentsContext context, String argument) {
        context.setToken(argument);
    }

    @Override
    protected String getUsageDescription() {
        return "This arguments sets the authorization token to "
                + "communicate with broker server";
    }

}
