from flask import Flask, jsonify
from flask_cors import CORS
from scrape import Scrape

URL_TOP_MOVIE = "https://www.imdb.com/chart/top/"
URL_POP_MOVIE = "https://www.imdb.com/chart/moviemeter/"
URL_TOP_SHOW = "https://www.imdb.com/chart/toptv/"
URL_POP_SHOW = "https://www.imdb.com/chart/tvmeter/"

app = Flask(__name__)
CORS(app)

@app.route("/topmovies")
def getTopMovies():
    TopMovies = Scrape(URL_TOP_MOVIE, "movie")
    return jsonify( TopMovies.getData() )

@app.route("/popmovies")
def getPopMovies():
    PopMovies = Scrape(URL_POP_MOVIE, "movie")
    return jsonify( PopMovies.getData() )

@app.route("/topshows")
def getTopShows():
    TopShows = Scrape(URL_TOP_SHOW, "show")
    return jsonify( TopShows.getData() )

@app.route("/popshows")
def getPopShows():
    PopShows = Scrape(URL_POP_SHOW, "show")
    return jsonify( PopShows.getData() )

if __name__ == "__main__":
    app.run(debug=True)
    
    