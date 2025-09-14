# Accessible Life 🧭

A Java-based system for managing reviews and accessibility information for public places. This project helps users discover and rate locations based on accessibility features like ramps, elevators, braille signage, and more.

---

## 📦 Project Structure

accessible-life/ 
├── src/ 
│└── main/ 
│       └── java/ 
│           └── com/ 
│               └── accessiblelife/ 
│                   ├── Main.java 
│                   ├── model/ 
│                   │   └── Place.java 
│                   ├── ratingreview/ 
│                   │   ├── RatingReview.java 
│                   │   ├── RatingReviewManager.java 
│                   │   └── Report.java 
│                   └── admin/ 
│                       └── Admin.java 
├── README.md 
├── .gitignore 
└── accessiblelife.iml

---

## 🚀 How to Run

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/accessible-life.git

2. Open the project in IntelliJ IDEA or VS Code.

3. Make sure your Main.java is in the correct package:
    ```java
    package com.accessiblelife;

4. Run Main.java to test sample reviews and output.

🧠 Features
- Add and view reviews for accessible places
- Calculate average ratings
- Modular structure with Place, RatingReview, and Report classes
- Easy to extend with admin tools, search filters, or UI

🛠️ Contributing
- Fork the repo
- Create a new branch:
  git checkout -b feature/your-feature-name
- Commit your changes with clear messages
- Push and open a pull request

📚 Documentation
- Place.java: Stores location details and accessibility features
- RatingReview.java: Represents a user review with rating and comment
- RatingReviewManager.java: Manages reviews, calculates averages
- Report.java: Optional class for flagging issues or feedback
- Admin.java: Placeholder for future admin tools

