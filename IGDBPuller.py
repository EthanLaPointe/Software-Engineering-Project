import requests
from tabulate import tabulate
import pandas as pd
from datetime import datetime
import json
import sqlite3 

CLIENT_ID = "rdoke9snvpqvwh24bo7ck51je2rq2x"
CLIENT_SECRET = "290nhv6q3jdibuhhvemm8tohvlwdpa"

def fetch_game_details(title): 
    IGDB_URL = "https://api.igdb.com/v4/games"
    IGDB_HEADERS = {
        'Client-ID': CLIENT_ID,
        'Authorization': f'Bearer {4936617}'
    }

igdb_body = f"fields name,genres.name,themes.name,websites.url,game_engines.name,game_modes.name,platforms.name,player_perspectives.name; where name=\"{title}\"; limit 1;"

response = requests.post(IGDB_URL, headers = IGDB_HEADERS, data=igdb_body)

conn = sqlite3.connect("gamed_database.db")
cursor = conn.cursor 

igdb_body = "fields name; where name =\"Grand Theft Auto V\"; limit 1;"

if response.status_code == 200: 
    igdb_data = response.json()
    for game in data: 
        game_name = game['name']
        platforms = game['platforms']

        cursor.execute("INSERT INTO games (name, platforms) VALUES (?,?)", (game_name, ", ".join(platforms)))
        conn.commit()

    else: 
        print(f"Error: {response.status_code}")

    conn.close()