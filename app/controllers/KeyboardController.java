package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Keyboard;
import play.mvc.Controller;
import play.mvc.Result;

public class KeyboardController extends Controller {

    public Result createKeyboard(String brand) {
        Keyboard keyboard = new Keyboard(brand);
        return created((JsonNode) new ObjectMapper().valueToTree(keyboard));
    }

    public Result getAll() {
        return ok((JsonNode) new ObjectMapper().valueToTree(Keyboard.getAll()));
    }

    public Result updateKeyboard(Long id, String brand) {

        Keyboard keyboard = Keyboard.getById(id);
        keyboard.update(brand);
        return ok((JsonNode) new ObjectMapper().valueToTree(keyboard));
    }

    public Result getKeyboard(Long id) {

        Keyboard keyboard = Keyboard.getById(id);
        return ok((JsonNode) new ObjectMapper().valueToTree(keyboard));
    }

    public Result deleteKeyboard(Long id) {

        Keyboard keyboard = Keyboard.getById(id);
        keyboard.delete();
        return noContent();
    }
}
