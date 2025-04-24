# 🏗️ Fog Carport Project

This project is a web application built for **Johannes Fog**, allowing customers to design and order custom carports online. It includes dynamic SVG drawings, an internal sales dashboard, and integrations such as SendGrid for email and PostgreSQL for data management.

---

## 🚀 Tech Stack

| Area            | Technology                | Version        |
|-----------------|----------------------------|----------------|
| Language        | Java                       | 17             |
| Backend         | Javalin, Jetty             | Javalin 5.x, Jetty 11.x |
| Frontend        | Thymeleaf, HTML, CSS       | Thymeleaf 3.1, HTML5, CSS3 |
| Database        | PostgreSQL, HikariCP       | PostgreSQL 15, HikariCP 5.0 |
| DevOps          | DigitalOcean, GitHub Projects | N/A            |
| Testing         | JUnit         | JUnit 5|
| Email           | SendGrid                   | SDK 4.10.1     |
| Diagrams/Mockup | Figma, Draw.io      | Latest (Web)   |


---

## 📚 Project Standards

- 📄 [Definition of Done](docs/process/definition-of-done.md)
- 🔤 [Naming Conventions](docs/process/naming-conventions.md)

---

## 🔁 Agile Workflow

- 4 sprints (1 week each) + buffer time
- Weekly review with teacher (“customer”)
- Daily standups for internal coordination
- Issues tracked using GitHub Projects
- Feature branches with pull requests

---

## 👥 Team Roles

| Role            | Name         | Responsibilities |
|-----------------|--------------|------------------|
| Scrum Master    | Mia          | Facilitates standups, retrospectives |
| Product Owner   | Daniel       | Owns backlog, gathers feedback |
| Tech Lead       | Styrbjørn    | Oversees code, reviews, architecture |
| Wildcard        | Esben        | Supports wherever needed most |

---

## 🧪 Getting Started

1. Clone this repo
2. Configure `.env` or environment variables (e.g. `SENDGRID_API_KEY`)
3. Build and run using Maven/Jetty
4. Access locally at `http://localhost:7070`

---

## 📝 Contributor Tips

- Refer to [Definition of Done](docs/process/definition-of-done.md) before closing issues
- Follow [Naming Conventions](docs/process/naming-conventions.md) for consistency
- Always use pull requests — no direct pushes to `main`
- Include screenshots, logs, or test notes in issue comments if applicable

---

📦 Happy building! Let’s make the best carport configurator Fog have ever seen.