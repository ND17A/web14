package murach.util;

import com.sendgrid.SendGrid;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.Method;

import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Content;

import java.io.IOException;

public class MailUtilSendGrid {

    private static final String SENDGRID_API_KEY =
            "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";

    public static void sendMail(String to, String from,
                                String subject, String body,
                                boolean bodyIsHTML) throws IOException {

        Email fromEmail = new Email(from);
        Email toEmail = new Email(to);

        Content content = bodyIsHTML
                ? new Content("text/html", body)
                : new Content("text/plain", body);

        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Body: " + response.getBody());
            System.out.println("Headers: " + response.getHeaders());

        } catch (IOException ex) {
            throw ex;
        }
    }
}
