package uz.smartgeo.example.vertxApiKeycloak;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.apache.log4j.Logger;
import uz.smartgeo.example.vertxApiKeycloak.utils.ConfigService;

public class MainVerticle extends AbstractVerticle {

  private static final Logger LOGGER = Logger.getLogger(MainVerticle.class.getName());
  JsonObject config = new JsonObject();

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
//    DOMConfigurator.configure("./resources/log4j.properties");

    ConfigService configService = new ConfigService();

    configService.getConfig(vertx, "conf").onComplete(getConfigRes -> {
      if (getConfigRes.succeeded()) {
        config = getConfigRes.result();

        LOGGER.info(getConfigRes.result());

        // Make router
        Router router = new MainRouter().createRouting(vertx);

        createHttpServer(startPromise, router);
      } else {
        LOGGER.error("getConfig failed -> " + getConfigRes.cause().getMessage());
      }
    });
  }

  private void createHttpServer(Promise<Void> startPromise, Router router) {
    // enable http compression (e.g. gzip js)
    final HttpServerOptions options = new HttpServerOptions()
      .setCompressionSupported(config().getBoolean("enable.http.compression", true));

    // Create HTTP server
    HttpServer httpServer = vertx.createHttpServer(options);

    httpServer.requestHandler(router)
      .listen(config.getInteger("server.port"),
        config.getString("server.host"), r -> {
          if (r.succeeded()) {
            LOGGER.info("Http server started at " + "http://" + config.getString("server.host") + ":" +  httpServer.actualPort());

            LOGGER.info("get user " + "http://" +
              config.getString("server.host") + ":" +  httpServer.actualPort() + "/api/user");

          } else {
            LOGGER.error("Http server start failed -> " + r.cause().getMessage());
          }
        });

//    vertx.createHttpServer().requestHandler(req -> {
//      req.response()
//        .putHeader("content-type", "text/plain")
//        .end("Hello from Vert.x!");
//    }).listen(8888, http -> {
//      if (http.succeeded()) {
//        startPromise.complete();
//        System.out.println("HTTP server started on port 8888 - http://localhost:8888/");
//
//      } else {
//        startPromise.fail(http.cause());
//      }
//    });


  }

}
