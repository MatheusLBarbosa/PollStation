# Polling Station

This project is the solution to a technical challenge. Its purpose is to expose an REST API that will manage a voting session in an assembly and for this the creation of four endpoints was requested.

<p align="center"><img src="https://static.independent.co.uk/s3fs-public/thumbnails/image/2019/12/11/16/polling-station.jpg?width=300" alt="project-image"></p>


## 🛠️ Installation Steps:

É necessário que tenha o Docker e docker-compose instalado na sua máquina;

```bash
  docker-compose up
```
    
## 💻 API Documentation

#### Create new issue

```http
  POST /api/v1/issue
```

````
{
  "title": "string",
  "description": "string",
  "owner": "string"
}
````
#### Start a polling session

```http
  POST /api/v1/session/{isueId}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `issueId`      | `Long` | **Obrigatório**. ID of the issue that the session will be opened  |

#### Vote in a polling session

```http
  POST /api/v1/vote
```

```
{
  "sessionId": 0,
  "cpf": "string",
  "voteChoice": "YES"
}
```

#### Return the polling result of a session

```http
  GET /api/v1/vote//{sessionId}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `sessionId`      | `Integer` | **Obrigatório**. ID of the session that you want the result |
