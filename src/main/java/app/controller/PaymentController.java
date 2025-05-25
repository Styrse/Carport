package app.controller;

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
            e.printStackTrace();
            ctx.status(500).result("Betalingssiden kunne ikke indlæses.");
        }
    }

    public static void confirmPayment(Context ctx) {
        try {
            String rawOrderId = ctx.formParam("orderId");
            if (rawOrderId == null || rawOrderId.isBlank()) {
                ctx.status(400).result("Fejl: Ordre-ID mangler i formularen.");
                return;
            }

            int orderId;
            try {
                orderId = Integer.parseInt(rawOrderId);
            } catch (NumberFormatException e) {
                ctx.status(400).result("Fejl: Ugyldigt ordre-ID format.");
                return;
            }

            Order order = OrderMapper.getOrderByOrderId(orderId);
            if (order == null) {
                ctx.status(404).result("Fejl: Ingen ordre fundet med ID " + orderId + ".");
                return;
            }

            OrderMapper.addOrderStatusHistory(orderId, "Betalt");

            ctx.attribute("order", order);
            ctx.render("public/payment-confirmation.html");

        } catch (Exception e) {
            ctx.status(500).result("Intern fejl: Kunne ikke behandle betalingen.");
        }
    }
}
