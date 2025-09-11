const express = require("express");
const serwer = express();
var bodyParser = require("body-parser");

serwer.use(bodyParser.json());
console.log("Hello world");
console.log("");

serwer.set('view engine', 'ejs');
serwer.use(bodyParser.urlencoded({ extended: true }));


serwer.get('/', (req, res) => {
    res.render('form');
});

let userInputs = [];
let available = ['Bogdan', 'Olga', 'Marzena', 'Jakub', 'Maciej', "Gabrysia", 'Klaudia', 'Milosz', 'Jaasio'];
let users = ['Bogdan', 'Olga', 'Marzena', 'Jakub', 'Maciej', "Gabrysia", 'Klaudia', 'Milosz', 'Jaasio'];
// http://xx.xxx.xxx.xx:1111/

serwer.post('/submit', (req, res) => {
    const userInput = req.body.text;
    userInputs.push(userInput);

    let losowal = false;
    for (let j = 0; j < userInputs.length -1; j++){
      if (userInputs[j] == userInput){
        losowal = true;
      }
    }



    if (!losowal){
      let poprawne = false;
      for (let i = 0; i < available.length; i++) {
          if (userInput === users[i]) {
              let found = false;
              let count = 0;
              while (!found && count<100) {
                  let randomDigit = Math.floor(Math.random() * available.length);                  
                  if (available[randomDigit] !== "" && available[randomDigit] !== userInput) {
                      if (userInput == 'Bogdan' || userInput == 'Marzena'){
                        res.send(message(`Dzien dobry ${userInput}`,`wylosowales/as ${available[randomDigit]}`));
                      } else {
                        res.send(message(`Siema ${userInput}`,`wylosowales/as ${available[randomDigit]}`));
                      }
                      console.log(`${userInput} dostal osobe`)
                      found = true;
                      available[randomDigit] = "";  
                  }
                  count = count + 1;
              }
              if (count == 100){
                res.send(message(userInput,"sorki ale musimy powtorzyc losowanie"));
              }
              poprawne = true;
          }
      }
      if (!poprawne){
        res.send(message(userInput,"jakas literowka"));
      }
    } else {
      res.send(message(userInput,"juz losowales"));
    }

    //console.log(userInputs);
    //console.log(available);
});

function message(name, text) {
  return `
    <html>
      <head>
        <style>
          body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            font-family: Arial, sans-serif;
            text-align: center;
            background-color: hotpink;

          }
          .content {
            font-size: 60px; /* Dynamic font size */
          }
        </style>
      </head>
      <body>
        <div class="content">
          <h1>${name}</h1>
          <p>${text}</p>
        </div>
      </body>
    </html>
  `;
}

serwer.listen(1111);
