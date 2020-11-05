package uz.smartgeo.example.vertxApiKeycloak.utils;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.function.Function;

public class ResponseUtil {
//    public static void respondWithCreated(RoutingContext routingContext, String content) {
//        routingContext
//                .response()
//                .setStatusCode(200)
//                .putHeader("content-type", "application/json; charset=utf-8")
//                .putHeader("Location", routingContext.request().absoluteURI())
//                .end(content);
//    }

    public static void respondSuccess(RoutingContext routingContext, JsonObject content) {
        routingContext
                .response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Location", routingContext.request().absoluteURI())
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .end(new JsonObject().put("result", content).putNull("error").toString());
    }

    public static void respondWithCreated(RoutingContext routingContext, List<JsonObject> content) {
        routingContext
                .response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Location", routingContext.request().absoluteURI())
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .end(new JsonObject().put("result", content).putNull("error").toString());
    }

    public static void respondWithCreatedAPI(RoutingContext routingContext, JsonObject content) {
        routingContext
                .response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Location", routingContext.request().absoluteURI())
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .end(content.toString());
    }

    public static void respondWithDeleted(RoutingContext routingContext) {
        routingContext
                .response()
                .setStatusCode(204)
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Location", routingContext.request().absoluteURI())
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .end();
    }

    public static Handler<RoutingContext> respondWithJson(Function<HttpServerRequest, List<?>> listSupplier) {
        return (RoutingContext rc) -> rc
                .response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .end(Json.encode(listSupplier.apply(rc.request())));
    }

    public static void respondError(RoutingContext routingContext, String content) {
        routingContext
                .response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Location", routingContext.request().absoluteURI())
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .end(new JsonObject()
                        .put("error", content)
                        .putNull("result").toString());
    }

    public static void respondErrorAPI(RoutingContext routingContext, String content) {
        routingContext
                .response()
                .setStatusCode(200)
                .putHeader("content-type", "application/json; charset=utf-8")
                .putHeader("Location", routingContext.request().absoluteURI())
                .putHeader("Access-Control-Allow-Origin", "*")
                .putHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE")
                .end(content);
    }

}
