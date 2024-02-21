from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.options import Options
from time import sleep
import json

class Scrape:
    
    def __init__(self, url, type):
        self.url = url
        self.raw_data_dict = {}
        self.list_data = []
        self.type = type
    
    def getRawData(self):
        options = Options()
        # options.add_argument("--headless=new")
        driver = webdriver.Chrome(options=options)
        driver.get(self.url)

        pos_props = driver.page_source.find('{"props"')
        temp1 = driver.page_source[pos_props:]
        
        if temp1[0] != "{":
            pos_props = driver.page_source.find('{"props"')
            temp1 = driver.page_source[pos_props:] 
        
        
        pos_requestContext = temp1.find(',"requestContext"')
        temp2 = temp1[pos_requestContext:]
        
        
        data_str = temp1.replace(temp2, "")
        data_str = data_str + "}}}"
        
        driver.quit()
        data_dict = json.loads(data_str)
        self.raw_data_dict = data_dict
    
    
    def getCleanData(self):
        all_data = self.raw_data_dict["props"]['pageProps']["pageData"]["chartTitles"]["edges"]
        
        for data in all_data:
            dict_data = {}
            
            try:
                rank = data["currentRank"]
                titulo = data["node"]["titleText"]["text"]
                url = data["node"]["primaryImage"]["url"]
                genero = data["node"]["titleGenres"]["genres"][0]["genre"]["text"]
                nota = data["node"]["ratingsSummary"]["aggregateRating"]
                plot = data["node"]["plot"]["plotText"]["plainText"]
                imdb_id = data["node"]["id"] 
                
                if self.type == "movie":
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
                
                if self.type == "show":
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
                      
                self.list_data.append(dict_data)
            
            except Exception as e:
                pass
            
    
        
    
    def getData(self):
        self.getRawData()
        self.getCleanData()
        return self.list_data


if __name__ == "__main__":
    testeTopMovie = Scrape("https://www.imdb.com/chart/top/", "movie")
    print(testeTopMovie.getData())