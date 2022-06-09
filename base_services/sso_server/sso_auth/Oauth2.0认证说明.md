                                      Oauth2.0认证中心认证功能说明
   一、账号登录
       
    1.URL:http://127.0.0.1:9000/api/auth/oauth/login
    2.方法:Post
    3.BODY:
            {
              "client_id": "client",
              "client_secret": "secret",
              "grant_type": "password",
              "password": "123456",
              "username": "admin"
            }
    4.参数:grant_type,username,password,client_id,client_secret
   二、短信登录 （对于同一账号---短信登录可强制此已登录账号下线重新登录获取token）
    
    1.URL:http://127.0.0.1:9000/api/auth/oauth/login
    2.方法:Post
    3.BODY:
            {
             "client_id": "client",
             "client_secret": "secret",
             "grant_type": "phone",
             "password": "123456",
             "username": "wangchao",
             "phone":15308180302,
             "code":000989
            } 
   三、刷新token
    
    1.URL:http://127.0.0.1:9000/api/auth/oauth/refresh
    2.方法:Post
    3.BODY:
        {
          "client_id": "client",
          "client_secret": "secret",
          "grant_type": "refresh_token",
          "refresh_token": "eyJhbGciOiJIUzUxMiJ9.CxeyJ1c2VySWQiOjM3LCJ1c2VyX25hbWUiOiJhZG1pbiIsImF1dGhvcml0aWVzIjoiW1wiU3lzdGVtQ29udGVudERlbGV0ZVwiLFwidGVzdFwiLFwiU3lzdGVtQ2xpZW50SW5zZXJ0XCIsXCJTeXN0ZW1Vc2VyVmlld1wiLFwiU3lzdGVtVXNlclwiLFwiU3lzdGVtVXNlckluc2VydFwiLFwiU3lzdGVtUmVnaXN0ZXJcIixcIlN5c3RlbVVzZXJEZWxldGVcIixcIlN5c3RlbVVzZXJVcGRhdGVcIixcIlN5c3RlbUNvbnRlbnRVcGRhdGVcIixcIlN5c3RlbUNvbnRlbnRWaWV3XCIsXCJTeXN0ZW1cIl0iLCJjbGllbnRfaWQiOiJjbGllbnQiLCJzY29wZSI6IltcImFwcFwiXSIsImp0aSI6IjE0MzZkYjZjLWE2MjAtNGFjOC04ZjhiLTAyYzVmMGUxMjI5MSIsImF0aSI6IjAxNjNlM2UwLWFiYjgtNDA0Yy04ZjIyLTMyOThiMDhmZDljNyIsImlhdCI6MTYxMDUyNjA1NiwiZXhwIjoxNjExNTYyODU1fQdB.gJBkAW7pDLFH2hrf164F9by00au2NoZ75TDFxCDURRwtJ6-MZmVXOX1yS-_BpBi9OECDd2xsX9B9F6m_hgsRnA"
        }
    4.参数:grant_type,refresh_token,client_id,client_secret
   四、登出
     
    1.URL:http://127.0.0.1:9000/api/auth/oauth/logout?access_token=eyJhbGciOiJIUzUxMiJ9.XceyJ1c2VySWQiOjM3LCJ1c2VyX25hbWUiOiJhZG1pbiIsImF1dGhvcml0aWVzIjoiW1wiU3lzdGVtQ29udGVudERlbGV0ZVwiLFwidGVzdFwiLFwiU3lzdGVtQ2xpZW50SW5zZXJ0XCIsXCJTeXN0ZW1Vc2VyVmlld1wiLFwiU3lzdGVtVXNlclwiLFwiU3lzdGVtVXNlckluc2VydFwiLFwiU3lzdGVtUmVnaXN0ZXJcIixcIlN5c3RlbVVzZXJEZWxldGVcIixcIlN5c3RlbVVzZXJVcGRhdGVcIixcIlN5c3RlbUNvbnRlbnRVcGRhdGVcIixcIlN5c3RlbUNvbnRlbnRWaWV3XCIsXCJTeXN0ZW1cIl0iLCJjbGllbnRfaWQiOiJjbGllbnQiLCJzY29wZSI6IltcImFwcFwiXSIsImp0aSI6ImU1MDhlZGE0LTEyMjItNDc1MS04MWI2LWRiMmNhZjVhYWNiMCIsImlhdCI6MTYxMDA3NDc5MCwiZXhwIjoxNjEwMTE3OTg5fQdB.5JDk0roxgjtP4Ov4RD8pAoowmBWGuUbIBzQ9sxMS_t2ZawCvlTevkcaj2qACKA3V5XaywxATQQApGzuhJnWYyQ
    2.方法:DELETE
    3.参数:access_token
    
   五、获取短信验证码
         
        1.URL:http://127.0.0.1:6621/api/auth/oauth/sms/send?mobile=15620182302
        2.方法:POST
        3.BODY:
                {
                  "client_id": "client",
                  "client_secret": "secret"
                }
   
   六、密码模式：（使用密码模式，授权码模式在经过网关跳转时，出现丢失后缀路径参数情况）
   
    1.获取token
    方法：Get
    URL：http://localhost:8083/oauth/token?grant_type=password&username=admin&password=123456
    2.刷新token
    方法：Get
    URL：http://localhost:8083/oauth/token?grant_type=refresh_token&refresh_token=1ee68055-b35b-44a2-9910-eedeca82fb6c&client_id=client&client_secret=secret
   七、授权码模式：
   
    1.获取令牌
    方法：Get
    URL：http://localhost:8083/oauth/authorize?client_id=client&response_type=code 
    
    2.获取token
    方法：Post 
    Body：form-data 
    grant_type:authorization_code
    code:aWyiXy
    URL：http://client2:secret@127.0.0.1:8083/oauth/token 
    
    3.刷新token
    方法：Get
    URL：http://localhost:8083/oauth/token?grant_type=refresh_token&refresh_token=1ee68055-b35b-44a2-9910-eedeca82fb6c&client_id=client&client_secret=secret