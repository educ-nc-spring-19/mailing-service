# mailing-service
Implementation of Mailing microservice

### Init and fill Database
1. Create database `educ_nc_spring_19`
2. Connect to database `educ_nc_spring_19`
3. Run `INIT_USER-SCHEMA-TABLES.sql` to create user and schema `mentoring_engine` with tables
4. Run `FILL_TABLES.sql` to fill tables with some values

### Request examples

1. 1. **POST** - localhost:55060/mailing-service/rest/api/v1/template/create
   2. body (json):
     
            {
                "creator_id":"be8ddc66-58b6-409e-ae35-fc1d80a456b4",
            
                "type":"congratulations",
            
                "header":"Поздравляем, %Student name%!",
            
                "text":"Вы прошли собеседование на участие в Учебном Центре. Вашим куратором будет %Mentor name%, ваш бэкап - %Backup name%"
            }
    
2. 1. **POST** - localhost:55060/mailing-service/rest/api/v1/letter/send
   2. body (json):
   
           {
                "receiver_id":"8b7bce93-3d3b-45fe-b699-ea04d5c3afb3",
                
                "receiver_type":"student",
               
                "type":"congratulations",
               
                "Args":{
                
                    "Student name":"8b7bce93-3d3b-45fe-b699-ea04d5c3afb3",
                    
                    "Mentor name":"d8950a90-7541-4636-af69-3b77954bff8c",
                    
                    "Backup name":"b7e728a7-ee15-407b-9996-05236fbb966e"
                }
           }
   
3. 1. **POST** - localhost:55060/mailing-service/rest/api/v1/template/create
   2. body (json):
   
            {
                "creator_id":"be8ddc66-58b6-409e-ae35-fc1d80a456b4",
           
                "type":"test",
           
                "header":"Testing all data types",
           
                "text":"Mentor name from Master Data - %Mentor name%\nBackup name from Master Data -  %Backup name%\nStudent name from Master Data -  %Student name%\nAddress -  %Address%\nDate -  %Date%\nTime -  %Time%\nSome for default -  %random arg type%\nAccept link -  %Accept link%\nReject link -  %Reject link%\nDefault -  %Default%\nSome random again -  %random arg%"
           }
   
4. 1. **POST** - localhost:55060/mailing-service/rest/api/v1/letter/send
   2. body (json):
   
           {
                "receiver_id":"97ee68a6-2993-49cd-926b-887911876422",
                
                "receiver_type":"student",
               
                "type":"test",
               
                "Args":{
                    
                    "Mentor name":"d8950a90-7541-4636-af69-3b77954bff8c",
                    
                    "Backup name":"b7e728a7-ee15-407b-9996-05236fbb966e",
                    
                    "Student name":"97ee68a6-2993-49cd-926b-887911876422",
                    
                    "Address":"Кронверкский 49, г. Санкт-Петербург",
                    
                    "Date":"2019-05-07T21:38:38.132+03:00",
                    
                    "Time":"2019-05-07T21:38:38.132+03:00",
                    
                    "random arg type":"Я рандомная штука",
                    
                    "Accept link":"",
                    
                    "Reject link":"localhost:55010/mentoring-engine/rest/api/v1/rpc/invite?link=vpsxrROnu9mfZ9MsX6a%2Faok3%2FYVe%2BkaZFU1pcJvs6uERYGoOpbNQ6L8SRZwIBPP04YEj1ezWrdajqbPfZgBngvbBG6xKKviV2KcY7zj1DCtMx34pHRXkdBzS06lMuO%2BRPEWNUj59P1%2BxPMIGu0Kkq1rSlt3l5OcZDalw7KNHrqyWed%2BhCJuexCccGBgDTHmAMEhU11txOG19VTH%2FuJ0D9Q%3D%3D",
                    
                    "Default":"немного дополнительной инфы",
                    
                    "random arg":"любовь ещё быть может в душе моей остыла не совсем"
                }
           }