package com.github.peggybrown.speechrank;import javaslang.collection.List;import ratpack.server.RatpackServer;import static ratpack.jackson.Jackson.json;public class Main {    private static Storage storage;    public static void main(String... args) throws Exception {        addFakeConference();        RatpackServer.start(server -> server                .handlers(chain -> chain                        .post("rating", ctx ->                                ctx.parse(Rate.class).then(rate -> {                                    storage.add(rate);                                    ctx.render("OK");                                }))                        .post("comment", ctx ->                                ctx.parse(Comment.class).then(comment -> {                                    storage.add(comment);                                    ctx.render("OK");                                }))                        .get(ctx -> ctx.render(options))                        .get("conferences", ctx -> ctx.render(json(storage.getYears().toString())))                        .get("conference/:name", ctx -> {                            ctx.render(json(storage.getConference(ctx.getPathTokens().get("name")).toString()));                        })));    }    private static void addFakeConference() {        storage = new Storage();        storage.addYear("2015");        Presentation prez = new Presentation("1", "title", "yt.com/sds", 0.0, List.empty(), List.empty());        Conference conf = new Conference("11", "Geecon", List.of(prez));        storage.add("2015",conf);    }    private static String options =            "GET /conferences\n" +                    "GET /conference/:id\n" +                    "POST /rating\n" +                    "POST /comment\n";}
