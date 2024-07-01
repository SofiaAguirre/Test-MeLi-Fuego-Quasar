package com.meli.fuegoquasar.services;

import com.meli.fuegoquasar.exceptions.InvalidMessageException;
import com.meli.fuegoquasar.entities.Satellite;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MessageDecoderService {

    public String getMessage(List<Satellite> satellites) {
        List<List<String>> messagesList = satellites.stream()
                .map(Satellite::getMessage)
                .collect(Collectors.toList());

        validateMessageLength(messagesList);

        return constructFinalMessage(messagesList);
    }

    private void validateMessageLength(List<List<String>> messagesList) {
        int messageListSize = messagesList.get(0).size();
        boolean isMessageLengthEqual = messagesList.stream()
                .allMatch(message -> message.size() == messageListSize);

        if (!isMessageLengthEqual) {
            throw new InvalidMessageException("ERROR: UNREADABLE MESSAGE. Message size is not equal on every Satellite");
        }
    }

    private String constructFinalMessage(List<List<String>> messagesList) {
        StringBuilder finalMessage = new StringBuilder();
        int messageListSize = messagesList.get(0).size();

        IntStream.range(0, messageListSize).forEach(i -> {
            String word = messagesList.stream()
                    .map(message -> message.get(i))
                    .filter(item -> !item.isEmpty())
                    .findFirst()
                    .orElse("");

            finalMessage.append(word);

            if (i != messageListSize - 1) {
                finalMessage.append(" ");
            }
        });

        return finalMessage.toString();
    }

}
