package ir.sharif.ap2021.server.Controller.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSyntaxException;
import ir.sharif.ap2021.server.Controller.ServerSocketManager;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.LoginResponse;
import ir.sharif.ap2021.shared.Response.MainMenuResponse;
import ir.sharif.ap2021.shared.Response.Response;
import ir.sharif.ap2021.shared.Util.Gson.*;
import ir.sharif.ap2021.shared.Util.Letter;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.security.SecureRandom;
import java.time.*;
import java.util.Base64;
import java.util.Optional;
import java.util.Scanner;

public class SocketResponseSender implements ResponseSender {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();
    private final ServerSocketManager serverSocketManager;
    private final Scanner scanner;
    private final PrintStream printStream;
    private final Gson gson;
    private String token;

    public SocketResponseSender(ServerSocketManager serverSocketManager, Socket socket) throws IOException {
        this.serverSocketManager = serverSocketManager;
        scanner = new Scanner(socket.getInputStream());
        printStream = new PrintStream(socket.getOutputStream(), true);

        gson = new GsonBuilder()
                .registerTypeAdapter(Event.class, new EventAdapter())
                .registerTypeAdapter(Response.class,new ResponseAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();

    }

    @Override
    public Event getEvent() throws CliectDisconnectException {
        Optional<Letter> optionalLetter;
        do {
            String encoded;
            try {
                encoded = scanner.nextLine();
            } catch (Throwable t) {
                throw new CliectDisconnectException(t);
            }
            String json = new String(decoder.decode(encoded));
            optionalLetter = getEventLetter(json);
        } while (optionalLetter.isEmpty());
        return optionalLetter.get().getEvent();
    }

    private Optional<Letter> getEventLetter(String json) {
        Letter letter;
        try {
            letter = gson.fromJson(json, Letter.class);
        } catch (JsonSyntaxException e) {
            return Optional.empty();
        }
        if (letter != null) {
            if (token != null && !token.equals(letter.getToken()))
                return Optional.empty();
            if (letter.getEvent() != null) {
                return Optional.of(letter);
            }
        }
        return Optional.empty();
    }

    @Override
    public void sendResponse(Response... responses) throws CliectDisconnectException {
        Letter letter = new Letter(token, responses);
        checkToken(letter);
        String json = gson.toJson(letter);
        String encode = encoder.encodeToString(json.getBytes());
        try {
            printStream.println(encode);
        } catch (Throwable t) {
            throw new CliectDisconnectException(t);
        }
    }

    @Override
    public void close() {
        printStream.close();
        scanner.close();
        serverSocketManager.removeClientHandler(this);
    }

    private void checkToken(Letter letter) {
        if (letter.getResponses() != null && letter.getResponses().length > 0) {
            if (letter.getResponses()[0] instanceof LoginResponse
                    && ((LoginResponse) letter.getResponses()[0]).isEntered()) {
                token = generateNewToken();
                letter.setToken(token);
            } else if (letter.getResponses()[0] instanceof MainMenuResponse
                    && ((MainMenuResponse) letter.getResponses()[0]).getOrder().equals("logOut")) {
                token = null;
            }
        }
    }

    public String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return encoder.encodeToString(randomBytes);
    }

}
