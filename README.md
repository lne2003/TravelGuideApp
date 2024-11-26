# **TRIP HUNTER: YOUR PERSONALIZED TRAVEL GUIDE**

Trip Hunter is an all-in-one travel companion that helps you discover, explore, and enjoy your journeys with ease. From finding top-rated restaurants and nightlife to caching data for offline access and leaving reviews, Trip Hunter is your ultimate travel buddy.

---

## **TABLE OF CONTENTS**
1. [**OVERVIEW**](#overview)
2. [**FEATURES**](#features)
3. [**TECH STACK**](#tech-stack)
4. [**SETUP INSTRUCTIONS**](#setup-instructions)
5. [**USAGE INSTRUCTIONS**](#usage-instructions)
6. [**APP ARCHITECTURE**](#app-architecture)
7. [**ROADMAP**](#roadmap)
8. [**CHALLENGES FACED**](#challenges-faced)
9. [**CONTRIBUTING**](#contributing)
10. [**FEEDBACK**](#feedback)

---

## 1. **OVERVIEW**

Trip Hunter is designed to make your travels more convenient by providing personalized travel guides, offline functionality through data caching, and real-time push notifications. Whether you're looking for restaurants, nightlife, or simply reviews from other travelers, Trip Hunter has you covered.

---

## 2. **FEATURES**

### **GENERAL**
- Beautiful and user-friendly design.
- Offline functionality with local caching.

### **CORE FUNCTIONALITIES**
- **SPLASH SCREEN**: Welcomes users with a logo and loading spinner.
- **AUTHENTICATION**:
  - Login and Signup using Firebase Authentication.
  - Smooth navigation between authentication screens.
- **DESTINATION DETAILS**:
  - View detailed information about destinations, including restaurants, nightlife spots, and user reviews.
  - Images and details are cached locally for offline access.
- **RESTAURANTS**:
  - Browse a curated list of restaurants for selected destinations.
  - Add restaurants to your favorites and view them offline.
- **NIGHTLIFE**:
  - Discover nightlife venues and view their locations on Google Maps.
- **RATINGS & REVIEWS**:
  - Leave reviews and ratings for destinations.
  - Read reviews left by other users.
- **FAVORITES**:
  - Manage and save favorite restaurants.
  - View all favorites on an interactive map.
- **PUSH NOTIFICATIONS**:
  - Receive updates about trending destinations or new features.
- **OFFLINE CACHING**:
  - Automatically cache data when online.
  - Notify users when no cached data is available during offline sessions.

---

## 3. **TECH STACK**

- **LANGUAGES**: Java, XML
- **DEVELOPMENT TOOLS**: Android Studio
- **BACKEND SERVICES**:
  - Firebase Authentication (User Authentication)
  - Firebase Firestore (Database for destinations, reviews, and favorites)
  - Firebase Cloud Messaging (Push Notifications)
- **LIBRARIES**:
  - Google Maps API (Location-based features)
  - Picasso (Image loading)
  - Retrofit (Networking)
  - SharedPreferences (Offline Caching)

---

## 4. **SETUP INSTRUCTIONS**

### **PREREQUISITES**
- Android Studio installed on your computer.
- Firebase project set up with Authentication, Firestore, and Cloud Messaging.
- Physical Android device or emulator configured.

### **STEPS TO SET UP**
1. **CLONE THE REPOSITORY**:
   ```bash
   git clone https://github.com/lne2003/TravelGuideApp.git
    cd TravelGuideApp

   
## **OPEN PROJECT IN ANDROID STUDIO**

1. Open Android Studio and select **"Open an Existing Project."**
2. Navigate to the cloned directory and open it.

---

## **CONFIGURE FIREBASE**

1. Create a Firebase project in the [Firebase Console](https://firebase.google.com/).
2. Add the following services to your Firebase project:
   - **Firebase Authentication**
   - **Firestore**
   - **Cloud Messaging**
3. Download the `google-services.json` file from the Firebase Console.
4. Place the `google-services.json` file in the `app/` folder of your project.

---

## **ADD API KEYS**

See how to create a Google Maps API Key here: https://developers.google.com/maps/documentation/javascript/get-api-key 

1. Add your **Google Maps API Key** in the `AndroidManifest.xml` file:
   ```xml
   <meta-data
       android:name="com.google.android.geo.API_KEY"
       android:value="YOUR_GOOGLE_MAPS_API_KEY" />

   ---

## **BUILD AND SYNC THE PROJECT**

1. **BUILD**: 
   - Open Android Studio.
   - Sync Gradle dependencies by clicking **"Sync Now"** when prompted.

2. **RUN THE APP**:
   - Use a physical Android device or an emulator to launch the app.
   - Click **"Run"** in Android Studio to build and install the app.

---

## 5. **USAGE INSTRUCTIONS**

### **INITIAL LAUNCH**
- The app starts with a **Splash Screen** displaying the logo and a loading spinner.
- Navigate to the **LoginSignupPage** for authentication.

### **POST LOGIN**
- Browse destinations and select one for detailed views.
- Access features such as:
  - **Restaurants**
  - **Nightlife Venues**
  - **Weather Details**
  - **User Reviews**
- Save destinations or restaurants to your **Favorites** for offline access.
- Access profile/privacy settings to view and modify user details.

### **OFFLINE USAGE**
- Data fetched during an online session is cached for offline access.
- If no data is cached and the user is offline, the app will display a notification.

---

## 6. **APP ARCHITECTURE**

Trip Hunter follows the **MVC (MODEL-VIEW-CONTROLLER)** architecture for clean and modular code:

- **MODEL**:
  - Defines data structures and handles Firebase interactions.
  - **Example**: `NightlifeActivity`, `RatingItem`.

- **VIEW**:
  - XML layouts for the user interface components.
  - **Example**: `activity_ratings.xml`, `activity_nightlife.xml`.

- **CONTROLLER**:
  - Manages UI and business logic.
  - **Example**: `DestinationDetailsActivity.java`, `RatingsPage.java`.

---


## 7. **ROADMAP**

### **COMPLETED FEATURES**
- Push notifications for updates and recommendations.
- Offline caching for destinations and restaurants.
- Reviews and ratings with Firestore integration.
- Favorites management with map view support.

### **PLANNED FEATURES**
- User-to-user chat for travel recommendations.
- Integration with flight and hotel booking services.
- AI-driven travel recommendations based on user preferences.

---

## 8. **CHALLENGES FACED**

### **OFFLINE CACHING**
- Ensuring data is dynamically fetched and stored locally for offline usage.
- Handling user scenarios where no cached data is available.

### **FIREBASE INTEGRATION**
- Configuring Firestore and Authentication seamlessly.
- Managing asynchronous operations efficiently.

### **USER EXPERIENCE**
- Designing an intuitive and engaging interface.
- Maintaining a consistent and responsive UI across various devices.

---

## 9. **CONTRIBUTING**

Contributions are welcome! Here's how you can contribute:

1. **FORK THE REPOSITORY**:
   - Click the **"Fork"** button on GitHub.

2. **CREATE A FEATURE BRANCH**:
   - Create a branch for your feature or bug fix.

3. **COMMIT YOUR CHANGES**:
   - Write clear and concise commit messages.

4. **OPEN A PULL REQUEST**:
   - Submit your changes with a detailed description of what was implemented or fixed.


---

## 10. **FEEDBACK**

For questions or feedback, feel free to reach out or open an issue in the repository. Your input is highly valued and helps us improve!
Enjoy!

