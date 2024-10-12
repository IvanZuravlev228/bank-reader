package bank.reader.dto;

import lombok.Data;

@Data
public class Read {
    private String id;
    private String app;
    private String sender;
    private String text;
    private String operator_token;
    private String requisite;
    private String device_id;
}
