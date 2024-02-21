from flask import Flask, jsonify, request
from flask_cors import CORS
from scrape import getData
from search import searchTitle

URL_TOP_MOVIE = "https://www.imdb.com/chart/top/"
URL_POP_MOVIE = "https://www.imdb.com/chart/moviemeter/"
URL_TOP_SHOW = "https://www.imdb.com/chart/toptv/"
URL_POP_SHOW = "https://www.imdb.com/chart/tvmeter/"

app = Flask(__name__)
CORS(app)

@app.route("/topmovies")
def getTopMovies():
    top_movies = getData(URL_TOP_MOVIE, "movie")
    return jsonify( top_movies )

@app.route("/popmovies")
def getPopMovies():
    pop_movies = getData(URL_POP_MOVIE, "movie")
    return jsonify( pop_movies )

@app.route("/topshows")
def getTopShows():
    top_shows = getData(URL_TOP_SHOW, "show")
    return jsonify( top_shows )

@app.route("/popshows")
def getPopShows():
    pop_shows = getData(URL_POP_SHOW, "show")
    return jsonify( pop_shows )

@app.route("/search")
def search():
    title = request.args.get('q')
    searchResult = searchTitle(title)
    return jsonify( searchResult )


if __name__ == "__main__":
    app.run(debug=True, host='0.0.0.0')
    
    
