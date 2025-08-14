# E-Commerce Microservices Platform

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/Abdelrahman-Mabrouk/E-Commerce_Microservices)
[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0+-green.svg)](https://spring.io/projects/spring-boot)

## 📋 Overview

This project is an E-Commerce platform built using microservices architecture as part of the Ejada internship final project. The system provides a comprehensive online shopping experience with multiple independent services managing different business domains.

## 🏗️ Architecture

The system follows a microservices architecture pattern with service-oriented design principles.

### Services Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   API Gateway   │────│   Config Server │────│  Naming Server  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │
    ┌────┴────┬─────────────┬──────────────┐
    │         │             │              │
┌───▼───┐ ┌──▼─────────┐ ┌─▼─────────────┐ ┌▼──────────────┐
│ Shop  │ │ Inventory  │ │ Wallet        │ │               │
│Service│ │ Service    │ │ Service       │ │               │
└───────┘ └────────────┘ └───────────────┘ └───────────────┘
```

## 🚀 Services

### Infrastructure Services
- **api-gateway**: Main entry point for all client requests, handles routing and load balancing
- **config-server**: Centralized configuration management for all microservices
- **naming-server**: Service discovery and registration (Eureka Server)

### Business Services
- **shop-service**: Core shopping functionality, product browsing and purchase operations
- **inventory-service**: Inventory and stock management, product availability tracking
- **wallet-service**: Payment processing and user wallet management

## 🛠️ Technology Stack

### Backend Technologies
- **Java**: Core programming language
- **Spring Boot**: Application framework
- **Spring Cloud**: Microservices infrastructure
- **Maven**: Dependency management and build tool

### Infrastructure
- **Eureka**: Service discovery and registration
- **Spring Cloud Config**: Configuration management
- **Spring Cloud Gateway**: API Gateway routing

## 📁 Project Structure

```
E-Commerce_Microservices/
├── api-gateway/                 # API Gateway service
├── config-server/              # Configuration management
├── naming-server/              # Eureka service registry
├── shop-service/               # Core shopping operations
├── inventory-service/          # Inventory management
├── wallet-service/             # Payment and wallet management
├── config-server-files/        # Configuration files
├── .gitmodules                 # Git submodules configuration
└── README.md                   # Project documentation
```

## ⚙️ Prerequisites

- **Java Development Kit (JDK) 17+**
- **Apache Maven 3.6+**
- **Git**

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/Abdelrahman-Mabrouk/E-Commerce_Microservices.git
cd E-Commerce_Microservices
```

### 2. Build All Services
```bash
mvn clean install
```

### 3. Start Services in Order

**Step 1: Start Infrastructure Services**
```bash
# 1. Start Naming Server (Eureka)
cd naming-server
mvn spring-boot:run
```

**Step 2: Start Configuration Server**
```bash
# 2. Start Config Server
cd ../config-server
mvn spring-boot:run
```

**Step 3: Start API Gateway**
```bash
# 3. Start API Gateway
cd ../api-gateway
mvn spring-boot:run
```

**Step 4: Start Business Services**
```bash
# 4. Start Shop Service
cd ../shop-service
mvn spring-boot:run

# 5. Start Inventory Service (in new terminal)
cd ../inventory-service
mvn spring-boot:run

# 6. Start Wallet Service (in new terminal)
cd ../wallet-service
mvn spring-boot:run
```

## 📚 Service Information

### Default Ports

| Service | Default Port | Purpose |
|---------|--------------|---------|
| Naming Server | 8761 | Service Registry (Eureka Dashboard) |
| Config Server | 8888 | Configuration Management |
| API Gateway | 8080 | Main Application Entry Point |
| Shop Service | 8081 | Shopping Operations |
| Inventory Service | 8082 | Inventory Management |
| Wallet Service | 8083 | Payment Processing |

### Health Checks
Access service status:
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080/actuator/health
- **Individual Services**: http://localhost:808X/actuator/health

## 🔧 Configuration

The project uses Spring Cloud Config for centralized configuration management. Configuration files are stored in the `config-server-files` directory and served by the Config Server to all microservices.

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Test Individual Services
```bash
cd shop-service
mvn test
```

## 📊 Monitoring

### Service Discovery
Access the Eureka dashboard to monitor registered services:
```
http://localhost:8761
```

### Service Health
All services expose actuator endpoints for health monitoring:
```
http://localhost:<service-port>/actuator/health
```

## 🚀 Usage

Once all services are running:

1. **Access the application** through the API Gateway at `http://localhost:8080`
2. **Monitor services** via Eureka dashboard at `http://localhost:8761`
3. **Service communication** happens automatically through the API Gateway

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Open a Pull Request

## 👨‍💻 Author

**Abdelrahman Mabrouk**
- GitHub: [@Abdelrahman-Mabrouk](https://github.com/Abdelrahman-Mabrouk)
- Project: Ejada Internship Final Project

## 🙏 Acknowledgments

- **Ejada**: For the internship opportunity and project guidance
- **Spring Community**: For the excellent microservices framework
- **Java Community**: For comprehensive development ecosystem

---

⭐ **If you find this project helpful, please consider giving it a star!** ⭐