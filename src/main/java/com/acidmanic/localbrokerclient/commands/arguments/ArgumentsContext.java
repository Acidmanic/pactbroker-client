/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands.arguments;

import com.acidmanic.commandline.commands.context.ExecutionContext;
import java.io.File;

/**
 *
 * @author diego
 */
public class ArgumentsContext implements ExecutionContext {

    private File root;
    private String server;
    private String token;

    public File getRoot() {
        return root;
    }

    public void setRoot(File root) {
        this.root = root;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
