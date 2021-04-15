/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acidmanic.localbrokerclient.commands.utility;

import java.io.File;
import java.util.function.Consumer;

/**
 *
 * @author diego
 */
public class DirectoryScanner {

    public void scan(File root, Consumer<File> scanner) {

        File[] files = root.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                scanner.accept(file);
            }
            if (file.isDirectory()) {
                scan(file, scanner);
            }
        }
    }
}
