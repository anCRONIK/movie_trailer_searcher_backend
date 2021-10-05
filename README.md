# Movie Trailer Searcher
This is simple application that uses another services and public APIs to find movie trailers.
Currently, it uses Imdb and Vimeo to search for trailers, but in future there could be some other services

This is API implementation that to be used by `view` application.

## Support folder
Contains data for local run.

## Usage
For local run please use spring profile `local` and check `support` folder for `docker-compose`. For local usage there is Wiremock setup which simulates APIs which are called by applicaton.

To search for trailers, call `api/movie-trailers?title=something`, for instance GET `http://34.116.169.92:8080/api/movie-trailers?title=spartan`. Application is secured with basic authentication so you need to provide those information as environment variables, otherwise, defualt password is `password` (for `user` and `admin`)

For usage, check Swagger generated documentation on path `v2/api-docs`

Application is deployed on google cloud and it is avaliable on url: `http://34.116.169.92:8080/`
