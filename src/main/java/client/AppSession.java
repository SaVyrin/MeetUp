/*
package client;

import ru.vsu.csf.model.Game;
import ru.vsu.csf.model.Player;
import ru.vsu.csf.model.strategy.RandomStrategy;
import ru.vsu.csf.model.strategy.SocketStrategy;

import java.awt.*;
import java.net.Socket;

public class AppSession implements Runnable {

    private final Game game;

    public AppSession(Socket socket) {
        Player p1 = new Player("Player", Color.BLUE, new SocketStrategy(socket));
        Player p2 = new Player("Bot", Color.RED, new RandomStrategy("Bot"));
        game = new Game(p1, p2);
    }

    public void run() {
        while (!game.gameOver()) {
            game.makeStep();
            game.getStatistics().forEach(System.out::println);
        }
        System.out.println("Game Over!");
    }

}*/
