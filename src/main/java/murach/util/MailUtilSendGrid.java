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
            System.getenv("SENDGRID_API_KEY");

    public static void sendMail(String to, String from,
                                String subject, String body,
                                boolean bodyIsHTML) throws IOException {

        if (SENDGRID_API_KEY == null || SENDGRID_API_KEY.isEmpty()) {
            throw new RuntimeException("Lỗi: SENDGRID_API_KEY chưa được cấu hình trên server!");
        }

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
