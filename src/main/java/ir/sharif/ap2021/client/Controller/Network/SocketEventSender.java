package ir.sharif.ap2021.client.Controller.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.LoginResponse;
import ir.sharif.ap2021.shared.Response.MainMenuResponse;
import ir.sharif.ap2021.shared.Response.Response;
import ir.sharif.ap2021.shared.Util.Gson.*;
import ir.sharif.ap2021.shared.Util.Letter;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.*;
import java.util.Base64;
import java.util.Scanner;

public class SocketEventSender implements EventSender {
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private final Scanner scanner;
    private final PrintStream printStream;
    private final Gson gson;
    private String token;

    public SocketEventSender(Socket socket) throws IOException {
        scanner = new Scanner(socket.getInputStream());
        printStream = new PrintStream(socket.getOutputStream());
        gson = new GsonBuilder()
                .registerTypeAdapter(Event.class, new EventAdapter())
                .registerTypeAdapter(Response.class, new ResponseAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();
    }

    @Override
    public Response[] sendEvent(Event event) {
        Letter requestLetter = new Letter(token, event);
        String encode = encoder.encodeToString(toJson(requestLetter).getBytes());
        printStream.println(encode);
        String json = null;
        try {
            json = scanner.nextLine();
        } catch (Exception e) {

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Oops! Server got shutdown I guess");
                alert.showAndWait();
            });

            Platform.exit();
            System.exit(0);

        }
        String decode = new String(decoder.decode(json.getBytes()));
        Letter responseLetter = toMessage(decode);
        checkToken(responseLetter);
        return responseLetter.getResponses();
    }

    @Override
    public void close() {
        scanner.close();
        printStream.close();
    }

    private String toJson(Letter letter) {
        return gson.toJson(letter);
    }

    private Letter toMessage(String json) {
        return gson.fromJson(json, Letter.class);
    }

    private void checkToken(Letter letter) {

        if (letter.getResponses() != null && letter.getResponses().length > 0) {
            if (letter.getResponses()[0] instanceof LoginResponse) {
                token = letter.getToken();
            } else if (letter.getResponses()[0] instanceof MainMenuResponse
                    && ((MainMenuResponse) letter.getResponses()[0]).getOrder().equals("logOut")) {
                token = null;
            }
        }

    }
}

