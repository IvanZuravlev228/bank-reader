package bank.reader.conrtoller;

import bank.reader.dto.Read;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/read")
public class ReadController {
    private static final String botToken = "7654091447:AAE2k13psDkl6Y34869ua0kiU9EAtBeInSY";

    private static final String chatId = "529624024";

    @PostMapping
    public void getData(@RequestBody Read readData) {
        sendMessageToTelegram(readData);
    }

    @PostMapping("/test")
    public void testGetData() {
        Read read = new Read();
        read.setId("123456");
        read.setApp("TestApp");
        read.setSender("test test ");
        read.setText("This is a test test message");
        read.setOperator_token(" test abc123token");
        read.setRequisite("Test requisite data");
        read.setDevice_id("test device_987654");
        sendMessageToTelegram(read);
    }

    public void sendMessageToTelegram(Read readData) {
        String url = "https://api.telegram.org/bot" + botToken + "/sendMessage";

        RestTemplate restTemplate = new RestTemplate();

        String message = "ID: " + readData.getId() + "\n" +
                "App: " + readData.getApp() + "\n" +
                "Sender: " + readData.getSender() + "\n" +
                "Text: " + readData.getText() + "\n" +
                "Operator Token: " + readData.getOperator_token() + "\n" +
                "Requisite: " + readData.getRequisite() + "\n" +
                "Device ID: " + readData.getDevice_id();

        String telegramUrl = url + "?chat_id=" + chatId + "&text=" + message;

        restTemplate.exchange(telegramUrl, HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);
    }
}
