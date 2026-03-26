
```markdown
# 📚 Smart Study Planner

<div align="center">
  
  ![Version](https://img.shields.io/badge/version-2.0.0-blue.svg)
  ![Java](https://img.shields.io/badge/Java-17-orange.svg)
  ![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)
  ![License](https://img.shields.io/badge/license-MIT-green.svg)
  
  **An Intelligent Goal-Based Productivity System for Students**
  
  [Features](#-features) • [Tech Stack](#-tech-stack) • [Installation](#-installation) • [Usage](#-usage) • [Screenshots](#-screenshots) • [Contributing](#-contributing)
  
</div>

---

## 📖 Overview

Smart Study Planner is a comprehensive desktop application designed to help students manage their studies effectively through intelligent planning, progress tracking, and personalized study schedules. With dual authentication support, it caters to both regular students and IT students who want to integrate their GitHub workflow.

### 🎯 Why Smart Study Planner?

- **Dual Authentication**: Login with Google (Normal Student) or GitHub (IT Student)
- **Personalized Plans**: Create custom study plans based on subjects or GitHub repositories
- **Smart Task Generation**: AI-powered task distribution based on your available time
- **Progress Tracking**: Visual progress indicators and streak system to keep you motivated
- **GitHub Integration**: For IT students, track commits and project progress

---

## ✨ Features

### 🔐 Authentication
- **Google OAuth 2.0** - For Normal Students
- **GitHub OAuth** - For IT Students with repository integration
- Role-based data isolation (NORMAL vs IT)

### 📝 Study Plans
- Create plans with multiple subjects
- Set exam dates and daily study hours
- Add topics with difficulty levels and weights
- AI-powered task generation based on topics

### 🎯 Task Management
- Automatic task distribution across available days
- Intelligent rescheduling of missed tasks
- Double-click tasks to mark complete/incomplete
- Task completion tracking with visual indicators

### 📊 Progress Tracking
- Daily progress bar
- Overall plan completion percentage
- Streak system to maintain motivation
- Visual indicators for completed/missed/pending tasks

### 🐙 GitHub Integration (IT Students)
- Repository selection from GitHub account
- AI-powered project structure generation
- Commit tracking and verification
- Planned vs actual commits comparison

### 📱 User Interface
- Modern, intuitive design
- Role-specific dashboards
- Interactive profile pages
- Comprehensive About section

---

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 17** | Core application logic |
| **Swing** | GUI framework |
| **MySQL 8.0** | Database management |
| **JDBC** | Database connectivity |
| **Maven** | Build automation |
| **OAuth 2.0** | Authentication (Google & GitHub) |
| **OpenAI API** | AI-powered plan generation |
| **GitHub API** | Repository and commit tracking |

---

## 📋 Prerequisites

- **Java JDK 17** or higher
- **MySQL 8.0** or higher
- **Maven 3.6** or higher
- **Internet connection** for OAuth and API calls
- **Google OAuth 2.0 credentials** (for Google login)
- **GitHub OAuth App credentials** (for GitHub login)
- **OpenAI API key** (optional, for AI features)

---

## 🚀 Installation

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/Smart-Study-Planner.git
cd Smart-Study-Planner
```

### 2. Database Setup

```sql
-- Create database
CREATE DATABASE smart_study_planner;
USE smart_study_planner;

-- Import database schema
SOURCE src/main/resources/database/schema.sql;
```

### 3. Configuration

Create `config.properties` in `src/main/resources/`:

```properties
# Database Configuration
db.url=jdbc:mysql://localhost:3306/smart_study_planner?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.username=your_db_username
db.password=your_db_password

# Google OAuth
google.client.id=YOUR_GOOGLE_CLIENT_ID
google.client.secret=YOUR_GOOGLE_CLIENT_SECRET

# GitHub OAuth
github.client.id=YOUR_GITHUB_CLIENT_ID
github.client.secret=YOUR_GITHUB_CLIENT_SECRET

# OpenAI API (Optional)
openai.api.key=YOUR_OPENAI_API_KEY
```

### 4. Build the Project

```bash
mvn clean package
```

### 5. Run the Application

```bash
mvn exec:java "-Dexec.mainClass=app.Main"
```

Or run the generated JAR:

```bash
java -jar target/SmartStudyPlanner.jar
```

---

## 📸 Screenshots

### Login Page
![Login Page](screenshots/login.png)

### Dashboard (Normal Student)
![Dashboard Normal](screenshots/dashboard-normal.png)

### Dashboard (IT Student)
![Dashboard IT](screenshots/dashboard-it.png)

### Study Plan Generator
![Study Plan](screenshots/study-plan.png)

### Plan Management
![Plan Management](screenshots/plan-management.png)

### View My Plans
![View Plans](screenshots/view-plans.png)

### Profile Page
![Profile](screenshots/profile.png)

---

## 🎮 Usage Guide

### For Normal Students (Google Login)

1. **Login** with your Google account
2. **Create a Plan**:
   - Select subjects (Marathi, Hindi, English, Physics, etc.)
   - Set exam date and daily hours
   - Choose difficulty level
3. **Add Topics** to each subject with difficulty levels
4. **Generate Tasks** - AI distributes tasks across available days
5. **Complete Tasks** - Double-click to mark complete
6. **Track Progress** - Monitor daily and overall progress
7. **Reschedule** if you fall behind

### For IT Students (GitHub Login)

1. **Login** with your GitHub account
2. **Select a Repository** from your GitHub account
3. **Describe Your Project** or use AI to generate structure
4. **Add Features** with priority and experience levels
5. **Generate Tasks** - AI creates daily development tasks
6. **Complete Tasks** with commit tracking
7. **Check Commits** - Verify your progress against GitHub
8. **Track Progress** through commit counts and task completion

---

## 📁 Project Structure

```
Smart-Study-Planner/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── app/                 # Main application entry
│   │   │   ├── config/              # Configuration management
│   │   │   ├── dao/                 # Data Access Objects
│   │   │   ├── db/                  # Database connection
│   │   │   ├── enums/               # Enum definitions
│   │   │   ├── model/               # Domain models
│   │   │   ├── service/             # Business logic
│   │   │   ├── ui/                  # Swing UI components
│   │   │   └── util/                # Utility classes
│   │   └── resources/               # Configuration files
│   └── test/                        # Unit tests
├── database/
│   └── schema.sql                   # Database schema
├── screenshots/                     # Application screenshots
├── pom.xml                          # Maven configuration
└── README.md                        # This file
```

---

## 🔧 Database Schema

### Users Table
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    role ENUM('NORMAL', 'IT'),
    oauth_provider ENUM('GOOGLE', 'GITHUB'),
    github_username VARCHAR(100),
    access_token TEXT,
    avatar_url VARCHAR(500),
    active_plan_id INT
);
```

### Goals Table (Study Plans)
```sql
CREATE TABLE goals (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    plan_name VARCHAR(255),
    repository_name VARCHAR(255),
    subjects TEXT,
    end_date DATE,
    daily_hours INT,
    difficulty VARCHAR(50),
    completion_percentage INT DEFAULT 0,
    ai_generated BOOLEAN DEFAULT FALSE,
    role ENUM('NORMAL', 'IT') DEFAULT 'NORMAL',
    login_type ENUM('GOOGLE', 'GITHUB') DEFAULT 'GOOGLE',
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### Study Tasks Table
```sql
CREATE TABLE study_tasks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    goal_id INT,
    task_date DATE,
    description TEXT,
    required_commit BOOLEAN DEFAULT FALSE,
    status ENUM('PENDING', 'COMPLETED', 'MISSED') DEFAULT 'PENDING',
    topic_id INT,
    session_type VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (goal_id) REFERENCES goals(id)
);
```

---

## 🐛 Troubleshooting

### Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| **Port 8888 already in use** | Kill the process using port 8888 or change port in LoginFrame.java |
| **Database connection failed** | Check MySQL service is running and credentials in config.properties |
| **OAuth login fails** | Verify client IDs and redirect URIs in Google/GitHub console |
| **Tasks not generating** | Ensure topics are added and deadline is in the future |
| **GitHub commits not tracking** | Check access token and repository permissions |

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- **Google OAuth** for authentication
- **GitHub API** for repository integration
- **OpenAI** for AI-powered plan generation
- **MySQL** for reliable database storage

---

## 📧 Contact

- **Developer**: Abhay Zombade
- **Email**: abhayzombade26@gmail.com
- **GitHub**: [Abhay9326397439](https://github.com/Abhay9326397439)

---

## ⭐ Star History

If you find this project useful, please give it a star! ⭐

---

<div align="center">
  Made with ❤️ for students worldwide
</div>
```

## Additional Sections You Might Want:

### Create a `screenshots` folder and add:

- `login.png` - Login screen
- `dashboard-normal.png` - Normal student dashboard
- `dashboard-it.png` - IT student dashboard
- `study-plan.png` - Study plan generator
- `plan-management.png` - Plan management interface
- `view-plans.png` - View my plans section
- `profile.png` - Profile page

### Create a `database` folder with `schema.sql`:

```sql
-- Create database
CREATE DATABASE IF NOT EXISTS smart_study_planner;
USE smart_study_planner;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    role ENUM('NORMAL', 'IT') DEFAULT 'NORMAL',
    oauth_provider ENUM('GOOGLE', 'GITHUB'),
    github_username VARCHAR(100),
    access_token TEXT,
    avatar_url VARCHAR(500),
    active_plan_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Goals table
CREATE TABLE IF NOT EXISTS goals (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    plan_name VARCHAR(255),
    repository_name VARCHAR(255),
    subject_name VARCHAR(255),
    subjects TEXT,
    end_date DATE,
    experience_level VARCHAR(50),
    daily_hours INT,
    completion_percentage INT DEFAULT 0,
    ai_generated BOOLEAN DEFAULT FALSE,
    role ENUM('NORMAL', 'IT') DEFAULT 'NORMAL',
    login_type ENUM('GOOGLE', 'GITHUB') DEFAULT 'GOOGLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Study tasks table
CREATE TABLE IF NOT EXISTS study_tasks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    goal_id INT,
    user_id INT,
    repository_name VARCHAR(255),
    task_date DATE,
    planned_hours INT DEFAULT 2,
    actual_hours INT DEFAULT 0,
    planned_commits INT DEFAULT 1,
    actual_commits INT DEFAULT 0,
    description TEXT,
    required_commit BOOLEAN DEFAULT TRUE,
    status VARCHAR(50) DEFAULT 'PENDING',
    topic_id INT DEFAULT 0,
    session_type VARCHAR(20) DEFAULT 'CODING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (goal_id) REFERENCES goals(id) ON DELETE CASCADE
);

-- Topics table
CREATE TABLE IF NOT EXISTS topics (
    id INT PRIMARY KEY AUTO_INCREMENT,
    plan_id INT,
    subject VARCHAR(100),
    name VARCHAR(255),
    difficulty INT,
    size INT,
    weight DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (plan_id) REFERENCES goals(id) ON DELETE CASCADE
);
