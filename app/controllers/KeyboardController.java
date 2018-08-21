package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Keyboard;
import play.mvc.Controller;
import play.mvc.Result;
import play.Logger;

import java.util.List;

public class KeyboardController extends Controller {

    public Result createKeyboard(String brand) {

        Logger.info("KeyboardController.createKeyboard: brand is {}", brand);

        if (brand == null || brand.isEmpty()) {
            Logger.error("KeyboardController.createKeyboard: empty brand");
            return badRequest("Empty brand");
        }

        Keyboard keyboard = new Keyboard(brand);

        Logger.info("KeyboardController.createKeyboard: generated id is {}", keyboard.getId());

        return created((JsonNode) new ObjectMapper().valueToTree(keyboard));
    }

    public Result getAll() {

        List<Keyboard> keyboards = Keyboard.getAll();

        if (keyboards == null || keyboards.isEmpty()) {
            Logger.error("KeyboardController.getAll: no keyboards found in database");
            return notFound("No keyboards found in database");
        } else {
            Logger.info("KeyboardController.getAll: returning keyboards found in database");
            return ok((JsonNode) new ObjectMapper().valueToTree(Keyboard.getAll()));
        }
    }

    public Result updateKeyboard(Long id, String brand) {

        Logger.info("KeyboardController.updateKeyboard: id is {}, brand is {}", id, brand);

        if (brand == null || brand.isEmpty()) {
            Logger.error("KeyboardController.updateKeyboard: empty brand");
            return badRequest("Empty brand");
        }

        if (id == null) {
            Logger.error("KeyboardController.updateKeyboard: empty id");
            return badRequest("Empty id");
        }

        Keyboard keyboard = Keyboard.getById(id);

        if (keyboard == null) {
            Logger.error("KeyboardController.updateKeyboard: keyboard id not found in database");
            return notFound("Keyboard id not found in database");
        }

        Logger.info("KeyboardController.updateKeyboard: updating keyboard");
        keyboard.update(brand);
        return ok((JsonNode) new ObjectMapper().valueToTree(keyboard));
    }

    public Result getKeyboard(Long id) {

        Logger.info("KeyboardController.getKeyboard: id is {}", id);

        Keyboard keyboard = Keyboard.getById(id);

        if (keyboard == null) {
            Logger.error("KeyboardController.getKeyboard: keyboard id not found in database");
            return notFound("Keyboard id not found in database");
        }

        Logger.info("KeyboardController.getKeyboard: keyboard found in database");
        return ok((JsonNode) new ObjectMapper().valueToTree(keyboard));
    }

    public Result deleteKeyboard(Long id) {

        Logger.info("KeyboardController.deleteKeyboard: id is {}", id);

        Keyboard keyboard = Keyboard.getById(id);

        if (keyboard == null) {
            Logger.error("KeyboardController.createKeyboard: keyboard id not found in database");
            return notFound("Keyboard id not found in database");
        }

        Logger.info("KeyboardController.getKeyboard: keyboard found in database, deleting it");
        keyboard.delete();
        return noContent();
    }
}
