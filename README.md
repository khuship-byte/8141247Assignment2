# WildLife - NIT3213 Android Application

## Project Information

**Project Name:** 8141247Assignment2  
**Application Name:** WildLife  
**Unit:** NIT3213 - Android Application Development  
**Assessment:** Final Android Application Development Project

---

## Overview

WildLife is an Android application developed for the NIT3213 final assessment. The application allows a user to authenticate through the provided NIT3213 API and explore information about different wildlife species.

The application contains three main required screens:

- Login
- Dashboard
- Details

The app retrieves animal information from the provided API and displays the data using a clean and user-friendly wildlife-themed interface.

---

## Features

### Login

The Login screen allows the user to enter a username and password.

The application sends the credentials to the Brisbane authentication endpoint using a POST request.

After successful authentication, the API returns a `keypass`, which is passed internally to the Dashboard screen.

The Login screen also provides appropriate error messages for invalid credentials, missing fields, and connection problems.

### Dashboard

The Dashboard uses the keypass received during authentication to retrieve animal data from the API.

Animals are displayed using a RecyclerView with a two-column grid layout.

Each animal card displays:

- Animal image
- Species name
- Scientific name
- View Details option

Selecting an animal opens the Details screen.

The application also includes a favorites feature. Animals saved as favorites display a red heart on their Dashboard card.

### Details

The Details screen displays information about the selected animal, including:

- Species
- Scientific name
- Habitat
- Diet
- Average lifespan
- Conservation status
- Description

The screen also includes a Discover More section containing additional educational information about the selected species.

Users can save or remove an animal from their favorites.

---

## API

The application uses the NIT3213 API:

`https://nit3213apinew.onrender.com`

### Authentication Endpoint

For the Brisbane class:

`POST /br/auth`

The authentication response provides a keypass used to retrieve Dashboard data.

### Dashboard Endpoint

`GET /dashboard/{keypass}`

The response contains the entities displayed in the application.

---

## Architecture

The application follows a structured architecture to separate user interface, business logic, networking, and data access responsibilities.

The main architecture flow is:

`Activity -> ViewModel -> Repository Interface -> Repository -> API Service`

### Activities

The application contains:

- `LoginActivity`
- `DashboardActivity`
- `DetailsActivity`

Activities are responsible for displaying the interface and responding to user interactions.

### ViewModels

The application uses:

- `LoginViewModel`
- `DashboardViewModel`

ViewModels manage application logic and expose UI state using StateFlow.

### Repository

`AnimalRepositoryInterface` defines the repository contract.

`AnimalRepository` implements this interface and communicates with `ApiService`.

Using an interface improves separation of concerns and makes the ViewModels easier to unit test.

---

## Dependency Injection

The application uses Hilt for dependency injection.

Hilt is used to provide and manage dependencies including:

- Retrofit
- Moshi
- OkHttpClient
- ApiService
- AnimalRepository

`NetworkModule` provides networking dependencies.

`RepositoryModule` binds `AnimalRepository` to `AnimalRepositoryInterface`.

The application class is annotated with `@HiltAndroidApp`, while Activities requiring injected ViewModels use `@AndroidEntryPoint`.

ViewModels use `@HiltViewModel` and constructor injection.

---

## Networking

The application uses the following networking technologies:

- Retrofit
- Moshi
- OkHttp
- Kotlin Coroutines

Retrofit performs API requests, Moshi converts JSON responses into Kotlin data classes, and OkHttp provides the HTTP client.

Network operations are performed asynchronously using Kotlin coroutines.

---

## Local Resources

The API provides animal information but does not provide image URLs.

Therefore, animal images are stored locally in the Android drawable resources.

`AnimalImageMapper` maps each API species name to the appropriate local drawable resource.

Additional educational information used by the Discover More section is separated into `AnimalInfoProvider`.

---

## Favorites

Favorites are stored locally using Android SharedPreferences.

When an animal is added to favorites:

- The favorite state is stored locally.
- A red heart appears on the animal's Dashboard card.
- The Favorites navigation option can be used to view saved animals.

The favorite state remains available when navigating between screens.

---

## Unit Testing

Local unit tests are included in the project using:

- JUnit
- MockK
- kotlinx-coroutines-test

The project contains the following test classes:

### AnimalImageMapperTest

Tests that each supported species is mapped to the correct local drawable resource and verifies the fallback image for an unknown species.

### LoginViewModelTest

Tests important login behaviours including:

- Blank username
- Blank password
- Successful authentication
- Missing keypass
- Invalid authentication
- Network exception handling

### DashboardViewModelTest

Tests Dashboard behaviours including:

- Blank keypass
- Successful Dashboard response
- Empty entity response
- Unsuccessful API response
- Network exception handling

The project currently contains **19 passing unit tests**.

---

## Technologies Used

- Kotlin
- Android Studio
- XML layouts
- Material Components
- RecyclerView
- MVVM
- StateFlow
- Kotlin Coroutines
- Retrofit
- Moshi
- OkHttp
- Hilt
- SharedPreferences
- JUnit
- MockK
- Git

---

## Project Structure

```text
com.example.assignment2
|
|-- data
|   |-- model
|   |-- remote
|   `-- repository
|
|-- di
|   |-- NetworkModule
|   `-- RepositoryModule
|
|-- ui
|   |-- dashboard
|   `-- login
|
|-- util
|   |-- AnimalImageMapper
|   `-- AnimalInfoProvider
|
|-- AnimalApplication
|-- LoginActivity
|-- DashboardActivity
`-- DetailsActivity

## Running the Application

1. Open the project in Android Studio.
2. Allow Gradle to finish syncing.
3. Start an Android emulator or connect an Android device.
4. Run the application.
5. Enter the required NIT3213 authentication credentials.
6. After successful authentication, the Dashboard will load animal information from the API.
7. Select an animal to view its complete details.

An internet connection is required for authentication and Dashboard API requests.

---

## Error Handling

The application provides user-friendly error messages for situations including:

- Empty login fields
- Invalid username or password
- Missing authentication keypass
- Failed Dashboard requests
- Empty Dashboard responses
- Network connection problems

---

## Conclusion

WildLife demonstrates Android application development using API integration, MVVM-style architecture, Hilt dependency injection, asynchronous networking, RecyclerView, local persistence, navigation, clean code practices, and unit testing.

The application satisfies the core Login, Dashboard, and Details requirements while also providing additional features such as favorites and educational wildlife content.