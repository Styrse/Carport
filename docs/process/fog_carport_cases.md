
# GitHub Issues – Fog Carport

---

## 📂 User Story 1 – Easy Navigation on the Website
**As a user, I want to navigate the site easily so I can find information and order a carport without confusion.**

### 🔹 Issue 1.1 – Implement sticky homepage navigation
- [ ] Add sticky header with links to configurator, info, and contact
- [ ] Ensure it adjusts based on screen size (mobile-friendly)
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: frontend`, `area: UX`

### 🔹 Issue 1.2 – Add breadcrumb navigation to configurator
- [ ] Show current step and allow going back
- [ ] Highlight active step
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: UX`

### 🔹 Issue 1.3 – Add footer with key information
- [ ] Include address, phone, email
- [ ] Add opening hours and a link to support or live chat
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: UX`

---

## 📂 User Story 2 – Clear Configuration Process
**As a user, I want a clear step-by-step configuration process so I can customize my carport easily.**

### 🔹 Issue 2.1 – Build 3-step configuration flow
- [ ] Implement step navigation: 1) Size, 2) Material, 3) Summary
- [ ] Prevent users from skipping steps
- [ ] Allow navigation back to previous steps
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: UX`

### 🔹 Issue 2.2 – Visual indicators for step completion
- [ ] Add checkmark or color change for completed steps
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: UX`

### 🔹 Issue 2.3 – Save progress using local storage
- [ ] Store configuration input locally in the browser
- [ ] Restore progress if page is refreshed or reopened
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: UX`

### 🔹 Issue 2.4 – Ensure flow works for guests and sales users
- [ ] Support configuration for both unauthenticated and logged-in users
- [ ] Plan for sales role having access to additional internal fields (separate task)
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: UX`

---

## ✅ User Story 3 – Order Review (Updated)
**As a user, I want to review my order before submitting so I can make sure everything is correct.**

### 🔹 Issue 3.1 – Build order confirmation page (not a popup)
- [ ] Display order summary with all selections and customer input
- [ ] Provide "Back" and "Confirm Order" buttons
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: UX`

### 🔹 Issue 3.2 – Add PDF download/print button for summary
- [ ] Generate downloadable version of the order summary
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: UX`

### 🔹 Issue 3.3 – Send confirmation email to customer
- [ ] Email includes order summary and contact details
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: email`

### 🔹 Issue 3.4 – Add free-text comment field to order confirmation
- [ ] Field for user to add delivery instructions or notes
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: UX`

---

## ✅ User Story 4 – Customer Database (Updated)
**As a developer, I want to store customer data in a structured way.**

### 🔹 Issue 4.1 – Create customer table in PostgreSQL (no company field)
- [ ] Fields: name, email, phone, address
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: database`

### 🔹 Issue 4.2 – Add DAO methods for customer CRUD
- [ ] Create, read, and update customer data
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: database`

### 🔹 Issue 4.3 – Validate email format at form level
- [ ] Ensure email input has correct structure (basic check)
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: validation`

---

## 📂 User Story 5 – Order Database Table
**As a developer, I want to store orders and link them to customers.**

### 🔹 Issue 5.1 – Create order table (all-in-one structure)
- [ ] Include fields: order ID, customer ID, date, status, price, and all config data
- [ ] Include salesperson ID
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: database`

### 🔹 Issue 5.2 – Add DAO methods for order table
- [ ] Create, read, update, delete orders
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: database`

### 🔹 Issue 5.3 – Implement order status history tracking
- [ ] Create order_status_history table
- [ ] Track: order_id, status, updated_by, timestamp
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: tracking`

---

## 📂 User Story 6 – Carport Configuration (logic structure)
**As a developer, I want to work with carport configuration logic to ensure it is consistent across the system.**

Although configuration data is saved directly in the order table, this logic will still be reused for validation, PDF, and visual preview.

### 🔹 Issue 6.1 – Define required config fields
- [ ] Width, length, height
- [ ] Roof type (flat / pitched)
- [ ] Angle for pitched roof
- [ ] Material (e.g., wood, steel)
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: config`

### 🔹 Issue 6.2 – Validate min/max values for size
- [ ] Prevent invalid dimensions (e.g., width < 2m, height > 5m)
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: validation`

---

(Slutningen på listen fortsætter i næste trin – for at holde det læsbart og klart. Skal jeg fortsætte med stories 7–20?)  

---

## 📂 User Story 7 – Hikari Connection Pool
**As a developer, I want fast and efficient database connections.**

### 🔹 Issue 7.1 – Add HikariCP to project dependencies
- [ ] Add HikariCP to Maven/Gradle
- [ ] Make sure it's compatible with PostgreSQL
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: config`

### 🔹 Issue 7.2 – Configure HikariCP with environment-based DB support
- [ ] Load credentials depending on environment: `production` or `test`
- [ ] Separate DB URLs and settings in environment configs
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: config`

---

## 📂 User Story 8 – Jetty Web Server Setup
**As a developer, I want to run the backend on a lightweight Java server.**

### 🔹 Issue 8.1 – Add Jetty to the project
- [ ] Add Jetty dependencies (based on start code)
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: config`

### 🔹 Issue 8.2 – Configure Jetty for serving HTML pages
- [ ] Serve HTML using Thymeleaf templates
- [ ] Set up basic route handling for multiple views (e.g., home, configurator)
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: config`

---

## 📂 User Story 9 – Deploy to Digital Ocean
**As a developer, I want to deploy the app online so it's accessible to users.**

### 🔹 Issue 9.1 – Create Digital Ocean droplet
- [ ] Install Java and PostgreSQL
- [ ] Set up SSH access and firewall rules
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: devops`, `area: deploy`

### 🔹 Issue 9.2 – Deploy the application
- [ ] Upload files and start Jetty manually
- [ ] Confirm app is reachable via IP or domain
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: devops`, `area: deploy`

---

## 📂 User Story 10 – Unit Tests
**As a developer, I want to test core logic to ensure it works correctly.**

### 🔹 Issue 10.1 – Unit test for price calculation
- [ ] Write tests for base carport price
- [ ] Include extras like shed, paint, EV charger
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: test`, `area: backend`

### 🔹 Issue 10.2 – Input validation tests for forms
- [ ] Test that required fields cannot be empty
- [ ] Validate that email input is properly formatted
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: test`, `area: backend`

### 🔹 Issue 10.3 – Unit tests for bill of materials (stykliste)
- [ ] Confirm that material quantities match expected output
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: test`, `area: backend`

### 🔹 Issue 10.4 – Measure and monitor code coverage
- [ ] Use a coverage tool (e.g., JaCoCo)
- [ ] Add coverage report to build process
- [ ] Aim for at least 85% code coverage
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: test`, `area: backend`

---

## 📂 User Story 11 – Integration Tests
**As a developer, I want to test the full flow from configuration to order submission so I know the most important parts of the system work together.**

### 🔹 Issue 11.1 – Write integration test for configuration + order flow
- [ ] Simulate HTTP request to submit carport configuration
- [ ] Validate correct database entry is created
- [ ] Check if PDF is generated successfully
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: test`, `area: integration`

### 🔹 Issue 11.2 – Test error handling (e.g., missing fields)
- [ ] Simulate incomplete or invalid order request
- [ ] Ensure system returns clear error messages
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: test`, `area: integration`

---

## 📂 User Story 12 – Design UI with Figma
**As a designer, I want to create a Figma prototype so we can plan and review the user interface.**

### 🔹 Issue 12.1 – Mockup: "Design your own carport" homepage
- [ ] Include main CTA, menu, and branding
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: design`, `area: frontend`

### 🔹 Issue 12.2 – Mockup: Step 1 – Enter measurements
- [ ] Fields for width, length, height
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: design`, `area: frontend`

### 🔹 Issue 12.3 – Mockup: Step 2 – Choose materials
- [ ] Options for wood type, roof type, etc.
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: design`, `area: frontend`

### 🔹 Issue 12.4 – Mockup: Step 3 – Review configuration
- [ ] Summary of selections, edit links
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: design`, `area: frontend`

### 🔹 Issue 12.5 – Mockup: Confirmation page
- [ ] Final confirmation message + order ID
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: design`, `area: frontend`

### 🔹 Issue 12.6 – Mockup: Contact page
- [ ] Fields for name, email, message
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: design`, `area: frontend`

### 🔹 Issue 12.7 – Mockup: Sales dashboard + details page
- [ ] Overview of orders, filters, and links to order detail page
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: design`, `area: admin`

### 🔹 Issue 12.8 – Mockup: Payment page (quote received)
- [ ] Show price, payment method, order info
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: design`, `area: frontend`

### 🔹 Issue 12.9 – Mockup: Payment confirmation page
- [ ] Success message and receipt
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: design`, `area: frontend`

### 🔹 Issue 12.10 – Mockup: Track order page
- [ ] Input order ID → show current status
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: design`, `area: frontend`

### 🔹 Issue 12.11 – Mobile versions for all pages
- [ ] Responsive mobile versions for each mockup
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: design`, `area: responsive`

---

## 📂 User Story 13 – Validate Address with Zoning Plan
**As a user, I want the system to check my address against the local development plan so I can be informed about zoning restrictions.**

### 🔹 Issue 13.1 – Design address input field in configurator
- [ ] Add address input to configuration step
- [ ] Trigger automatic zoning check after typing complete
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: extra-feature`

### 🔹 Issue 13.2 – Simulate zoning plan API response (mock service)
- [ ] Create mock service that returns zoning status for test addresses
- [ ] Include fallback case with "N/A"
- **Labels:** `t-shirt-size: M`, `priority: low`, `type: backend`, `area: extra-feature`

### 🔹 Issue 13.3 – Display zoning info to user AND sales staff
- [ ] Show result: allowed, restricted, or N/A
- [ ] Message if N/A: "Please check your local development plan"
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: extra-feature`

---

## 📂 User Story 14 – View and Filter Incoming Orders
**As a salesperson, I want to view all orders and filter them by status or date so I can process them efficiently.**

### 🔹 Issue 14.1 – Display order table for sales team
- [ ] Show customer name, order date, assigned salesperson, and status
- [ ] Default view shows orders assigned to logged-in salesperson
- [ ] Allow viewing all orders with filter override
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: admin`

### 🔹 Issue 14.2 – Add filter by order status
- [ ] Include statuses: Request received, Quote sent, Payment received, Preparing order, Order shipped, Completed, Cancelled, Refunded
- [ ] Default filter shows orders for current salesperson
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: admin`

### 🔹 Issue 14.3 – Add search and sorting functionality
- [ ] Sort by date and status
- [ ] Search by customer name or order ID
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: admin`

---

## 📂 User Story 15 – Add Internal Notes to an Order
**As a salesperson, I want to write internal notes on an order so I can document conversations with the customer.**

### 🔹 Issue 15.1 – Add internal notes field in database
- [ ] Add notes table linked to orders (with author and timestamp)
- [ ] Include fields for note content, note type (e.g., call, meeting, reminder), and tag (e.g., Payment, Delivery)
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: backend`, `area: admin`

### 🔹 Issue 15.2 – Create note input section in UI
- [ ] Text field for entering note content
- [ ] Dropdown to select note type and tags
- [ ] Save button to submit note
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: frontend`, `area: admin`

### 🔹 Issue 15.3 – Display note history on order detail page
- [ ] Show notes in reverse chronological order (newest first)
- [ ] Display author, timestamp, type, and tag
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: frontend`, `area: admin`

### 🔹 Issue 15.4 – Permissions for notes
- [ ] All salespeople can view each other’s notes
- [ ] Clarify behavior for editing/deleting notes (to be decided or configurable)
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: backend`, `area: admin`

---

## 📂 User Story 16 – Change Order Status
**As a salesperson, I want to update the status of an order so I can reflect its progress.**

### 🔹 Issue 16.1 – Add dropdown to update order status
- [ ] Options: Request received, Quote sent, Payment received, Preparing order, Order shipped, Completed, Cancelled, Refunded
- [ ] Prevent skipping steps (e.g., cannot jump from Quote sent to Completed)
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: frontend`, `area: admin`

### 🔹 Issue 16.2 – Save status changes in backend and DB
- [ ] Store updated status in database
- [ ] Track who made the change and when
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: admin`

### 🔹 Issue 16.3 – Require confirmation for "Cancelled" and "Refunded"
- [ ] Ask for sales manager or economics division approval (simple toggle for now)
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: admin`

### 🔹 Issue 16.4 – Determine customer notifications per status
- [ ] Only trigger email for relevant statuses (e.g., Payment received, Order shipped)
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: comms`

### 🔹 Issue 16.5 – Add optional comment field when changing status
- [ ] Optional comment for notes like “paid manually” or “delayed delivery”
- [ ] Skip if status is changed automatically
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: backend`, `area: admin`

---

## 📂 User Story 17 - Add-ons

### 🔹 Issue 17.1 – Add shed option to configuration
- [ ] Checkbox in configurator and field in DB
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: extra-feature`

### 🔹 Issue 17.2 – Add paint option
- [ ] Allow choosing paint type/color
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: extra-feature`

### 🔹 Issue 17.3 – Add EV charger option
- [ ] Checkbox and field in backend logic
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: extra-feature`

### 🔹 Issue 17.4 – Gutter option in configuration
- [ ] Optional gutters, material type?
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: extra-feature`

### 🔹 Issue 17.5 – Recommend external installer based on postcode
- [ ] Show contact to local partners depending on address
- **Labels:** `t-shirt-size: M`, `priority: low`, `type: frontend`, `area: extra-feature`

---

## 📂 User Story 18 – Login for Internal Users
**As a salesperson or manager, I want to log in securely so I can access role-based tools.**

### 🔹 Issue 18.1 – Create login form (HTML page)
- [ ] Add login fields (username and password)
- [ ] Display login errors clearly
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: frontend`, `area: auth`

### 🔹 Issue 18.2 – Implement backend authentication
- [ ] Validate credentials against database
- [ ] Use hashed passwords (e.g., BCrypt)
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: auth`

### 🔹 Issue 18.3 – Handle session or token management
- [ ] Store session data securely
- [ ] Auto-logout after inactivity (e.g., 30 min)
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: auth`

### 🔹 Issue 18.4 – Support multiple user roles (salesperson, manager, admin)
- [ ] Different dashboards/permissions based on role
- [ ] Store role info in user table
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: auth`

### 🔹 Issue 18.5 – Add "Forgot password" functionality
- [ ] Input field to request password reset link
- [ ] Backend route to process and send reset email (or simulated)
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: auth`

---

## 📂 User Story 19 – Frontend Pages for Sales System
**As a developer, I want to create login and dashboard pages for sales staff and managers.**

### 🔹 Issue 19.1 – Create login HTML page with branding
- [ ] Add form with username and password fields
- [ ] Include "show/hide password" toggle
- [ ] Redirect user to appropriate dashboard based on role
- [ ] Add Fog branding/logo
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: frontend`, `area: auth`

### 🔹 Issue 19.2 – Create dashboard page for sales staff
- [ ] Display sections for "My Orders" and "Unassigned Orders"
- [ ] Add quick filters: My Orders, All Orders, Unpaid Orders
- [ ] Show order table with filters and sort options
- [ ] Allow staff to assign themselves to unassigned orders
- [ ] Display recent activity (e.g., last order update)
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: admin`

### 🔹 Issue 19.3 – Create dashboard page for sales managers
- [ ] Show all orders by default
- [ ] Add access to "Add Staff", order cancellation, and refund approval
- [ ] Show team performance metrics (e.g., orders per salesperson)
- [ ] Include a revenue chart:
  - Lines for current sales, last year, and sales goal
  - Filterable by month and quarter
- **Labels:** `t-shirt-size: L`, `priority: high`, `type: frontend`, `area: admin`

### 🔹 Issue 19.4 – Manager: Add Sales Staff page
- [ ] Create page where manager can add new sales accounts
- [ ] Assign roles on creation
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: frontend`, `area: admin`

---

## 📂 User Story 20 – Server Monitoring
**As a developer, I want to monitor server health so we can react to crashes or performance issues.**
**Labels:** `extra-feature`

### 🔹 Issue 20.1 – Add minimal server monitoring (CPU, memory)
- [ ] Use built-in OS tools or lightweight Java libs
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: devops`, `area: monitoring`

### 🔹 Issue 20.2 – Send alert emails to IT support if server fails
- [ ] Integrate error/exception notification by email
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: devops`, `area: monitoring`

---

## 📂 User Story 21 – Contact Form
**As a customer, I want to contact Fog for questions, quotes, or help.**
**Labels:** `extra-feature`

### 🔹 Issue 21.1 – Build contact form
- [ ] Fields: Name, Email, Phone, Message, Subject, Order ID, Preferred Contact Method
- [ ] Show confirmation message after submission
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: contact`

### 🔹 Issue 21.2 – Send contact messages to sales via email
- [ ] Format message and send via SMTP
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: contact`

### 🔹 Issue 21.3 – Add CAPTCHA to prevent spam
- [ ] Use Google reCAPTCHA or a simple math challenge
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: frontend`, `area: contact`

### 🔹 Extra Feature – Add internal help desk / support inbox
- [ ] Create a customer service dashboard (future feature)
- **Labels:** `t-shirt-size: L`, `priority: low`, `type: backend`, `area: contact`

---

## 📂 User Story 22 – FAQ Page
**As a customer, I want to see answers to common questions.**
**Labels:** `extra-feature`

### 🔹 Issue 22.1 – Create static FAQ page
- [ ] Hardcoded HTML with initial content
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: content`

### 🔹 Info Needed – FAQ Content
- [ ] Request FAQ content from Fog (add to Questions for Customer)
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: planning`, `area: content`

### 🔹 Extra Feature – Make FAQ editable by Fog
- [ ] Build admin edit panel + DB connection (future feature)
- **Labels:** `t-shirt-size: L`, `priority: low`, `type: backend`, `area: content`

---

## 📂 User Story 23 – Terms and Conditions Page
**As a customer, I want to read the terms and conditions before I place an order.**
**Labels:** `extra-feature`

### 🔹 Issue 23.1 – Add "Terms and Conditions" page
- [ ] Create a static HTML page with legal content
- [ ] Link it from the footer and the order confirmation step
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: legal`

---

## 📂 User Story 24 – Print-Friendly FAQ
**As a customer, I want to print the FAQ so I can read it offline or share it.**
**Labels:** `extra-feature`

### 🔹 Issue 24.1 – Add print-friendly stylesheet for FAQ page
- [ ] Create @media print style to hide nav/footers and optimize layout
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: content`

---

## 📂 User Story 25 – Accessibility Features
**As a user, I want the site to be accessible so everyone can use it comfortably.**
**Labels:** `extra-feature`

### 🔹 Issue 25.1 – Add keyboard navigation and tab order
- [ ] Ensure all interactive elements can be reached with tab key
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: accessibility`

### 🔹 Issue 25.2 – Ensure contrast and font readability
- [ ] Check all text/background combinations meet WCAG AA standards
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: accessibility`

---

## 📂 User Story 26 – Manager: Material Management
**As a manager, I want to manage available materials so we can update the configurator easily.**

### 🔹 Issue 26.1 – Create material database table
- [ ] Store name, type, supplier, cost, availability
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: admin`

### 🔹 Issue 26.2 – Build material CRUD backend logic
- [ ] Create, read, update, delete routes
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: admin`

### 🔹 Issue 26.3 – Add frontend form for material management
- [ ] Table view + edit form with validation
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: admin`

---

## 📂 User Story 27 – Show Days Since Last Interaction
**As a salesperson, I want to see how many days it's been since an order was last updated.**
**Labels:** `extra-feature`

### 🔹 Issue 27.1 – Add "days since last update" column to dashboard
- [ ] Calculate from last modified timestamp
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: admin`

---

## 📂 User Story 28 – Auto-Assign Returning Customers
**As a manager, I want returning customers to be automatically assigned to their previous salesperson.**
**Labels:** `extra-feature`

### 🔹 Issue 28.1 – Detect if new order is from returning customer
- [ ] Match by email or phone
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: logic`

### 🔹 Issue 28.2 – Assign order to previous rep if found
- [ ] Check customer’s most recent salesperson and apply assignment
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: logic`, `extra-feature`

---

## 📂 User Story 29 – Exception Handling and User Feedback
**As a developer, I want proper exception handling so users and developers get clear feedback when something goes wrong.**

### 🔹 Issue 29.1 – Handle known exceptions with custom messages
- [ ] Show friendly error messages for login failures, form validation, missing fields, etc.
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: error-handling`

### 🔹 Issue 29.2 – Create global error handler
- [ ] Catch unexpected backend errors and show generic “Something went wrong” to users
- [ ] Log technical details to server log for developers
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: error-handling`

### 🔹 Issue 29.3 – Create fallback error pages (HTML)
- [ ] Design and display a nice 404 page (Page Not Found)
- [ ] Display a styled 500 page for internal server errors
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: error-handling`

---

## 📂 User Story 30 – Send Emails via SendGrid
**As a developer, I want to send confirmation and notification emails using SendGrid so customers and sales staff are informed automatically.**

### 🔹 Issue 30.1 – Set up SendGrid account and authentication
- [ ] Create SendGrid account
- [ ] Verify sender email and set up sender authentication
- [ ] Create a dynamic template in SendGrid Email API
- [ ] Create and save API key as environment variable `SENDGRID_API_KEY`
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: setup`, `area: email`

### 🔹 Issue 30.2 – Integrate SendGrid into Java project
- [ ] Add SendGrid dependency to `pom.xml`
- [ ] Create utility class with `sendMail(String to, String name, String email, String zip)`
- [ ] Use dynamic template ID for message content
- [ ] Log or handle status code from response
- **Labels:** `t-shirt-size: L`, `priority: high`, `type: backend`, `area: email`

### 🔹 Issue 30.3 – Test SendGrid integration
- [ ] Print response status to confirm success
- [ ] Handle error cases with try-catch and logging
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: email`

### 🔹 Issue 30.4 – Extend method to support flexible templates
- [ ] Allow sending a HashMap of parameters
- [ ] Accept template ID as a method parameter
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: email`

---

## 📂 User Story 31 – SVG Drawing Integration
**As a developer, I want to create SVG drawings of carports so customers can see a visual representation of their order.**

### 🔹 Issue 31.1 – Learn SVG basics via static tutorials
- [ ] Complete static SVG drawing exercises (HTML only)
- [ ] Learn to draw rectangles, lines, text, and groups
- [ ] Understand how to position and style elements
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: learning`, `area: frontend`

### 🔹 Issue 31.2 – Learn dynamic SVG generation in Java
- [ ] Watch tutorials on generating SVG with Java
- [ ] Explore backend generation via Thymeleaf or Javalin
- [ ] Practice by generating simple shapes based on backend values
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: learning`, `area: backend`

### 🔹 Issue 31.3 – Create basic carport drawing based on configuration
- [ ] Accept width, length, and roof type from configurator
- [ ] Generate dynamic SVG string with corresponding dimensions
- [ ] Embed into Thymeleaf template
- **Labels:** `t-shirt-size: L`, `priority: high`, `type: backend`, `area: drawing`

### 🔹 Issue 31.4 – Improve precision and styling of drawing
- [ ] Align elements like poles, text labels, roof slope
- [ ] Use groups and IDs for styling via CSS
- [ ] Add tooltips or labels to describe parts
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: frontend`, `area: drawing`

---

## 📂 User Story 32 – SEO Optimization and Marketing Input
**As a marketing team member, I want the site to follow basic SEO practices so it's easier to find and share.**
**Labels:** `extra-feature`

### 🔹 Issue 32.1 – Add meta tags and page titles
- [ ] Set unique `<title>` tags for each page  
- [ ] Add meta descriptions and keywords for each main route  
- [ ] Include viewport and charset in `<head>`  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: SEO`

---

### 🔹 Issue 32.2 – Add favicon and social media tags
- [ ] Create and link to a favicon  
- [ ] Add Open Graph tags for better social preview (title, image, URL)  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: SEO`

---

### 🔹 Issue 32.3 – Involve marketing team for input
- [ ] Ask marketing to review wording on front page  
- [ ] Get suggested keywords or calls to action  
- [ ] Review FAQ and About page content from a user experience/branding perspective  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: planning`, `area: marketing`, `extra-feature`