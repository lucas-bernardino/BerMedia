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
            genero = data["node"]["titleGenres"]["genres"][0]["genre"]["text"]
            nota = data["node"]["ratingsSummary"]["aggregateRating"]
            plot = data["node"]["plot"]["plotText"]["plainText"]
            imdb_id = data["node"]["id"]

            
            if type == "movie":
                ano = data["node"]["releaseYear"]["year"]
                horas = data["node"]["runtime"]["seconds"] // 3600
                minut = int(((data["node"]["runtime"]["seconds"] / 3600) % horas) * 60)
                
                dict_data = {
                    "id": imdb_id,
                    "url": url,
                    "title": titulo,
                    "rank": rank,
                    "plot": plot,
                    "genre": genero,
                    "rating": nota,
                    "year": ano,
                    "length": {"hour": horas, "min": minut}
                }
            
            if type == "show":
                ano_inicio = data["node"]["releaseYear"]["year"]
                ano_fim = data["node"]["releaseYear"]["endYear"] if data["node"]["releaseYear"]["endYear"] is not None else -1
                
                dict_data = {
                    "id": imdb_id,
                    "url": url,
                    "title": titulo,
                    "rank": rank,
                    "plot": plot,
                    "genre": genero,
                    "rating": nota,
                    "year_start": ano_inicio,
                    "year_end": ano_fim,
                }    
                
            list_data.append(dict_data)
        
        except Exception as e:
            pass

    return list_data
