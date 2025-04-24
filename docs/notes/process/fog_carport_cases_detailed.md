
# GitHub Issues – Fog Carport

---

## 📂 User Story 1 – Easy Navigation on the Website
**As a user, I want to navigate the site easily so I can find information and order a carport without confusion.**
**Labels:** `sprint: 1`

### 🔹 Issue 1.1 – Implement sticky homepage navigation
📌 *What this is:* Adds a navigation bar that stays at the top of the screen while scrolling.  
🎯 *Why it matters:* Helps users quickly jump to configurator, info, or contact without losing track.  
- [ ] Add sticky header with links to configurator, info, and contact  
- [ ] Ensure it adjusts based on screen size (mobile-friendly)  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: frontend`, `area: UX`

### 🔹 Issue 1.2 – Add breadcrumb navigation to configurator
📌 *What this is:* Adds a step indicator for the configuration flow.  
🎯 *Why it matters:* Makes it clear which step the user is on, and provides a path to go back if needed.  
- [ ] Show current step and allow going back  
- [ ] Highlight active step  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: UX`

### 🔹 Issue 1.3 – Add footer with key information
📌 *What this is:* A permanent footer shown at the bottom of all pages.  
🎯 *Why it matters:* Gives users a consistent place to find contact details and opening hours.  
- [ ] Include address, phone, email  
- [ ] Add opening hours and a link to support or live chat  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: UX`

---

## 📂 User Story 2 – Clear Configuration Process
**As a user, I want a clear step-by-step configuration process so I can customize my carport easily.**
**Labels:** `sprint: 1`

### 🔹 Issue 2.1 – Build 3-step configuration flow
📌 *What this is:* Creates the basic structure of the configuration process with three clear steps.  
🎯 *Why it matters:* Provides clarity, structure, and validation for user inputs across the process.  
- [ ] Implement step navigation: 1) Size, 2) Material, 3) Summary  
- [ ] Prevent users from skipping steps  
- [ ] Allow navigation back to previous steps  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: UX`

### 🔹 Issue 2.2 – Visual indicators for step completion
📌 *What this is:* Adds small UI cues like checkmarks to indicate a step is complete.  
🎯 *Why it matters:* Gives users feedback that they've completed a section successfully.  
- [ ] Add checkmark or color change for completed steps  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: UX`

### 🔹 Issue 2.3 – Save progress using local storage
📌 *What this is:* Stores the user’s configuration locally in the browser.  
🎯 *Why it matters:* Prevents loss of data if the page is accidentally closed or refreshed.  
- [ ] Store configuration input locally in the browser  
- [ ] Restore progress if page is refreshed or reopened  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: UX`

### 🔹 Issue 2.4 – Ensure flow works for guests and sales users
📌 *What this is:* Makes the configurator accessible to both logged-in sales users and guests.  
🎯 *Why it matters:* Supports flexibility for both end users and internal staff during phone orders.  
- [ ] Support configuration for both unauthenticated and logged-in users  
- [ ] Plan for sales role having access to additional internal fields (separate task)  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: UX`

---

## 📂 User Story 3 – Order Review
**As a user, I want to review my order before submitting so I can make sure everything is correct.**
**Labels:** `sprint: 2`

### 🔹 Issue 3.1 – Build order confirmation page (not a popup)
📌 *What this is:* Adds a full page where users see their order before confirming.  
🎯 *Why it matters:* Builds trust and reduces errors by letting users double-check.  
- [ ] Display order summary with all selections and customer input  
- [ ] Provide "Back" and "Confirm Order" buttons  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: UX`

### 🔹 Issue 3.2 – Add PDF download/print button for summary
📌 *What this is:* Lets users export a copy of their order as a PDF.  
🎯 *Why it matters:* Useful for physical record keeping or emailing later.  
- [ ] Generate downloadable version of the order summary  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: UX`

### 🔹 Issue 3.3 – Send confirmation email to customer
📌 *What this is:* Automatically sends a confirmation email after the user submits their order.  
🎯 *Why it matters:* Confirms that the order was received and builds trust.  
- [ ] Email includes order summary and contact details  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: email`

### 🔹 Issue 3.4 – Add free-text comment field to order confirmation
📌 *What this is:* Lets users add delivery notes or custom requests with their order.  
🎯 *Why it matters:* Increases flexibility for customers with special instructions.  
- [ ] Field for user to add delivery instructions or notes  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: UX`

---

## ✅ User Story 4 – Customer Database
**As a developer, I want to store customer data in a structured way.**
**Labels:** `sprint: 1`

### 🔹 Issue 4.1 – Create customer table in PostgreSQL (no company field)
📌 *What this is:* Adds a table to store individual customer details like name and contact info.  
🎯 *Why it matters:* Enables linking orders to customers and contacting them post-purchase.  
- [ ] Fields: name, email, phone, address  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: database`

### 🔹 Issue 4.2 – Add DAO methods for customer CRUD
📌 *What this is:* Implements backend methods to create, read, and update customer entries.  
🎯 *Why it matters:* Makes it possible for both the configurator and sales tools to work with customer data.  
- [ ] Create, read, and update customer data  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: database`

### 🔹 Issue 4.3 – Validate email format at form level
📌 *What this is:* Adds basic validation to ensure email input is in a valid format before submission.  
🎯 *Why it matters:* Reduces invalid submissions and improves overall data quality.  
- [ ] Ensure email input has correct structure (basic check)  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: validation`

---

## 📂 User Story 5 – Order Database Table
**As a developer, I want to store orders and link them to customers.**
**Labels:** `sprint: 1`

### 🔹 Issue 5.1 – Create order table (all-in-one structure)
📌 *What this is:* Adds a table to store each submitted order with customer link and configuration data.  
🎯 *Why it matters:* Central place to hold and access customer orders — essential for admin tools and customer tracking.  
- [ ] Include fields: order ID, customer ID, date, status, price, and all config data  
- [ ] Include salesperson ID  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: database`

### 🔹 Issue 5.2 – Add DAO methods for order table
📌 *What this is:* Backend logic for working with orders — insert, retrieve, modify, delete.  
🎯 *Why it matters:* Supports the full lifecycle of an order — from creation to cancellation or update.  
- [ ] Create, read, update, delete orders  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: database`

### 🔹 Issue 5.3 – Implement order status history tracking
📌 *What this is:* Adds a second table to track how the status of an order changes over time.  
🎯 *Why it matters:* Important for transparency, internal processes, and customer-facing tracking tools.  
- [ ] Create order_status_history table  
- [ ] Track: order_id, status, updated_by, timestamp  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: tracking`

---

## 📂 User Story 6 – Carport Configuration (logic structure)
**As a developer, I want to work with carport configuration logic to ensure it is consistent across the system.**
**Labels:** `sprint: 1`

### 🔹 Issue 6.1 – Define required config fields
📌 *What this is:* Lists the exact config values we store for each order.  
🎯 *Why it matters:* Ensures standardization and makes it easier to reuse config data for other features.  
- [ ] Width, length, height  
- [ ] Roof type (flat / pitched)  
- [ ] Angle for pitched roof  
- [ ] Material (e.g., wood, steel)  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: config`

### 🔹 Issue 6.2 – Validate min/max values for size
📌 *What this is:* Adds logic to ensure carport size is within valid physical limits.  
🎯 *Why it matters:* Prevents errors or unrealistic submissions that could break visual or structural logic.  
- [ ] Prevent invalid dimensions (e.g., width < 2m, height > 5m)  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: validation`

---

## 📂 User Story 7 – Hikari Connection Pool
**As a developer, I want fast and efficient database connections.**
**Labels:** `sprint: 1`

### 🔹 Issue 7.1 – Add HikariCP to project dependencies
📌 *What this is:* Integrates HikariCP (a high-performance JDBC connection pool) into the backend project.  
🎯 *Why it matters:* Improves performance and reliability when accessing the database under load.  
- [ ] Add HikariCP to Maven/Gradle  
- [ ] Make sure it's compatible with PostgreSQL  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: config`

### 🔹 Issue 7.2 – Configure HikariCP with environment-based DB support
📌 *What this is:* Dynamically configures HikariCP to use different database connections based on the environment.  
🎯 *Why it matters:* Supports better development and production practices by separating test and live DB setups.  
- [ ] Load credentials depending on environment: `production` or `test`  
- [ ] Separate DB URLs and settings in environment configs  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: config`

---

## 📂 User Story 8 – Jetty Web Server Setup
**As a developer, I want to run the backend on a lightweight Java server.**
**Labels:** `sprint: 1`

### 🔹 Issue 8.1 – Add Jetty to the project
📌 *What this is:* Adds Jetty web server as a dependency in the backend project.  
🎯 *Why it matters:* Enables local and production hosting of the backend without needing a large application server.  
- [ ] Add Jetty dependencies (based on start code)  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: config`

### 🔹 Issue 8.2 – Configure Jetty for serving HTML pages
📌 *What this is:* Sets up Jetty to serve static and dynamic content using Thymeleaf.  
🎯 *Why it matters:* Allows users to interact with a multipage web application on both desktop and mobile.  
- [ ] Serve HTML using Thymeleaf templates  
- [ ] Set up basic route handling for multiple views (e.g., home, configurator)  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: config`

---

## 📂 User Story 9 – Deploy to Digital Ocean
**As a developer, I want to deploy the app online so it's accessible to users.**
**Labels:** `sprint: 2`

### 🔹 Issue 9.1 – Create Digital Ocean droplet
📌 *What this is:* Sets up a virtual private server (droplet) for hosting the backend.  
🎯 *Why it matters:* Provides a live production environment accessible from anywhere with internet.  
- [ ] Install Java and PostgreSQL  
- [ ] Set up SSH access and firewall rules  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: devops`, `area: deploy`

### 🔹 Issue 9.2 – Deploy the application
📌 *What this is:* Moves the compiled backend project to the cloud and starts it via Jetty.  
🎯 *Why it matters:* Makes the application live for users to interact with the carport configurator and dashboard.  
- [ ] Upload files and start Jetty manually  
- [ ] Confirm app is reachable via IP or domain  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: devops`, `area: deploy`

---

## 📂 User Story 10 – Unit Tests
**As a developer, I want to test core logic to ensure it works correctly.**
**Labels:** `sprint: 2`

### 🔹 Issue 10.1 – Unit test for price calculation
📌 *What this is:* Tests logic for calculating carport price, including extras like sheds or EV charger.  
🎯 *Why it matters:* Ensures consistent, predictable pricing logic across the app.  
- [ ] Write tests for base carport price  
- [ ] Include extras like shed, paint, EV charger  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: test`, `area: backend`

### 🔹 Issue 10.2 – Input validation tests for forms
📌 *What this is:* Checks that form validations work for required fields and correct email formatting.  
🎯 *Why it matters:* Prevents bad user input and improves frontend reliability.  
- [ ] Test that required fields cannot be empty  
- [ ] Validate that email input is properly formatted  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: test`, `area: backend`

### 🔹 Issue 10.3 – Unit tests for bill of materials (stykliste)
📌 *What this is:* Verifies that the materials list reflects the user’s configuration.  
🎯 *Why it matters:* Ensures users and sales staff receive accurate, reliable materials info.  
- [ ] Confirm that material quantities match expected output  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: test`, `area: backend`

### 🔹 Issue 10.4 – Measure and monitor code coverage
📌 *What this is:* Tracks which parts of the codebase are tested.  
🎯 *Why it matters:* Helps ensure quality and avoid gaps in critical logic.  
- [ ] Use a coverage tool (e.g., JaCoCo)  
- [ ] Add coverage report to build process  
- [ ] Aim for at least 85% code coverage  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: test`, `area: backend`

---

## 📂 User Story 11 – Integration Tests
**As a developer, I want to test the full flow from configuration to order submission so I know the most important parts of the system work together.**
**Labels:** `sprint: 2`

### 🔹 Issue 11.1 – Write integration test for configuration + order flow
📌 *What this is:* End-to-end test to simulate the order process from config to DB + PDF.  
🎯 *Why it matters:* Proves that the key customer journey actually works.  
- [ ] Simulate HTTP request to submit carport configuration  
- [ ] Validate correct database entry is created  
- [ ] Check if PDF is generated successfully  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: test`, `area: integration`

### 🔹 Issue 11.2 – Test error handling (e.g., missing fields)
📌 *What this is:* Checks how the system handles bad input or missing required data.  
🎯 *Why it matters:* Prevents crashes and ensures users are shown clear errors.  
- [ ] Simulate incomplete or invalid order request  
- [ ] Ensure system returns clear error messages  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: test`, `area: integration`

---

## 📂 User Story 12 – Design UI with Figma
**As a designer, I want to create a Figma prototype so we can plan and review the user interface.**
**Labels:** `sprint: 1`

### 🔹 Issue 12.1 – Mockup: "Design your own carport" homepage
📌 *What this is:* The landing page with call-to-action, brand intro, and menu.  
🎯 *Why it matters:* Sets the tone and helps users begin the configuration process.  
- [ ] Include main CTA, menu, and branding  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: design`, `area: frontend`

### 🔹 Issue 12.2 – Mockup: Step 1 – Enter measurements
📌 *What this is:* First step in carport builder where users input basic dimensions.  
🎯 *Why it matters:* Critical for defining the base structure of the carport.  
- [ ] Fields for width, length, height  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: design`, `area: frontend`

### 🔹 Issue 12.3 – Mockup: Step 2 – Choose materials
📌 *What this is:* UI for selecting wood, roof type, and extras.  
🎯 *Why it matters:* Ensures a smooth experience when customizing the carport.  
- [ ] Options for wood type, roof type, etc.  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: design`, `area: frontend`

### 🔹 Issue 12.4 – Mockup: Step 3 – Review configuration
📌 *What this is:* Summary step showing all selected options.  
🎯 *Why it matters:* Gives user a chance to verify and go back if needed.  
- [ ] Summary of selections, edit links  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: design`, `area: frontend`

### 🔹 Issue 12.5 – Mockup: Confirmation page
📌 *What this is:* Final confirmation screen after placing an order.  
🎯 *Why it matters:* Confirms to the user that the order has been received.  
- [ ] Final confirmation message + order ID  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: design`, `area: frontend`

### 🔹 Issue 12.6 – Mockup: Contact page
📌 *What this is:* Simple form for users to reach out to Fog.  
🎯 *Why it matters:* Gives users a way to ask questions or report issues.  
- [ ] Fields for name, email, message  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: design`, `area: frontend`

### 🔹 Issue 12.7 – Mockup: Sales dashboard + details page
📌 *What this is:* Internal tool for staff to view orders and see details.  
🎯 *Why it matters:* Enables staff to assist customers and process orders efficiently.  
- [ ] Overview of orders, filters, and links to order detail page  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: design`, `area: admin`

### 🔹 Issue 12.8 – Mockup: Payment page (quote received)
📌 *What this is:* Where customers pay after receiving a quote.  
🎯 *Why it matters:* Final step before the order goes into production.  
- [ ] Show price, payment method, order info  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: design`, `area: frontend`

### 🔹 Issue 12.9 – Mockup: Payment confirmation page
📌 *What this is:* Thank-you screen after a successful payment.  
🎯 *Why it matters:* Provides reassurance and receipt after checkout.  
- [ ] Success message and receipt  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: design`, `area: frontend`

### 🔹 Issue 12.10 – Mockup: Track order page
📌 *What this is:* Allows user to check their order status by ID.  
🎯 *Why it matters:* Reduces support workload and keeps users informed.  
- [ ] Input order ID → show current status  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: design`, `area: frontend`

### 🔹 Issue 12.11 – Mobile versions for all pages
📌 *What this is:* Responsive versions of all mockups for mobile screens.  
🎯 *Why it matters:* Ensures a smooth user experience on phones and tablets.  
- [ ] Responsive mobile versions for each mockup  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: design`, `area: responsive`

---

## 📂 User Story 13 – Validate Address with Zoning Plan
**As a user, I want the system to check my address against the local development plan so I can be informed about zoning restrictions.**
**Labels:** `sprint: 2`

### 🔹 Issue 13.1 – Design address input field in configurator
📌 *What this is:* Adds an address input to the configuration form.  
🎯 *Why it matters:* Starts the process of validating zoning rules based on location.  
- [ ] Add address input to configuration step  
- [ ] Trigger automatic zoning check after typing complete  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: extra-feature`

### 🔹 Issue 13.2 – Simulate zoning plan API response (mock service)
📌 *What this is:* Backend logic that pretends to check the address against a zoning API.  
🎯 *Why it matters:* Lets us build and test the feature without needing a real external service.  
- [ ] Create mock service that returns zoning status for test addresses  
- [ ] Include fallback case with "N/A"  
- **Labels:** `t-shirt-size: M`, `priority: low`, `type: backend`, `area: extra-feature`

### 🔹 Issue 13.3 – Display zoning info to user AND sales staff
📌 *What this is:* Shows the zoning result in the UI for both users and internal staff.  
🎯 *Why it matters:* Informs the customer and helps sales guide the order process.  
- [ ] Show result: allowed, restricted, or N/A  
- [ ] Message if N/A: "Please check your local development plan"  
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: extra-feature`

---

## 📂 User Story 14 – View and Filter Incoming Orders
**As a salesperson, I want to view all orders and filter them by status or date so I can process them efficiently.**
**Labels:** `sprint: 3`

### 🔹 Issue 14.1 – Display order table for sales team
📌 *What this is:* Shows a list of orders with key info in a dashboard view.  
🎯 *Why it matters:* Allows staff to quickly review and manage active orders.  
- [ ] Show customer name, order date, assigned salesperson, and status  
- [ ] Default view shows orders assigned to logged-in salesperson  
- [ ] Allow viewing all orders with filter override  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: admin`

### 🔹 Issue 14.2 – Add filter by order status
📌 *What this is:* Adds dropdown or button filters for common order statuses.  
🎯 *Why it matters:* Speeds up navigation and task prioritization for sales.  
- [ ] Include statuses: Request received, Quote sent, Payment received, Preparing order, Order shipped, Completed, Cancelled, Refunded  
- [ ] Default filter shows orders for current salesperson  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: admin`

### 🔹 Issue 14.3 – Add search and sorting functionality
📌 *What this is:* Adds interactive filters for searching and sorting the order list.  
🎯 *Why it matters:* Helps sales staff locate and process specific orders more efficiently.  
- [ ] Sort by date and status  
- [ ] Search by customer name or order ID  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: admin`

---

## 📂 User Story 15 – Add Internal Notes to an Order
**As a salesperson, I want to write internal notes on an order so I can document conversations with the customer.**
**Labels:** `sprint: 3`

### 🔹 Issue 15.1 – Add internal notes field in database
📌 *What this is:* Creates a table for storing order-specific notes written by staff.  
🎯 *Why it matters:* Keeps a history of customer communication within the order.  
- [ ] Add notes table linked to orders (with author and timestamp)  
- [ ] Include fields for note content, note type (e.g., call, meeting, reminder), and tag (e.g., Payment, Delivery)  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: backend`, `area: admin`

### 🔹 Issue 15.2 – Create note input section in UI
📌 *What this is:* Frontend UI to allow sales staff to write and submit new notes.  
🎯 *Why it matters:* Makes it easy to keep track of customer interactions.  
- [ ] Text field for entering note content  
- [ ] Dropdown to select note type and tags  
- [ ] Save button to submit note  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: frontend`, `area: admin`

### 🔹 Issue 15.3 – Display note history on order detail page
📌 *What this is:* A chronological list of previously written notes on an order.  
🎯 *Why it matters:* Allows staff to quickly understand past communication.  
- [ ] Show notes in reverse chronological order (newest first)  
- [ ] Display author, timestamp, type, and tag  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: frontend`, `area: admin`

### 🔹 Issue 15.4 – Permissions for notes
📌 *What this is:* Defines who can view, edit, or delete internal notes.  
🎯 *Why it matters:* Prevents accidental changes and preserves internal record integrity.  
- [ ] All salespeople can view each other’s notes  
- [ ] Clarify behavior for editing/deleting notes (to be decided or configurable)  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: backend`, `area: admin`

---

## 📂 User Story 16 – Change Order Status
**As a salesperson, I want to update the status of an order so I can reflect its progress.**
**Labels:** `sprint: 3`

### 🔹 Issue 16.1 – Add dropdown to update order status
📌 *What this is:* Dropdown menu to select the current status of an order.  
🎯 *Why it matters:* Keeps both the customer and sales team informed about the order’s progress.  
- [ ] Options: Request received, Quote sent, Payment received, Preparing order, Order shipped, Completed, Cancelled, Refunded  
- [ ] Prevent skipping steps (e.g., cannot jump from Quote sent to Completed)  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: frontend`, `area: admin`

### 🔹 Issue 16.2 – Save status changes in backend and DB
📌 *What this is:* Stores the current status in the database when updated.  
🎯 *Why it matters:* Keeps status history traceable and accurate.  
- [ ] Store updated status in database  
- [ ] Track who made the change and when  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: admin`

### 🔹 Issue 16.3 – Require confirmation for "Cancelled" and "Refunded"
📌 *What this is:* Adds a safeguard for critical status changes.  
🎯 *Why it matters:* Prevents accidental or unauthorized status updates.  
- [ ] Ask for sales manager or economics division approval (simple toggle for now)  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: admin`

### 🔹 Issue 16.4 – Determine customer notifications per status
📌 *What this is:* Logic to decide which status changes should trigger customer emails.  
🎯 *Why it matters:* Keeps customers informed without spamming them on minor updates.  
- [ ] Only trigger email for relevant statuses (e.g., Payment received, Order shipped)  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: comms`

### 🔹 Issue 16.5 – Add optional comment field when changing status
📌 *What this is:* Optional field to include a reason for the status change.  
🎯 *Why it matters:* Useful for internal documentation or customer-facing clarification.  
- [ ] Optional comment for notes like “paid manually” or “delayed delivery”  
- [ ] Skip if status is changed automatically  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: backend`, `area: admin`

---

## 📂 User Story 17 – Add-ons
**As a customer, I want to be able to customize and have add-ons.**
**Labels:** `extra-feature`

### 🔹 Issue 17.1 – Add shed option to configuration
📌 *What this is:* Adds a checkbox to include a shed in the design.  
🎯 *Why it matters:* Increases flexibility and value of carport offering.  
- [ ] Checkbox in configurator and field in DB  
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: extra-feature`

### 🔹 Issue 17.2 – Add paint option
📌 *What this is:* Let the user choose paint options for their carport.  
🎯 *Why it matters:* Offers more customization and visual appeal.  
- [ ] Allow choosing paint type/color  
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: extra-feature`

### 🔹 Issue 17.3 – Add EV charger option
📌 *What this is:* Adds an option to include an electric vehicle charger.  
🎯 *Why it matters:* Adds a modern feature aligned with sustainability trends.  
- [ ] Checkbox and field in backend logic  
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: extra-feature`

### 🔹 Issue 17.4 – Gutter option in configuration
📌 *What this is:* Adds the ability to include rain gutters in the design.  
🎯 *Why it matters:* Increases the functional value of the carport in rainy climates.  
- [ ] Optional gutters, material type?  
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: extra-feature`

### 🔹 Issue 17.5 – Recommend external installer based on postcode
📌 *What this is:* Suggests a local installer based on user's location.  
🎯 *Why it matters:* Adds helpful service without requiring Fog to offer installation.  
- [ ] Show contact to local partners depending on address  
- **Labels:** `t-shirt-size: M`, `priority: low`, `type: frontend`, `area: extra-feature`

---

## 📂 User Story 18 – Login for Internal Users
**As a salesperson or manager, I want to log in securely so I can access role-based tools.**
**Labels:** `sprint: 3`

### 🔹 Issue 18.1 – Create login form (HTML page)
📌 *What this is:* A login page where internal users enter credentials.  
🎯 *Why it matters:* Required for secure access to tools like dashboards and customer info.  
- [ ] Add login fields (username and password)  
- [ ] Display login errors clearly  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: frontend`, `area: auth`

### 🔹 Issue 18.2 – Implement backend authentication
📌 *What this is:* Verifies login data against database using secure hashing.  
🎯 *Why it matters:* Protects the platform from unauthorized access.  
- [ ] Validate credentials against database  
- [ ] Use hashed passwords (e.g., BCrypt)  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: auth`

### 🔹 Issue 18.3 – Handle session or token management
📌 *What this is:* Tracks the login session and times it out automatically.  
🎯 *Why it matters:* Improves security by preventing stale sessions from staying open.  
- [ ] Store session data securely  
- [ ] Auto-logout after inactivity (e.g., 30 min)  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: auth`

### 🔹 Issue 18.4 – Support multiple user roles (salesperson, manager, admin)
📌 *What this is:* Assigns and enforces permissions based on user type.  
🎯 *Why it matters:* Ensures only authorized users can see or change specific data.  
- [ ] Different dashboards/permissions based on role  
- [ ] Store role info in user table  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: auth`

### 🔹 Issue 18.5 – Add "Forgot password" functionality
📌 *What this is:* Adds a way to reset a forgotten password via email or simulation.  
🎯 *Why it matters:* Prevents lockouts and makes login recovery possible.  
- [ ] Input field to request password reset link  
- [ ] Backend route to process and send reset email (or simulated)  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: auth`

---

## 📂 User Story 19 – Frontend Pages for Sales System
**As a developer, I want to create login and dashboard pages for sales staff and managers.**
**Labels:** `sprint: 3`

### 🔹 Issue 19.1 – Create login HTML page with branding
📌 *What this is:* A styled login screen that matches the Fog branding.  
🎯 *Why it matters:* Gives internal users a consistent and professional experience.  
- [ ] Add form with username and password fields  
- [ ] Include "show/hide password" toggle  
- [ ] Redirect user to appropriate dashboard based on role  
- [ ] Add Fog branding/logo  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: frontend`, `area: auth`

### 🔹 Issue 19.2 – Create dashboard page for sales staff
📌 *What this is:* A page showing only the orders relevant to the logged-in salesperson.  
🎯 *Why it matters:* Makes it easier to manage customer relationships and follow up.  
- [ ] Display sections for "My Orders" and "Unassigned Orders"  
- [ ] Add quick filters: My Orders, All Orders, Unpaid Orders  
- [ ] Show order table with filters and sort options  
- [ ] Allow staff to assign themselves to unassigned orders  
- [ ] Display recent activity (e.g., last order update)  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: admin`

### 🔹 Issue 19.3 – Create dashboard page for sales managers
📌 *What this is:* A broader admin dashboard with team management tools.  
🎯 *Why it matters:* Empowers managers to oversee performance and manage accounts.  
- [ ] Show all orders by default  
- [ ] Add access to "Add Staff", order cancellation, and refund approval  
- [ ] Show team performance metrics (e.g., orders per salesperson)  
- [ ] Include a revenue chart:  
  - Lines for current sales, last year, and sales goal  
  - Filterable by month and quarter  
- **Labels:** `t-shirt-size: L`, `priority: high`, `type: frontend`, `area: admin`

### 🔹 Issue 19.4 – Manager: Add Sales Staff page
📌 *What this is:* A page where managers can create new accounts and assign roles.  
🎯 *Why it matters:* Allows onboarding of new team members without external help.  
- [ ] Create page where manager can add new sales accounts  
- [ ] Assign roles on creation  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: frontend`, `area: admin`

---

## 📂 User Story 20 – Server Monitoring
**As a developer, I want to monitor server health so we can react to crashes or performance issues.**
**Labels:** `extra-feature`

### 🔹 Issue 20.1 – Add minimal server monitoring (CPU, memory)
📌 *What this is:* Monitors server resource usage using simple tools or libraries.  
🎯 *Why it matters:* Helps identify if the server is overloaded or failing.  
- [ ] Use built-in OS tools or lightweight Java libs  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: devops`, `area: monitoring`

### 🔹 Issue 20.2 – Send alert emails to IT support if server fails
📌 *What this is:* Sends an alert if the server goes down or hits an error threshold.  
🎯 *Why it matters:* Ensures issues are noticed and resolved quickly.  
- [ ] Integrate error/exception notification by email  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: devops`, `area: monitoring`

---

## 📂 User Story 21 – Contact Form
**As a customer, I want to contact Fog for questions, quotes, or help.**
**Labels:** `extra-feature`

### 🔹 Issue 21.1 – Build contact form
📌 *What this is:* A form for website visitors to send messages to Fog.  
🎯 *Why it matters:* Provides a simple way for customers to reach out for help or information.  
- [ ] Fields: Name, Email, Phone, Message, Subject, Order ID, Preferred Contact Method  
- [ ] Show confirmation message after submission  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: contact`

### 🔹 Issue 21.2 – Send contact messages to sales via email
📌 *What this is:* Backend process to email submitted contact form data to the team.  
🎯 *Why it matters:* Gets customer requests in front of the right people fast.  
- [ ] Format message and send via SMTP  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: contact`

### 🔹 Issue 21.3 – Add CAPTCHA to prevent spam
📌 *What this is:* Simple bot protection on the contact form.  
🎯 *Why it matters:* Keeps the form useful by stopping junk messages.  
- [ ] Use Google reCAPTCHA or a simple math challenge  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: frontend`, `area: contact`

### 🔹 Extra Feature – Add internal help desk / support inbox
📌 *What this is:* A backend tool for managing customer service interactions (future idea).  
🎯 *Why it matters:* Could improve internal handling of support requests and long-term tracking.  
- [ ] Create a customer service dashboard (future feature)  
- **Labels:** `t-shirt-size: L`, `priority: low`, `type: backend`, `area: contact`

---

## 📂 User Story 22 – FAQ Page
**As a customer, I want to see answers to common questions.**
**Labels:** `extra-feature`

### 🔹 Issue 22.1 – Create static FAQ page
📌 *What this is:* Build a hardcoded HTML page with a list of frequently asked questions.  
🎯 *Why it matters:* Reduces support load by answering basic questions in advance.  
- [ ] Hardcoded HTML with initial content  
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: content`

### 🔹 Info Needed – FAQ Content
📌 *What this is:* We need to get the actual questions and answers from Fog.  
🎯 *Why it matters:* Ensures the FAQ reflects real customer concerns.  
- [ ] Request FAQ content from Fog (add to Questions for Customer)  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: planning`, `area: content`

### 🔹 Extra Feature – Make FAQ editable by Fog
📌 *What this is:* Admin panel for Fog to update the FAQ themselves.  
🎯 *Why it matters:* Allows non-technical staff to keep the content up to date.  
- [ ] Build admin edit panel + DB connection (future feature)  
- **Labels:** `t-shirt-size: L`, `priority: low`, `type: backend`, `area: content`

---

## 📂 User Story 23 – Terms and Conditions Page
**As a customer, I want to read the terms and conditions before I place an order.**
**Labels:** `extra-feature`

### 🔹 Issue 23.1 – Add "Terms and Conditions" page
📌 *What this is:* A static page outlining the legal policies of ordering a carport.  
🎯 *Why it matters:* Protects both the business and the customer with clear expectations.  
- [ ] Create a static HTML page with legal content  
- [ ] Link it from the footer and the order confirmation step  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: legal`

---

## 📂 User Story 24 – Print-Friendly FAQ
**As a customer, I want to print the FAQ so I can read it offline or share it.**
**Labels:** `extra-feature`

### 🔹 Issue 24.1 – Add print-friendly stylesheet for FAQ page
📌 *What this is:* CSS style for when the FAQ is printed.  
🎯 *Why it matters:* Improves user experience for those who want a physical copy.  
- [ ] Create @media print style to hide nav/footers and optimize layout  
- **Labels:** `t-shirt-size: S`, `priority: low`, `type: frontend`, `area: content`

---

## 📂 User Story 25 – Accessibility Features
**As a user, I want the site to be accessible so everyone can use it comfortably.**
**Labels:** `extra-feature`

### 🔹 Issue 25.1 – Add keyboard navigation and tab order
📌 *What this is:* Ensure all navigation and buttons work via keyboard.  
🎯 *Why it matters:* Improves usability for keyboard-only users.  
- [ ] Ensure all interactive elements can be reached with tab key  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: accessibility`

### 🔹 Issue 25.2 – Ensure contrast and font readability
📌 *What this is:* Visual accessibility check for color contrast and font sizes.  
🎯 *Why it matters:* Makes the site easier to read for all users, including those with vision impairments.  
- [ ] Check all text/background combinations meet WCAG AA standards  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: accessibility`

---

## 📂 User Story 26 – Manager: Material Management
**As a manager, I want to manage available materials so we can update the configurator easily.**
**Labels:** `sprint: 3`

### 🔹 Issue 26.1 – Create material database table
📌 *What this is:* Define a database table for storing material information used in the configurator.  
🎯 *Why it matters:* Allows dynamic updates to materials without code changes.  
- [ ] Store name, type, supplier, cost, availability  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: admin`

### 🔹 Issue 26.2 – Build material CRUD backend logic
📌 *What this is:* Add routes and service methods for managing materials from the backend.  
🎯 *Why it matters:* Enables staff to update material info easily.  
- [ ] Create, read, update, delete routes  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: admin`

### 🔹 Issue 26.3 – Add frontend form for material management
📌 *What this is:* A page where managers can view and edit materials.  
🎯 *Why it matters:* Improves accessibility and control over what customers can choose.  
- [ ] Table view + edit form with validation  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: frontend`, `area: admin`

---

## 📂 User Story 27 – Show Days Since Last Interaction
**As a salesperson, I want to see how many days it's been since an order was last updated.**
**Labels:** `extra-feature`

### 🔹 Issue 27.1 – Add "days since last update" column to dashboard
📌 *What this is:* A new column that shows time since an order was modified.  
🎯 *Why it matters:* Helps sales staff prioritize follow-ups and not forget inactive leads.  
- [ ] Calculate from last modified timestamp  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: admin`

---

## 📂 User Story 28 – Auto-Assign Returning Customers
**As a manager, I want returning customers to be automatically assigned to their previous salesperson.**
**Labels:** `extra-feature`

### 🔹 Issue 28.1 – Detect if new order is from returning customer
📌 *What this is:* Identifies whether the order is from someone who has ordered before.  
🎯 *Why it matters:* Improves customer service and sales efficiency.  
- [ ] Match by email or phone  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: logic`

### 🔹 Issue 28.2 – Assign order to previous rep if found
📌 *What this is:* Logic to assign returning customers to the same salesperson.  
🎯 *Why it matters:* Maintains continuity and builds customer relationships.  
- [ ] Check customer’s most recent salesperson and apply assignment  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: logic`, `extra-feature`

---

## 📂 User Story 29 – Exception Handling and User Feedback
**As a developer, I want proper exception handling so users and developers get clear feedback when something goes wrong.**
**Labels:** `sprint: 4`

### 🔹 Issue 29.1 – Handle known exceptions with custom messages
📌 *What this is:* Display user-friendly errors for expected problems.  
🎯 *Why it matters:* Improves user experience by clearly explaining what went wrong.  
- [ ] Show friendly error messages for login failures, form validation, missing fields, etc.  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: error-handling`

### 🔹 Issue 29.2 – Create global error handler
📌 *What this is:* A centralized way to catch and handle errors in backend code.  
🎯 *Why it matters:* Prevents system crashes and logs useful debugging info.  
- [ ] Catch unexpected backend errors and show generic “Something went wrong” to users  
- [ ] Log technical details to server log for developers  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: backend`, `area: error-handling`

### 🔹 Issue 29.3 – Create fallback error pages (HTML)
📌 *What this is:* User-friendly error pages for common HTTP errors.  
🎯 *Why it matters:* Makes the app feel more polished and helps users recover.  
- [ ] Design and display a nice 404 page (Page Not Found)  
- [ ] Display a styled 500 page for internal server errors  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: error-handling`

---

## 📂 User Story 30 – Send Emails via SendGrid
**As a developer, I want to send confirmation and notification emails using SendGrid so customers and sales staff are informed automatically.**
**Labels:** `sprint: 2`

### 🔹 Issue 30.1 – Set up SendGrid account and authentication
📌 *What this is:* Account setup and environment configuration to use SendGrid API.  
🎯 *Why it matters:* Enables email delivery for confirmation and notifications.  
- [ ] Create SendGrid account  
- [ ] Verify sender email and set up sender authentication  
- [ ] Create a dynamic template in SendGrid Email API  
- [ ] Create and save API key as environment variable `SENDGRID_API_KEY`  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: setup`, `area: email`

### 🔹 Issue 30.2 – Integrate SendGrid into Java project
📌 *What this is:* Add and use SendGrid Java library with reusable utility method.  
🎯 *Why it matters:* Core part of backend communication flow.  
- [ ] Add SendGrid dependency to `pom.xml`  
- [ ] Create utility class with `sendMail(String to, String name, String email, String zip)`  
- [ ] Use dynamic template ID for message content  
- [ ] Log or handle status code from response  
- **Labels:** `t-shirt-size: L`, `priority: high`, `type: backend`, `area: email`

### 🔹 Issue 30.3 – Test SendGrid integration
📌 *What this is:* Validate email logic with logs and try/catch.  
🎯 *Why it matters:* Ensures email communication is reliable.  
- [ ] Print response status to confirm success  
- [ ] Handle error cases with try-catch and logging  
- **Labels:** `t-shirt-size: S`, `priority: high`, `type: backend`, `area: email`

### 🔹 Issue 30.4 – Extend method to support flexible templates
📌 *What this is:* Add support for reusable templates with dynamic content.  
🎯 *Why it matters:* Allows reuse of same function for different types of emails.  
- [ ] Allow sending a HashMap of parameters  
- [ ] Accept template ID as a method parameter  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: backend`, `area: email`

---

## 📂 User Story 31 – SVG Drawing Integration
**As a developer, I want to create SVG drawings of carports so customers can see a visual representation of their order.**

### 🔹 Issue 31.1 – Learn SVG basics via static tutorials
📌 *What this is:* Complete exercises drawing SVG manually in HTML.  
🎯 *Why it matters:* Builds the foundation for later dynamic SVG generation.  
- [ ] Complete static SVG drawing exercises (HTML only)  
- [ ] Learn to draw rectangles, lines, text, and groups  
- [ ] Understand how to position and style elements  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: learning`, `area: frontend`, `sprint: 1`

### 🔹 Issue 31.2 – Learn dynamic SVG generation in Java
📌 *What this is:* Study how to render SVG in Java using backend logic.  
🎯 *Why it matters:* Enables generation of custom drawings based on user input.  
- [ ] Watch tutorials on generating SVG with Java  
- [ ] Explore backend generation via Thymeleaf or Javalin  
- [ ] Practice by generating simple shapes based on backend values  
- **Labels:** `t-shirt-size: M`, `priority: high`, `type: learning`, `area: backend`, `sprint: 2`

### 🔹 Issue 31.3 – Create basic carport drawing based on configuration
📌 *What this is:* Dynamic drawing of a carport that matches selected config.  
🎯 *Why it matters:* Provides visual confirmation of design choices to customer.  
- [ ] Accept width, length, and roof type from configurator  
- [ ] Generate dynamic SVG string with corresponding dimensions  
- [ ] Embed into Thymeleaf template  
- **Labels:** `t-shirt-size: L`, `priority: high`, `type: backend`, `area: drawing`, `sprint: 2`

### 🔹 Issue 31.4 – Improve precision and styling of drawing
📌 *What this is:* Enhance SVG quality for better display and interpretation.  
🎯 *Why it matters:* Ensures drawing is both accurate and aesthetically pleasing.  
- [ ] Align elements like poles, text labels, roof slope  
- [ ] Use groups and IDs for styling via CSS  
- [ ] Add tooltips or labels to describe parts  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: frontend`, `area: drawing`, `sprint: 4`

---

## 📂 User Story 32 – SEO Optimization and Marketing Input
**As a marketing team member, I want the site to follow basic SEO practices so it's easier to find and share.**
**Labels:** `extra-feature`

### 🔹 Issue 32.1 – Add meta tags and page titles
📌 *What this is:* Setup of HTML headers for SEO indexing.  
🎯 *Why it matters:* Improves visibility in search results and social shares.  
- [ ] Set unique `<title>` tags for each page  
- [ ] Add meta descriptions and keywords for each main route  
- [ ] Include viewport and charset in `<head>`  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: SEO`

### 🔹 Issue 32.2 – Add favicon and social media tags
📌 *What this is:* Visual and social media metadata.  
🎯 *Why it matters:* Makes shared links more attractive and recognizable.  
- [ ] Create and link to a favicon  
- [ ] Add Open Graph tags for better social preview (title, image, URL)  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: frontend`, `area: SEO`

### 🔹 Issue 32.3 – Involve marketing team for input
📌 *What this is:* Loop marketing team in for content review.  
🎯 *Why it matters:* Ensures site aligns with brand voice and market strategy.  
- [ ] Ask marketing to review wording on front page  
- [ ] Get suggested keywords or calls to action  
- [ ] Review FAQ and About page content from a user experience/branding perspective  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: planning`, `area: marketing`, `extra-feature`

---

## 📂 User Story 33 – Hosting Recovery & Monitoring  
**As a developer, I want to monitor uptime and be able to redeploy easily if our host goes down, so we can minimize downtime.**  
**Labels:** `extra-feature`

### 🔹 Issue 33.1 – Set up uptime monitoring  
📌 *What this is:* A simple tool to alert the team if the deployed app becomes unreachable.  
🎯 *Why it matters:* Ensures the team can react quickly if the server is down.  
- [ ] Use a service like UptimeRobot (free tier works fine)  
- [ ] Add primary site URL and set email notifications  
- [ ] Test with a manual shutdown and confirm alerts are received  
- **Labels:** `t-shirt-size: S`, `priority: medium`, `type: devops`, `area: monitoring`

### 🔹 Issue 33.2 – Create redeployment guide  
📌 *What this is:* A short technical guide describing how to redeploy the app if Digital Ocean crashes.  
🎯 *Why it matters:* Allows the team to quickly spin up a new droplet and restore service if needed.  
- [ ] Write step-by-step instructions for setting up a new droplet  
- [ ] Include instructions for setting up DB and Jetty  
- [ ] Add guide to the GitHub README or wiki  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: devops`, `area: deploy`

---

## 📂 User Story 34 – Phone Service Integration
**As a salesperson, I want to connect the system to the phone service so I can access customer data instantly during a call and make outbound calls easily.**
**Labels:** `extra-feature`

### 🔹 Issue 34.1 – Incoming call popup with customer match
📌 *What this is:* Shows a small notification when a known customer calls.  
🎯 *Why it matters:* Saves time and ensures staff have context before answering.  
- [ ] Match incoming number with database records  
- [ ] Show popup with customer name + “Go to customer” button  
- [ ] Display in bottom-right corner (non-intrusive)  
- **Labels:** `t-shirt-size: L`, `priority: medium`, `type: backend`, `area: integration`

### 🔹 Issue 34.2 – Protect unsaved work before navigating
📌 *What this is:* Adds a save prompt if a user has unsaved data before switching pages.  
🎯 *Why it matters:* Prevents accidental data loss during call handling.  
- [ ] Detect unsaved inputs on sales dashboard  
- [ ] Confirm “Save changes?” before redirecting to customer page  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: frontend`, `area: UX`

### 🔹 Issue 34.3 – Click-to-call functionality
📌 *What this is:* Lets sales staff click a customer’s phone number to call them directly.  
🎯 *Why it matters:* Streamlines outbound calls without manual dialing.  
- [ ] Use tel: links or integrate with phone client (depending on system)  
- [ ] Ensure it works on both desktop and mobile  
- **Labels:** `t-shirt-size: M`, `priority: medium`, `type: frontend`, `area: integration`