# billing service

## Build the image

```bash
docker build -t billing .
```

## Create network for all our microservices

```bash
docker network create rso
```

## Run database in network
```bash
docker run -d --name pg-billing --network="rso" -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=billing -p 5435:5432 postgres:13
```

## Run the container in network

```bash
docker run -p 8087:8087 --name billing --network="rso" -e KUMULUZEE_DATASOURCES0_CONNECTIONURL=jdbc:postgresql://pg-billing:5432/billing billing
```

## Run the container from Docker hub in network

```bash
docker run -p 8087:8087 --name billing --network="rso" anzeha/billing:latest
```