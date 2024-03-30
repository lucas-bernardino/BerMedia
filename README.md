# BerMedia

### BerMedia is designed to be a clone of IMDb, but with better usability.

## About BerMedia

BerMedia is a source for searching movies and TV Shows, where you can see other people's reviews and also create your own, save a media to watch later, look the most popular and rated movies or shows and search for any media you like. For pulling the content from IMDb, it uses a **Python** script as a web scraping tool. The backend is built using **Java Spring Boot** while the frontend is built with **React**. In order to save the comments and overall user information, **PostgreSQL** is used as the database while running in a **Docker** container. It also features registration and login with **JWT Authentication** and unit tests with **Mockito**

## How it Works

- **Web Scraping**

  - The code uses Python to get the content from the IMDb. It uses **BeautifulSoup** to parse the HTML from the page, such as when it's requested to get the most popular or rated movie/show or when it's requested to make a search for a particular media.
  - In order to communicate with the other services, Python was also used as a server by creating several routes using the **Flask** framework. This way, after the request was sent to the server, it responds back with a JSON response containing the information about the media.
  - The information sent in the JSON can change depending if it's a movie or a show, but generally it looks like this

  ```
  {
  "certificate": "16",
  "genre": [
    "Crime",
    "Drama",
    "Thriller"
  ],
  "imdbId": "tt0903747",
  "length": 2700,
  "pictureUrl": "https://m.media-amazon.com/images/M/MV5BYmQ4YWMxYjUtNjZmYi00MDQ1LWFjMjMtNjA5ZDdiYjdiODU5XkEyXkFqcGdeQXVyMTMzNDExODE5._V1_.jpg",
  "plot": "A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine with a former student in order to secure his family's future.",
  "rank": 1,
  "score": 9.5,
  "title": "Breaking Bad",
  "titleType": "tvSeries",
  "users": [],
  "yearEnd": 2013,
  "yearStart": 2008
  }
  ```

  After sending a request to /topshows within the Flask server, the response contains a list of the 249 top rated TV Shows. The JSON above represents the first item in the list.

- **Backend**
  - The backend uses Java Spring Boot as the main tool to handle the user authentication and information, and to interact with the database and client.
  - The structure is divided by Auth, Config, Controller, Exception, Model, Repository, Service and Test
    - Auth: responsible for securing the project by allowing only registered users to save medias, make new comments or like medias. When the user signs in, it sends a valid **Json Web Token** that the client will set in storage so that this user is now able to do the actions mentioned earlier.
    - Config: it allows Cross Origin Resource Sharing (CORS) for GET, POST, UPDATE, DELETE so that the client can communicate and share data with the server.
    - Controller: defines the entry points used to talk with the client. It has four classes:
      - AuthenticationController: responsible for both sign up and sign in routes
      - CommentController: handles the routes responsible for comment operations, such as adding a new comment and fetching all the comments from a media.
      - MediaController: deals with the interaction of a user with a media.
      - UserController: creates the user manually and gets all the user.
    - Exception: responsible for creating custom exceptions in the API.
    - Model: contains the classes, relationships and informations that will be saved in the database.
    - Repository: layer responsible for using the JPA to communicate with the database.
    - Service: the Controller will communicate with the service, and the service will communicate with the repository.
