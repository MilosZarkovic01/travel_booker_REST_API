# TravelBooking

Welcome to TravelBooking, your go-to application for easy and efficient travel reservations. With TravelBooking, users can seamlessly plan their next trips with just a few clicks.

## Features

### User Authentication

Users are required to log in with their credentials to access their profiles. If they don't have an account yet, they can easily register.

### Destination Search

Users can search for destinations by country, city, or both. This feature allows users to explore various travel options based on their preferences.

### Destination Details

Once users find their desired destination, they can view detailed information about it, including available accommodations and travel plans. Multiple travel plans with different departure dates are available, and users can filter them according to their preferred timeframe.

### Travel Plans Search

Users can search for travel plans based on specific dates (`from` and `to`). This feature enables users to find travel options that match their preferred travel period.

### Reservation

After selecting a travel plan and accommodation, users can make reservations seamlessly within the app. In case users decide to cancel their travel plans, they can easily cancel their reservations.

### Email Notifications

TravelBooking incorporates an email service responsible for sending various types of emails, including:
- Account verification email upon registration to activate the user's account.
- Registration confirmation email.
- Forgot password email if the user forgets their password.
- Reservation confirmation email sent after a successful reservation.

### Asynchronous Communication

TravelBooking communicates with the email service via RabbitMQ, enabling asynchronous communication between the two services to enhance performance.