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
    let nodesNamesUrl = "webresources/rest/nodeFilenames";
    let s = "";
    let c = 0;
    let alpha="A";
    let getXHR=function(method,url,callback){
        let xhr = new XMLHttpRequest();

        xhr.onreadystatechange=function(){
            if(xhr.readyState===4){
                if(xhr.status===200){
                    callback(xhr);
                }
            }
        };
        xhr.open(method,url);
        return xhr;
    };
    let nodeFilenames = function(c){
        let fnames = document.getElementById('fileNames');
        fnames.innerHTML="";
        let json;
        let r = getXHR("GET",nodesNamesUrl+"/"+c,(xhr)=>{
            json = JSON.parse(xhr.responseText);
            let opt;
            for(let i=0;i<json.length;i++){
                opt = document.createElement("option");
                opt.innerHTML = json[i];
                opt.onclick= ()=>{ig.nodes(alpha,json[i]);};
                fnames.appendChild(opt);
            }
            
        });
        r.send(null);
    };
    return{
        s,c,getXHR,alpha,
        getFace:function(){
            
            window.open("hello.html",'_self');
        },
        home:function(){
            window.open("index.html",'_self');
        },
        
        login:function(){
            let out = document.getElementById("output");
            let req = getXHR("POST",loginUrl,(xhr)=>{
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
            let req = getXHR("GET",tokenUrl,(xhr)=>{
                out.innerHTML=(xhr.responseText||xhr.responseXML||"no response");
               
            });
            req.send(null);
        },
        accounts:function(){
            let out = document.getElementById("output");
            let req = getXHR("GET",accountsUrl,(xhr)=>{
                out.innerHTML=(xhr.responseText||xhr.responseXML||"no response");
               
            });
            req.send(null);
        },
        positions:function(){
            let out = document.getElementById("output");
            let req = getXHR("GET",positionsUrl,(xhr)=>{
                out.innerHTML=(xhr.responseText||xhr.responseXML||"no response");
               
            });
            req.send(null);
        },
        nodes:function(letter,name){
            let out = document.getElementById("nodes");
            
            alpha=letter;
            if(name!==null){
                let req = getXHR("GET",nodesUrl+"/"+alpha+"/"+name,(xhr)=>{
                    let json = JSON.parse(xhr.responseText);
                    let opt;
                    out.innerHTML="";
                    for(let i=0;i<json.length;i++){
                        opt = document.createElement("option");
                        opt.innerHTML = json[i];
                        out.appendChild(opt);
                    }
                });
                req.send(null);
            }
            else{
                nodeFilenames(letter);
            }
        }
    };
})();


