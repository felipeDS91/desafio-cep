<h1 align="center">
  <img
    alt="Logo"
    src="https://res.cloudinary.com/dixtjpk8s/image/upload/v1615168598/Projects/logo_sn6wod.png" width="250px"
  />
</h1>

<h3 align="center">
  Desafio consulta CEP (Backend Java)
</h3>

<p align="center">
  <img alt="GitHub top language" src="https://img.shields.io/github/languages/top/felipeDS91/desafio-cep?color=%230484ff">
  
  <a href="https://www.linkedin.com/in/felipe-douglas-dev/" target="_blank" rel="noopener noreferrer">
    <img alt="Made by" src="https://img.shields.io/badge/made%20by-felipe%20douglas-%230484ff">
  </a>

  <img alt="Repository size" src="https://img.shields.io/github/repo-size/felipeDS91/desafio-cep?color=%230484ff">
  
  <a href="https://github.com/FelipeDS91/goldman-frontend/commits/master">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/FelipeDS91/desafio-cep?color=%230484ff">
  </a>

  <a href="https://github.com/FelipeDS91/goldman-frontend/issues">
    <img alt="Repository issues" src="https://img.shields.io/github/issues/FelipeDS91/desafio-cep?color=%230484ff">
  </a>

<p align="center">
  <a href="#-sobre-o-projeto">Sobre o projeto</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-tecnologias-utilizadas">Tecnologias utilizadas</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#-getting-started">Getting started</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;  
</p>

</br>


## 👨🏻‍💻 Sobre o projeto

Api desenvolvida para consulta de CEP que utiliza o webservice da [ViaCEP](https://viacep.com.br/) para consultar CEP e mantem um cache de todos os resultados previamente consultados.

Esta Api conta com teste unitário e de integração para garantir o funcionamento correto das funcionalidades.

Para acessar esta aplicação, clique aqui: [Desafio Consulta CEP API](https://desafio-busca-cep.herokuapp.com/swagger-ui.html)

Para acessar o código fonte do frontend que utiliza esta API, clique aqui: [Consulta CEP Frontend](https://github.com/FelipeDS91/desafio-cep-frontend)

Para acessar a aplicação frontend que utiliza esta API, clique aqui: [Desafio Consulta CEP](https://desafio-consulta-cep.netlify.app/)


## 🚀 Tecnologias utilizadas

Tecnologias utilizadas para desenvolver esse projeto

- [Java](https://www.java.com/pt_BR/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [JUnit](https://junit.org/junit5/)
- [PostgreSQL](https://www.postgresql.org/)
- [H2 Database](https://www.h2database.com/html/main.html)
- [Swagger](https://swagger.io/)


## 💻 Getting started

### Requerimentos
 - [Docker](https://docs.docker.com/)

**Clone o projeto e acesse a pasta**

```bash
$ git clone https://github.com/felipeDS91/desafio-cep.git && cd desafio-cep
```

**Siga os passos abaixo**

```bash
# Inicie o projeto 
$ docker-compose up
```

* O comando "docker-compose up" irá compilar o projeto, rodar os testes, gerar o pacote e criar o banco de dados, portanto, é normal que demore alguns minutos

### Documentação
  - Disponível através do link ```http://localhost:8080/swagger-ui.html``` ou ainda em ```https://desafio-busca-cep.herokuapp.com/swagger-ui.html```

Made with 💜&nbsp; by Felipe Douglas 👋 [See my linkedin](https://www.linkedin.com/in/felipe-douglas-dev/)
