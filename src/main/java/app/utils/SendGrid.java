package app.utils;

import app.entities.users.User;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;

public class SendGrid {

    public static Email SENDER_EMAIL = new Email("styrse92@gmail.com", "Johannes Fog Byggemarked");
    public static String API_KEY = System.getenv("SENDGRID_API_KEY");

    public static void sendConfirmationEmail(User user) throws IOException {
        Mail mail = new Mail();
        mail.setFrom(SENDER_EMAIL);

        Personalization personalization = new Personalization();
        
        personalization.addTo(new Email(user.getEmail()));
        personalization.addDynamicTemplateData("name", user.getFirstName() + " " + user.getLastName());
        personalization.addDynamicTemplateData("email", user.getEmail());
        personalization.addDynamicTemplateData("password", user.getPassword());
        mail.addPersonalization(personalization);

        mail.addCategory("carportapp");

        com.sendgrid.SendGrid sg = new com.sendgrid.SendGrid(API_KEY);
        Request request = new Request();
        try
        {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            mail.templateId = "d-a9f311c2d4824d489f1f02267186dcab";
            request.setBody(mail.build());
            Response response = sg.api(request);
        }
        catch (IOException ex)
        {
            System.out.println("Error sending mail");
            throw ex;
        }
    }

    public static void sendMessage(String email, String subject, String message) throws IOException {
        Mail mail = new Mail();
        mail.setFrom(SENDER_EMAIL);

        Personalization personalization = new Personalization();

        personalization.addTo(new Email(email));
        personalization.addDynamicTemplateData("subject", subject);

        String htmlMessage = message.replace("\n", "<br>");
        personalization.addDynamicTemplateData("message", htmlMessage);
        mail.addPersonalization(personalization);

        mail.addCategory("carportapp");

        com.sendgrid.SendGrid sg = new com.sendgrid.SendGrid(API_KEY);
        Request request = new Request();
        try
        {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");

            mail.templateId = "d-4863af6913a5406fb200e18a590c3015";
            request.setBody(mail.build());
            Response response = sg.api(request);
        }
        catch (IOException ex)
        {
            System.out.println("Error sending mail");
            throw ex;
        }
    }
}
