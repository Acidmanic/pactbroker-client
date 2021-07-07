/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands;

import com.acidmanic.commandline.commands.FractalCommandBase;
import com.acidmanic.commandline.commands.Help;
import com.acidmanic.commandline.commands.TypeRegistery;
import com.acidmanic.localbrokerclient.commands.arguments.ArgumentsContext;
import com.acidmanic.localbrokerclient.commands.arguments.Server;
import com.acidmanic.localbrokerclient.commands.arguments.Token;

/**
 *
 * @author diego
 */
public abstract class ApplicationCommandBase extends FractalCommandBase<ArgumentsContext> {

    @Override
    protected void addArgumentClasses(TypeRegistery reg) {
        
        reg.registerClass(Token.class);
        
        reg.registerClass(Server.class);

        reg.registerClass(Help.class);
    }

    @Override
    protected void execute(ArgumentsContext argumentsContext) {

        ApplicationContext applicationContext = getContext();

        boolean succeed = validateArguments(argumentsContext);

        if (succeed) {
            
            execute(applicationContext, argumentsContext);
        } else {
            
            error("Entered Arguments are not correct.");
            
            applicationContext.fail();
        }
    }

    @Override
    protected ArgumentsContext createNewContext() {
        return new ArgumentsContext();
    }

    protected abstract void execute(ApplicationContext applicationContext, ArgumentsContext argumentsContext);

    protected boolean validateArguments(ArgumentsContext argumentsContext) {
        
        
        if(argumentsContext.getServer()==null){
            return false;
        }
        if(argumentsContext.getServer().length()<1){
            return false;
        }
        if(argumentsContext.getToken()==null){
            return false;
        }
        if(argumentsContext.getToken().length()<1){
            return false;
        }
        return true;
    }
}
