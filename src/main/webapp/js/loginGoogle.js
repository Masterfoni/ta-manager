jQuery(function(){
	jQuery.noConflict();
});
onload = startApp;

var googleUser = {};

//var startApp = function() {
function startApp(){
  gapi.load('auth2', function(){
    // Retrieve the singleton for the GoogleAuth library and set up the client.
    auth2 = gapi.auth2.init({
      client_id: '835716531913-m3mt3905k8itbmflg8t7mlqabgjcruce.apps.googleusercontent.com',
      cookiepolicy: 'single_host_origin',
      // Request scopes in addition to 'profile' and 'email'
      //scope: 'additional_scope'
    });
    attachSignin(document.getElementById('customBtn'));
  });
};

function attachSignin(element) {
  console.log(element.id);
  auth2.attachClickHandler(element, {},
      function(googleUser) {
        onSignIn(googleUser);
      }, 
      function(error) {
        alert(JSON.stringify(error, undefined, 2));
      });
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
}

function onSignIn(googleUser) {
	  var profile = googleUser.getBasicProfile();
    
    if(googleUser.getHostedDomain() != 'a.recife.ifpe.edu.br') {
      document.getElementById("loginErrorButton").click();
      signOut();
    }
	  
	  var id_token = googleUser.getAuthResponse().id_token;
	  
	  login([{name:'idToken', value:id_token}]);
}

function handleComplete(xhr, status, args) {
    var nomeDoAtributo = args.logou;
    alert(nomeDoAtributo);
}
