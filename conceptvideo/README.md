# RELAY FOR MODEL-API

<br/>

## Project Build
	npm install

<br/>

## Start Local Project
	npm start

<br/><br/>

---
<br/><br/>

## Example
|||
|:-|:-|
|`url`|/api/v1/develop/diagnosis/setting|
| `headers` |{'**X-API-KEY**': 'lzNBWPWXa24afsu8dWSitDLzcTkyidei'}|
| `body` |{"mbrId":"TEST0203020203023096","deviceScnCd":"NORMAL","deviceNm":"Note10",  "gameVer":"Farm1.296","osScnCd":"MINT","bgnLvl":"C","gameCd":"TST","langCd":"KO",  "nationCd":"JP","timeZone":-2}|

<br/>

## Game Api Host
||||
|:-|:-|:-|
|`dev`|`ip`|http://21.123.7.230:8088/health-check|
|`dev`|`domain`|https://dev-brs-game-api.wjtb.kr/health-check|
|`prd`|`domain`|https://prd-brs-game-api.mathpid.com/health-check|
|`prd`|`domain`|https://prd-brs-modelapi.mathpid.com/health-check|


<br/>

## Game Url List
	/api/v1/game/diagnosis/setting
	/api/v1/game/diagnosis/progress
	/api/v1/game/learning/setting
	/api/v1/game/learning/progress

## Competition Url List
	/api/v1/develop/diagnosis/setting
	/api/v1/develop/diagnosis/progress
	/api/v1/develop/learning/setting
	/api/v1/develop/learning/progress

<br/><br/>

---
<br/><br/>

## Deploy Development
	https://git.wjtb.kr/ai-labs/barosem/relay-model.git
	dev-brs-relay-model
	develop

## Deploy Production
	https://git.wjtb.kr/ai-labs/barosem/relay-model.git
	prd-brs-relay-model
	master