1. Framework
- JDK 1.8
- Spring boot 2
- SPring JPA
- Maven 3
- Podam lib (generate test data automatically)
2. Technical Debt
Because this project is just POC so that's why i do it simple and fast as possible as. However, if you want to base on it to build real project. You should be awared some potential issues following:
- Problem N + 1 query:
  + Now, in entity, i am using One-To-Many relationship to map related entities in main entity. So It causes performance     
    problem if data is large, because we need to run N + 1 query. Refer to this link to know more detail about the problem :   
    https://dzone.com/articles/how-identify-and-resilve-n1. 
  + Solution: I suggest use SpringJDBC instead of Spring JPA
- Read and process large file:
  + If we read and process whole large file as 5Mb,10Mb.... It's problem about performance
  + Solution: read and process each partial file.
- Sugesstion: use mapstruct lib to map back and forth between DTO and Entity if having too much
3. Endpoint
- POST /gpx/upload: Upload and store .gpx file
- GET /gpx/{id}: get detail gpx file by id
- GET /gpx/track: get latest track list. By default, it will return 10 latest track. If you would like to get more, you should use param "size" with this API
