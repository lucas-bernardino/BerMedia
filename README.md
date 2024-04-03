# BerMedia

#### BerMedia is designed to be a clone of IMDb, but with better usability.

---

[About](#about-bermedia)

[How It Works](#how-it-works)

[Usage](#usage)

---

![Top shows page](/assets/topshows.png)

![Pop movies page](/assets/popmovies.png)

![Comments in profile](/assets/comments.png)

![Register page](/assets/register.png)

![Login page](/assets/login.png)

## About BerMedia

BerMedia is a source for searching movies and TV Shows, where you can see other people's reviews and also create your own, save a media to watch later, look the most popular and rated movies or shows and search for any media you like. For pulling the content from IMDb, it uses a **Python** script as a web scraping tool. The backend is built using **Java Spring Boot** while the frontend is built with **React** using **Typescript**. In order to save the comments and overall user information, **PostgreSQL** is used as the database while running in a **Docker** container. It also features registration and login with **JWT Authentication** and unit tests with **Mockito** and **JUnit**.

## How It Works

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
    - Test: responsible for testing the Service and Controller layers. It uses Mockito and JUnit.

- **Frontend**

  - The frontend is built using Typescript and React.
  - When the user signs up, it sends a post request to the backend to check if the user can be registered.
  - When the user signs in, the local storage is set with the JWT sent from the backend.
  - It's not necessary to be logged in to access BerMedia, but if the user wants to save a media or make a new comment, it's necessary to be logged in and have a valid token.

- **Database**

  - The database contains the data defined in the Models directory.
  - There are three main entities in the project:
    - User: besides having the basic fields of the user, such as id, username, password and role, it also has a field dedicated to the medias it can store. Since a user can have many medias and a media can have many users, they have a ManyToMany relationship.
    - Media: in addition to having the information about a media, it also has a field dedicated to users and performs a ManyToMany relationship with users as explained earlier.
    - Comment: has the id, the username of who made the comment, the comment itself and the media which was commented about.
  - The following diagram represents graphically how the database is organised

    ![Schema representing the database](/assets/schema3.jpg)

## Usage

- Since this project uses Docker, the installation is pretty straightforward. You basically need to have Docker installed on your machine

  - Setting up Docker

  ```
  # Add Docker's official GPG key:
  sudo apt-get update
  sudo apt-get install ca-certificates curl
  sudo install -m 0755 -d /etc/apt/keyrings
  sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
  sudo chmod a+r /etc/apt/keyrings/docker.asc

  # Set up Docker's APT repository:
  echo \
    "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
    $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
    sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
  sudo apt-get update

  # Install the Docker packages:
  sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

  # Add docker privileges:
  sudo usermod -aG docker ${USER}

  # You also need to have docker compose. Before installing it, install its dependencies.
  sudo apt-get install libffi-dev libssl-dev
  sudo apt install python3-dev
  sudo apt-get install -y python3 python3-pip

  # Install Docker-Compose
  sudo pip3 install docker-compose

  # Enable docker system service
  sudo systemctl enable docker
  ```

  - Running the project

  ```
  # First, clone the repository
  git clone https://github.com/lucas-bernardino/BerMedia.git

  # Go to the directory that contains the starting script
  cd movie-app/server/server

  # You probably need to allow the bash script to be executable
  sudo chmod +x start.sh

  # Run the script to start the project
  ./start.sh
  ```

  - Go to http://localhost:5173/ to access BerMedia.
