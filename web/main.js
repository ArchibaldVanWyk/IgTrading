/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var ig = (function(){
    let loginUrl="webresources/rest/login";
    let logoutUrl="webresources/rest/logout";
    let marketNavUrl="webresources/rest/marketnavigation";
    let tokenUrl = "webresources/rest/token";
    let accountsUrl = "webresources/rest/account";
    let positionsUrl = "webresources/rest/positions";
    let nodesUrl = "webresources/rest/nodes";
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
//                        callback(xhr);
                    }
                    callback(xhr);
                }
            };
            xhr.open(method,url);
            return xhr;
        },
        login:function(){
            let out = document.getElementById("output");
            let req = this.getXHR("POST",loginUrl,(xhr)=>{
                out.innerHTML=xhr.responseText||xhr.responseXML||"no response"+xhr.getAllResponseHeaders().toString();;
            });
            let username = document.getElementById("username").value==="username"?"username":document.getElementById("username").value;
            let password = document.getElementById("password").value==="password"?"password":document.getElementById("password").value;;
            
            let json = {"identifier":"igtrading","password":"IgTrading1"};
            req.send(JSON.stringify(json));
        },
        logout:function(){
            let out = document.getElementById("output");
            let req = this.getXHR("POST",logoutUrl,(xhr)=>{
                out.innerHTML=xhr.responseText||xhr.responseXML||"no response";
            });
            let json = {"identifier":"igtrading","password":"IgTrading1"};
            req.send(null);
        },
        marketNav:function(){
            let out = document.getElementById("output");
            let req = this.getXHR("GET",marketNavUrl,(xhr)=>{
                out.innerHTML=(xhr.responseText||xhr.responseXML||"no response");
               
            });
            let json = {"identifier":"igtrading","password":"IgTrading1"};
            req.send(null);
        },
        token:function(){
            let out = document.getElementById("output");
            let req = this.getXHR("GET",tokenUrl,(xhr)=>{
                out.innerHTML=(xhr.responseText||xhr.responseXML||"no response");
               
            });
            req.send(null);
        },
        accounts:function(){
            let out = document.getElementById("output");
            let req = this.getXHR("GET",accountsUrl,(xhr)=>{
                out.innerHTML=(xhr.responseText||xhr.responseXML||"no response");
               
            });
            req.send(null);
        },
        positions:function(){
            let out = document.getElementById("output");
            let req = this.getXHR("GET",positionsUrl,(xhr)=>{
                out.innerHTML=(xhr.responseText||xhr.responseXML||"no response");
               
            });
            req.send(null);
        },
        nodes:function(){
            let out = document.getElementById("output");
            let req = this.getXHR("GET",nodesUrl,(xhr)=>{
                out.innerHTML=(xhr.responseText||xhr.responseXML||"no response");
            });
            req.send(null);
        }
    };
})();


