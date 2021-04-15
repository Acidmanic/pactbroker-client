/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.localbrokerclient.commands.arguments.ArgumentsContext;

/**
 *
 * @author diego
 */
public class Pull extends ApplicationCommandBase{

    @Override
    protected void execute(ApplicationContext applicationContext, ArgumentsContext argumentsContext) {
        
    }
    
    @Override
    protected String getUsageDescription() {
        return "This command uploads given pact files into pact broker server.";
    }
    
}
