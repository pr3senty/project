import json
import requests
import time

def send(messages):
    prompt = {
        "modelUri": "gpt://b1gsics667a04445463a/yandexgpt",
        "completionOptions": {
            "stream": False,
            "temperature": 0.6,
            "maxTokens": "2000"
        },
        "messages": messages
    }

    url = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion"
    headers = {
        "Content-Type": "application/json",
        "Authorization": "Api-Key AQVNwQ1cLuJZUuM1NMWh0622UP6kEtv_zgx-HQXw"
    }

    response = requests.post(url, headers=headers, json=prompt)

    response.raise_for_status()

    return response.json()

def get_gpt_response_by_query(raw_string):
    messages = json.loads(raw_string)

    a = send(messages)

    return a["result"]