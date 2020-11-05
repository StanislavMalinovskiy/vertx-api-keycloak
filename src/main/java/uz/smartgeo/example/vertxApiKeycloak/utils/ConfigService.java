package uz.smartgeo.example.vertxApiKeycloak.utils;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class ConfigService {

  //private static Logger LOGGER = Logger.getLogger(ConfigService.class);

  public Future<JsonObject> getConfig(Vertx vertx, String filename) {
    Promise<JsonObject> promise = Promise.promise();

    // Read config and say after finish
    ConfigRetriever retriever = readFromFile(vertx, filename);

    retriever.getConfig(
      ar -> {
        if (ar.failed()) {
          // Failed to retrieve the configuration
          promise.fail("Config not read");
        } else {
          // Success
          promise.complete(ar.result());
        }
      });

    return promise.future();
  }


  private static ConfigRetriever readFromFile(Vertx vertx, String filename) {
    // Set file where to read configurations
    ConfigStoreOptions fileStore = new ConfigStoreOptions()
      .setType("file")
      .setFormat("json")
      .setConfig(new JsonObject().put("path", "./" + filename + ".json"));

    ConfigRetrieverOptions options = new ConfigRetrieverOptions()
      .addStore(fileStore);

    // Read config and say after finish
    return ConfigRetriever.create(vertx, options);
  }
}
