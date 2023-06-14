package com.weatherknow.Service;
import com.weatherknow.Models.Subscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
@Slf4j
public class TemplateService {
    private final EmailService emailService;

    public void sendUpdates(Subscription subscription) {
        String subject = "Successfully subscribed to WeatherKNOW";
        StringBuilder message = new StringBuilder("<!DOCTYPE html><html lang=\"en\"><head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <title>WeatherKNOW</title> <style> * { overflow-x: hidden; } body { margin: 0; padding: 0; background-color: rgb(165, 188, 232); } #logo { display: block; margin: auto; width: 400px; } .main { width: 100%; background-image: url(\"https://raw.githubusercontent.com/pranavbisaria/WeatherKNOW/master/src/main/resources/static/bg.webp\"); background-repeat: repeat-y; background-size: cover; } footer { color: aliceblue; text-align: center; height: 20%; margin: 0; padding: 0; background-color: rgb(44, 50, 62); } .weatherBlock { margin: 30px auto; width: 500px; background-color: rgba(0, 0, 0, 0.339); border-radius: 20px; padding: 20px; } @media (max-width: 720px) { #logo { width: 95%; } .weatherBlock { width: 85%; } } </style></head><body> <div class=\"main\"> <img id=\"logo\" src=\"https://raw.githubusercontent.com/pranavbisaria/WeatherKNOW/master/src/main/resources/static/logo.png\" alt=\"logo\"> <div id=\"content\"> <h2 style=\"text-align: center; margin: 5px;\">Hey ");
        message.append(subscription.getName())
                .append(", Your Weather Updates!</h2> <div id=\"WeatherInfo\">");

        subscription.getCitiesList().forEach((city)->{
            message.append(" <div class=\"weatherBlock\"> <h1>")
                    .append(city.getName())
                    .append("</h1> <h3>")
                    .append(city.getTz_id())
                    .append("</h3> <div> <p>Last Updated: ")
                    .append(city.getLast_updated())
                    .append("</p> <b>Temperature (&deg;C): </b>")
                    .append(city.getTemp_c())
                    .append("  <b>Temperature (&deg;F): </b>")
                    .append(city.getTemp_f())
                    .append("<div> <h2>Weather Conditions:</h2> <div>")
                    .append(city.getCondition().getText().toUpperCase())
                    .append(": <img src=\"https:")
                    .append(city.getCondition().getIcon())
                    .append("\" alt=\"weatherIcon\"> </div> </div> <b>Humidity: </b>")
                    .append(city.getHumidity())
                    .append(" <b>Cloud: </b>")
                    .append(city.getCloud())
                    .append(" </div> </div> ");
        });
        message.append("</div> </div> </div> <footer> <h3><a href=\"http://3.111.192.28:8080/unsubscribe/"+subscription.getEmail()+"\">Unsubscribe from all services</a></h3> <p>WeathterKNOW &copy; Pranav Bisaria 2023</p> </footer></body></html>");
        String to = subscription.getEmail();
        this.emailService.sendEmail(subject, message.toString(), to);
    }
}