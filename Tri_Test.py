import os
import hashlib

def get_file_hash(filename):
    """Function to return the SHA-256 hash of the specified file."""
    sha256 = hashlib.sha256()
    try:
        with open(filename, 'rb') as f:
            for block in iter(lambda: f.read(4096), b""):
                sha256.update(block)
        return sha256.hexdigest()
    except FileNotFoundError:
        print(f"File {filename} not found.")
        return None

def store_user_info(username, password):
    """Function to store user info (insecurely) in a file."""
    # Insecure storage of user credentials - This is an intentional vulnerability
    with open("user_info.txt", "a") as f:
        f.write(f"{username}:{password}\n")

def main():
    # Insecure input handling - This is an intentional vulnerability
    username = input("Enter your username: ")
    password = input("Enter your password: ")
    store_user_info(username, password)

    # Intentional insecure file handling
    filename = input("Enter the filename to hash: ")
    file_hash = get_file_hash(filename)
    if file_hash:
        print(f"The SHA-256 hash of the file {filename} is: {file_hash}")

if __name__ == "__main__":
    main()
