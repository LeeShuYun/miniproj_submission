const https = require('node:https');
export function get(hostName, serverPort, pathParam) {
    const options = {
        hostname: hostName,
        port: serverPort,
        path: pathParam,
        method: 'GET',
    };

    const req = https.request(options, (res) => {
        console.log('statusCode:', res.statusCode);
        console.log('headers:', res.headers);

        res.on('data', (d) => {
            process.stdout.write(d); //wow
        });
    });

    req.on('error', (e) => {
        console.error(e);
    });
    req.end();
}
export function GET2(url) {
    const httpsRequest = https.request(url, (response) => {
        let data = '';
        response.on('data', (chunk) => {
            data = data + chunk.toString();
        });

        response.on('end', () => {
            const body = JSON.parse(data);
            console.log(body);
            return body;
        });
    })

    httpsRequest.on('error', (error) => {
        console.log('An error', error);
    });

    httpsRequest.end()
}

export function post(url, body) {
    app.post(route, function (request, response) {

    })
}