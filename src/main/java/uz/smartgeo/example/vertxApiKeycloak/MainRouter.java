package uz.smartgeo.example.vertxApiKeycloak;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import uz.smartgeo.example.vertxApiKeycloak.utils.ResponseUtil;

public class MainRouter {

  private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(MainRouter.class.getName());

  public Router createRouting(Vertx vertx) {
    final Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());

//    LocalSessionStore localSessionStore = LocalSessionStore.create(vertx);
//    router.route().handler(SessionHandler.create(localSessionStore));

    router.route().handler(io.vertx.ext.web.handler.CorsHandler.create("*")
      .allowedHeader("Authorization"));

//    router.route("/api/user", new UserRouter(vertx, config, dbQueries).getRouter());

    router.route("/api/user").handler(this::getUser);

    router.post("/api/protected/*").handler(this::authHandler);
    router.get("/api/protected/*").handler(this::authHandler);

    // disable cache for static asset reload
    router.route("/static/*").handler(StaticHandler.create().setCachingEnabled(false).setWebRoot("static"));

    return router;
  }

  public void getUser(RoutingContext context) {
    ResponseUtil.respondSuccess(context, new JsonObject().put("userName", "John"));
  }

  private void authHandler(RoutingContext context) {
    final String authorization = context.request().getHeader(HttpHeaders.AUTHORIZATION);

//    if (AuthUtils.tokenIsValid(authorization)) {
    context.next();
//    } else {
//      ResponseUtil.respondError(context, "Not authorized request");
//    }

  }

}
