# OOP-JavaSwing-Capstone
# Morsel
![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven)
![FlatLaf](https://img.shields.io/badge/FlatLaf-UI-blue)
![License](https://img.shields.io/badge/License-MIT-green)

**BSCS-2 F1 FINAL PROJECT**\
**_Morsel_** is a desktop-based client developed using **Java Swing UI** 
with **FlatLaf** as part of the **CSIT227 Object-Oriented Program Course**.

This project simulates a food delivery platform using synthetically 
generated data stored on disk via JSON files. It features various actors such as 
**customers**, **merchants/restaurants**, **deliver riders**, as well as other entities 
that are involved in the food ordering and delivery process. 

# Architecture

## Project Structure

```
morsel/
├── controller/
│   ├── Client.java           # Main client controller with state management
│   ├── Server.java           # Backend server handling file I/O and business logic
│   └── states/
│       ├── ClientState.java         # Base state class
│       ├── ClientAuthState.java     # Authentication state (login/register)
│       ├── ClientCustomerHomeState.java  # Customer dashboard state
│       ├── ClientMerchantDashboardState.java  # Merchant dashboard state
│       ├── ClientAdminDashboardState.java    # Admin dashboard state
│       ├── ClientRiderDashboardState.java    # Rider dashboard state
│       └── ClientProfileState.java    # Profile management state
├── model/
│   ├── ClientContext.java    # Shared context between states
│   ├── actors/
│   │   ├── Actor.java        # Base actor class
│   │   ├── Admin.java        # Admin actor
│   │   ├── Customer.java     # Customer actor
│   │   ├── Merchant.java     # Merchant actor
│   │   └── Rider.java        # Rider actor
│   └── data/
│       ├── UserEntry.java    # User authentication data
│       ├── Order.java        # Order status enum
│       ├── OrderEntry.java   # Order with metadata
│       ├── MenuItem.java     # Menu item data
│       └── exceptions/       # Custom exceptions
└── service/
    └── StorageService.java   # JSON file I/O service with Jackson
└── view/
    ├── AuthPanel.java        # Login and registration panel
    ├── BaseDashboardPanel.java # Base dashboard with navigation sidebar
    ├── CustomerDashboardPanel.java  # Customer dashboard UI
    ├── MerchantDashboardPanel.java  # Merchant dashboard UI
    ├── AdminDashboardPanel.java   # Admin dashboard UI
    ├── RiderDashboardPanel.java   # Rider dashboard UI
    └── ProfilePanel.java      # Profile management UI
```

## Technology Stack

- **Language**: Java 17
- **Build System**: Maven
- **UI Framework**: Java Swing with FlatLaf Look and Feel
- **JSON Processing**: Jackson Databind 2.17.0
- **File I/O**: Custom StorageService with JSON serialization

## Features

### Authentication
- User login with username and password
- SHA-256 password hashing for security
- User registration with role selection (CUSTOMER, MERCHANT, RIDER)
- Input validation with JOptionPane dialogs
- Error handling for invalid credentials and duplicate usernames

### File I/O Backend
The backend uses `StorageService<T>` for persistent data management:
- **JSON-based storage** using Jackson
- **Auto-creation** of missing files and directories
- **Default values** for empty files
- **Type-safe** read/write operations

Data is stored in the `runtimeData/` directory:
- `userStore.json` - User credentials and profiles
- `orderStore.json` - All orders with status tracking
- `catalogStore.json` - Menu items catalog
- `activeRiderStore.json` - Currently available riders
- `sharedState.json` - Global state (UUID counters, etc.)

### Multi-Screen Navigation
The application uses a state-based navigation system:
- **State Stack**: Client maintains a stack of states for navigation history
- **Transition**: `transitionTo()` pushes a new state onto the stack
- **Backtracking**: `backtrack()` pops states from the stack
- **Lifecycle Methods**: Each state has `onEntry()`, `onExit()`, `onSuspend()`, `onResume()`

### Dashboards by Role

#### Customer Dashboard
- View and track orders
- Browse menu items
- Search functionality
- Shopping cart management
- Checkout process

#### Merchant Dashboard
- View pending orders
- Manage menu items (add, edit, remove)
- Track order status
- View sales analytics

#### Rider Dashboard
- Toggle availability (online/offline)
- View available deliveries
- Accept/reject delivery requests
- Track current deliveries
- View delivery history

#### Admin Dashboard
- View all system users
- View all orders across the platform
- System statistics
- User management capabilities

### Common Features
- **Sidebar Navigation**: All dashboards include a consistent sidebar with:
  - Welcome message with username
  - Role display
  - Dashboard button
  - Profile button
  - Logout button with confirmation dialog
- **Profile Management**: View user information, edit profile, change password
- **JOptionPane Integration**: Consistent dialog-based user feedback:
  - Success notifications
  - Error messages
  - Warning prompts
  - Confirmation dialogs (e.g., logout, overwrite)

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6+ (recommended)

### Build
```bash
mvn clean compile
```

### Run
```bash
mvn exec:java -Dexec.mainClass="morsel.Main"
```

Or compile and run directly:
```bash
javac -cp "target/classes:$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout)" src/main/java/morsel/Main.java
java -cp "target/classes:$(mvn dependency:build-classpath -q -Dmdep.outputFile=/dev/stdout)" morsel.Main
```

### Testing
```bash
mvn test
```

## Usage

1. **Launch the application**: The login screen appears first
2. **Register a new account**: Click "Don't have an account? Register"
3. **Select your role**: Choose CUSTOMER, MERCHANT, or RIDER
4. **Login**: Enter your credentials
5. **Navigate**: Use the sidebar to switch between dashboard, profile, and logout

## FlatLaf Theme

The application uses **FlatLaf Light** theme by default, configured in `Main.java`:

```java
FlatLightLaf.setup();
```

You can change the theme by replacing with:
- `FlatDarkLaf.setup()` - Dark theme
- `FlatIntelliJLaf.setup()` - IntelliJ-inspired theme
- `FlatDarculaLaf.setup()` - Darcula theme

## Members:
- Cui, Jayvee Martin
- Lim, Joshua Leon
- Navarro, Angelo Jade
- Sable, Jayden Steiner P.
- Sandoval, Thadeos Paul C.
