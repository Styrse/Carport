package app.controller.customer;

import app.entities.orders.Order;
import app.persistence.mappers.OrderMapper;
import io.javalin.http.Context;

public class PaymentController {

    public static void showPaymentPage(Context ctx) {
        try {
            int orderId = Integer.parseInt(ctx.queryParam("orderId"));
            Order order = OrderMapper.getOrderByOrderId(orderId);

            ctx.attribute("order", order);
            ctx.attribute("totalPrice", order.getTotalPrice());
            ctx.render("public/payment.html");

        } catch (Exception e) {
            ctx.attribute("errorMessage", "Betalingssiden kunne ikke indlæses.");
            ctx.render("public/public-error.html");
        }
    }

    public static void confirmPayment(Context ctx) {
        try {
            String rawOrderId = ctx.formParam("orderId");
            if (rawOrderId == null || rawOrderId.isBlank()) {
                ctx.attribute("errorMessage", "Fejl: Ordre-ID mangler i formularen.");
                ctx.render("public/public-error.html");
                return;
            }

            int orderId;
            try {
                orderId = Integer.parseInt(rawOrderId);
            } catch (NumberFormatException e) {
                ctx.attribute("errorMessage", "Fejl: Ugyldigt ordre-ID format.");
                ctx.render("public/public-error.html");
                return;
            }

            Order order = OrderMapper.getOrderByOrderId(orderId);
            if (order == null) {
                ctx.attribute("errorMessage", "Fejl: Ingen ordre fundet med ID " + orderId + ".");
                ctx.render("public/public-error.html");
                return;
            }

            OrderMapper.addOrderStatusHistory(orderId, "Betalt");

            ctx.attribute("order", order);
            ctx.render("public/payment-confirmation.html");

        } catch (Exception e) {
            ctx.attribute("errorMessage", "Intern fejl: Kunne ikke behandle betalingen.");
            ctx.render("public/public-error.html");
        }
    }
}
