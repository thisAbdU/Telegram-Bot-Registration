package com.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {
    private final UserFormData userData = new UserFormData();
    private int formState = 0;

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = update.getMessage().getChatId();
        String messageText = update.getMessage().getText();

        // Check if a command is issued
        if (messageText.equals("/start")) {
            // Initialize the form
            SendMessage welcomeMessage = new SendMessage();
            welcomeMessage.setChatId(String.valueOf(chatId));
            welcomeMessage.setText("Welcome to the registration form. Your name, please...");
            try {
                execute(welcomeMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            // Process form input based on the current formState
            if (formState == 0) {
                // Collect the name
                userData.setName(messageText);
                formState = 1;

                // Prompt for email
                SendMessage emailPrompt = new SendMessage();
                emailPrompt.setChatId(String.valueOf(chatId));
                emailPrompt.setText("Great! Now, please enter your email.");
                try {
                    execute(emailPrompt);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (formState == 1) {
                // Collect the email
                userData.setEmail(messageText);
                formState = 2;

                // Prompt for phone
                SendMessage phonePrompt = new SendMessage();
                phonePrompt.setChatId(String.valueOf(chatId));
                phonePrompt.setText("Nice, now your number? ");
                try {
                    execute(phonePrompt);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (formState == 2) {
                // Collect the phone
                userData.setPhone(messageText);
                formState = 3;

                // Prompt for gender
                SendMessage genderPrompt = new SendMessage();
                genderPrompt.setChatId(String.valueOf(chatId));
                genderPrompt.setText("Boy or Girl? ");
                try {
                    execute(genderPrompt);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (formState == 3) {
                // Collect the gender
                userData.setGender(messageText);

                // End the form and display collected data
                SendMessage confirmationMessage = new SendMessage();
                confirmationMessage.setChatId(String.valueOf(chatId));
                confirmationMessage.setText("Thank you for completing the form. Here's the collected data:\n"
                        + "Name: " + userData.getName() + "\n"
                        + "Email: " + userData.getEmail() + "\n"
                        + "Phone: " + userData.getPhone() + "\n"
                        + "Gender: " + userData.getGender());
                try {
                    execute(confirmationMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                DatabaseManager.insertUserData(userData);

                // Reset the form state for the next user
                formState = 0;
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "myfirstwithJavaBot";
    }

    @Override
    public String getBotToken() {
        return "6913984755:AAGOTIoK3dPsML-g-UqVQfW4MCQRcI8cg0Q";
    }
}
