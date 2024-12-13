# Android Application - Product Catalog with Cart

This is an Android application developed using Java that implements a simple product catalog system with CRUD operations, API calls, and a local cart. The app uses Retrofit to make API calls and stores cart data locally. The application includes several pages for user interaction and displays product details, allowing users to add items to a cart, view cart contents, and log out to clear locally stored data.

## Technologies Used

- **Android** (Java)
- **API Calls** (Retrofit for network operations)
- **Gradle** (for adding project dependencies)
- **XML** (for designing user interfaces)

## Features

- **Landing Page**: Asks for the username of the person using the application. The username is displayed on all subsequent pages.
- **Product List Page**: Displays a list of products fetched from the API.
- **Product Details Page**: Shows detailed information about a product when clicked.
- **Cart Page**: Allows users to add products and quantities to the cart, stored locally.
- **Logout**: Clears all locally stored data and redirects back to the landing page to ask for the username again.
- **Mobile Responsiveness**: All pages are designed to be mobile-friendly and neat.

## API Used

The application fetches product data from the following API:

- [DummyJSON Products API](https://dummyjson.com/docs/products)

The API supports CRUD operations (Create, Read, Update, Delete) for managing product data.

## Installation

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/yourusername/BlueTreeAssignment.git


## Notes 
 **Real Device Required**:
 Due to network issues, the app may not function correctly on the emulator. 
 Please run the app on a physical Android device for best performance.
 
**Apk File**: 
 You can directly install the APK on your Android device for testing and demo purposes.

