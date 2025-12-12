## API Endpoints

### 1. Register User

**Method:** POST  
**URL:** `/api/auth/register`

**Request Body**

```json
{
  "name": "john_doe",
  "email": "john@example.com",
  "password": "mypassword123"
}
```

**Success Response**

```json
{
  "success": true,
  "message": "User registered successfully"
}
```

**Failure Responses**

```json
{
  "success": false,
  "message": "Email must be a valid umass.edu address"
}
```

```json
{
  "success": false,
  "message": "User with this email already exists. Login instead."
}
```

### 2. Login User

**Method:** POST  
**URL:** `/api/auth/login`

**Request Body**

```json
{
  "email": "john@example.com",
  "password": "mypassword123"
}
```

**Success Response**

```json
{
  "success": true,
  "message": "Login successful"
}
```

**Failure Response**

```json
{
  "success": false,
  "message": "Invalid credentials"
}
```

### 3. Submit User Survey

**Method:** POST  
**URL:** `/api/survey/submit`

**Request Body**

```json
{
  "userId": 1,
  "sleepSchedule": "EARLY_BIRD",
  "foodType": "VEGETARIAN",
  "personalityType": "INTROVERT",
  "petsPreference": "OKAY_WITH_PETS",
  "smokingPreference": "PREFER_NON_SMOKER_ONLY"
}
```

Note: To see all possible options that request body can take, please refer to: roommate-matcher/src/main/java/com/cs520group8/roommatematcher/model

**Success Response**

```json
{
  "success": true,
  "message": "Survey submitted successfully."
}
```

**Failure Response**

```json
{
  "success": false,
  "message": "Survey already submitted by this user."
}
```

### 4. Get List of All Users

**Method:** POST  
**URL:** `/api/users/getAllUsers`

**Request Body**

```json
{}
```

**Response**

```json
[
  {
    "name": "John Doe",
    "email": "john@example.com",
    "id": 1
  },
  {
    "name": "Jack Williams",
    "email": "jack@example.com",
    "id": 2
  },
  {
    "name": "Toby Pitt",
    "email": "toby@example.com",
    "id": 3
  }
]
```

### 5. Get List of Filtered Users

**Method:** POST  
**URL:** `/api/users/getFilteredUsers`

**Request Body**

```json
{
  "sleepSchedule": "NIGHT_OWL",
  "foodType": "VEGETARIAN",
  "personalityType": "INTROVERT",
  "petsPreference": "NO_PETS",
  "smokingPreference": "NO_PREFERENCE"
}
```

**Response**

```json
[
  {
    "name": "John Doe",
    "email": "john@example.com",
    "id": 1
  },
  {
    "name": "Jack Williams",
    "email": "jack@example.com",
    "id": 2
  }
]
```

### 6. Get List of Recommended Users

**Method:** POST  
**URL:** `/api/users/getRecommendedUsers`

**Request Body**

```json
{
  "userId": 1
}
```

**Response**

```json
[
  {
    "name": "John Doe",
    "email": "john@example.com",
    "id": 1,
    "percentMatch": 80.0
  },
  {
    "name": "Jack Williams",
    "email": "jack@example.com",
    "id": 2,
    "percentMatch": 40.0
  }
]
```