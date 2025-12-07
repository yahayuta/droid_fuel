# Droid Fuel - Fuel Usage Analyzer

[![Android](https://img.shields.io/badge/Android-API%2029%20--%20API%2034-green.svg)](https://developer.android.com/about/versions/android-10)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Version](https://img.shields.io/badge/version-2.0.0-blue.svg)](https://github.com/yourusername/droid_fuel/releases)

A comprehensive Android application for tracking and analyzing fuel consumption patterns. Droid Fuel helps you monitor your vehicle's fuel efficiency, track expenses, and maintain detailed records of your fuel usage.

## 📱 Features

### 🔧 Core Functionality
- **Fuel Record Management**: Easily record fuel purchases with odometer readings
- **Automatic Calculations**: Automatically calculates price per unit and fuel economy
- **Data Navigation**: Browse through historical records with forward/backward navigation
- **Edit Mode**: Modify existing fuel records as needed

### 📊 Analytics & Reporting
- **Detailed Statistics**: View comprehensive fuel usage statistics
- **Fuel Economy Tracking**: Monitor your vehicle's fuel efficiency over time
- **Cost Analysis**: Track total fuel expenses and average costs
- **Usage Charts**: Visualize fuel consumption patterns with built-in charts

### 💾 Data Management
- **Local Database**: Secure SQLite database for data storage
- **Data Export**: Export fuel usage records for backup or analysis
- **Data Reset**: Option to clear all records when needed
- **Input Validation**: Robust validation to ensure data accuracy

### 🌐 Internationalization
- **Multi-language Support**: Available in English and Japanese
- **Localized Interface**: User-friendly interface in multiple languages

## 🚀 Installation

### Prerequisites
- Android device running API level 29 (Android 10) or higher
- Android Studio (for development)

### Building from Source

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/droid_fuel.git
   cd droid_fuel
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the cloned directory and select it

3. **Build and Run**
   - Connect your Android device or start an emulator
   - Click the "Run" button (green play icon) in Android Studio
   - Select your target device and install the app

### APK Installation
Download the latest APK from the [Releases](https://github.com/yourusername/droid_fuel/releases) page and install it on your Android device.

## 📖 Usage

### Recording Fuel Data
1. **Open the app** and you'll see the main recording interface
2. **Enter Odometer Reading**: Input your current odometer value
3. **Enter Fuel Amount**: Specify how much fuel you purchased
4. **Enter Fuel Price**: Input the total cost of the fuel
5. **Save**: Tap the "SAVE" button to store the record

### Navigating Records
- Use **FORWARD** and **BACKWARD** buttons to browse through historical records
- Switch to **EDIT MODE** to modify existing records
- View detailed statistics and charts through the menu options

### Menu Options
- **Record New Data**: Add a new fuel purchase record
- **Edit Past Data**: Modify existing records
- **Fuel Usage Detail**: View comprehensive statistics
- **Fuel Usage Chart**: Visualize consumption patterns
- **Export Records**: Save your data for backup
- **Delete All Records**: Clear all stored data (use with caution)

## 🏗️ Project Structure

```
droid_fuel/
├── app/
│   ├── src/main/
│   │   ├── java/droid/fuel/
│   │   │   ├── FuelUsageAnalyzerActivity.java    # Main activity
│   │   │   ├── FuelUsageAnalyzerDetailActivity.java
│   │   │   ├── FuelUsageAnalyzerDBHelper.java    # Database helper
│   │   │   ├── FuelUsageAnalyzerUtil.java        # Utility functions
│   │   │   └── FuelUsageRecordEntity.java        # Data entity
│   │   ├── res/
│   │   │   ├── layout/                           # UI layouts
│   │   │   ├── values/                           # English strings
│   │   │   ├── values-ja/                        # Japanese strings
│   │   │   └── drawable/                         # App icons
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── gradle/
├── build.gradle
└── settings.gradle
└── gradle.properties
```

## 🛠️ Technical Details

### Requirements
- **Minimum SDK**: API 29 (Android 10)
- **Target SDK**: API 34 (Android 14)

- **Gradle Version**: 8.9

### Dependencies
- Android SDK
- SQLite Database
- Android Charts (for data visualization)

### Architecture
- **Activity-based**: Traditional Android activity architecture
- **SQLite Database**: Local data storage using SQLite
- **MVC Pattern**: Model-View-Controller architecture for data management

## 🤝 Contributing

We welcome contributions! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

### Development Setup
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**yasupong** - *Initial work* - [GitHub Profile](https://github.com/yasupong)

## 🙏 Acknowledgments

- Android Developer Community
- Open source contributors
- Users who provided feedback and suggestions

## 📞 Support

If you encounter any issues or have questions:

1. Check the [Issues](https://github.com/yourusername/droid_fuel/issues) page
2. Create a new issue with detailed information
3. Include your Android version and device information

## 📈 Roadmap

- [ ] Cloud backup integration
- [ ] Multiple vehicle support
- [ ] Fuel price alerts
- [ ] Advanced analytics
- [ ] Dark mode support
- [ ] Widget support

---

⭐ **Star this repository if you find it useful!** 