import requests
from bs4 import BeautifulSoup
import json


def searchTitle(title):
    
    url = f'https://www.imdb.com/search/title/?title={title}'

    headers = {"User-Agent":"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36", "Accept-Encoding":"gzip, deflate", "Accept":"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", "DNT":"1","Connection":"close", "Upgrade-Insecure-Requests":"1"}
    page = requests.get(url, headers=headers)
    soup = BeautifulSoup(page.text,"html.parser")

    raw_data = soup.find(id="__NEXT_DATA__")
    STRING_TO_BE_REPLACED ='<script id="__NEXT_DATA__" type="application/json">'
    data = str(raw_data).replace(STRING_TO_BE_REPLACED, '').replace('</script>', '')
    data_json_raw = json.loads(data)

    all_data = data_json_raw["props"]['pageProps']["searchResults"]["titleResults"]["titleListItems"]

    list_data = []

    for data in all_data:
        
        dict_data = {}

        try:
            title_type = data["titleType"]["id"]
            certificate = data["certificate"]
            genres = data["genres"]
            title = data["originalTitleText"]
            plot = data["plot"]
            image = data["primaryImage"]["url"]
            rating = data["ratingSummary"]["aggregateRating"]
            id = data["titleId"]
            runtime = data["runtime"]
            
            year_end = 0
            year_start = 0

            if title_type == "movie":
                year_start = data["releaseYear"]

            if title_type == "tvSeries":
                year_start = data["releaseYear"]
                year_end = data["endYear"] if data["endYear"] is not None else -1 

            foo = ""
            for item in genres: foo += item + ", "
            genre = data["genres"]

            dict_data = {
                "title": title,
                "imdbId": id,
                "plot": plot,
                "pictureUrl": image,
                "certificate": certificate,
                "genre": genre,
                "length": runtime,
                "score": rating,
                "rank": 1,
                "titleType": title_type,
                "yearStart": year_start,
                "yearEnd": year_end,
                "users": []
            }

            list_data.append(dict_data)


        except Exception as e: 
            pass

    return list_data

if __name__ == "__main__":
    print(searchTitle("dark"))
    # __import__('pprint').pprint(searchTitle("godfather"))
