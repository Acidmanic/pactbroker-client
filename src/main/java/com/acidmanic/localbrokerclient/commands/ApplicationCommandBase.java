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
import com.acidmanic.localbrokerclient.commands.arguments.Root;
import com.acidmanic.localbrokerclient.commands.arguments.Server;
import com.acidmanic.localbrokerclient.commands.arguments.Token;

/**
 *
 * @author diego
 */
public abstract class ApplicationCommandBase extends FractalCommandBase<ArgumentsContext> {

    @Override
    protected void addArgumentClasses(TypeRegistery reg) {

        reg.registerClass(Root.class);

        reg.registerClass(Server.class);

        reg.registerClass(Token.class);

        reg.registerClass(Help.class);
    }

    @Override
    protected void execute(ArgumentsContext argumentsContext) {

        ApplicationContext applicationContext = getContext();

        execute(applicationContext, argumentsContext);
    }

    @Override
    protected ArgumentsContext createNewContext() {
        return new ArgumentsContext();
    }

    protected abstract void execute(ApplicationContext applicationContext, ArgumentsContext argumentsContext);
}
