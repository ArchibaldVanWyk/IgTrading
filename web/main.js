/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var ig = (function(){
    let loginUrl="webresources/rest/login";
    
    return{
        getFace:function(){

            window.open("hello.html",'_self');
        },
        home:function(){
            window.open("index.html",'_self');
        },
        getXHR:function(method,url,callback){
            let xhr = new XMLHttpRequest();
            
            xhr.onreadystatechange=function(){
                if(xhr.readyState===4){
                    if(xhr.status===200){
                        callback(xhr);
                        console.log("in side getXHR");
                    }
                }
            };
            xhr.open(method,url);
//            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.setRequestHeader("Content-Type", "text/plain");
//            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Accept", "text/plain");
            return xhr;
        },
        login:function(){
            let out = document.getElementById("output");
            let req = this.getXHR("POST",loginUrl,(xhr)=>{
                out.innerHTML=xhr.responseText||xhr.responseXML||"no response";
                console.log("inside callback");
            });
            let json = {"identifier":"igtrading","password":"IgTrading1"};
            req.send(JSON.stringify(json));
        }
    };
})();


