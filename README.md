# Warehouse Client Order Management System

## Description
This is a Warehouse Client Order Management application that allows employees to manage client orders in a warehouse. The system provides CRUD (Create, Read, Update, Delete) operations for clients, products, and orders. Employees can create new orders by selecting clients and products, check stock availability, and generate bills after placing orders.
![PT_ASSIGN3_CRUD_REFLECTION](https://github.com/user-attachments/assets/123f728c-5907-496d-aa5c-4252482453db)
## Features
- **Client Management**: Add, edit, delete, and view clients.
- **Product Management**: Add, edit, delete, and view products with price and stock.
- **Order Management**: Create new orders by selecting clients and products, and choose quantities (with stock checks).
- **Bill Generation**: Automatically generate bills after successful orders.
- **User-Friendly Interface**: Simple, intuitive UI for performing warehouse operations.
- **Error Handling**: Clear error messages for invalid inputs like negative stock or invalid email addresses.


## Problem Analysis, Modeling, Scenarios, Use Cases

### Functional Requirements:
- The application should allow the user to select which window to display next: operations for clients, products, create a new order, or view the bills.
- After selecting an option, the corresponding window provides intuitive components to allow the user to perform the desired CRUD operations on the database.

### Non-Functional Requirements:
- The application should be intuitive and easy to use.
- The application should provide quick responses to user inputs.
- The application should handle edge cases gracefully and display friendly error messages.

### Use Cases

#### Use Case: Select Window
**Primary Actor**: Employee  
**Main Success Scenario**:
1. The employee clicks one of the buttons from the main window: clients, products, create new order, or view bills.
2. If the employee chooses “clients,” they will be directed to a window to insert, edit, delete, or view all clients in a table.
3. If the employee chooses “products,” they will be directed to a window to insert, edit, delete, or view all products in a table.
4. If the employee chooses “create new order,” they will be directed to a window to select the client for the order, then select products and quantities for the order.
5. If the employee chooses “view bills,” they will be directed to a window to view all the bills in a table.

#### Use Case: Add Product
**Primary Actor**: Employee  
**Main Success Scenario**:
1. The employee selects the option to add a new product.
2. The application displays a form to insert product details.
3. The employee inserts the product name, price, and current stock.
4. The employee clicks the “Add” button.
5. The application stores the product data in the database and displays an acknowledgment message.  
**Alternative Sequence**: Invalid product data:
- If the user enters a negative value for the product stock, the application displays an error message and requests valid input.

#### Use Case: Edit Product
**Primary Actor**: Employee  
**Main Success Scenario**:
1. The employee selects the option to edit a product.
2. The application displays a form to input updated product details.
3. The employee updates the product data.
4. The employee clicks the “Edit” button.
5. The application stores the updated product data and displays an acknowledgment message.  
**Alternative Sequence**: Invalid product data:
- If the user enters a negative stock value, an error message is displayed, and the employee is asked to input valid data.

#### Use Case: Delete Product
**Primary Actor**: Employee  
**Main Success Scenario**:
1. The employee selects the option to delete a product.
2. The application displays a form to input the product ID to be deleted.
3. The employee enters the product ID.
4. The employee clicks the “Delete” button.
5. The application deletes the product and displays an acknowledgment message.

#### Use Case: Get Products
**Primary Actor**: Employee  
**Main Success Scenario**:
1. The employee selects the option to view all products.
2. The application displays a table with all products and their details.

#### Use Case: Add Client
**Primary Actor**: Employee  
**Main Success Scenario**:
1. The employee selects the option to add a new client.
2. The application displays a form to insert client details.
3. The employee inserts the client’s name and email.
4. The employee clicks the “Insert” button.
5. The application stores the client data and displays an acknowledgment message.  
**Alternative Sequence**: Invalid client data:
- If the email format is invalid, the application shows an error message requesting a valid email.

#### Use Case: Edit Client
**Primary Actor**: Employee  
**Main Success Scenario**:
1. The employee selects the option to edit a client.
2. The application displays a form to input updated client details.
3. The employee updates the client data.
4. The employee clicks the “Edit” button.
5. The application stores the updated client data and displays an acknowledgment message.  
**Alternative Sequence**: Invalid client data:
- If the email format is invalid, an error message is displayed, and the employee is prompted to input a valid email.

#### Use Case: Delete Client
**Primary Actor**: Employee  
**Main Success Scenario**:
1. The employee selects the option to delete a client.
2. The application displays a form to input the client ID to be deleted.
3. The employee enters the client ID.
4. The employee clicks the “Delete” button.
5. The application deletes the client and displays an acknowledgment message.

#### Use Case: Get Clients
**Primary Actor**: Employee  
**Main Success Scenario**:
1. The employee selects the option to view all clients.
2. The application displays a table with all clients and their details.

#### Use Case: Create Order
**Primary Actor**: Employee  
**Main Success Scenario**:
1. The employee selects the option to create a new order.
2. The application displays a combo box to select a client for the order.
3. The employee selects a client.
4. The application displays a list of available products with radio buttons.
5. The employee selects products for the order and chooses the desired quantities.
6. The employee clicks the “Create Order” button to confirm.
**Alternative Sequence**: Invalid product quantity:
- If the user enters a negative value, decimal, or exceeds the available stock, the application displays an error message and requests a valid quantity.

## Conclusion
This Warehouse Client Order Management System effectively streamlines the process of managing client orders, inventory, and bill generation. The system offers intuitive functionalities and robust error handling, making it easy for employees to manage warehouse operations efficiently.
