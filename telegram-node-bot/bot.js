// const hReq = require('./httpRequests.js');

//env variables
require('dotenv').config()
//httpsrequests
const https = require('node:https');
//init bot
const TelegramBot = require('node-telegram-bot-api');

// const serverUrl = "https://localhost:8080/api/";

let loggedInUsers = [
  { userid: 1, chatid: process.env.TELEGRAM_CHATID }
]

const token = process.env.TELEGRAM_TOKEN;

// Create a bot that uses 'polling' to fetch new updates

const bot = new TelegramBot(token, { polling: true });

//TODO - FIX and test all the Https requests to Springboot... it's no working

// HttpsRequest helper functions =========================================================

function getRequest(URI) {
  fetch('https://localhost:8080/api/tasks')
    .then(response => response.json())
    .then(data => {
      // Handle the response data
      console.log(data);
    })
    .catch(error => {
      // Handle any errors
      console.error('Error:', error);
    });
}

// postReq
// https://localhost:8080/api/tasks
const payload = {
  "email": "test@test.com",
  "password": "test"
}
function postRequest(POST_URI, payload) {

  fetch(POST_URI, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(payload)
  })
    .then(response => console.log(response.json()))
    .then(data => {
      // Handle the response data
      console.log(data);
      return data;
    })
    .catch(error => {
      // Handle any errors
      console.error('Error:', error);
      // return error;
    });
}


// function getRequest(hostName, serverPort, pathParam) {
//   const options = {
//     hostname: hostName,
//     port: serverPort,
//     path: pathParam,
//     method: 'GET',
//   };

//   const req = https.request(options, (res) => {
//     console.log('statusCode:', res.statusCode);
//     console.log('headers:', res.headers);

//     res.on('data', (d) => {
//       process.stdout.write(d);
//       return d;
//     });
//   });

//   req.on('error', (e) => {
//     console.error(e);
//   });
//   req.end();
// }

// function httpsGETRequest(url) {
//   const httpsRequest = https.request(url, (response) => {
//     let data = '';
//     response.on('data', (chunk) => {
//       data = data + chunk.toString();
//     });

//     response.on('end', () => {
//       const body = JSON.parse(data);
//       console.log(body);
//       return body;
//     });
//   })

//   httpsRequest.on('error', (error) => {
//     console.log('An error', error);
//   });

//   httpsRequest.end()
// }

// function httpsPOSTRequest(url) {
//   app.post(route, function (request, response) {

//   })
// }


// telegram =============================================================


// Matches "/echo <text>" (.+) regex means everything
bot.onText(/\/echo (.+)/, (msg, match) => {
  console.log("echo received")
  // 'msg' is the received Message from Telegram
  // 'match' is the result of executing the regexp above on the text content
  // of the message
  console.log(match);
  console.log(msg);
  const chatId = msg.chat.id;
  const resp = match[1]; // the captured text

  // send back the text to the chat
  bot.sendMessage(chatId, resp);
});

//greets the user
bot.onText(/\/greet (.+)/, function (msg, match) {

  const response = `Hello ${msg.from.first_name} ${msg.from.last_name}`;

  bot.sendMessage(msg.chat.id, response);

});

//start
bot.onText(/\/start/, function (msg, match) {
  const currentChatId = msg.chat.id;
  const response = "Hi I'm the LifeGuild bot."
  bot.sendMessage(chatid, response);
  array.forEach(user => {
    let userX = user.find(chatid => loggedInUsers.chatid = chatid);
    if (!!userX) {
      bot.sendMessage(currentChatId, "Welcome.")
      const response = "\n\nYou can send me these commands: \n/login - login to your LifeGuild account \n/tasks - get today's tasks \n/todos - get todos \n/health - check your health count \n/coin - check your coin amount";
      bot.sendMessage(currentChatId, response)
    }
  });
  bot.sendMessage(currentChatId, "Sorry, you're logged out. Please login in the format '/login username_password'.")
});

//login
bot.onText(/\/login (.+)/, async function (msg, match) {
  //do something
  // getRequest(hostName, serverPort, pathParam);
  console.log("match ======================");
  console.log(match);
  console.log("msg =======================");
  console.log(msg);
  const chatId = msg.chat.id;
  console.log("captured text ======================");
  bot.sendMessage(chatId, "logging in...");
  const capturedText = match[1];
  const loginDetail = capturedText.split(" ");
  // console.log(loginDetail[0], loginDetail[1])

  const payload =
  {
    "email": loginDetail[0],
    "password": loginDetail[1],
    "tchatid": chatId
  }
  console.log("posting to backend ======================");
  let response = await fetch("http://localhost:8080/api/auth/login", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(payload)
  });
  let confirmation = await response.json();

  console.log("backend response ======================");
  // if (200 != response.status) {
  //   bot.sendMessage(chatId, "Login failed");
  // }
  console.log("return response to user ======================");
  console.log(confirmation, "hello")
  let pet = confirmation.pet.image.split(".");
  pet = pet[0];
  let character = confirmation.character.imageUrl.split(".");
  character = character[0];
  const details = "Character details: \n"
    + "Health: " + confirmation.character.health + "\n"
    + "Coins: " + confirmation.character.coinwallet + "\n"
    + "Character: " + character + "\n"
    + "Pet: " + pet;
  bot.sendMessage(msg.chat.id, `Thanks for waiting. Welcome, ${username}`);
  bot.sendMessage(chatId, "Your current Character: ");
  bot.sendMessage(chatId, details);
});

bot.onText(/\/tasks/), function (msg, match) {

  bot.sendMessage(msg.chat.id, "hold on, getting your tasks...");


}



// Listen for any kind of message. There are different kinds of
// messages.
// bot.on('message', (msg) => {
//   const chatId = msg.chat.id;

//   const res = `Hi ${msg.from.first_name}! I received your message: ${msg.text}`;

//   // send a message to the chat acknowledging receipt of their message
//   // bot.sendMessage(chatId, 'Received your message');
//   bot.sendMessage(msg.chat.id, res);
//   console.log();

// });

