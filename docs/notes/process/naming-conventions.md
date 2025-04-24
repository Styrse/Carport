# 🔤 Naming Conventions – Fog Carport Project

Consistent naming makes your codebase easier to read, maintain, and extend across the team.

---

## 🟨 Java (Backend)

| Element         | Convention             | Example                          |
|----------------|------------------------|----------------------------------|
| **Classes**     | PascalCase             | `OrderService`, `MaterialDAO`    |
| **Methods**     | camelCase              | `calculatePrice()`, `sendMail()` |
| **Variables**   | camelCase              | `totalPrice`, `customerEmail`    |
| **Constants**   | UPPER_SNAKE_CASE       | `MAX_HEIGHT`, `SENDGRID_API_KEY` |
| **Packages**    | lowercase.with.dots    | `dk.fogcarport.service.config`   |

---

## 🟦 HTML/CSS (Frontend)

| Element         | Convention             | Example                          |
|----------------|------------------------|----------------------------------|
| **CSS classes** | kebab-case             | `main-header`, `order-summary`   |
| **IDs**         | camelCase or kebab-case| `submitButton`, `svg-container`  |
| **Templates**   | kebab-case.html        | `carport-configurator.html`      |

---

## 🟩 Database (PostgreSQL)

| Element         | Convention             | Example                          |
|----------------|------------------------|----------------------------------|
| **Tables**      | snake_case_plural      | `orders`, `materials`            |
| **Columns**     | snake_case             | `created_at`, `customer_id`      |
| **Primary Keys**| `id` or `table_id`     | `id`, `order_id`                 |
| **Foreign Keys**| `ref_table_id`         | `customer_id`, `salesperson_id`  |

---

📌 This guide is meant to improve collaboration — treat it as a team agreement, not strict enforcement.