package com.godsplayground;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Random;

import static spark.Spark.*;

public class GodsPlaygroundServer {

    static final Random randomGenerator = new Random();

    static final String COOKIE_IDENTIFIER = "user-identifier";

    static int x = 10;

    public static void main(String[] args) throws Exception {
        System.out.println("Hello world");
        externalStaticFileLocation("/Users/gmilos/Dropbox/Unison/Documents/Projects/GodsPlayground/webpage-resources");

        /*
        get("/hello", new Route() {

            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "Hello World";
            }
        });
        */
        final Gson gson = new Gson();
        get("/longpoll", (req, res) -> {
            System.out.println("==> long poll start. " + req.cookie(COOKIE_IDENTIFIER));
            Thread.sleep(2000);
            System.out.println("==> long poll end. " + req.cookie(COOKIE_IDENTIFIER));
            final GodsPlaygroundInterestingDataStructure ret = new GodsPlaygroundInterestingDataStructure();
            ret.someArray = new int[]{13, 14, 17};
            ret.someLong = x++;
            ret.someString = "kukulka";
            ret.someClass = new GodsPlaygroundInterestingDataStructure.SomeClass();
            ret.someClass.someInt = 15;

            return ret;
        }, gson::toJson);

        get("/hello", (req, res) -> {
            System.out.println("==> Hello world. " + req.cookie(COOKIE_IDENTIFIER));
            return "Hello World";
        });

        get("/hello2", (req, res) -> {
            System.out.println("==> Hello world2. " + req.cookie(COOKIE_IDENTIFIER));
            return "Hello World2";
        });

        /*
        get("/index.html", (req, res) -> {
            final byte[] bytes = FileUtils.readFileToByteArray(new File("/Users/gmilos/Dropbox/Unison/Documents/Projects/GodsPlayground/index.html"));

            res.cookie(COOKIE_IDENTIFIER, "" + randomGenerator.nextInt(100));
            return bytes;
        });
        */
    }
}
