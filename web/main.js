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
    let recur = function(a,c,count,out){
        setTimeout((ch,i,j,el)=>{
            let r = getXHR("GET",nodesUrl+"/"+ch+"/"+i,(req)=>{
                el.innerHTML=el.innerHTML+req.responseText;
                if(i<j)recur(ch,++i,j,el);
            });
            r.send(null);
        },300,a,c,count,out);
    };
    let nodeFilenames = function(c){
        let fnames = document.getElementById('fileNames');
        fnames.innerHTML="";
        let json;
        let r = getXHR("GET",nodesNamesUrl+"/"+c,(xhr)=>{
            json = JSON.parse(xhr.responseText);
            let opt;console.log("adding opts len"+json.length);
            for(let i=0;i<json.length;i++){
                opt = document.createElement("option");console.log("option "+i);
                opt.innerHTML = json[i];
                fnames.appendChild(opt);
            }
            
        });
        r.send(null);
    };
    return{
        s,c,getXHR,
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
        nodes:function(a,c){
            let out = document.getElementById("output");
            let count =0;
            if(count===0)nodeFilenames(a);
            let req = getXHR("GET",nodesUrl+"/"+a+"/"+(c===null?0:c),(xhr)=>{
                if(xhr.responseText==="busy"){
                    setTimeout(()=>{
                        out.innerHTML=(xhr.responseText||xhr.responseXML||"no response")+ig.s;
                        ig.s+=".....";
                        if(ig.c>=10){ig.s="";ig.c=0;}
                        ig.c++;
//                        ig.nodes(a,null);
                    },1000);
                }
                else{
                    let json = JSON.parse(xhr.responseText);
                    count = parseInt(json[0].split(":")[1]);
                    out.innerHTML=JSON.stringify(json);
                    let cc = 1;
                    recur(a,cc,count,out);
                    
                    
                }
            });
            req.send(null);
        }
    };
})();


