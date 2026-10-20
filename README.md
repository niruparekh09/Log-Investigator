# Log-Investigator

A powerful log investigation and analysis tool for efficient log analysis and troubleshooting.

## Features

- **Pattern Matching**: Search logs for specific patterns and keywords
- **Log Aggregation**: Centralized log viewing and management
- **Error Detection**: Automatically identify and highlight error logs
- **Performance Monitoring**: Track application performance metrics
- **Alert System**: Get notified for critical log patterns
- **Real-time Analysis**: Stream and analyze logs in real-time
- **Database Storage**: Persistent log storage using H2 database
- **AI-Powered Insights**: (Coming soon) Use AI to analyze and summarize log patterns

## Tech Stack

- **Backend**: Spring Boot 4.1.1 (Java 21)
- **Web Framework**: Spring WebMVC
- **Database**: H2 Database (2.3.232)
- **AI Integration**: Spring AI (Ollama model)
- **Build Tool**: Maven

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- Ollama (for AI features)

## Setup

### Clone the Repository

```bash
git clone <repository-url>
cd log-investigator
```

### Maven Setup

Ensure you have the `pom.xml` configured with all dependencies:

```xml
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>2.3.232</version>
</dependency>
```

### Configuration

Create a `application.properties` file:

```properties
# Database Configuration
spring.datasource.url=jdbc:h2:mem:logdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Application Settings
log.investigator.app.name=Log-Investigator
log.investigator.db.path=./logs
```

## Installation

### Option 1: Maven JAR

```bash
mvn clean package
```

Run with:

```bash
java -jar target/log-investigator-0.0.1-SNAPSHOT.jar
```

### Option 2: Direct Spring Boot

```bash
./mvnw spring-boot:run
```

## Usage

### View Logs

```bash
./mvnw spring-boot:run
# Then visit: http://localhost:8080/api/logs
```

### Search Logs

```bash
GET /api/logs?query=ERROR
GET /logs/search?pattern=ERROR&start=2024-01-01&end=2024-12-31
```

### View H2 Console

```bash
http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:logdb
# Username: sa
# Password: (leave empty)
```

## Project Structure

```
log-investigator/
├── src/
│   ├── main/
│   │   ├── java/com/nrv/loginvestigator/
│   │   │   ├── controller/
│   │   │   ├── service/
│   │   │   ├── model/
│   │   │   └── repository/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application.yml
│   └── test/
│       └── java/com/nrv/loginvestigator/
├── pom.xml
└── logs/
```

## API Endpoints

- `GET /api/logs` - Get all logs
- `GET /api/logs/search` - Search logs by pattern
- `POST /api/logs/ingest` - Ingest new log entries
- `GET /api/logs/stats` - Get log statistics
- `GET /api/logs/errors` - Get error logs
- `GET /api/logs/timeline` - View logs by timeline

## License

MIT License - Feel free to use and modify.

## Contributing

Contributions are welcome! Please read our contributing guidelines before submitting PRs.

## Roadmap

- [ ] Real-time log streaming
- [ ] Advanced filtering
- [ ] Export logs to multiple formats
- [ ] Dashboard with visual analytics
- [ ] AI-powered log analysis and anomaly detection

## Support

For issues and feature requests, please open an issue on [GitHub](https://github.com/your-username/log-investigator).

---

Built with ❤️ using Spring Boot and H2 Database