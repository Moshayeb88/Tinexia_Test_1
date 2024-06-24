const fs = require('fs');
const crypto = require('crypto');

function getFileHash(filename) {
    try {
        const data = fs.readFileSync(filename);
        const hash = crypto.createHash('sha256');
        hash.update(data);
        return hash.digest('hex');
    } catch (err) {
        console.error(`File ${filename} not found.`);
        return null;
    }
}

function storeUserInfo(username, password) {
    // Insecure storage of user credentials - This is an intentional vulnerability
    fs.appendFileSync('user_info.txt', `${username}:${password}\n`);
}

function main() {
    // Insecure input handling - This is an intentional vulnerability
    const readline = require('readline').createInterface({
        input: process.stdin,
        output: process.stdout
    });

    readline.question('Enter your username: ', (username) => {
        readline.question('Enter your password: ', (password) => {
            storeUserInfo(username, password);

            readline.question('Enter the filename to hash: ', (filename) => {
                const fileHash = getFileHash(filename);
                if (fileHash) {
                    console.log(`The SHA-256 hash of the file ${filename} is: ${fileHash}`);
                }
                readline.close();
            });
        });
    });
}

main();
