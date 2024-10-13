package bank.reader.dto;

import lombok.Data;

@Data
public class Notification {
    private String sender;
    private String text;
    private Double amount;
    private String from;
    private Long time;
}
