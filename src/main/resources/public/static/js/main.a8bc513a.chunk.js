(this.webpackJsonpnss=this.webpackJsonpnss||[]).push([[0],{25:function(e,t,n){e.exports=n(39)},30:function(e,t,n){},39:function(e,t,n){"use strict";n.r(t);var a=n(0),r=n.n(a),o=n(12),l=n.n(o),u=(n(30),n(4)),c=n(5),i=n(6),s=n(7),p=n(16),m=n(1),g=n(9),d=n(15),h=n.n(d),f=function(e){Object(s.a)(n,e);var t=Object(i.a)(n);function n(){return Object(u.a)(this,n),t.apply(this,arguments)}return Object(c.a)(n,[{key:"componentDidMount",value:function(){}},{key:"render",value:function(){return r.a.createElement("div",{className:"navbar-wrapper"},r.a.createElement("nav",null,r.a.createElement("ul",null,r.a.createElement("li",null,r.a.createElement(h.a,{to:"/"},"Homepage")),r.a.createElement("li",null,r.a.createElement(h.a,{to:"/login"},"Login")),this.props.loggedIn?r.a.createElement("li",null,r.a.createElement(h.a,{to:"/j_spring_security_logout",onClick:this.props.logout},"Logout")):null,r.a.createElement("li",null,r.a.createElement("a",null,"4")),r.a.createElement("li",null,r.a.createElement("a",null,"5")),r.a.createElement("li",null,r.a.createElement("a",null,"6")))))}}]),n}(r.a.Component),b=Object(g.b)((function(e,t){return{loggedIn:e.loggedIn}}),(function(e){return{logout:function(){e({type:"LOGOUT"})}}}))(f),E=function(e){Object(s.a)(n,e);var t=Object(i.a)(n);function n(){return Object(u.a)(this,n),t.apply(this,arguments)}return Object(c.a)(n,[{key:"render",value:function(){return r.a.createElement("div",{className:"homepage-wrapper"},r.a.createElement(b,null))}}]),n}(r.a.Component),O=function(e){Object(s.a)(n,e);var t=Object(i.a)(n);function n(){var e;Object(u.a)(this,n);for(var a=arguments.length,r=new Array(a),o=0;o<a;o++)r[o]=arguments[o];return(e=t.call.apply(t,[this].concat(r))).loginRequest=function(t){t.preventDefault();var n={username:document.querySelector("input#username-input").value,password:document.querySelector("input#password-input").value};fetch("http://localhost:8080/api/users/login",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(n)}).then((function(t){202===t.status&&t.ok&&t.json().then((function(t){return e.props.userLoggedIn(t)}))})).catch((function(e){console.log(e)}))},e.login=function(t){fetch("http://localhost:8080/api/users/current",{}).then((function(t){t.json().then((function(t){return e.props.userLoggedIn(t)}))}))},e}return Object(c.a)(n,[{key:"render",value:function(){return r.a.createElement("div",{className:"login-wrapper"},r.a.createElement(b,null),r.a.createElement("form",{onSubmit:this.loginRequest},r.a.createElement("label",{htmlFor:"username-input"},"username:"),r.a.createElement("input",{id:"username-input",placeholder:"Username"}),r.a.createElement("label",{htmlFor:"password-input"},"password:"),r.a.createElement("input",{id:"password-input",type:"password",placeholder:"password"}),r.a.createElement("button",{type:"submit"},"submit")))}}]),n}(r.a.Component),v=Object(g.b)((function(e){return{user:e.user}}),(function(e){return{userLoggedIn:function(t){e({type:"LOGGING_IN",user:t})}}}))(O),j=function(e){Object(s.a)(n,e);var t=Object(i.a)(n);function n(){return Object(u.a)(this,n),t.apply(this,arguments)}return Object(c.a)(n,[{key:"render",value:function(){return r.a.createElement("div",{className:"logout-wrapper"},r.a.createElement(m.Redirect,{to:"/"}),">")}}]),n}(r.a.Component),y=function(e){Object(s.a)(n,e);var t=Object(i.a)(n);function n(){return Object(u.a)(this,n),t.apply(this,arguments)}return Object(c.a)(n,[{key:"render",value:function(){return r.a.createElement(p.a,null,r.a.createElement(m.Route,{exact:!0,path:"/",component:E}),r.a.createElement(m.Route,{exact:!0,path:"/login",component:v}),r.a.createElement(m.Route,{exact:!0,path:"/j_spring_security_logout",component:j}))}}]),n}(a.Component),w=n(14);Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));var I=n(21),k={loggedIn:sessionStorage.getItem("loggedin")||!1,user:sessionStorage.getItem("user")||null},S=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:k,t=arguments.length>1?arguments[1]:void 0;return"LOGGING_IN"==t.type?(sessionStorage.setItem("user",JSON.stringify(t.user)),sessionStorage.setItem("loggedin",!0),Object(I.a)({},e,{loggedIn:!0,user:t.user})):"LOGOUT"==t.type?(sessionStorage.clear(),Object(I.a)({},e,{loggedIn:!1,user:null})):e},N=Object(w.b)(S);l.a.render(r.a.createElement(r.a.StrictMode,null,r.a.createElement(g.a,{store:N},r.a.createElement(y,null))),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then((function(e){e.unregister()})).catch((function(e){console.error(e.message)}))}},[[25,1,2]]]);
//# sourceMappingURL=main.a8bc513a.chunk.js.map