package org.example.homework5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;


/**
 * 0. Осознать код, который мы написали на уроке.
 * При появлении вопросов, пишем в общий чат в телеграме.
 * 1. По аналогии с командой отправки сообщений, реализовать следующие команды:
 * 1.1 BroadcastMessageRequest - послать сообщение ВСЕМ пользователям (кроме себя)
 * 1.2 UsersRequest - получить список всех логинов, которые в данный момент есть в чате (в любом формате)
 * 1.3 DisconnectRequest - клиент оповещает сервер о том, что он отключился
 * 1.3.1 * Доп. задание: при отключении юзера, делать рассылку на остальных
 *
 * Можно сделать только один пункт из 1.1-1.3.
 */

public class ChatClient {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        System.out.println("Введите логин");
        Scanner console = new Scanner(System.in);
        String clientLogin = console.nextLine();

        try (Socket server = new Socket("localhost", 8888)) {
            System.out.println("Успешно подключились к серверу");

            try (PrintWriter out = new PrintWriter(server.getOutputStream(), true)) {
                Scanner in = new Scanner(server.getInputStream());

                String loginRequest = createLoginRequest(clientLogin);
                out.println(loginRequest);

                String loginResponseString = in.nextLine();
                if (!checkLoginResponse(loginResponseString)) {
                    throw new RuntimeException("Не удалось подключиться к серверу! Логин занят");
                }

                new Thread(() -> {
                    while (true) {
                        // TODO: парсим сообщение в AbstractRequest
                        //  по полю type понимаем, что это за request, и обрабатываем его нужным образом
                        String msgFromServer = in.nextLine();
                        System.out.println("Сообщение от сервера" + " " + msgFromServer);
                    }
                }).start();


                while (true) {
                    System.out.println("Что хочу сделать?");
                    System.out.println("1. Послать сообщение другу");
                    System.out.println("2. Послать сообщение всем");
                    System.out.println("3. Получить список логинов");

                    String type = console.nextLine();
                    if (type.equals("1")) {
                        // TODO: считываете с консоли логин, кому отправить
                        System.out.println("Введите логин пользователя");
                        String login = console.nextLine();

                        SendMessageRequest request = new SendMessageRequest();
                        request.setMessage(console.nextLine());
                        request.setRecipient(login); // TODO указываем логин получателя

                        String sendMsgRequest = objectMapper.writeValueAsString(request);
                        out.println(sendMsgRequest);
                    } else if (type.equals("2")) {
                        // TODO: отправить сообщение всем
                        BroadcastMessageRequest broadcastMessageRequest = new BroadcastMessageRequest();
                        broadcastMessageRequest.setMessage(console.nextLine());
                        String sendMsgAllRequest = objectMapper.writeValueAsString(broadcastMessageRequest);
                        out.println(sendMsgAllRequest);
                    } else if (type.equals("3")) {

                    }

                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка во время подключения к серверу: " + e.getMessage());
        }

        System.out.println("Отключились от сервера");
    }

    private static String createLoginRequest(String login) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin(login);

        try {
            return objectMapper.writeValueAsString(loginRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Ошибка JSON: " + e.getMessage());
        }
    }

    private static boolean checkLoginResponse(String loginResponse) {
        try {
            LoginResponse resp = objectMapper.reader().readValue(loginResponse, LoginResponse.class);
            return resp.isConnected();
        } catch (IOException e) {
            System.err.println("Ошибка чтения JSON: " + e.getMessage());
            return false;
        }
    }
}
