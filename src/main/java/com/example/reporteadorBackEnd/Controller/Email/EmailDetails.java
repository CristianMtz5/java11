package com.example.reporteadorBackEnd.Controller.Email;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    private String to;
    private String subject;
    private String body;
    private ArrayList<String> attachment;
}
