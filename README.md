# 📚 BookStore E-Commerce API (Backend)

A comprehensive RESTful API developed with Spring Boot for managing a full-featured online bookstore with advanced e-commerce capabilities including user management, product catalog, shopping cart, order processing, and integrated payment gateway.

---

## ✨ Key Features

### 🔐 Authentication & Authorization
- User registration with email verification (OTP-based)
- Secure JWT-based authentication with refresh tokens
- Google OAuth2 integration for social login
- Role-based access control (Admin/User)
- Password reset and change functionality

### 📖 Product Management
- Complete CRUD operations for books and categories
- Multi-category book organization with publishers
- Advanced product filtering and search capabilities
- Image and audio file upload support with Cloudinary integration
- Product inventory management with stock tracking
- Product ranking by popularity (most sold items)

### 🛒 Shopping Experience
- Dynamic shopping cart with real-time updates
- Wishlist functionality for users
- Advanced product search and filtering by:
  - Category, Publisher, Author
  - Price range, Format (Paperback/Hardcover/AudioCD)
  - Title-based search

### 📦 Order Management
- Complete order lifecycle management
- Multiple order status tracking (Placed → Shipped → Delivered)
- Order history for users and admin dashboard
- Automatic inventory updates on order fulfillment
- User points/rewards system (earn points on delivery)

### 💳 Payment Integration
- Dual payment methods: Cash on Delivery (COD) and VNPay
- Secure VNPay payment gateway integration
- Transaction tracking and callback handling
- Payment status verification and security

### 👤 User Management
- User profile management with multiple addresses
- Email notifications for account verification and password reset
- User activity tracking and order history
- Admin dashboard for user management

### 🎫 Promotions & Discounts
- Coupon system with user-specific assignments
- Discount calculation and application
- Coupon usage tracking

### 📝 Reviews & Feedback
- Product review and rating system
- Comment management with moderation capabilities

### 🔔 Notifications
- Real-time order status notifications
- Email integration for account-related communications

---

## 🛠️ Tech Stack

### Core Framework
- **Spring Boot 3.2.2** - Main application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations with Hibernate
- **Spring Validation** - Input validation
- **Spring Mail** - Email functionality

### Database & ORM
- **MySQL** - Primary database
- **JPA/Hibernate** - Object-relational mapping
- **H2** - Testing database

### Security & Authentication
- **JWT (JSON Web Tokens)** - Stateless authentication
- **OAuth2 Resource Server** - Google integration
- **BCrypt** - Password encryption
- **CORS** - Cross-origin resource sharing

### File Management & Cloud Services
- **Cloudinary** - Image and audio file storage
- **Multipart File Handling** - File upload support

### Payment Gateway
- **VNPay** - Vietnamese payment gateway integration
- **Custom payment utilities** - Secure transaction handling

### Code Quality & Mapping
- **MapStruct** - Object mapping between DTOs and entities
- **Lombok** - Boilerplate code reduction
- **Validation API** - Bean validation

### Testing & Development
- **JUnit** - Unit testing
- **Testcontainers** - Integration testing with MySQL
- **Dotenv** - Environment variable management

### API Documentation & Client
- **OpenFeign** - Declarative REST client for Google APIs
- **RESTful API** - Standard REST endpoints

---

## 📁 Project Structure

```
src/main/java/com/ntn/ecommerce/
├── configuration/          # Security, CORS, JWT, VNPay configuration
│   ├── SecurityConfig.java
│   ├── CustomJwtDecoder.java
│   ├── VNPAYConfig.java
│   └── ApplicationInitConfig.java
├── controller/             # REST API endpoints
│   ├── AuthenticationController.java
│   ├── UserController.java
│   ├── ProductController.java
│   ├── CategoryController.java
│   ├── OrderController.java
│   ├── CartController.java
│   ├── WishlistController.java
│   └── TransactionController.java
├── service/               # Business logic layer
│   ├── AuthenticationService.java
│   ├── UserService.java
│   ├── ProductService.java
│   ├── OrderService.java
│   ├── CartService.java
│   └── TransactionService.java
├── repository/            # Data access layer
│   ├── UserRepository.java
│   ├── ProductRepository.java
│   ├── OrderRepository.java
│   └── CartRepository.java
├── entity/               # JPA entities
│   ├── User.java
│   ├── Product.java
│   ├── Order.java
│   ├── Cart.java
│   ├── Payment.java
│   └── Transaction.java
├── dto/                  # Data Transfer Objects
│   ├── request/         # Request DTOs
│   └── response/        # Response DTOs
├── mapper/              # MapStruct mappers
├── utilities/           # Utility classes
│   ├── VNPayUtilities.java
│   ├── EmailUtilities.java
│   └── ImageUtilities.java
├── constant/            # Application constants
└── exception/           # Custom exceptions and error handling
```

---

## 🚀 Key API Endpoints

### Authentication
- `POST /auth/token` - Login
- `POST /auth/refresh` - Refresh JWT token
- `POST /auth/logout` - Logout
- `POST /auth/outbound/authentication` - Google OAuth login

### User Management
- `POST /users` - User registration
- `POST /users/verify-account` - Email verification
- `GET /users/my-info` - Get current user info
- `POST /users/forgot-password` - Password reset

### Products
- `GET /products` - Get products with filtering
- `GET /products/{id}` - Get product details
- `POST /products` - Create product (Admin)
- `GET /products/search` - Search products

### Shopping Cart
- `POST /cart/add/{userId}` - Add item to cart
- `GET /cart/{userId}` - Get user's cart
- `PUT /cart/edit/{userId}` - Update cart quantities

### Orders
- `POST /order/create` - Create new order
- `GET /order/getAll/{userId}` - Get user's orders
- `GET /order/getAll` - Get all orders (Admin)

### Payments
- `POST /transactions/vn-pay-callback/{userId}` - VNPay callback

---

## 🔧 Configuration

### Database Configuration
```yaml
spring:
  datasource:
    url: ${SPRING_DATASOURCE_URL}
    username: ${SPRING_DATASOURCE_USERNAME}
    password: ${SPRING_DATASOURCE_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
```

### JWT Configuration
```yaml
jwt:
  signerKey: ${SIGNING_KEY}
  valid-duration: 86400
  refreshable-duration: 172800
```

### VNPay Payment Configuration
```yaml
payment:
  vnPay:
    url: "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"
    tmnCode: "A3JJWFNP"
    secretKey: ${SECRET_KEY}
    returnUrl: "https://book-store-web-app-seven.vercel.app/payment-callback"
```

---

## 🛡️ Security Features

- **JWT Authentication** with secure token generation and validation
- **Role-based authorization** using `@PreAuthorize` annotations
- **CORS configuration** for frontend integration
- **Password encryption** using BCrypt
- **Token invalidation** for logout functionality
- **Secure payment processing** with signature verification

---

## 📊 Business Logic Highlights

### Order Processing
- Intelligent cart management (split cart when partial checkout)
- Automatic inventory updates on order status changes
- Points reward system (1 point per 1000 VND on delivery)
- Real-time order status tracking

### Payment Processing
- Dual payment methods (COD/VNPay)
- Secure VNPay integration with transaction verification
- Automatic payment status updates
- Transaction history tracking

### Inventory Management
- Real-time stock tracking
- Automatic stock updates on order fulfillment
- Product availability validation during checkout

---

## 🎯 Purpose & Learning Outcomes

This project demonstrates:
- **Enterprise-level Spring Boot application** development
- **Secure REST API design** with proper authentication/authorization
- **Payment gateway integration** with VNPay
- **File upload and cloud storage** with Cloudinary
- **Email service integration** for notifications
- **Comprehensive e-commerce workflow** implementation
- **Modern Java development practices** with Spring Boot 3.x

---

## 🔄 Development & Deployment

### Local Development
```bash
# Clone the repository
git clone [repository-url]

# Set up environment variables in .env file
# Run the application
./mvnw spring-boot:run
```

### Production Deployment
- Configured for deployment on cloud platforms
- Environment-based configuration using Spring profiles
- Production-ready with proper logging and error handling

---

## 📧 Contact & Support

For questions, suggestions, or support regarding this BookStore E-Commerce API, please contact the development team.

---

*This project serves as a comprehensive example of modern Spring Boot development practices, showcasing real-world e-commerce functionality with secure payment processing, user management, and scalable architecture.*