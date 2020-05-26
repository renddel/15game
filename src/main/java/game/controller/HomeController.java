package game.controller;

import game.model.Game;
import game.model.MoveForm;
import game.rest.InvalidMoveException;
import game.rest.MoveErrorResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    List<Game> list = new ArrayList<>();

    @RequestMapping("/")
    public String index() {
        return "index";
    }
    
    @RequestMapping(value = "/game/{gameId}", method = RequestMethod.GET)
    public String getGame(@PathVariable int gameId, Model model) {
        
        // checking if there is a match in the list
        if (list.stream().anyMatch(p->p.getId()==gameId)) {
            model.addAttribute("items", findByCodeIsIn(gameId).getBoard());
        } else {
            // if there is no match on the list then create a new game
            Game game = new Game(gameId);
            model.addAttribute("items", game.getBoard());
            list.add(game);
        }

        return "index";
    }

    @RequestMapping(value = "/game/{gameId}/move/{tileValue}", method = RequestMethod.POST)
    public String makeMove(@PathVariable int gameId, @ModelAttribute(name = "moveForm") MoveForm moveForm,
            @PathVariable String tileValue, Model model) {

        Game game = findByCodeIsIn(gameId);
        int valueToChange = Integer.valueOf(tileValue);
        game.findCoordinate(valueToChange);
        int iCoordinate = game.getiCoordinate();
        int jCoordinate = game.getjCoordinate();

        game.findCoordinate(-1);
        int blankICoordinate = game.getiCoordinate();
        int blankJCoordinate = game.getjCoordinate();

        if (game.isMoveAllowed(valueToChange)) {
            game.changeTiles(game.getBoard(), iCoordinate, blankICoordinate, jCoordinate, blankJCoordinate);
        } else {
            throw new InvalidMoveException("Move is invalid " + tileValue);
        }

        model.addAttribute("items", game.getBoard());
        return "index";
    }

    public Game findByCodeIsIn(int codeIsIn) {
        return list.stream().filter(c -> codeIsIn == c.getId()).findFirst().orElse(null);
    }

    @ExceptionHandler
    public ResponseEntity<MoveErrorResponse> handleException(InvalidMoveException e) {
        MoveErrorResponse error = new MoveErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
