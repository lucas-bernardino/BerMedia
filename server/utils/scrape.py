import requests
from bs4 import BeautifulSoup
import json

def getData(url, type):
    headers = {"User-Agent":"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36", "Accept-Encoding":"gzip, deflate", "Accept":"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8", "DNT":"1","Connection":"close", "Upgrade-Insecure-Requests":"1"}
    page = requests.get(url, headers=headers)
    soup = BeautifulSoup(page.text,"html.parser")
    
    raw_data = soup.find(id="__NEXT_DATA__")
    STRING_TO_BE_REPLACED ='<script id="__NEXT_DATA__" type="application/json">'
    data = str(raw_data).replace(STRING_TO_BE_REPLACED, '').replace('</script>', '')
    data_json_raw = json.loads(data)
    
    all_data = data_json_raw["props"]['pageProps']["pageData"]["chartTitles"]["edges"]

    list_data = []

    for data in all_data:
 
        dict_data = {}

        try:
            rank = data["currentRank"]
            titulo = data["node"]["originalTitleText"]["text"]
            url = data["node"]["primaryImage"]["url"]
            genero = [x["genre"]["text"] for x in list(data["node"]["titleGenres"]["genres"])] 
            nota = data["node"]["ratingsSummary"]["aggregateRating"]
            plot = data["node"]["plot"]["plotText"]["plainText"]
            imdb_id = data["node"]["id"]
            certificate = data["node"]["certificate"]["rating"]
            length = data["node"]["runtime"]["seconds"]
            titleType = data["node"]["titleType"]["id"]
            year_start = data["node"]["releaseYear"]["year"] 
            year_end = 0

            if type == "show": 
                year_end = data["node"]["releaseYear"]["endYear"] if data["node"]["releaseYear"]["endYear"] is not None else -1


            dict_data = {
                "title": titulo,
                "imdbId": imdb_id,
                "plot": plot,
                "pictureUrl": url,
                "certificate": certificate,
                "genre": genero,
                "length": length,
                "score": nota,
                "rank": rank,
                "titleType": titleType,
                "yearStart": year_start,
                "yearEnd": year_end,
                "users": []
            }
              
            list_data.append(dict_data)
        
        except Exception as e:
            pass

    return list_data

if __name__ == "__main__":
    getData("https://www.imdb.com/chart/top/", "movie")
    # __import__('pprint').pprint(getData("https://www.imdb.com/chart/toptv/", "show"))
