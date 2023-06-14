package com.weatherknow.Service;
import com.weatherknow.Models.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
@Slf4j
public class TemplateService {
    private final EmailService emailService;
    public void sendUpdates(Subscription subscription) {
        String subject = "Successfully subscribed to WeatherKNOW";
        StringBuilder message = new StringBuilder("<!DOCTYPE html><html lang=\"en\"><head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>WeathterKNOW</title> <style> *{ overflow-x: hidden; } body { margin: 0; padding: 0; background-color: rgb(165, 188, 232); } #logo{ width: 400px; } #content { width: 100%; background-image: url(\"/bg.webp\"); background-repeat: repeat-y; background-size:cover; display: flex; flex-wrap: wrap; flex-direction: column; align-items: center; justify-content: center; } .flex { padding: 0; margin: auto; width: 100%; display: flex; flex-wrap: wrap; flex-direction: column; align-items: center; justify-content: center; } #footer { color: aliceblue; text-align: center; height: 20%; margin: 0%; padding: 0; background-color: rgb(44, 50, 62); } #weatherBlock{ margin: 30px; width: 300px; background-color: rgba(0, 0, 0, 0.339); border-radius: 20px; padding: 20px; } </style></head><body> <div class=\"flex\"> <img id=\"logo\" src=\"/logo.webp\" alt=\"logo\"> <div id=\"content\"> <h2>Hey ");
        message.append(subscription.getName())
                .append(", Your Weather Updates!</h2>");

        subscription.getCitiesList().forEach((city)->{
            message.append("<div id=\"weatherBlock\"> <h1>")
                    .append(city.getName())
                    .append("</h1> <h3>")
                    .append(city.getTz_id())
                    .append("</h3><div><p>Last Updated: ")
                    .append(city.getLast_updated())
                    .append("</p><b>Temperature (℃): </b>")
                    .append(city.getTemp_c())
                    .append(" <b>Temperature (℉): </b>")
                    .append(city.getTemp_f())
                    .append("<div><h2>Weather Conditions:</h2><div>")
                    .append(city.getCondition().getText().toUpperCase())
                    .append(": <img src=\"")
                    .append(city.getCondition().getIcon())
                    .append("\" alt=\"weatherIcon\"></div></div><b>Humidity: </b>")
                    .append(city.getHumidity())
                    .append("<b>Cloud: </b>")
                    .append(city.getCloud())
                    .append("</div></div>");
        });
        message.append("</div></div><div id=\"footer\"><h3><a href=\"#\">Unsubscribe to all services<a></h3><p>WeathterKNOW &copy; Pranav Bisaria 2023</p></div></body></html>");
        String to = subscription.getEmail();
        this.emailService.sendEmail(subject, message.toString(), to);
    }
}