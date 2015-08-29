package com.godsplayground;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Random;

import static spark.Spark.*;

public class GodsPlaygroundServer {

    static final Random randomGenerator = new Random();

    static final String COOKIE_IDENTIFIER = "user-identifier";

    public static void main(String[] args) throws Exception {
        System.out.println("Hello world");
        /*
        get("/hello", new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "Hello World";
            }
        });
        */
        get("/hello", (req, res) -> {
            System.out.println("==> Hello world. " + req.cookie(COOKIE_IDENTIFIER));
            return "Hello World";
        });

        get("/hello2", (req, res) -> {
            System.out.println("==> Hello world2. " + req.cookie(COOKIE_IDENTIFIER));
            return "Hello World2";
        });

        get("/index.html", (req, res) -> {
            final byte[] bytes = FileUtils.readFileToByteArray(new File("/Users/gmilos/Dropbox/Unison/Documents/Projects/GodsPlayground/index.html"));

            res.cookie(COOKIE_IDENTIFIER, "" + randomGenerator.nextInt(100));
            return bytes;
        });
    }
}
