1. Web Service built as a Maven project. 
2. Jersey is used to implement the servlet SongWebService.java which manages all requests.
3. Song.java is the class which is used to model the data.
4. Jersey Test is used to implement the SongWebServiceTest.java class.
5. All methods are the following:

To Create
http://localhost:8080/JAXRS-SongWebService/rest/songwebservice/create?id=1&title=title1&artist=artist1&genre=genre1
To Update
http://localhost:8080/JAXRS-SongWebService/rest/songwebservice/update?id=1&artist=artist2
//To List
http://localhost:8080/JAXRS-SongWebService/rest/songwebservice/list?artist=artist1
http://localhost:8080/JAXRS-SongWebService/rest/songwebservice/list?genre=genre1
//To Search
http://localhost:8080/JAXRS-SongWebService/rest/songwebservice/search?artist=artist1
http://localhost:8080/JAXRS-SongWebService/rest/songwebservice/search?title=title1
//To Remove
http://localhost:8080/JAXRS-SongWebService/rest/songwebservice/delete?id=1
