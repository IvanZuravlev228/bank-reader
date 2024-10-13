package bank.reader.conrtoller;

import bank.reader.dto.Notification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/read")
public class ReadController {
    private static final String botToken = "7654091447:AAE2k13psDkl6Y34869ua0kiU9EAtBeInSY";

    private static final String chatId = "529624024";

    @PostMapping("/test")
    public void testGetData() {
        Notification read = new Notification();
        read.setSender("test test ");
        read.setText("This is a test test message");
        sendMessageToTelegram(read);
    }

    @PostMapping
    public void handleRequest(@RequestBody String body) {
        String[] parses = body.split("parse");
        Notification read = new Notification();
        read.setSender(parses[0]);
        read.setText(parses[1]);

        formatSMSData(read.getText(), read);
        System.out.println(read);
        sendMessageToTelegram(read);
    }

    public void sendMessageToTelegram(Notification notification) {
        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

        RestTemplate restTemplate = new RestTemplate();

        String message = "Sender: " + notification.getSender() + "\n" +
                "Amount: " + notification.getAmount() + "\n" +
                "From: " + notification.getFrom() + "\n" +
                "Text: " + notification.getText();

        String telegramUrl = url + "?chat_id=" + chatId + "&text=" + message;

        restTemplate.exchange(telegramUrl, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);
    }

    private void formatSMSData(String data, Notification read) {
        String[] lines = data.split("\n");

        // Извлекаем сумму
        String sumLine = lines[3];
        String amountStr = sumLine.split(":")[1].trim();
        read.setAmount(Double.parseDouble(amountStr.split(" ")[0]));

        // Извлекаем отправителя
        String fromLine = lines[3];
        String fromStr = fromLine.split("UAH")[1].trim();
        read.setFrom(fromStr);

        // Извлекаем время и преобразуем в UNIX
        String timeLine = lines[1];
        String timeStr = timeLine.split(" ")[0] + " " + timeLine.split(" ")[1];
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH:mm");
        Date date = null;
        try {
            date = sdf.parse(timeStr);
        } catch (ParseException e) {
            throw new RuntimeException("Can't parse date: " + timeStr, e);
        }
        read.setTime(date.getTime() / 1000);
    }
}
