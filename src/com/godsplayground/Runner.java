package com.godsplayground;

import com.godsplayground.controller.Controller;
import com.godsplayground.server.Server;
import org.apache.log4j.BasicConfigurator;

public class Runner {

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        final Controller controller = new Controller();
        final Server server = new Server();
        server.initialise();
        server.registerDelegate(controller);
    }
}
